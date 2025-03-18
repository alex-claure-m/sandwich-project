package com.example.sandwich.services.translate;

import com.example.sandwich.services.weather.InterceptorApi;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//ka configuracion de la "url-api" del traductor. tengo que inicializarla de alguna forma

@Configuration
@PropertySource("classpath:translator.properties")
public class RetrofitDelegacionTranslator {

  @Value("${translator.api}")
  private String baseUrl;

  //no necesito okhttps
  @Bean(name="retrofitTranslater")
  public Retrofit retrofit(){
    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }
}
