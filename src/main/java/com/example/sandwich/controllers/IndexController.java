package com.example.sandwich.controllers;

import com.example.sandwich.services.translate.TraductorService;
import com.example.sandwich.services.weather.ServicioClimaOpenSource;
import com.example.sandwich.services.weather.model.Pais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class IndexController {

  //traigo la interface como inyeccion de dependnecia
private final TraductorService servicioTraduccion;

  @Autowired
  public IndexController(TraductorService servicioTraduccion) {
    this.servicioTraduccion = servicioTraduccion;
  }

  @GetMapping("/")
  public String index(@AuthenticationPrincipal UserDetails userdetails, Model model) throws IOException {

    if (userdetails != null) {

      model.addAttribute("username", userdetails);
    }try {

    //seteare todo servicio como clima y uno mas si no me equivoco
    ServicioClimaOpenSource servicioClima = ServicioClimaOpenSource.getInstance();
    Pais lugar = servicioClima.getCountry("Buenos Aires");
    String descripcionTraducido = servicioTraduccion.translaterText(lugar.getWheaterDescripcionPpal());
    model.addAttribute("ciudad",lugar.getNombreCiudad());
    model.addAttribute("sensacionTermica",lugar.getSensacionTermicaActual() );
    model.addAttribute("viento", lugar.getSpeedWind());
    model.addAttribute("descripcion", descripcionTraducido);
    model.addAttribute("icono", lugar.getWheaterIcon());
    model.addAttribute("temperaturaActual", lugar.getTemepraturaActual());
    model.addAttribute("temperaturaMinima", lugar.getTemperaturaMinima());
    model.addAttribute("temperaturaMaxima", lugar.getTemperaturaMaxima());
    System.out.println("traduccion:" + descripcionTraducido);
    }catch (IOException e) {
      model.addAttribute("error", "no se pudo obtener datos");
    }
    return "index"; //retorna el nombre de la vista a la cual quiero acceder

  }


}
