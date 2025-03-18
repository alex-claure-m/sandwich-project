package com.example.sandwich.controllers;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServicio {
  private final Map<String,String> tokenStorage = new HashMap<>();

  public void guardarToken(String token , String email){
    tokenStorage.put(token,email);
  }
  public String getToken(String email){
    return tokenStorage.get(email);
  }
  public void eliminarToken(String email) {
    tokenStorage.remove(email);
  }
}
