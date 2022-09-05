package com.katanemimena.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fusionauth.jwt.JWTExpiredException;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);
        }else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                try{
                    Verifier verifier = HMACVerifier.newVerifier("secretKey");
                    String token =authorizationHeader.substring(7);
                    JWT decodedJWT= JWT.getDecoder().decode(token, verifier);

                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority( decodedJWT.uniqueId));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(decodedJWT.subject, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                    } catch (JWTExpiredException e) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    new ObjectMapper().writeValue(response.getOutputStream(),"Expired token!");

                    }catch (Exception e) {
                    //Wrong Authorization header= 401_UNAUTHORIZED
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    new ObjectMapper().writeValue(response.getOutputStream(),"Token error: "+e);
                    }
                }else {
                    //No authorization header= 403_FORBIDDEN
                    filterChain.doFilter(request, response);
                }
        }
    }
}
