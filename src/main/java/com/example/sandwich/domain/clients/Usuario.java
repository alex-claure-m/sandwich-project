package com.example.sandwich.domain.clients;

import com.example.sandwich.domain.PersistenceId;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario extends PersistenceId {

  private String usuario;
  private String password;

  @OneToOne(mappedBy = "usuario") // mappedby= nombreAtributo de la otra clase a mappear, donde la dueña es la clase donde esta ese atributo  y dice que NO NECESITA OTRA FK , la dueña de la unica FK esta en la PERSONA
  // mappedBy = "persona" - para definir la Bidireccionalidad
  //no genero nueva columna con el mappedby, solo sirve para clave decir: la fk esta en Persona class
  // recorda que la bidireccionalidad es para decir que ambos se conocen entre si y es visibble
  // pero yo, no necesito saber que usuario conozca a Persona, pero si Persona que conozca a usuario
  // update2: en realidad si es solo loggin, unidireccional, si requiero lueo cargar datos como el perfil y actualizar -> bidirecc
  // update3: a la bidirec -> la podes "reemplazar" por consulta con query en el repository, pero es limitado
  private Persona persona;


}
