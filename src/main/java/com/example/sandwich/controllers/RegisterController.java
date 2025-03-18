package com.example.sandwich.controllers;


import com.example.sandwich.domain.clients.Persona;
import com.example.sandwich.dto.RegistroDTO;
import com.example.sandwich.domain.TokenCreate;
import com.example.sandwich.domain.clients.Usuario;
import com.example.sandwich.repository.PersonaRepository;
import com.example.sandwich.repository.UsuarioRepository;
import com.example.sandwich.services.mail.EmailService;
import com.example.sandwich.services.mail.models.EmailDetailsDTO;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/register-with-mail")
public class RegisterController {

  // para inyectar las clases, antes de llegue a este controller
  // no es necesario que PersonaRepository sea @bean o que tenga esa anotacion BEAN
  @Autowired
  private PersonaRepository personaAlta;

  @Autowired
  private UsuarioRepository usuarioAlta;
  @Autowired
  private TokenCreate token;

  @Autowired
  private EmailService servicioMail;

  @Autowired
  //para la validacion y checkeo del codigo
  private TokenServicio codigosVerificacion;
/*
//ANDA! EL TEMA ES QUE NECESITO UN DTO PARA GUARDAR PERSONA Y USER
  @PostMapping("/alta")
  public String darAltaPersona(@ModelAttribute Persona persona){
    String generacionToken = token.tokenGenerate();
    persona.setToken(generacionToken);
    persona.setEstaHabilitado(Boolean.FALSE);
    personaAlta.saveAndFlush(persona);
    //agarro el service de correo y le envio un mail
    EmailDetailsDTO emailDetails= new EmailDetailsDTO();
    emailDetails.setRecipient(persona.getEmail());
    emailDetails.setSubject("Verificacion Cuenta");
    emailDetails.setMsgBody("Tu token de verificacion es: " + generacionToken + " por favor ingrese este token en el casillero correspondiente para seguir");
    try{
      servicioMail.sendEmail(emailDetails);
    }catch(MessagingException e){
      e.printStackTrace();
    }
    return "register-verification"; // que retorne el nombre de la plantilla html!
  }
*/

  // funciona
  // modelAttribute basicamente me trae del html los datos de ingreso como un objeto total
  @PostMapping("/alta")
  public String darAltaPersona(@ModelAttribute RegistroDTO registro, Model model){
    String generacionToken = token.tokenGenerate();

    Persona persona = new Persona();
    persona.setNombre(registro.getName());
    persona.setApellido(registro.getApellido());
    persona.setTelefono(registro.getTelefono());
    persona.setEmail(registro.getEmail());
    persona.setEstaHabilitado(Boolean.FALSE);
    persona.setToken(generacionToken);
    //volvi servicio a hashToken para que este en el storage y se borre cada vez que se inicia el proyecto
    codigosVerificacion.guardarToken(generacionToken,persona.getEmail());

    Usuario usuario= new Usuario();
    usuario.setUsuario(registro.getUsername());

    persona.setUsuario(usuario);
    usuario.setPersona(persona);

    personaAlta.save(persona);
    usuarioAlta.save(usuario);
    //agarro el service de correo y le envio un mail
    EmailDetailsDTO emailDetails= new EmailDetailsDTO();
    emailDetails.setRecipient(persona.getEmail());
    emailDetails.setSubject("Verificacion Cuenta");
    emailDetails.setMsgBody("Tu token de verificacion es: " + generacionToken + " por favor ingrese este token en el casillero correspondiente para seguir");
    try{
      servicioMail.sendEmail(emailDetails);
    }catch(MessagingException e){
      e.printStackTrace();
    }
    return "register-verification"; // que retorne el nombre de la plantilla html!
  }


}
