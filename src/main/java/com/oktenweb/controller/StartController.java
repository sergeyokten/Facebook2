package com.oktenweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class StartController {

    @Autowired
    private FacebookConnectionFactory facebookConnectionFactory;

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    private String redirect_uri = "http://localhost:8080/fb/callback";
    private String scope = "public_profile,user_friends,user_about_me,publish_stream,user_actions.video,manage_pages,publish_pages,publish_actions";


    @GetMapping("/")
    public String home(HttpServletResponse response) throws IOException {
        return "index";
    }


    @GetMapping("/fb/login")
    public void signIn(HttpServletResponse response) throws IOException {
        OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
        oAuth2Parameters.setRedirectUri(redirect_uri);
        oAuth2Parameters.setScope(scope);


        OAuth2Operations oAuth2Operations = facebookConnectionFactory.getOAuthOperations();
        String authorizeUrl = oAuth2Operations.buildAuthorizeUrl(oAuth2Parameters);


        response.sendRedirect(authorizeUrl);
        System.out.println("authorizeUrl done redirect");


    }

    @GetMapping("/fb/callback")
    public String good(@RequestParam("code") String code, HttpServletRequest request) {
        System.out.println(code);

        OAuth2Operations oAuth2Operations = facebookConnectionFactory.getOAuthOperations();

        AccessGrant accessGrant = oAuth2Operations.exchangeForAccess(code,
                redirect_uri,
                null);
        String accessToken = accessGrant.getAccessToken();
        System.out.println("accessGrant.getAccessToken() - " + accessToken);
        request.getSession().setAttribute("accessToken", accessToken);

        return "redirect:/fb";
    }

    @GetMapping("/fb")
    public String suc(HttpServletRequest request) {
        String accessToken = (String) request.getSession().getAttribute("accessToken");
        System.out.println("accessToken : " + accessToken);
        Facebook facebook = new FacebookTemplate(accessToken);
        boolean facebookAuthorized = facebook.isAuthorized();
        System.out.println("facebookAuthorized:" + facebookAuthorized);

        User user = facebook.fetchObject("me", User.class, "first_name", "last_name");
        System.out.println(user.getFirstName());
//        facebook.feedOperations().post(new PostData("me").message("oktenweb2"));
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.set("link", "link.getLink()");
        map.set("name", "link.getName()");
        map.set("caption", "link.getCaption()");
        map.set("description", "link.getDescription()");
        map.set("message", "message");

        facebook.publish("me",connectionFactoryLocator.getConnectionFactory(Facebook.class).getProviderId(),map);

        System.out.println("done all!");

        return "fb";
    }

}
