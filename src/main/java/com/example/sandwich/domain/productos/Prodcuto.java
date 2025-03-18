package com.example.sandwich.domain.productos;

import com.example.sandwich.domain.PersistenceId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


//como habra datos en esta clase abstracta que debera almacenarse -> debo agregar un Entity
@Entity
//como va a ser clase padre-hijo y las entidades se deben almacenar
// decidir que estrategia de relacion va a tener  --- MIRAR HERENCIA Y BASE DE DATOS.TXT
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// el valor discrimatorio es para la base de datos , ya que al ser sigle table, y se unifican en una tabla
// debo crear el nombre del a columna y entonces agregar a sus subclases hijas , su tipo
@DiscriminatorColumn(name = "tipo_producto")
@Getter
@Setter
public abstract class Prodcuto extends PersistenceId {

  private String nombre;
  private String precio;

}
