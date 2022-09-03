package com.katanemimena.backend.authorized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/admin")
public class AuthorizedUserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizedUserService authorizedUserService;
    @Autowired
    public AuthorizedUserController(AuthorizedUserService authorizedUserService) {
        this.authorizedUserService = authorizedUserService;}



    @PostMapping("/deleteUser")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam String username){
        System.out.println("username is"+username);
        return authorizedUserService.deleteUserByUsername(username)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/addUser")
    public ResponseEntity<HttpStatus> addUser(AuthorizedUser user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return authorizedUserService.addUser(user) ?
                new ResponseEntity<>(HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Autowired
    @GetMapping("/getAllAuthorizedUsers")
    public List<AuthorizedUser> getAllAuthorizedUsers(){
        return authorizedUserService.getAllAuthorizedUsers();
    }


}
