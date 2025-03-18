package com.example.sandwich.controllers;

import com.example.sandwich.domain.clients.Persona;
import com.example.sandwich.domain.TokenCreate;
import com.example.sandwich.repository.PersonaRepository;
import com.example.sandwich.services.mail.EmailService;
import com.example.sandwich.services.mail.models.EmailDetailsDTO;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/register-verification")
public class RegisterValidationController {

  @Autowired
  private PersonaRepository personaRepository;

  // para esta instancia, que utilizo el token, es por que, al ser acumulativo en mi linea de proceso
  // paso por creacion del servicio - se utilizo para guardar el token y el mail en RegistroController-
  // ahora lo llamo de neuvo pero con  esto precargado y lo reutilizo en este otro controller
  // es nivel memoria el hashmap. PERO, aca es la magia donde se tiene que llegar
  @Autowired
  private TokenServicio servicioToken;

  @Autowired
  private TokenCreate token;

  @Autowired
  private EmailService servicioMail;

  //cuando verifico o checkeo el toquen que ingresa por pantalla
  // recorda que @ModelAttribute es para cuando queres que desde el HTML te regrese todos los datos como OBJETO
  // lo mejor que puedo hacer es un parametro email para luego buscarlo en la bbdd de persona
  // * tip: si tenes pensado devolver mensajes al html como por ejemplo : codigo veficiado en model.attributte("mensaje","xx")
  // -> tengo que modificar el register-verification.html por que no tiene un campo text para agregar ese mensaje
  // -> para esa ocacion puedo -> MODIFICAR EL HTML para acoplar esto. o que se encargue el js
  // -> y si se encarga JS entonces cambia su tipo de retorno
  // -> si estaba en public string validarToken. es por que penso para la primera situacion y su return era return "nombrePlantilla (html)"
  // -> ahora como se encarga JS y requiero mandarle mensaje , su tipo de returno es
  @PostMapping("/validation-check")
  @ResponseBody
  public ResponseEntity<Map<String,Object>> validarToken(@RequestBody Map<String,String> rb) throws IOException{
    //es un hashmap para asocair 2 valores Map<String,String>  , por si quiero asociar un mail, pero como mejor no lo hago
    // y en vez de crear un dto solo para estos valorse, prefiero un hash, podria hacer sido solo un string

    // el argumento de get, es el mismo que esta en el js que quiero traer, el codigo de verificacion
    String tokenGenerado = rb.get("verificationCode");

    // to search token asociado al la persona, suponiendo que el token no se repite
		Persona persona = personaRepository.findByToken(tokenGenerado);

    Map<String, Object> response = new HashMap<>();
    if (persona == null) {
      response.put("success", false); // cosas que le pasara al js
      response.put("message", "token invalido");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    // Verificar si el usuario está bloqueado
    if (persona.getUltimoIntentoFallido() != null && persona.getUltimoIntentoFallido().isAfter(LocalDateTime.now())) {
      response.put("success", false);
      response.put("message", "Demasiados intentos. Intente en 30 minutos.");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    // Verificar token correcto y validarlo con el usuario
    if (persona.getToken().equals(tokenGenerado)) {
      persona.setEstaHabilitado(true);
      persona.setIntentos(3);  // Reiniciar intentos si es correcto
      persona.setUltimoIntentoFallido(null);
      personaRepository.save(persona);
      System.out.println("pase por la linea compara el toquen con equals ");
      response.put("success", true); // cosas que le pasara al js
      response.put("message", "codigo correcto, redirigiendo...");
      response.put("usuario", persona.getUsuario().getUsuario());
      System.out.println("pase por la linea que hace el put luego del equals");

      return ResponseEntity.ok(response);

    } else {
      // Disminuir intentos -- cuando le erra
      persona.setIntentos(persona.getIntentos() - 1);
      response.put("success", false);
      response.put("message", "Código incorrecto. Intentos restantes: " + persona.getIntentos());

      if (persona.getIntentos() <= 0) {
        persona.setUltimoIntentoFallido(LocalDateTime.now().plusMinutes(30)); // Bloqueo 30 min
        response.put("success", false);
        response.put("message", "Demasiados intentos. Intente en 30 minutos.");
      } else {
        response.put("success", false);
        response.put("message", "Código incorrecto. Intentos restantes: " + persona.getIntentos());
      }
      personaRepository.save(persona);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    // verificar luego como hacer tomar el mail!
    // debido a que el token que toma por findybytoken, solo toma el token y de ahi a la PERSONA
      // pero si el token es incorrecto, entonces la persona == null
    // y si toma null -> no puede analizar por las demas condiciones!
  }

  @PostMapping("/resend-code")
  @ResponseBody
  public ResponseEntity<Map<String,Object>> reenvioCodigo(@RequestBody Map<String,String> rb) throws MessagingException {
    String email = rb.get("email");

    Persona persona = personaRepository.findByEmail(email);

    Map<String, Object> response = new HashMap<>();

    if (persona == null) {
      response.put("success", false);
      response.put("message", "Email no encontrado.");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    else if(!persona.getEmail().equals(email)){
      response.put("success", false);
      response.put("message", "No es el mismo correo registrado, ingrese de nuevo.");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    else{
    String nuevoToken = token.tokenGenerate();

    // Guardar el nuevo token en la base de datos
    persona.setToken(nuevoToken);
    persona.setEstaHabilitado(false); // El usuario no estará habilitado hasta que verifique el nuevo token
    persona.setIntentos(3);  // Reiniciar los intentos
    persona.setUltimoIntentoFallido(null); // Limpiar el bloqueo
    personaRepository.save(persona);

    EmailDetailsDTO emailDetails= new EmailDetailsDTO();
    emailDetails.setRecipient(persona.getEmail());
    emailDetails.setSubject("Verificacion Cuenta");
    emailDetails.setMsgBody("Tu token de verificacion es: " + nuevoToken + " por favor ingrese este token en el casillero correspondiente para seguir");
    try{
      servicioMail.sendEmail(emailDetails);
    }catch(MessagingException e){
      e.printStackTrace();
    }
    // Enviar el nuevo código al usuario
    servicioMail.sendEmail(emailDetails); // Función que envía el correo con el nuevo token

    response.put("success", true);
    response.put("message", "Nuevo código enviado con éxito.");
    return ResponseEntity.ok(response);
    }
  }


}
