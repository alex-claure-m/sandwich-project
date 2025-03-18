package com.example.sandwich.services.mail;

import com.example.sandwich.services.mail.models.EmailDetailsDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

//este eservicio es donde hace el uso de las clases como
@Service
public class EmailServiceImpl implements EmailService {
//no te olvides que siempre el autowired es para la inyeccion de dependencias
  // podemos no usar ese autowired, pero ahi tengo que crear a manopla la inyeccion
  // creando nueva instancia , en este caso un CONSTRUCTOR (si no esta instanciado)
	// (el autowired sirve para decirle a spring: che, esta atributo ya tiene una clase con su configuracion,
	// metodos y dependencias, si me anotas un autowired, te ahorro el hecho de instanciar a manopla la clase )
  // si no uso anotacion, mi otra forma de inteccion es por CONSTRUCTOR o en un metodo como aclare 3 lineas arriba al ppio
  // OTRO DATO: este atributo ya esta instanciado en MailSenderConfig.java , con el mismo nombre
  // asi que al estar con el @Bean, paso ese metodo/atributo en los otros lugares donde la invoque
  private final JavaMailSender javaMailSender;

  private final TemplateEngine templateEngine;

  @Value("${spring.mail.username}")
  private String sender;

// es el constructor por que no use AUTOWIRED (tambien podria haber usado Setter pero cada set es con la anotacion autowired)
  public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
    this.javaMailSender = javaMailSender;
    this.templateEngine = templateEngine;
  }


  @Override
  public void sendEmail(EmailDetailsDTO details) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
     // mimeMessageHelper.setFrom(sender); // desde quien
      mimeMessageHelper.setTo(details.getRecipient()); // para quien
     // mimeMessageHelper.setText(details.getMsgBody()); // texto plano
      mimeMessageHelper.setSubject(details.getSubject()); //motivo

      // *********** INICIO DEL FRONT CON THYMELEAF PARA ENVIO DEL MAIL ***********
      //para el front con thymeleaf -- si no requiero el front -> quedate con METODO DE TEXTOPLANO
      Context context = new Context();
      context.setVariable("mensaje",details.getMsgBody());
      //encapsula el contenido del html donde esta el textbox para el envio del email
			String contentHTML = templateEngine.process("email",context);

      mimeMessageHelper.setText(contentHTML,true);
      // *********** FIN DEL FRONT CON THYMELEAF PARA ENVIO DEL MAIL ***********

      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new MessagingException("Error while Sending Mail");
    }
  }

  /*
  @Override
  public String sendEmail() throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      mimeMessageHelper.setFrom(sender); // desde quien
      mimeMessageHelper.setTo("alexmclm@gmail.com"); // para quien
      mimeMessageHelper.setSubject("mensaje de texto enviado"); //motivo
      mimeMessageHelper.setText("mensaje de prueba a ver s illego"); // texto plano
      javaMailSender.send(mimeMessage);
      return "enviado";
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    } catch (MailException e) {
      throw new RuntimeException(e);
    }
  }
  */
}