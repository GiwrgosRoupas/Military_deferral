package com.katanemimena.backend.citizen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;

    @Autowired
    public CitizenService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    public List<Citizen> getCitizen(){
        return citizenRepository.findAll();
    }

    public Optional<Citizen> getCitizenByEmail(String email){
        return citizenRepository.findCitizenByEmail(email);
    }


    public void addNewCitizen(Citizen citizen){
        citizenRepository.save(citizen);
    }

}
