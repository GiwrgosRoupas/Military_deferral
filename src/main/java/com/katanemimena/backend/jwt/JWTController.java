package com.katanemimena.backend.jwt;


import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {

    @GetMapping("/refreshToken")
    public String refreshToken(String refreshToken) {
        Verifier verifier = HMACVerifier.newVerifier("secretKey");
        JWT decodedJWT= JWT.getDecoder().decode(refreshToken, verifier);
        if(decodedJWT!=null){
            return "Should give new";
        }else {
            return "Expired refresh token";
        }

    }
}
