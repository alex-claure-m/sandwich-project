package com.example.sandwich.domain;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.UUID;

//es un servicio en si, debido a que en usuarioService
// tiene un autowired y si no tiene service o configuration annotation
// no podre crear un token en usuarioService
@Service
public class TokenCreate {
  public String tokenGenerate(){
    return UUID.randomUUID().toString();
  }
}
