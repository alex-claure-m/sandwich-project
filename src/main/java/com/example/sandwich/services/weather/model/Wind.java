package com.example.sandwich.services.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wind {
  @JsonProperty("speed")
	public float speed;
  @JsonProperty("deg")
  public int deg;
}
