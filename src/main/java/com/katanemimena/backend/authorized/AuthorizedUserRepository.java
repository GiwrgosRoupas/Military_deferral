package com.katanemimena.backend.authorized;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizedUserRepository extends JpaRepository<AuthorizedUser, Long> {


    @Query("SELECT u FROM AuthorizedUser u WHERE u.username =?1")
    AuthorizedUser findByUsername(String username);

    @Query(value="SELECT u FROM AuthorizedUser u WHERE u.id IS NOT NULL")
    List<AuthorizedUser> getAllAuthorizedUsers();

//    @Modifying
    @Query(value = "DELETE FROM AuthorizedUser u WHERE u.username=:username")
    void deleteUserByUsername(String username);

}
