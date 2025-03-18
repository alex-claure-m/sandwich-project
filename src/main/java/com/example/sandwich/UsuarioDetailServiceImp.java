package com.example.sandwich;

import com.example.sandwich.domain.clients.Persona;
import com.example.sandwich.domain.clients.Usuario;
import com.example.sandwich.dto.PersonaDTO;
import com.example.sandwich.repository.PersonaRepository;
import com.example.sandwich.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailServiceImp implements UserDetailsService {
  // el autowired basicamente es para cargar los datos a la hora de la ejecucion
  // basicamente inyecto dependencias para evitar escribir un new class() etc etc etc
  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PersonaRepository repositorioPersona;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByUsuario(username);
    if (usuario == null) {
      throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
    //return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    return User.withUsername(usuario.getUsuario())
        .password(usuario.getPassword())
        .build();
  }


  public Usuario obtenerUsuarioPorUsername(String username) {
    Usuario user= usuarioRepository.findByUsuario(username);
    if(user == null){
      throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }else
      return user;
  }

  //
  public void actualizarPerfil(String username, PersonaDTO perfilDTO){
    // primero busco al usuario en cuestion, que es el que estara loggeado
    Usuario user = usuarioRepository.findByUsuario(username);
    // luego busco a la persona asociada al usuario
    if(user !=null){
      Persona persona = repositorioPersona.findByUsuario(username); // me deberia traer la persona solo con el username asociado a persona
      if(persona !=null){
        persona.setNombre(perfilDTO.getNombre());
        persona.setApellido(perfilDTO.getApellido());
        persona.setEmail(perfilDTO.getEmail());

        if(perfilDTO.getUsuario() !=null && !perfilDTO.getUsuario().isEmpty()) {
          user.setUsuario(perfilDTO.getUsuario()); // ya que en esta clase guardara del DTO a la clase del DOMAIN
        }
        repositorioPersona.saveAndFlush(persona);
        usuarioRepository.saveAndFlush(user);
      }else{
        throw new EntityNotFoundException("la persona no fue encontrada para el usuario " + username);
      }
    } else{
      throw new UsernameNotFoundException("el usuario no fue encontrado");
    }
  }

  public void changePass(String username, String newPass) {
    Usuario user = usuarioRepository.findByUsuario(username);
    if (user != null) {
      user.setPassword(newPass);
      usuarioRepository.saveAndFlush(user);
    } else {
      throw new UsernameNotFoundException("el usuario no fue encontrado");
    }
  }
}