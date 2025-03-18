package com.example.sandwich.controllers;

import com.example.sandwich.domain.clients.Persona;
import com.example.sandwich.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  // instancio el repositorio Usuario para , en teoria poder loggear
  private UsuarioRepository usuarios;

  public LoginController(UsuarioRepository usuarios) {
    this.usuarios = usuarios;
  }
  @GetMapping("/login")
  public String login(@RequestParam(name="error", required=false) String error, Model model){
    if(error != null){
      model.addAttribute("error","Nombre de usuario o contraseña incorrecta/s");
    }
    return "login";
  }

  @GetMapping("/dashboard")
  //clave el redirect:/ para que me guarde los atributos y permisos cuando pasa a index.html!
  //sino, pasa a url dashboard sin guardarme permisos y no usa el indexController
  public String dashboard() {
    return "redirect:/";
  }

  //hare un endpoint de register para la persona en si
  // donde creo un modelo y vista llamado "register" (spring buscara "register".html)
  // y ademas le paso un objeto Persona , vacio, que se llenara en el siguiente metodo del endpoint
  // por que vacio? -> xq es lo que se va a visualizar en la plantilla del formulario
  @GetMapping("/registergmailusing")
  public ModelAndView register() {
    //estoy guardando un modelo y vista llamado "registerGmailUsing -> el mismo que el html para que luego lo guarde
    ModelAndView modelAndView = new ModelAndView("registergmailusing");
    modelAndView.addObject("persona", new Persona());
    return modelAndView;
  }


}
