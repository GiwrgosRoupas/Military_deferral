package com.katanemimena.backend.authorized;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table
@NoArgsConstructor
@Getter @Setter
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

    private Long id;

    private String username;
    private String password;
    private String role;

    private String dateCreated;
    private String lastLogin;

    public AuthorizedUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public AuthorizedUser(String username, String password, String role, LocalDateTime dateCreated) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.dateCreated = dateCreated.toString();
    }

}
