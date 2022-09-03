package com.katanemimena.backend.web;

import com.katanemimena.backend.form.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    //status= True- approved     False- denied
    public void sendMail (Form form, Boolean status){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(form.getEmail());
        message.setFrom("isaidnoreplyidiot@gmail.com");
        message.setSubject("Στρατολογία- Αίτηση αναβολής στράτευσης");
        if (status) {
            message.setText("Η αίτηση σας για αναβολή στράτευσης έγινε δεκτή για "
            +form.getMonths()
            +"\nΣχόλια γραμματείας:\n "
            +form.getSecretaryComments()
                            +"\nΣχόλια αξιωματικού: \n"
                    +form.getOfficerComments()
            );
        }else{
            message.setText("Η αίτηση σας για αναβολή στράτευσης δεν έγινε δεκτή! "
                    +"\nΣχόλια γραμματείας:\n "
                    +form.getSecretaryComments()
                    +"\nΣχόλια αξιωματικού: \n"
                    +form.getOfficerComments()
            );
        }
        mailSender.send(message);
    }
}
