package com.example.sandwich.repository;

import com.example.sandwich.domain.clients.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
  Persona save(String email);

  Persona findByEmail(String email);

  Persona findByToken(String token);

  @Query("SELECT p FROM Persona p WHERE p.usuario.usuario = :username")
  Persona findByUsuario(@Param("username") String username);
}

