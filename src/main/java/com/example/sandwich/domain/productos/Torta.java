package com.example.sandwich.domain.productos;

import com.example.sandwich.domain.PersistenceId;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "torta")
public class Torta extends Prodcuto {

  private String relleno;
  private List<String> contenido;

}
