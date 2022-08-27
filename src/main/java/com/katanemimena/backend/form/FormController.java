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

    @CrossOrigin(origins = "http://localhost:63342")
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

    @GetMapping("getAllFormsSecretary")
    public List<Form> getAllFormsSecretary(){return formService.getAllFormsSecretary();}

    @GetMapping("getAllFormsOfficer")
    public List<Form> getAllFormsOfficer(){
        return formService.getAllFormsOfficer();
    }

    @GetMapping("getFileById{id}")
    public ResponseEntity<byte[]> getFileById(@RequestParam Long id){
        return formService.getFileById(id);
//        Form form= formsList.get(1);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(form.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION,
//                        "form; filename=\""+form.getFileName())
//                .body(new ByteArrayResource(form.getData()));
    }
}
