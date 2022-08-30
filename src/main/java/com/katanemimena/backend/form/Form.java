package com.katanemimena.backend.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Table(name="form")
@Entity
@NoArgsConstructor
public class Form {

    @Id
    @SequenceGenerator(
            name="form_sequence",
            sequenceName="form_sequence",
            allocationSize=1
    )
    @GeneratedValue(
            strategy= GenerationType.SEQUENCE,
            generator= "form_sequence"
    )

    private Long id;
    private String fullname;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate DOB;
    private String email;
    private String phoneNumber;
    private String militaryNumber;
    private String idNumber;
    private String deferralId;
    private LocalDateTime timeSubmitted;

    @Transient
    //It is used only to receive file from front-end, not stored in DB
    private MultipartFile document;
    @Lob
    private byte[] data;
    public String fileName;
    public String fileType;

    private String comments="";
    private boolean validated=false;
    private boolean approved=false;
    //0- 6 months   1- 1 year   2- 2 years
    private int months;


    public Form(Long id,String fullname, String email){
        this.id = id;
        this.fullname=fullname;
        this.email=email;
    }

    public Form(String fullname, LocalDate DOB, String email, String phoneNumber,
                String militaryNumber, String idNumber, String deferralId, MultipartFile document){
        this.fullname = fullname;
        this.DOB = DOB;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.militaryNumber = militaryNumber;
        this.idNumber = idNumber;
        this.deferralId = deferralId;
        this.document=document;
    }



}
