package com.example.sandwich.domain.productos;

import com.example.sandwich.domain.PersistenceId;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "por_kg")
public class ProductoPorKg extends Prodcuto {
  private float peso;

}
