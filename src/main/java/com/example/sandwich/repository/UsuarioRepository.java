package com.example.sandwich.repository;

import com.example.sandwich.domain.clients.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/*
* Una de las características poderosas de Spring Data JPA es la capacidad de definir
* consultas basadas en el nombre del método. Spring Data JPA analiza el nombre del método
* y construye una consulta automáticamente. Por ejemplo:
*
findBy: Se utiliza para buscar entidades basadas en las propiedades.
And: Se utiliza para combinar condiciones.
Or: Se utiliza para combinar condiciones con una operación OR.
OrderBy: Se utiliza para ordenar los resultados.
*
* Si tienes una entidad Persona con campos id, nombre, y apellido, podrías definir métodos
* en tu repositorio como:

public interface PersonaRepository extends JpaRepository<Persona, Long> {
*  List<Persona> findByNombre(String nombre);
*  List<Persona> findByNombreAndApellido(String nombre, String apellido);
*  List<Persona> findByNombreOrApellido(String nombre, String apellido);
*  List<Persona> findByNombreOrderByApellido(String nombre); }
*
* Spring Data JPA generará automáticamente las consultas SQL necesarias para estos métodos.
* Por lo tanto, aunque no hayas definido explícitamente estos métodos, Spring Data JPA los reconoce
* y los implementa en tiempo de ejecución basándose en las convenciones de nombres
* */
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

  Optional<Usuario> findById(Long id); // revisar por hay un datazo que no concientice

  Usuario findByUsuario(String user);


}
