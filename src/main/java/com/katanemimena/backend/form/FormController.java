package com.katanemimena.backend.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/form")
public class FormController {

    private final FormService formService;
    @Autowired
    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping("/addForm")
    @ResponseBody
    public ResponseEntity<HttpStatus> addForm(Form form) {
        try {
            formService.addForm(form);
        }catch (IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/secretary/getAllForms")
    public List<Form> getAllFormsSecretary(){return formService.getAllFormsSecretary();}

    @GetMapping("/officer/getAllForms")
    public List<Form> getAllFormsOfficer(){
        return formService.getAllFormsOfficer();
    }

    @GetMapping("getFileById{id}")
    public ResponseEntity<byte[]> getFileById(@RequestParam Long id){
        return formService.getFileById(id);
    }

    @PostMapping("/denyForm")
    public ResponseEntity<HttpStatus> deleteForm(@RequestParam Long id){
        return formService.denyForm(id)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/secretary/validateForm")
    public ResponseEntity<HttpStatus> validateForm(@RequestParam Long id){
        return formService.validateForm(id)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/officer/approveForm")
    public ResponseEntity<HttpStatus> approveForm(@RequestParam Long id, @RequestParam String months){
        System.out.println(months);
        return formService.approveForm(id, months)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/secretary/comments")
    public ResponseEntity<HttpStatus> setSecretaryComments(@RequestParam Long id, @RequestBody String comments) {
        return formService.setSecretaryComments(id,comments)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/officer/comments")
    public ResponseEntity<HttpStatus> setOfficerComments(@RequestParam Long id,@RequestBody String comments){
        return formService.setOfficerComments(id,comments)?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
