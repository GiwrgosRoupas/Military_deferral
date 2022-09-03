package com.katanemimena.backend.form;

import com.katanemimena.backend.web.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FormService {

    @Autowired
    private MailService mailService;
    private  final FormRepository formRepository;
    @Autowired
    public FormService( FormRepository formRepository) { this.formRepository = formRepository; }

    public void addForm(Form form) throws IOException {
        //Breaks file to fileName, fileType, data[] to be stored
        MultipartFile file=form.getDocument();

        form.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        form.setFileType(file.getContentType());
        form.setData(file.getBytes());
        form.setTimeSubmitted(LocalDateTime.now());
        formRepository.save(form);
    }

    public List<Form> getAllFormsSecretary() {return formRepository.getNotValidatedForms();
    }


    public List<Form> getAllFormsOfficer() {return formRepository.getValidatedForms();
    }

    public ResponseEntity<byte[]> getFileById(Long id) {
        Form form=formRepository.getFormById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(form.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename="+form.getFileName())
                .body(form.getData());
    }

    public boolean denyForm(Long id) {
        Form form= formRepository.getFormById(id);
        mailService.sendMail(form, false);

        try{
            formRepository.deleteById(id);
        }catch(IllegalArgumentException e){
            return false;
        }
        return true;
    }

    public boolean validateForm(Long id) {
        return formRepository.validateFormById(id)==1? true: false;
    }

    public boolean approveForm(Long id) {
        Form form= formRepository.getFormById(id);
        mailService.sendMail(form, true);
        return formRepository.approveFormById(id)==1? true: false;
    }

    public boolean setSecretaryComments(Long id,String comments) {
        return formRepository.setSecretaryComments(id,comments)==1? true : false;
    }

    public boolean setOfficerComments(Long id, String comments) {
        return formRepository.setOfficerComments(id,comments)==1? true : false;
    }
}
