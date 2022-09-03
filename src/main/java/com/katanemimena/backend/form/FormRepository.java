package com.katanemimena.backend.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {

    @Query("SELECT f FROM Form f WHERE f.id=?1")
    Form getFormById(Long id);
    @Query(value="SELECT f from Form f WHERE f.validated IS FALSE ORDER BY f.timeSubmitted")
    List<Form> getNotValidatedForms();
    @Query(value="SELECT f from Form f WHERE f.validated IS TRUE AND f.approved IS FALSE ORDER BY f.timeSubmitted")
    List<Form> getValidatedForms();

    @Query(value = "SELECT COUNT(u) FROM Form u WHERE u.id IS NOT NULL")
    int getNumberOfEntities();

    @Query(value="SELECT u FROM Form u WHERE u.validated IS FALSE")
    List<Form> getAllForms();

    @Modifying @Transactional
    @Query("UPDATE Form SET validated=TRUE WHERE id=?1")
    int validateFormById(Long id);

    @Modifying @Transactional
    @Query("UPDATE Form SET approved=TRUE WHERE id=?1")
    int approveFormById(Long id);

    @Modifying @Transactional
    @Query("UPDATE Form SET secretaryComments=?2 WHERE id=?1")
    int setSecretaryComments(Long id,String comments);

    @Modifying @Transactional
    @Query("UPDATE Form SET officerComments=?2 WHERE id=?1")
    int setOfficerComments(Long id,String comments);
}
