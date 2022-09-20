package com.jantoniodomga.macropay.Entitys;

import com.google.gson.Gson;
import com.jantoniodomga.macropay.Contracts.jsonparser;

public class LoginResponse extends WebResponse implements jsonparser<LoginResponse> {
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public LoginResponse fromJson(String jsonvalue) {
        return new Gson().fromJson(jsonvalue,LoginResponse.class);
    }
}
