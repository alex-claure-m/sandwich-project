package com.example.sandwich.services.weather;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;

//justamente interceptor api es una intercepcion , en este caso a la url que esta generando
// para que?-> para agregar parametros o valores de una url convencional
// en  este caso es el id que se le esta pasando por parametro desde la clase que la invoca
// (RetrofitDelegacion)
// basicamente es cuando tengo mas parametros en la url-api proporiconada desde un tercero
// en la que deba matchearle algun parametro de mi properties en el medio de esa url
public class InterceptorApi implements Interceptor {

  private final String apikey;
  //constructor
  public InterceptorApi(String apikey) {
    this.apikey = apikey;
  }

  // sobreescribo el metodo para modificar de que tome la URL que se buildeo en RetrofitDelegation.class
  // y agrega un appKey
  // y asi devuelve la cadena de url modificada
  // volviendo a la clase que fue invocada / usada (retrofitdelegation.class)
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request originRequest = chain.request(); //obtengo la solicitud http orogina
    HttpUrl modifiedUrl = originRequest.url().newBuilder()
        .addQueryParameter("appid", apikey).build(); // y a cada linea le agregare la modificacion - en este caso es la api key
    System.out.println("Modified URL: " + modifiedUrl);
    Request modifiedRequest = originRequest.newBuilder()
        .url(modifiedUrl)
        .build();
    return chain.proceed(modifiedRequest); // devuelvo el link modificado, pero en realida esto lo usaria el climaOpenSource
  }

}
