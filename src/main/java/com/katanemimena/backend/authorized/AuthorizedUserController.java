package com.katanemimena.backend.authorized;

import com.katanemimena.backend.jwt.JwtRequest;
import com.katanemimena.backend.jwt.JwtResponse;
import com.katanemimena.backend.jwt.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/authorizedUser")
public class AuthorizedUserController {

    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizedUserService authorizedUserService;


    @Autowired
    public AuthorizedUserController(AuthorizedUserService authorizedUserService) {
        this.authorizedUserService = authorizedUserService;}

    //Login page for admin
    @GetMapping("/login")
    public String login() {return "login";}

    @DeleteMapping(path="/deleteUser")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam("username") String username){
        System.out.println(username);
        return authorizedUserService.deleteUserByUsername(username)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping(path = "/addUser")
    public ResponseEntity<HttpStatus> addUser(AuthorizedUser user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return authorizedUserService.addUser(user) ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Autowired
    @GetMapping(path="/getAllAuthorizedUsers")
    public List<AuthorizedUser> getAllAuthorizedUsers(){
        return authorizedUserService.getAllAuthorizedUsers();
    }

    @PostMapping(path="/authentication", consumes= "application/json")
    public JwtResponse authentication(@RequestBody JwtRequest jwtRequest)  {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(), jwtRequest.getPassword()
                    )
            );
        }catch (BadCredentialsException e){
            return new JwtResponse("-1");
        }
        final UserDetails userDetails =authorizedUserService.loadUserByUsername(
                jwtRequest.getUsername());
        System.out.println(userDetails.getUsername());
        final String token= jwtUtility.generateToken(userDetails);
        return  new JwtResponse(token);

    }
}
