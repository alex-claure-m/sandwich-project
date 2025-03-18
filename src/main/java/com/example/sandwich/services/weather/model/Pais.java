package com.example.sandwich.services.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Pais implements ConvertKelvinToCelsius{
  @JsonProperty("weather")
  //descripcion rapida del clima
  public List<Weather> weather;
  // temperaturas de la ciudad
  @JsonProperty("main")
  public Temperatura main;
  // viento de la ciudad
  @JsonProperty("wind")
  public Wind wind;
  public int id;
  public String name;

  public String getNombreCiudad() {
    return name;
  }
  public float getTemperaturaMinima(){
    return this.convertkelvinToCelsius(main.getTemp_min());
  }

  public String getWheaterDescripcionPpal(){
    return weather.get(0).getDescription();
  }
  public String getWheaterIcon(){
    return weather.get(0).getIcon();
  }
  public float getSpeedWind(){
    return wind.getSpeed();
  }
  public float getTemepraturaActual(){
    return this.convertkelvinToCelsius(main.getTemp());
  }
  public float getSensacionTermicaActual(){
    return this.convertkelvinToCelsius(main.getFeels_like());
  }

  public float getTemperaturaMaxima(){
    return this.convertkelvinToCelsius(main.getTemp_max());
  }

  @Override
  public float convertkelvinToCelsius(float tempKelvin) {
    return (float)(tempKelvin - 273.15);
  }
}
