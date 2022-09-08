package com.katanemimena.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katanemimena.backend.authorized.AuthorizedUserDetails;
import com.katanemimena.backend.authorized.AuthorizedUserRepository;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.w3c.dom.ls.LSOutput;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final AuthorizedUserRepository authorizedUserRepository;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, AuthorizedUserRepository authorizedUserRepository) {
        this.authenticationManager = authenticationManager;
        this.authorizedUserRepository = authorizedUserRepository;
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getHeader("Username");
        String password = request.getHeader("Password");

        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        AuthorizedUserDetails user =(AuthorizedUserDetails) authResult.getPrincipal();
        authorizedUserRepository.setLastLogin(user.getUsername(), LocalDateTime.now().withNano(0).toString());
        Signer signer= HMACSigner.newSHA256Signer("secretKey") ;

        JWT accessToken = new JWT().setIssuer(request.getRequestURI())
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(10))
                .setSubject(user.getUsername())
                .setUniqueId(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().get(0));


        JWT refreshToken = new JWT().setIssuer(request.getRequestURI())
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(30))
                .setSubject(user.getUsername());

        String encodedAccessToken = JWT.getEncoder().encode(accessToken, signer);
        String encodedRefreshToken = JWT.getEncoder().encode(refreshToken, signer);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken",encodedAccessToken);
        tokens.put("refreshToken",encodedRefreshToken);
        tokens.put("role", user.getAuthorities().toString().replaceAll("\\]","").replaceAll("\\[",""));
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
