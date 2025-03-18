package com.example.sandwich.services.weather;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//esta clase como es configuracion, solamente carga prevalores que le pase por parametro
//en este caso traidos desde la -variable de entorno- en el classpath
@Configuration
@PropertySource("classpath:weather.properties")
public class RetrofitDelegacion {

  //con el @BEAN inyecto el retrofit , en este caso seteo el valor del classpath
  // y buildeo el URL + appid pasado por parametro
  // que hace entonces?=> instancio el retrofil
  @Bean(name="retrofitWheater")
  public Retrofit retrofit(@Value("${weather.api.key}") String apikey) {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .addInterceptor(new InterceptorApi(apikey)) // aca creo un objeto para modificar la url
        .build(); // buildeo todo el url modificado con su appkey
    return new Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()) .build();
  }
}
