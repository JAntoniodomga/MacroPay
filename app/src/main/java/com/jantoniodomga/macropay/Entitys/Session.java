package com.jantoniodomga.macropay.Entitys;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.jantoniodomga.macropay.Contracts.jsonparser;

public class Session implements jsonparser<Session> {
    private String titular;
    private String url;
    private String email;
    private String solicitud;
    private String JWT;

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public Session fromJson(String jsonvalue) {
        return new Gson().fromJson(jsonvalue,Session.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Session fromJWT(String jwtToken)
    {
        String[] split_string = jwtToken.split("\\.");
        String base64EncodedBody = split_string[1];
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String payloadJson = new String(decoder.decode(base64EncodedBody));
        Session session=new Session().fromJson(payloadJson);
        return session;
    }
}
