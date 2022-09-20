package com.jantoniodomga.macropay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.jantoniodomga.macropay.Entitys.Session;
import com.jantoniodomga.macropay.Login.Presenter.ILoginPresenter;
import com.jantoniodomga.macropay.Login.Presenter.LoginPresenterCompl;
import com.jantoniodomga.macropay.Login.View.ILoginView;

public class MainActivity extends AppCompatActivity implements ILoginView {
    private Context contexto;
    private ILoginPresenter presenter;
    private MaterialButton btnLogin;
    private EditText txtUsername,txtPassword;
    private TextInputLayout tilUsername,tilPassword;
    private TextView.OnEditorActionListener imeOptions=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
            if (actionId== EditorInfo.IME_ACTION_SEND)
            {
                tilUsername.setError(null);
                tilPassword.setError(null);
                presenter.onRequestValidateAndGetSession(txtUsername,txtPassword,contexto);
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contexto=MainActivity.this;
        presenter=new LoginPresenterCompl(this);
        if (presenter.onContainsSession(contexto))
        {
            presenter.gotoMain(contexto);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=findViewById(R.id.btnLogin);
        txtUsername=findViewById(R.id.txtUsername);
        txtPassword=findViewById(R.id.txtpassword);
        tilUsername=findViewById(R.id.tilUsername);
        tilPassword=findViewById(R.id.tilPassword);
        txtPassword.setOnEditorActionListener(imeOptions);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tilUsername.setError(null);
                tilPassword.setError(null);
                presenter.onRequestValidateAndGetSession(txtUsername,txtPassword,contexto);
            }
        });
    }

    @Override
    public void onLaunchPrivateModule(Intent intent) {
        contexto.startActivity(intent);
        MainActivity.this.finish();
    }

    @Override
    public void onPasswordEmpty() {
        tilPassword.setError(contexto.getString(R.string.requieredField));
    }

    @Override
    public void onInvalidEmail() {
        tilUsername.setError(contexto.getString(R.string.emailInvalid));
    }

    @Override
    public void onUsernameEmpty() {
        tilUsername.setError(contexto.getString(R.string.requieredField));
    }

    @Override
    public void onLogInSuccess(Session logged, Intent intent) {
        MainActivity.this.startActivity(intent);
        Toast.makeText(contexto,logged.getTitular(),Toast.LENGTH_LONG).show();
        MainActivity.this.finish();
    }
}