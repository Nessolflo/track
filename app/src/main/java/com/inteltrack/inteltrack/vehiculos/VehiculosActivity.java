package com.inteltrack.inteltrack.vehiculos;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import com.inteltrack.inteltrack.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehiculosActivity extends AppCompatActivity implements VehiculosContract.View {

    @BindView(R.id.appbar)
    Toolbar appbar;
    @BindView(R.id.listaVehiculos)
    RecyclerView listaVehiculos;
    @BindView(R.id.progress)
    ProgressBar progress;

    private VehiculosContract.Presenter presenter;
    private VehiculosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        ButterKnife.bind(this);
        setToolbar();
        new VehiculosPresenter(this, new VehiculosInteractor(this));
    }

    @Override
    public void setPresenter(VehiculosContract.Presenter presenter) {
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
    public void abrirWaze(double latitud, double longitud) {
        try {
            String uri = "waze://?ll=" + latitud + "," + longitud+"&navigate=yes";
            startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(uri)));
        }catch ( ActivityNotFoundException ex  )
        {
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
            startActivity(intent);
        }
    }

    @Override
    public void abrirMaps(double latitud, double longitud) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitud+","+longitud);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public void abrirPlaystore() {

    }

    @Override
    public void crearAdapter(JsonArray jsonArray) {
        adapter= new VehiculosAdapter();
        adapter.setView(this);
        listaVehiculos.setHasFixedSize(true);
        listaVehiculos.setAdapter(adapter);
        listaVehiculos.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        listaVehiculos.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaVehiculos.setItemAnimator(new DefaultItemAnimator());
    }

    private void setToolbar(){
        appbar.setTitle(getString(R.string.vehiculos));
        setSupportActionBar(appbar);
    }
}
