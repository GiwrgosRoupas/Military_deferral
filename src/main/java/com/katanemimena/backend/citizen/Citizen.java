package com.katanemimena.backend.citizen;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


@Table
@Entity
@NoArgsConstructor
public class Citizen {

    @Id
    @SequenceGenerator(
            name="citizen_sequence",
            sequenceName="citizen_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE,
            generator= "citizen_sequence"
    )
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String fullname;
    @Getter @Setter @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate DOB;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String phoneNumber;
    @Getter @Setter
    private String militaryNumber;
    @Getter @Setter
    private String idNumber;

    @Getter @Setter
    private String deferralId;


    public Citizen(String fullname,
                   String email,
                   String phoneNumber,
                   LocalDate DOB,
                   String idNumber,
                   String militaryNumber,
                   String deferralId
    ){
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.DOB = DOB;
        this.idNumber = idNumber;
        this.militaryNumber = militaryNumber;
        this.deferralId=deferralId;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "fullname='" + fullname + '\'' +
                ", DOB=" + DOB +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", militaryNumber='" + militaryNumber + '\'' +
                ", idNumber='" + idNumber + '\'' +
                '}';
    }
}

