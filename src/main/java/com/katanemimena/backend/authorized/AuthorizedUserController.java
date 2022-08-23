package com.katanemimena.backend.authorized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/authorizedUser")
public class AuthorizedUserController {

    private final AuthorizedUserService authorizedUserService;
    @Autowired
    public AuthorizedUserController(AuthorizedUserService authorizedUserService) {
        this.authorizedUserService = authorizedUserService;}

    @Autowired
    @GetMapping(path="/getAllAuthorizedUsers")
    public List<AuthorizedUser> getAllAuthorizedUsers(){
        return authorizedUserService.getAllAuthorizedUsers();
    }

}
