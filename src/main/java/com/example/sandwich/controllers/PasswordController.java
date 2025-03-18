package com.example.sandwich.controllers;

import com.example.sandwich.domain.PasswordEncod;
import com.example.sandwich.dto.UserDTO;
import com.example.sandwich.domain.clients.Usuario;
import com.example.sandwich.repository.PersonaRepository;
import com.example.sandwich.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PasswordController {

  @Autowired
  private PersonaRepository persona;

  @Autowired
  private PasswordEncod pass;

  @Autowired
  private PersonaRepository personaRepository;

  @Autowired
  private UsuarioRepository userRepository;
  @Autowired
  private UsuarioRepository usuarioRepository;



  @GetMapping("/create-password")
  public ModelAndView createPassword(@RequestParam String username, Model model){
    ModelAndView plantiPass = new ModelAndView("create-password");
    //System.out.println("username de create-password"+ username);
    // traigo el username del JS(register-verification) a esta nueva plantilla
    model.addAttribute("username",username);
    return plantiPass;
  }

  //16/03-> error: el username que llega a getMappint(/create-password)
  // no esta reflejando en este postMapping("/new-pass"  )

  @PostMapping("/new-pass")
  public String createPassword(@ModelAttribute("userdto") @Valid UserDTO userdto,
                               BindingResult result,
                               Model model){
    String user = userdto.getUsername();
    String pass = userdto.getPassw();

    System.out.println("el usuario que trae es..."+user);
    System.out.println("a ver si muestra la contraseña aca:" + pass);
   //necesito hashear la contraseña y luego subirla a la bbdd
    if(pass.length()<4){
      result.rejectValue("password", "error.userdto", "contraseña muy corta");
      return "create-password";
    }
    if(user.isEmpty()) {
      result.rejectValue("usuario", "error.userdto", "usuario vacio, no valido");
      return "create-password";
    }
    Usuario usuario = usuarioRepository.findByUsuario(user);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
    String passwithhash = encoder.encode(pass);
    System.out.println("a ver si muestra la contraseña aca:" + passwithhash);
    usuario.setPassword(passwithhash);
    userRepository.save(usuario);
    return "redirect:/login";
  }



}
