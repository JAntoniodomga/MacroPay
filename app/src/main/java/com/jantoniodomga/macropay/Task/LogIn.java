package com.jantoniodomga.macropay.Task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.jantoniodomga.macropay.Contracts.Requestable;
import com.jantoniodomga.macropay.Entitys.LoginResponse;
import com.jantoniodomga.macropay.Entitys.Session;
import com.jantoniodomga.macropay.Utils.Constants;
import com.jantoniodomga.macropay.Utils.ServiceGenerator;
import com.jantoniodomga.macropay.Utils.SharedPreferenceGenerator;

import java.util.concurrent.TimeoutException;

import Conexion.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends Thread{
    private Context contexto;
    private String username, password;
    private Requestable<LoginResponse> listener;

    public LogIn(@NonNull Context contexto,@NonNull String username,@NonNull String password,@NonNull Requestable<LoginResponse> listener) {
        this.contexto = contexto;
        this.username = username;
        this.password = password;
        this.listener = listener;
    }

    @Override
    public void run() {
        super.run();
        API endpoint= ServiceGenerator.createService();
        Call<LoginResponse> logIn=endpoint.Login(username,password);
        logIn.enqueue(new Callback<LoginResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.errorBody()==null)
                {
                    if (response.body()!=null)
                    {
                        if (response.body().isSuccess())
                        {
                            SharedPreferences preferences= SharedPreferenceGenerator.getInstance(contexto);
                            preferences.edit().putString(Constants.KEY_SESSION,response.body().toJson()).commit();
                            Session logged=new Session().fromJWT(response.body().getToken());
                            listener.onSuccess(logged);
                        }
                        else
                        {
                            listener.onResponseFail(call,response.body());
                        }
                    }
                }
                else
                {
                    listener.onResponseError(call.clone(),response);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (t instanceof TimeoutException)
                {
                    listener.onTimeOut(call.clone(),t);
                }
                else
                {
                    listener.onFailure(call,t);
                }
            }
        });
    }
}
