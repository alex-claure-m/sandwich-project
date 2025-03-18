package com.example.sandwich.services.translate.model;

public class Traductor {
  public String mensaje;
  public String idioma;

  public Traductor(String mensaje, String idioma) {
    this.mensaje = mensaje;
    this.idioma = idioma;
  }

  public String getMensaje() {
    return mensaje;
  }

  public String getIdioma() {
    return idioma;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public void setIdioma(String idioma) {
    this.idioma = idioma;
  }
}
