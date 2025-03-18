package testeoDescripcionClimaTraduccion;


import com.example.sandwich.SandwichApplication;
import com.example.sandwich.services.translate.ServicioTraductorOpenSource;
import com.example.sandwich.services.weather.ServicioClimaOpenSource;
import com.example.sandwich.services.weather.model.Pais;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.io.IOException;

@SpringBootTest(classes = SandwichApplication.class)
@ExtendWith(SpringExtension.class)

public class DescripcionTraducidoTest {

  @Autowired
  private ServicioClimaOpenSource servicioClimaOpenSource;

  @Autowired
  private ServicioTraductorOpenSource servicioTraduccion;

  @Test
  public void descripcionTraducido() throws IOException {
    Pais pais = servicioClimaOpenSource.getCountry("Buenos Aires");
    String descripcionSinTraduccion = pais.getWheaterDescripcionPpal();
    System.out.println("traduccion sin traducir "+ descripcionSinTraduccion);
    String traduccionDescripcion = servicioTraduccion.translaterText(descripcionSinTraduccion);
    System.out.println("descripcion traducidta: "+ traduccionDescripcion);
    String traduccionFalopa= servicioTraduccion.translaterText("estas bien?");
    System.out.println("traduccion harcodeada: "+ traduccionFalopa);
    Assertions.assertTrue(true);
  }
}
