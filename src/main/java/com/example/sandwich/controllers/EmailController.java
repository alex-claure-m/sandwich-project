package com.example.sandwich.controllers;

import com.example.sandwich.services.mail.EmailService;
import com.example.sandwich.services.mail.models.EmailDetailsDTO;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmailController {

  final EmailService emailservice;

  public EmailController(EmailService emailservice) {
    this.emailservice = emailservice;
  }

  @PostMapping("/test-email")
  public ResponseEntity<String> enviarCorreo(@RequestBody EmailDetailsDTO emaildto) throws MessagingException {
    emailservice.sendEmail(emaildto);
    return new ResponseEntity<>("Correo enviado exitosamente", HttpStatus.OK);
  }

  /*
  @PostMapping("/send-email")
  public ResponseEntity<String> enviarCorreo() throws MessagingException {
    emailservice.sendEmail();
    return new ResponseEntity<>("Correo enviado exitosamente", HttpStatus.OK);
  }

   */
}
