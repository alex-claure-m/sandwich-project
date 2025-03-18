package com.example.sandwich.services.translate;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TranslateOpenSourceService {

  // debido a que hardcodee casi todo la url en translator.properties
  // no debo expecificar este GET ya que no hay parametros para que pueda ser definidos
  // ya que por ejemplo , las definiciones son GET("nombre")
  // de la ruta www.lala.com/nombre?=xxxxx&apellido?=sssss
  //y su funcion de este
  // get seria justamente : call<Object> datos (@Query("nombre" String nombre, @Query("apellido"))
  @GET("t?client=dict-chrome-ex&sl=auto&tl=en")
  Call<String[][]> traducir (@Query("q") String texto);

}
