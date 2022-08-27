package com.katanemimena.backend.authorized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public boolean deleteUserByUsername(String username){
        try{
            repository.deleteUserByUsername(username);
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean addUser(AuthorizedUser user){
        System.out.println(user.getRole());
        Optional<AuthorizedUser> userOptional= Optional.ofNullable(repository.findByUsername(user.getUsername()));
        if(userOptional.isPresent()){
            return false;
        }else {
            repository.save(user);
            return true;
        }
    }

    public List<AuthorizedUser> getAllAuthorizedUsers() {

        return repository.getAllAuthorizedUsers();
    }
}
