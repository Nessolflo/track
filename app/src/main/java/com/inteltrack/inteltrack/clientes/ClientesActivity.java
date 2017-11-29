package com.inteltrack.inteltrack.clientes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inteltrack.inteltrack.R;
import com.inteltrack.inteltrack.login.LoginPresenter;
import com.inteltrack.inteltrack.vehiculos.VehiculosActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientesActivity extends AppCompatActivity implements ClienteContract.View {

    public static final String KEY_VEHICULO= "vehiculo";

    @BindView(R.id.appbar)
    Toolbar appbar;
    @BindView(R.id.listaClientes)
    RecyclerView listaClientes;
    @BindView(R.id.progress)
    ProgressBar progress;
    private ClienteContract.Presenter presenter;
    private ClientesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        ButterKnife.bind(this);
        setToolbar();
        new ClientePresenter(this, new ClienteInteractor(this));
    }

    @Override
    public void setPresenter(ClienteContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.consultarData();
    }

    @Override
    public void setProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void message(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finalizar() {
        finish();
    }

    @Override
    public void errorDeConexion() {
        message(getString(R.string.conexioninternet));
    }

    @Override
    public void crearAdapter(JsonArray jsonArray) {
        adapter = new ClientesAdapter(this);
        adapter.setView(this);
        listaClientes.setHasFixedSize(true);
        listaClientes.setAdapter(adapter);
        listaClientes.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        listaClientes.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaClientes.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void abrirFlotilla(JsonObject cliente) {
        Intent i = new Intent(this, VehiculosActivity.class);
        i.putExtra(KEY_VEHICULO, cliente.toString());
        startActivity(i);
        finalizar();
    }

    private void setToolbar(){
        appbar.setTitle(getString(R.string.clientes));
        setSupportActionBar(appbar);
    }
}
