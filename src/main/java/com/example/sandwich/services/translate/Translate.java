package com.example.sandwich.services.translate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class Translate {

  private String mensaje;

  private ServicioTraductorOpenSource servicioTraduccion;

  public String traducir(String mensaje) throws IOException {
    String textoTraducido = servicioTraduccion.translaterText(mensaje);
    return textoTraducido;
  }
}
