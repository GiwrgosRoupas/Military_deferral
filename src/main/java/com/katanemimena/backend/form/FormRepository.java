package com.katanemimena.backend.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {

    @Query("SELECT f FROM Form f WHERE f.id=?1")
    Form getFormById(Long id);
    @Query(value="SELECT f from Form f WHERE f.validated IS FALSE ORDER BY f.timeSubmitted")
    List<Form> getNotValidatedForms();
    @Query(value="SELECT f from Form f WHERE f.validated IS TRUE ORDER BY f.timeSubmitted")
    List<Form> getValidatedForms();

    @Query(value = "SELECT COUNT(u) FROM Form u WHERE u.id IS NOT NULL")
    int getNumberOfEntities();

    @Query(value="SELECT u FROM Form u WHERE u.validated IS FALSE")
    List<Form> getAllForms();
}
