package com.inteltrack.inteltrack.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.inteltrack.inteltrack.R;
import com.inteltrack.inteltrack.clientes.ClientesActivity;
import com.inteltrack.inteltrack.domain.JsonKeys;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    @BindView(R.id.txtUsuario)
    EditText txtUsuario;
    @BindView(R.id.txtClave)
    EditText txtClave;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;

    private LoginContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        new LoginPresenter(this, new LoginInteractor(this));
        if(savedInstanceState!=null)
            restaurarData(savedInstanceState.getString(JsonKeys.KEY_DATA));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        JsonObject jsonInfo= new JsonObject();
        jsonInfo.addProperty(JsonKeys.user, txtUsuario.getText().toString());
        jsonInfo.addProperty(JsonKeys.pass, txtClave.getText().toString());
        outState.putString(JsonKeys.KEY_DATA, jsonInfo.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restaurarData(savedInstanceState.getString(JsonKeys.KEY_DATA));
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
        activityLogin.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void message(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finalizar() {
        Intent i = new Intent(this, ClientesActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean usuarioValido() {
        boolean result = !txtUsuario.getText().toString().isEmpty();
        if (!result)
            message(getString(R.string.usuarioobligatorio));
        return result;
    }

    @Override
    public boolean claveValida() {
        boolean result = !txtClave.getText().toString().isEmpty();
        if (!result)
            message(getString(R.string.claveobligatoria));
        return result;
    }

    @Override
    public void errorDeConexion() {
        message(getString(R.string.conexioninternet));
    }

    @Override
    @OnClick(R.id.btnIngresar)
    public void login() {
        if (usuarioValido() && claveValida())
            presenter.login(txtUsuario.getText().toString(), txtClave.getText().toString());
    }

    private void restaurarData(String datas){
        JsonObject data = new JsonParser().parse(datas).getAsJsonObject();
        txtClave.setText(data.get(JsonKeys.pass).getAsString());
        txtUsuario.setText(data.get(JsonKeys.user).getAsString());
    }
}
