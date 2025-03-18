package testeoMail;

import com.example.sandwich.SandwichApplication;
import com.example.sandwich.services.mail.EmailService;
//import com.example.sandwich.services.mail.MailSenderConfig;
import com.example.sandwich.services.mail.models.EmailDetailsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.MessagingException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = SandwichApplication.class)
public class mailSend {

  @Autowired
  private EmailService emailService;

  //@Autowired
  //private MailSenderConfig mailSenderConfig;


  @Test
  public void testSendSimpleMail() throws MessagingException, jakarta.mail.MessagingException {
    // Datos del correo a enviar
    EmailDetailsDTO details = new EmailDetailsDTO();
    details.setRecipient("alexmclm@gmail.com");
    details.setSubject("Prueba de correo");
    details.setMsgBody("Este es un mensaje de prueba enviado desde una prueba unitaria en Spring Boot.");

    // Llamada al servicio de envío de correo
   emailService.sendEmail(details);

    // Verificar el resultado esperado
    //assertEquals("Mail Sent Successfully...", result, "El correo no se envió correctamente.");
  }

}
