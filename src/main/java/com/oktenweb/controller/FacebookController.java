package com.oktenweb.controller;

import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Controller
public class FacebookController {


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/fb/login")
    public void login(HttpServletResponse response) throws
            IOException {
        FacebookConnectionFactory connectionFactory = new
                FacebookConnectionFactory("1560534824004318",
                "aa84a0c91a63bfac76bdaecf12d83ca4");
        OAuth2Parameters params = new OAuth2Parameters();
        params.setRedirectUri
                ("http://localhost:8080/fb/callback");
        params.setScope("public_profile");
        OAuth2Operations oauthOperations =
                connectionFactory.getOAuthOperations();
        String authorizeUrl =
                oauthOperations.buildAuthorizeUrl(params);
        response.sendRedirect(authorizeUrl);

        System.out.println(11111);
    }

    @RequestMapping("/fb/callback")
    public String callback(@RequestParam("code") String
                                   authorizationCode, HttpServletRequest request) {
        FacebookConnectionFactory connectionFactory = new
                FacebookConnectionFactory("1560534824004318",
                "aa84a0c91a63bfac76bdaecf12d83ca4");
        OAuth2Operations oauthOperations =
                connectionFactory.getOAuthOperations();
        AccessGrant accessGrant =
                oauthOperations.exchangeForAccess(authorizationCode,
                        "http://localhost:8080/fb/callback", null);
        String token = accessGrant.getAccessToken();
        request.getSession().setAttribute("facebookToken",
                token);
        System.out.println("22222");
        return "redirect:/fb";
    }

    @RequestMapping("/fb")
    public String fb(HttpServletRequest request) {

        System.out.println(3333);
//        String accessToken = (String)
//                request.getSession().getAttribute("facebookToken");
//        System.out.println(accessToken);
//        Facebook facebook = new FacebookTemplate(accessToken);
//        if(facebook.isAuthorized()) {
//            System.out.println(facebook.isAuthorized());
            return "fb";
//        }
//        else {
//            return "redirect:/fb/login";
//        }
    }
}
