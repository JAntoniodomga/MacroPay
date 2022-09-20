package com.jantoniodomga.macropay.Utils;

import Conexion.API;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static String CONEXION="http://testandroid.macropay.com.mx";
    
    public static API createService(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(CONEXION)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(API.class);
    }
}
