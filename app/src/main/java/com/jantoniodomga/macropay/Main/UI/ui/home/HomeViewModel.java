package com.jantoniodomga.macropay.Main.UI.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jantoniodomga.macropay.Contracts.IResultable;
import com.jantoniodomga.macropay.Entitys.Session;
import com.jantoniodomga.macropay.Task.BarCodeGenerator;
import com.jantoniodomga.macropay.Utils.Constants;
import com.jantoniodomga.macropay.Utils.SharedPreferenceGenerator;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<Session>session;
    private final MutableLiveData<Bitmap>code;
    private MutableLiveData<String>jwt;

    private SharedPreferences preferences;
    private Context contexto;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        session=new MutableLiveData<>();
        code=new MutableLiveData<>();
        jwt=new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<Session> getSession() {
        return session;
    }

    public MutableLiveData<Bitmap> getCode() {
        return code;
    }

    public MutableLiveData<String> getJwt() {
        return jwt;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setContext(Context context) {
        preferences= SharedPreferenceGenerator.getInstance(context);
        String jwt=preferences.getString(Constants.KEY_SESSION,"");
        this.jwt.setValue(jwt);
        this.session.setValue(new Session().fromJWT(jwt));
        mText.setValue(session.getValue().getEmail());
    }
}