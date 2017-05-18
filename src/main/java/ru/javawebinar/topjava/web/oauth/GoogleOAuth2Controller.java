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

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Controller
@RequestMapping("/oauth2/google")
public class GoogleOAuth2Controller extends AbstractOAuth2Controller {

    @Autowired
    @Qualifier("googleOAuth2Provider")
    private OAuth2Provider provider;

    @GetMapping("/authorize")
    public String authorize() {
        return "redirect:" + provider.getAuthorizeUrl()
                + "?client_id=" + provider.getClientId()
                + "&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile"
                + "&response_type=code"
                + "&redirect_uri=" + provider.getRedirectUri()
                + "&state=" + provider.getCode();
    }

    @Override
    protected UserTo getUserDetails(String accessToken) {
        UriComponentsBuilder builder = fromHttpUrl(provider.getUserInfoUrl())
                .queryParam("access_token", accessToken);
        ResponseEntity<JsonNode> responseEntity = template.getForEntity(builder.build().encode().toUri(), JsonNode.class);
        JsonNode user = responseEntity.getBody();
        String name = user.get("name").asText();
        String email = user.get("email").asText();
        return new UserTo(name, email);
    }

    protected String getAccessToken(String code) {
        return super.getAccessTokenFromOAuth2Provider(code, provider);
    }
}
