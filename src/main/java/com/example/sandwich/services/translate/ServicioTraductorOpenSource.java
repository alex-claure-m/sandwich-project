package com.example.sandwich.services.translate;

import com.example.sandwich.services.weather.ClimaOpenSourceService;
import com.example.sandwich.services.weather.ServicioClimaOpenSource;
import com.example.sandwich.services.weather.model.Pais;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

//este es el mapeo de la -url de la api- a una clase singlenton , es para el manejo de la conexion con la api
@Service
@PropertySource("classpath:translator.properties")
public class ServicioTraductorOpenSource implements TraductorService{

  @Value("${translator.api}")
  private String url;

  private final Retrofit retrofit;

  @Autowired
  public ServicioTraductorOpenSource(@Qualifier("retrofitTranslater") Retrofit retrofit) {
    this.retrofit = retrofit;
  }

  private static ServicioTraductorOpenSource instance;
  @PostConstruct
  private void init() {
    instance = this;
  }

  public static ServicioTraductorOpenSource getInstance() {
    if (instance == null) {
      throw new IllegalStateException("ServicioTraductorOpenSource no ha sido inicializado por Spring");
    }
    return instance;
  }

  /* ****************************** INVOCO ********************************** */
  @Override
  public String translaterText(String texto) throws IOException {
    TranslateOpenSourceService traductorOpenSource = this.retrofit.create(TranslateOpenSourceService.class);
    Call<String[][]> requestCall = traductorOpenSource.traducir(texto);
    Response<String[][]> responseTraduccion = requestCall.execute();
    System.out.println("a ver si funca el response "+ responseTraduccion);
    return responseTraduccion.body()[0][0];
  }
}
