package com.katanemimena.backend.citizen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/citizen")
public class CitizenController {
    private final CitizenService citizenService;
    @Autowired
    public CitizenController(CitizenService citizenService) {
        this.citizenService = citizenService;
    }


    @GetMapping("/getAll")
    public List<Citizen> getCitizen(){
        return citizenService.getCitizen();
    }

    @GetMapping("/getCitizenByEmail/email={email}")
    public Optional<Citizen> getCitizenByEmail(@PathVariable String email){
        return citizenService.getCitizenByEmail(email);}


    @PostMapping("/addCitizen")
    public void addCitizen(Citizen citizen){
        citizenService.addNewCitizen(citizen);
    }

    @PostMapping("/addFiles")
    public ResponseEntity<?> uploadFiles(@RequestParam("file")MultipartFile file){
        String fileName = file.getOriginalFilename();
        try{
            file.transferTo(new File("/home/giwrgos/Documents/"+fileName));
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
        return ResponseEntity.ok("File uploaded");

    }
}
