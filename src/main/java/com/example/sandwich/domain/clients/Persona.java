package com.example.sandwich.domain.clients;

import com.example.sandwich.domain.PersistenceId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Persona  extends PersistenceId {

  private String nombre;
  private String apellido;
  private String telefono;
  private String email;
  private LocalDateTime ultimoIntentoFallido;
  private int intentos =3;

  public Persona(String nombre, String apellido, String email) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.email = email;
  }


	@OneToOne(cascade = CascadeType.PERSIST) // para persistir el usuario por estar bidireccional
  @JoinColumn(name="usuario_id", referencedColumnName = "id") // name= nombre de la columna! en bbdd
  // la Persona Class es dueño de la relacion y almacena la clave foranea
  private Usuario usuario;

	private Boolean estaHabilitado;
  private String token;
}

