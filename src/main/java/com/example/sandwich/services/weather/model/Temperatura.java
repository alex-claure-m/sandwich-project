package com.example.sandwich.services.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Temperatura {
  @JsonProperty("temp")
  public float temp;
  @JsonProperty("feels_like")
  public float feels_like;
  @JsonProperty("temp_min")
  public float temp_min;
  @JsonProperty("temp_max")
  public float temp_max;
}
