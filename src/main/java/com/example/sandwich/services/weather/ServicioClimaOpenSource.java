package com.example.sandwich.services.weather;

import com.example.sandwich.services.weather.model.Pais;
import jakarta.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

//esta clase se lo considerar como Servicio
// en la que springboot tomara esta clase como una clase en la que se instancia
// todos los recursos necesarios
// en otro grandes rasgo funciona como un @Bean que engloba toda la clase
// funciona como @Configuration? -> NI
// por que lo que hago en configuration es instaciar todas las clases
// PERO en SERVICE, ademas de instanciar , defino logica de NEGOCIO
// como logica de negocio?->  asi es , clases en la que si requiere mi proyecto, asi como unServicio de MialSender
// que contiene beans que instancie puertos https , etc
// o en este caso instanciar una clase ServicioClimaOpenSource como singlenton!
@Service
@PropertySource("classpath:weather.properties")
public class ServicioClimaOpenSource {

/*
  //cierto que el GSON es distinta biblioteca vs maven retrofit

  //private static ServicioClimaOpenSource instance = null; // patron singleton

  private final Retrofit retrofit;

  @Value(value="${weather.api.key}")
  private String apikey;

  // Constructor privado para evitar la creación de instancias directas
  private ServicioClimaOpenSource(Retrofit retrofit) {
    this.retrofit = retrofit;
  }

  private ServicioClimaOpenSource() {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(new InterceptorApi(apikey))
        .build();

    this.retrofit = new Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  // Método para inicializar el Singleton utilizando Spring
  @Autowired
  public static void initialize(Retrofit retrofit) {
    if (instance == null) {
      instance = new ServicioClimaOpenSource(retrofit);
    }
  }

  public static ServicioClimaOpenSource getInstance() {
    if (instance == null) {
      throw new IllegalStateException("ServicioClimaOpenSource no ha sido inicializado por Spring");
    }
    return instance;
  }
  public Retrofit getRetrofit() {
    return retrofit;
  }

  @PostConstruct
  public void init() {
    System.out.println("API Key: " + apikey);
  }

 */
  //se retoco el esquema para trabjar con springboot y variable de etorno -- .properties --
  // por que?
  // cuando hay inyecciones de dpendencias , spring ya lo hace mediante anotaciones como @Autowired o incluso @Value
  // y por que cuando usaba @value -> no andaba? (codigo anterior comentado)
  // leer INYECCIONES - SPRINGBOOT
	@Value("${weather.api.key}")
  private String apikey;
  //este es un argumento retrofit desligado de RetrofitDelegation.class
  private final Retrofit retrofit;

  //pero le digo que se inyecte retrofit en este constructor
  @Autowired
  private ServicioClimaOpenSource(@Qualifier("retrofitWheater") Retrofit retrofit) {
    this.retrofit = retrofit;
  }

  public Retrofit getRetrofit() {
    return retrofit;
  }
  // Utiliza Spring para obtener la instancia Singleton

  private static ServicioClimaOpenSource instance;
  @PostConstruct
  private void init() {
    instance = this;
  }

public static ServicioClimaOpenSource getInstance() {
  if (instance == null) {
    throw new IllegalStateException("ServicioClimaOpenSource no ha sido inicializado por Spring");
  }
  return instance;
}
  /* ********************** ACA DEBERIA HACER UN TESTEO DE COMO TRAE LAS COSAS ***************************** */
  //me trae el body del pais
  public Pais getCountry(String city) throws IOException {
    ClimaOpenSourceService climaOpenSource = this.retrofit.create(ClimaOpenSourceService.class);
    Call<Pais> requestPaisArg = climaOpenSource.obtenerClimaPorCiudad(city);
    Response<Pais> responsePaisArgs = requestPaisArg.execute();
    System.out.println("a ver si funca el response "+ responsePaisArgs);
    return responsePaisArgs.body();
  }



}
