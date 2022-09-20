package com.jantoniodomga.macropay.Login.Presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.jantoniodomga.macropay.Contracts.Requestable;
import com.jantoniodomga.macropay.Entitys.LoginResponse;
import com.jantoniodomga.macropay.Entitys.Session;
import com.jantoniodomga.macropay.Login.View.ILoginView;
import com.jantoniodomga.macropay.Main.UI.HomeActivity;
import com.jantoniodomga.macropay.Task.LogIn;
import com.jantoniodomga.macropay.Utils.Constants;
import com.jantoniodomga.macropay.Utils.SharedPreferenceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginPresenterCompl implements ILoginPresenter {
    private ILoginView view;

    public LoginPresenterCompl(ILoginView view) {
        this.view = view;
    }

    @Override
    public boolean onContainsSession(Context contexto) {
        SharedPreferences preferences= SharedPreferenceGenerator.getInstance(contexto);
        if (preferences.contains(Constants.KEY_SESSION))
            return true;
        else
            return false;
    }

    @Override
    public void gotoMain(Context contexto) {
        SharedPreferences preferences= SharedPreferenceGenerator.getInstance(contexto);
        LoginResponse loginResponse=new LoginResponse().fromJson(preferences.getString(Constants.KEY_SESSION,"null"));
        Intent intent=new Intent(contexto, HomeActivity.class);
        intent.putExtra(Constants.KEY_SESSION, loginResponse.toJson());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
        view.onLaunchPrivateModule(intent);
    }

    @Override
    public void onRequestValidateAndGetSession(EditText txtUsername, EditText txtPassword, Context contexto) {
        if (!txtUsername.getText().toString().isEmpty())
        {
            if (Patterns.EMAIL_ADDRESS.matcher(txtUsername.getText().toString().trim()).matches())
            {
                if (!txtPassword.getText().toString().isEmpty())
                {
                    new LogIn(contexto, txtUsername.getText().toString().trim(), txtPassword.getText().toString().trim(), new Requestable<LoginResponse>() {
                        @Override
                        public void onTimeOut(Call<?> call, Throwable t) {
                            Log.e("Macro","onTimeOut> "+t.getMessage());
                        }

                        @Override
                        public void onFailure(Call<?> call, Throwable t) {
                            Log.e("Macro","onFailure> "+t.getMessage());
                        }

                        @Override
                        public void onResponseError(Call<?> call, Response<?> response) {
                            try {
                                Log.e("Macro",response.errorBody().source().readUtf8().toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onResponseFail(Call<?> call, LoginResponse response) {
                            Log.e("Macro","onResponseFail()");
                        }

                        @Override
                        public void onSuccess(Session logged) {
                            Intent intent=new Intent(contexto,HomeActivity.class);
                            intent.putExtra(Constants.KEY_SESSION,logged.toJson());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    view.onLogInSuccess(logged,intent);
                                }
                            });
                        }
                    }).start();
                }
                else
                {
                    view.onPasswordEmpty();
                }
            }
            else
            {
                view.onInvalidEmail();
            }
        }
        else
        {
            view.onUsernameEmpty();
        }
    }
}
