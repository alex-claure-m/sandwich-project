package com.example.sandwich.servic;

/*
@Service
public class UsuarioServices {

  @Autowired
  private TokenCreate tokenGenerate;
  @Autowired
  private EmailService emailService;

  @Autowired
  private PersonaRepository personaRepository;

  public void enviarToken(String email) {
    Persona persona = personaRepository.findByEmail(email);
    if (persona != null) {
      String token = tokenGenerate.tokenGenerate();
      persona.setToken(token);
      personaRepository.save(persona); // guardo el token en la bbdd de la persona
      // Usa el método sendSimpleMail de EmailService
      emailService.sendSimpleMail(new EmailDetails(email, "Tu token es: " + token, "Tu token de verificación", null));

    }
  }
  public boolean validarToken(String email, String token) {
    Persona persona = personaRepository.findByEmail(email);
    if (persona != null && persona.getToken().equals(token)) {
      persona.setEstaHabilitado(true);
      personaRepository.save(persona);
      return true;
    }
    return false;
  }

}
  */