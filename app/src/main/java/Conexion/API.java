package Conexion;

import com.jantoniodomga.macropay.Entitys.LoginResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface API {
    @POST("http://testandroid.macropay.com.mx")
    @FormUrlEncoded
    Call<LoginResponse>Login(@Field("email") String email,@Field("password")String password);
}
