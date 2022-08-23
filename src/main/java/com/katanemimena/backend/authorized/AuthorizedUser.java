package com.katanemimena.backend.authorized;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table
@NoArgsConstructor
public class AuthorizedUser {

    @Id
    @SequenceGenerator(
            name="user_sequence",
            sequenceName="user_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator= "user_sequence"
    )
    @Getter
    @Setter
    private Long id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String role;


    public AuthorizedUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
