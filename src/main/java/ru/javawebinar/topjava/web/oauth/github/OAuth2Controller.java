package ru.javawebinar.topjava.web.oauth.github;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.to.GithubUserDetails;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.oauth.OAuth2Source;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
@RequestMapping("/oauth2/github")
public class OAuth2Controller {
    @Autowired
    private RestTemplate template;

    @Autowired
    private UserDetailsService service;

    @Autowired
    private OAuth2Source source;

    @GetMapping("/authorize")
    public String authorize() {
        return "redirect:" + source.getAuthorizeUrl()
                + "?client_id=" + source.getClientId()
                + "&client_secret=" + source.getClientSecret()
//                + "&redirect_uri=" + source.getRedirectUri()
                + "&state=" + source.getCode();
    }

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr, HttpServletRequest request) {
        if (state.equals("topjava_csrf_token_auth")) {
            GithubUserDetails githubUserDetails = getGithubUserDetails(getAccessToken(code));
            try {
                UserDetails userDetails = service.loadUserByUsername(githubUserDetails.getEmail());
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                );
                // Create a new session and add the security context.
                HttpSession session = request.getSession(true);
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
                return new ModelAndView("redirect:/meals");
            } catch (UsernameNotFoundException e) {

                attr.addFlashAttribute("userTo", new UserTo(githubUserDetails.getName(), githubUserDetails.getEmail()));
                return new ModelAndView("redirect:/register");
            }
        }

        return new ModelAndView("login");
    }

    private GithubUserDetails getGithubUserDetails(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(source.getLoginUrl()).queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> responseEntity = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        JsonNode user = responseEntity.getBody();
        String login = user.get("login").asText();
        String name = user.get("name").asText();
        if (name.equals("null")) {
            name = login;
        }
        String email = user.get("email").asText();
        if (email.equals("null")) {
            throw new NotFoundException("No email found in Github account");
        }
        return new GithubUserDetails(login, name, email);
    }

    private String getAccessToken(String code) {
        UriComponentsBuilder builder = fromHttpUrl(source.getAccessTokenUrl())
                .queryParam("client_id", source.getClientId())
                .queryParam("client_secret", source.getClientSecret())
                .queryParam("code", code)
                .queryParam("redirect_uri", source.getRedirectUri());
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        return tokenEntity.getBody().get("access_token").asText();
    }
}
