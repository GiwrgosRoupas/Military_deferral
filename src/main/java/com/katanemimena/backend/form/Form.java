package com.katanemimena.backend.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
@Table
@Entity
@NoArgsConstructor
public class Form {
    @Getter @Setter
    private String fullname;
    @Getter @Setter @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate DOB;
    @Getter @Setter @Id
    private String email;
    @Getter @Setter
    private String phoneNumber;
    @Getter @Setter
    private String militaryNumber;
    @Getter @Setter
    private String idNumber;
    @Getter @Setter
    private String deferralId;

    @Getter @Setter @Transient
    //It is used only to receive file from front-end, not stored in DB
    private MultipartFile document;

    @Lob
    @Getter @Setter
    private byte[] data;
    @Getter @Setter
    public String fileName;
    @Getter @Setter
    public String fileType;


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
