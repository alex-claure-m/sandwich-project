package testeoTraduccion;

import com.example.sandwich.SandwichApplication;
import com.example.sandwich.services.translate.ServicioTraductorOpenSource;
import com.example.sandwich.services.weather.model.Pais;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SandwichApplication.class)
@ExtendWith(SpringExtension.class)

public class TestTraductorApi {
  @Autowired
  private ServicioTraductorOpenSource servicioTraduccion;

  @Test
  public void devolveTraduccion() throws IOException {
    String hola = servicioTraduccion.translaterText("hola a todos");
    System.out.println("EL NOMBRE QUE PAIS QUE BUSCO: " + hola);
    assertEquals("hello everyone", hola);
  }
}
