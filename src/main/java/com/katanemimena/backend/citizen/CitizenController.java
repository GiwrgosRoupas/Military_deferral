package com.katanemimena.backend.citizen;


import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/citizen")
public class CitizenController {
    private final CitizenService citizenService;
    private final DocumentRepository documentRepository;
    @Autowired
    public CitizenController(CitizenService citizenService, DocumentRepository documentRepository) {
        this.citizenService = citizenService;
        this.documentRepository = documentRepository;
    }


    @GetMapping("/getAll")
    public List<Citizen> getCitizen(){
        return citizenService.getCitizen();
    }

    @GetMapping("/getCitizenByEmail/email={email}")
    public Optional<Citizen> getCitizenByEmail(@PathVariable String email){
        return citizenService.getCitizenByEmail(email);}


    @PostMapping("/addCitizen")
    public void addCitizen( Form form) throws IOException {
        System.out.println(form.getEmail());
        Citizen citizen= new Citizen(form.getFullname(), form.getEmail(), form.getPhoneNumber(),
                form.getDOB(), form.getIdNumber(), form.getMilitaryNumber(), form.getDeferralId());
        citizenService.addNewCitizen(citizen);

        MultipartFile file = form.getDocument();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Document document= new Document(fileName, file.getContentType(), file.getBytes());
        documentRepository.save(document);

    }



}
