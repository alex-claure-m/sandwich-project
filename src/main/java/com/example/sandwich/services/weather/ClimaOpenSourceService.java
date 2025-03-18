package com.example.sandwich.services.weather;

import com.example.sandwich.services.weather.model.Pais;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClimaOpenSourceService {

  /*
   * como funcionaba el query? por CLAVE - VALOR  , y esto se ve mas cuando vien de esta forma : //x.com/name?name=xxxx
   * es un parametro de consulta
   * como funcionaba path? -> basicamente es un parametro de rutaen la que esta integrado en la url ppal
   * osea que si la url es : x.com/name/{ parte de la ruta del path a buscar }
   * */
  //@Query("appid") String apiKey
  @GET("weather") //viene de la url
  Call<Pais> obtenerClimaPorCiudad(@Query("q") String ciudad);
  //creo que es lo unico que hare, hay mas opciones segun el doc del clima
  /* como:
  https://api.openweathermap.org/data/2.5/weather?q={city name},{country code}&appid={API key}
	y
  https://api.openweathermap.org/data/2.5/weather?q={city name},{state code},{country code}&appid={API key}
  en la que agregas con state code y country code como query params. pero me limitare con solo el nombre de la ciudad
   */
}
