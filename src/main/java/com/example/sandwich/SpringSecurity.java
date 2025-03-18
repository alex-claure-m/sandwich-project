package com.example.sandwich;

import com.example.sandwich.domain.PasswordEncod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SpringSecurity  {

  //si bien aca
  @Autowired
  private UsuarioDetailServiceImp userDetailsService;

  @Autowired
  private PasswordEncod bCryptPasswordEncoder;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(authz -> authz
            .requestMatchers( "/","/send-email","/index","/login", "/registergmailusing","/register-with-mail/**","/register-verification/validation-check","/register-verification/resend-code","/create-password","/new-pass","/css/**","/js/**").permitAll() //permito acceso a las rutas, en este caso a /login y /register SIN pedir autenticacion
            // si no esta los endpoint en este requestMatchers -> spring asume que tiene que ir por los que si requieren autenticacion (y esta en :anyrequest.authenticated())
            .anyRequest().authenticated()) // para todos los demas rutas -endpoint, pido autenticacion
        .formLogin(formLogin -> formLogin // esto seria el manejo del formulario dell ogin
            .loginPage("/login") // para la seguridad, defino la ruta donde se hara el login
            .failureUrl("/login?error=true") // para redirigir al user si la autentitcacion falla
            .defaultSuccessUrl("/dashboard",true) // a donde va a redirigir luego de aceptar las credenciales
            .permitAll()) // permito el acceso al /login para todos los users esten o no logeados
        //mantener esta estrucutra del logout!
        .logout(logout -> logout // el manejo del logout es muy similar al login
            .logoutUrl("/logout") // la direccion que debe tener cuando se quiere desloggear
            .logoutSuccessUrl("/login?logout=true") // cuando se cierra una sesion de manera exitosa
            .permitAll())



        .csrf(csrf -> csrf.disable()) // para que no me pida csrf token, ya que estoy usando el token csrf de angular
        .build();

  }
  /*
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**");
  }

 */
	// instancio la clase UserDetailsService -> como si fuese un objeto en si, con sus atributos cargados
 // por eso ademas esta el autowired >
  @Bean
  public UserDetailsService userDetailsService() {
    return userDetailsService;
  }

  // cojfiguro el spring security para cargar al usuario durante la autenticacion
  //y de ahi usar el usuarioDetailsService y su passwordEncoder
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder.passwordEncoder());
  }

}
