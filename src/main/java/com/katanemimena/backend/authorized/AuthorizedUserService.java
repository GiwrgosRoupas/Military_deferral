package com.katanemimena.backend.authorized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizedUserService implements UserDetailsService {
    
    @Autowired
    private AuthorizedUserRepository repository;

    @Override
    public AuthorizedUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthorizedUser user= repository.findByUsername(username);
        if (user==null) { throw new UsernameNotFoundException("User not found!");}

        return new AuthorizedUserDetails(user);
    }

    public List<AuthorizedUser> getAllAuthorizedUsers() {

        return repository.getAllAuthorizedUsers();
    }
}
