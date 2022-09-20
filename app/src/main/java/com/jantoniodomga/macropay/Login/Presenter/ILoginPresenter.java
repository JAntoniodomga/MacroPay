package com.jantoniodomga.macropay.Login.Presenter;

import android.content.Context;
import android.widget.EditText;

public interface ILoginPresenter {
    boolean onContainsSession(Context contexto);

    void gotoMain(Context contexto);

    void onRequestValidateAndGetSession(EditText txtUsername, EditText txtPassword, Context contexto);
}
