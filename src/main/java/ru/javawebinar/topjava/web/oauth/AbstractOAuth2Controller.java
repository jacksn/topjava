package ru.javawebinar.topjava.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.to.UserTo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

public abstract class AbstractOAuth2Controller {

    @Autowired
    protected RestTemplate template;

    @Autowired
    private UserDetailsService service;

    @RequestMapping("/callback")
    public ModelAndView authenticate(@RequestParam String code, @RequestParam String state, RedirectAttributes attr, HttpServletRequest request) {
        if (state.equals("topjava_csrf_token_auth")) {
            String accessToken = getAccessToken(code);
            UserTo user = getUserDetails(accessToken);
            try {
                UserDetails userDetails = service.loadUserByUsername(user.getEmail());
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
                );
                // Create a new session and add the security context.
                HttpSession session = request.getSession(true);
                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
                return new ModelAndView("redirect:/meals");
            } catch (UsernameNotFoundException e) {

                attr.addFlashAttribute("userTo", user);
                return new ModelAndView("redirect:/register");
            }
        }
        return new ModelAndView("login");
    }

    protected abstract UserTo getUserDetails(String accessToken);

    protected abstract String getAccessToken(String code);

    protected String getAccessTokenFromOAuth2Source(String code, OAuth2Provider source) {
        UriComponentsBuilder builder = fromHttpUrl(source.getAccessTokenUrl())
                .queryParam("client_id", source.getClientId())
                .queryParam("client_secret", source.getClientSecret())
                .queryParam("code", code)
                .queryParam("redirect_uri", source.getRedirectUri());
        ResponseEntity<JsonNode> tokenEntity = template.postForEntity(builder.build().encode().toUri(), null, JsonNode.class);
        return tokenEntity.getBody().get("access_token").asText();
    }
}
