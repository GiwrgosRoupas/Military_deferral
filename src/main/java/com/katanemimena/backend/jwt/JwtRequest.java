package com.katanemimena.backend.jwt;

import lombok.Data;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class JwtRequest {
    private String username;
    private String password;

    JwtRequest(){}
    JwtRequest(String username, String password){
        this.username = username;
        this.password = password;
    }
}
