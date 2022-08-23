package com.katanemimena.backend.citizen;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    @Query("SELECT c FROM Citizen c WHERE c.idNumber =?1")
    Optional<Citizen> findCitizenByIdNumber(String idNumber);

    @Query("SELECT c FROM Citizen c WHERE c.email =?1")
    Optional<Citizen> findCitizenByEmail(String email);
}
