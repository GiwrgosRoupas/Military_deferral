package com.katanemimena.backend.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FormService {

    private  final FormRepository formRepository;
    @Autowired
    public FormService( FormRepository formRepository) { this.formRepository = formRepository; }

    public void addForm(Form form) throws IOException {
        //Breaks file to fileName, fileType, data[] to be stored
        MultipartFile file=form.getDocument();
        form.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        form.setFileType(file.getContentType());
        form.setData(file.getBytes());
        formRepository.save(form);

    }
}
