package com.katanemimena.backend.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("http://localhost:8080")
public class WebpagesController {



    @GetMapping("/login")

    public String login() {return "login";}

    @GetMapping("/admin")
    public String admin() {return "admin"; }

    @GetMapping("/citizen")
    public String citizen(){return "citizen";}


    @GetMapping("/officer")
    public String officer(){return "officer";}

    @GetMapping("/secretary")
    public String secretary(){return "secretary";}


}
