package com.katanemimena.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katanemimena.backend.authorized.AuthorizedUserDetails;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username, password);
        System.out.println(authenticationToken);
        System.out.println(authenticationManager.authenticate(authenticationToken));
        System.out.println(request.getHeader("Authorization"));
        return authenticationManager.authenticate(authenticationToken);
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        AuthorizedUserDetails user =(AuthorizedUserDetails) authResult.getPrincipal();
        Signer signer= HMACSigner.newSHA256Signer("secretKey") ;
//        String accessToken= JWT.create()
//                        .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() +20*60*100000000))
//                .withIssuer(request.getRequestURI())
//                .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
        JWT accessToken = new JWT().setIssuer(request.getRequestURI())
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60))
                .setSubject(user.getUsername())
                .addClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));



        io.fusionauth.jwt.domain.JWT refreshToken = new JWT().setIssuer(request.getRequestURI())
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60))
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
        System.out.println("ORIGINAL"+accessToken.toString());
        System.out.println("\nENCODED"+encodedAccessToken);


    }
}
