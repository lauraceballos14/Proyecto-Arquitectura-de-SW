package com.enviodecorreo.email.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enviodecorreo.email.Entity.Email;
import com.enviodecorreo.email.services.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    public EmailService emailService;
    //ERA PRIVATE SE PONE PUBLIC COMO EN EL VIDEO

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Email email) {
        try{
            emailService.sendEmail(email);
            return ResponseEntity.ok("Correo enviado correctamente");
        }
        catch (Exception e){
            return ResponseEntity.status(500).body("error al enviar el correo: "+ e.getMessage());
        }
    }
}
