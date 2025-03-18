package testeoWeather;

import com.example.sandwich.SandwichApplication;
import com.example.sandwich.services.weather.ClimaOpenSourceService;
import com.example.sandwich.services.weather.ServicioClimaOpenSource;
import com.example.sandwich.services.weather.model.Pais;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SandwichApplication.class)
@ExtendWith(SpringExtension.class)
public class ClimaTest {
/*
  @Test
  public void devuelveNombreCiudadBuenosAires() throws IOException {
    ServicioClimaOpenSource servicio = ServicioClimaOpenSource.getInstance();
    Pais pais = servicio.getCountry("Buenos Aires");
    //System.out.println("EL NOMBRE QUE PAIS QUE BUSCO: " + pais.getNombreCiudad());
    assertEquals("Buenos Aires", pais.getNombreCiudad());
    //assertTrue(true);
  }

  @Test
  public void laTemperaturaMinimaFueDe10Grados() throws IOException {
    ServicioClimaOpenSource servicio = ServicioClimaOpenSource.getInstance();
    Pais pais = servicio.getCountry("Buenos Aires");
    double viento = pais.getWind().getSpeed();
    String descripcion = pais.getWeather().get(0).getDescription();
    //System.out.println("el viento ES: " + viento);
    double temperaturaMinima = pais.getMain().getTemp_min();
    //System.out.println("hoy el cielo se encuentra: " + descripcion);
    assertTrue(true);
  }
 */

  @Autowired
  private ServicioClimaOpenSource servicioClimaOpenSource;

  @Test
  public void devuelveNombreCiudadBuenosAires() throws IOException {
    Pais pais = servicioClimaOpenSource.getCountry("Buenos Aires");
    System.out.println("EL NOMBRE QUE PAIS QUE BUSCO: " + pais.getNombreCiudad());
    assertEquals("Buenos Aires", pais.getNombreCiudad());
  }

}
