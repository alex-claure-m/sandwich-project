package com.example.sandwich.services.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

// es la configuracion de protocolos para conectarse con gmail
// + un bean, instanciacion,
@Configuration
@PropertySource("classpath:email.properties")
public class MailSenderConfig {

	@Value(value = "${spring.mail.username}")
  private String username;

  @Value(value="${spring.mail.password}")
  private String password;

  private Properties getMailProperties(){
	    Properties props = new Properties();
    	props.put("mail.smtp.host", "smtp.gmail.com");
    	props.put("mail.smtp.port", "587");
      props.put("mail.transport.protocol", "smtp");
   	 	props.put("mail.smtp.auth", "true");
    	props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    	props.put("mail.smtp.starttls.enable", "true");

      return props;
  }
//inicializo objetos y que sean @bean para que spring lo tome y pueda utilizarla en otras clases
  //ya instanciada (como por ejemplo en EmailServiceImpl.java)
  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
    javaMailSender.setJavaMailProperties(getMailProperties());
    javaMailSender.setUsername(username);
    javaMailSender.setPassword(password);

    return javaMailSender;

  }
  //instancio una impmentnacion para cargar los recursos externos
  // asi por ejemplo usar la anotacion classpath
  //tambien podria servir para cargar otros recursos como plantillas, o URL
  @Bean
  public ResourceLoader resourceLoader() {
    return new DefaultResourceLoader();
  }
}
