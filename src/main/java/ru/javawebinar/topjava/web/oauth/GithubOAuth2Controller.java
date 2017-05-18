package ru.javawebinar.topjava.web.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
@RequestMapping("/oauth2/github")
public class GithubOAuth2Controller extends AbstractOAuth2Controller {

    @Autowired
    @Qualifier("githubOAuth2Provider")
    private OAuth2Provider provider;

    @GetMapping("/authorize")
    public String authorize() {
        return "redirect:" + provider.getAuthorizeUrl()
                + "?client_id=" + provider.getClientId()
                + "&redirect_uri=" + provider.getRedirectUri()
                + "&state=" + provider.getCode();
    }

    @Override
    protected UserTo getUserDetails(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(provider.getUserInfoUrl()).queryParam("access_token", accessToken);
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
        return new UserTo(name, email);
    }

    protected String getAccessToken(String code) {
        return super.getAccessTokenFromOAuth2Provider(code, provider);
    }
}
