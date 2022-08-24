package com.katanemimena.backend.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
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
}
