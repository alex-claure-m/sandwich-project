package com.example.sandwich.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

  private String username;

  @NotBlank(message="no se permite contraseña vacias")
  @Size(min = 4, message= "la contraseña debe tener al menos 4 caracteres")
  private String passw;
}
