package com.katanemimena.backend.citizen;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class Form {
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
    @Getter @Setter
    private MultipartFile document;

    public Form(String fullname, LocalDate DOB, String email, String phoneNumber,
                String militaryNumber, String idNumber, String deferralId, MultipartFile document) {
        this.fullname = fullname;
        this.DOB = DOB;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.militaryNumber = militaryNumber;
        this.idNumber = idNumber;
        this.deferralId = deferralId;
        this.document = document;
    }


}
