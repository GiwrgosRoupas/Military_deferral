package com.katanemimena.backend.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.katanemimena.backend.BackEndApplication.clientUrl;

@Configuration
public class LoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String target=determineTargetUrl(authentication);
        if(response.isCommitted()) return;

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        redirectStrategy.sendRedirect(request,response,
                clientUrl+target);
    }

    protected String determineTargetUrl(Authentication authentication){
        String role=authentication.getAuthorities().toString();
        String url="";
        switch(role){
            case "[ROLE_ADMIN]"-> url="/admin.html";
            case "[ROLE_OFFICER]"-> url="/officer.html";
            case "[ROLE_SECRETARY]"-> url="/secretary.html";
        }
        return url;
    }
}
