package com.example.sandwich;

import com.example.sandwich.services.weather.ServicioClimaOpenSource;
import jakarta.annotation.PostConstruct;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class SandwichApplication {
  private final ServicioClimaOpenSource servicioClimaOpenSource;

/*
  public static void main(String[] args) {
    SpringApplication.run(SandwichApplication.class, args);
  }
*/
  //en el main.class, inyecto dependencia del servicioClimaOpenSource(que es un @Service)
  //por que? -> por que necesito que desde el main levante la API del clima
  //y a partir de ese punto pueda luego, usar el servicio
  @Autowired
  public SandwichApplication(ServicioClimaOpenSource servicioClimaOpenSource) {
    this.servicioClimaOpenSource = servicioClimaOpenSource;
  }
  public static void main(String[] args) {
    SpringApplication.run(SandwichApplication.class, args);
}
   @PostConstruct
    public void init() {
    System.out.println("ServicioClimaOpenSource initialized: "
        + ServicioClimaOpenSource.getInstance().getRetrofit());
  }
}