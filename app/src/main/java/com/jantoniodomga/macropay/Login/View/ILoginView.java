package com.jantoniodomga.macropay.Login.View;

import android.content.Intent;

import com.jantoniodomga.macropay.Entitys.Session;

public interface ILoginView {
    void onLaunchPrivateModule(Intent intent);

    void onUsernameEmpty();

    void onPasswordEmpty();

    void onInvalidEmail();

    void onLogInSuccess(Session logged, Intent intent);
}
