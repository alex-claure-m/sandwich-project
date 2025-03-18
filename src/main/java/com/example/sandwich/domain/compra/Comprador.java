package com.example.sandwich.domain.compra;

import com.example.sandwich.domain.PersistenceId;
import com.example.sandwich.domain.clients.Persona;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comprador extends PersistenceId {

  @OneToOne // necesito que Cliente conozca la clase Comprador? NO, no me interesa que cliente conozca que es un comprador
  // ya por dentro se sabe que lo sera -> unidireccional
  // solo agrego anotacion de relacion en esta clase
  @JoinColumn(name = "persona",referencedColumnName = "id")
  private Persona cliente;

  private String medioDePago; //bueno, no hay implementacion de esto

  @OneToOne
  @JoinColumn(name="carrito_id", referencedColumnName = "id")
  private Carrito carritoCompras;
}
