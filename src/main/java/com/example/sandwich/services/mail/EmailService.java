package com.example.sandwich.services.mail;

import com.example.sandwich.services.mail.models.EmailDetailsDTO;
import jakarta.mail.MessagingException;

public interface EmailService {

	void sendEmail(EmailDetailsDTO details) throws MessagingException;


}
