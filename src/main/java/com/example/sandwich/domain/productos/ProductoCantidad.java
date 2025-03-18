package com.example.sandwich.domain.productos;

import com.example.sandwich.domain.PersistenceId;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@DiscriminatorValue(value = "por_cantidad")
public class ProductoCantidad extends Prodcuto {

  private String descripcion;
	private int cantidad;
  private int unidades;
	private int precioTotal;

}
