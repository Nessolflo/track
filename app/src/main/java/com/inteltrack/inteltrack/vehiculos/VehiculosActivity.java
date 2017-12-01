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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inteltrack.inteltrack.R;
import com.inteltrack.inteltrack.domain.JsonKeys;
import com.inteltrack.inteltrack.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehiculosActivity extends AppCompatActivity implements VehiculosContract.View {

    @BindView(R.id.appbar)
    Toolbar appbar;
    @BindView(R.id.listaVehiculos)
    RecyclerView listaVehiculos;
    @BindView(R.id.progress)
    ProgressBar progress;
    private SearchView searchView;
    private VehiculosContract.Presenter presenter;
    private VehiculosAdapter adapter;
    private VehiculosContract.AppConstant appConstant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        ButterKnife.bind(this);
        setToolbar();
        new VehiculosPresenter(this, new VehiculosInteractor(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.buscador, menu);
        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                presenter.filtrarInfo(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.action_close_session){
            FirebaseAuth mAuth= FirebaseAuth.getInstance();
            mAuth.signOut();
            finalizar();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void setPresenter(VehiculosContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.consultarData();
    }

    @Override
    public void setProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
        listaVehiculos.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void message(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finalizar() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
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
    public void obtenerCoordenadas(String placa, VehiculosContract.AppConstant app) {
        presenter.consultarPlaca(placa);
        this.appConstant = app;
    }

    @Override
    public void abrirAplicacion(JsonObject jsonObject) {
        if(jsonObject!=null && jsonObject.get(JsonKeys.latitud)!=null && jsonObject.get(JsonKeys.longitud)!=null) {
            final double latitud = jsonObject.get(JsonKeys.latitud).getAsDouble();
            final double longitud = jsonObject.get(JsonKeys.longitud).getAsDouble();
            if (appConstant != null) {
                switch (appConstant) {
                    case WAZE:
                        abrirWaze(latitud, longitud);
                        break;
                    case GOOGLE:
                        abrirMaps(latitud, longitud);
                        break;
                }
            }
        }else
            message(getString(R.string.nodata));
    }

    @Override
    public void abrirPlaystore() {

    }

    @Override
    public void crearAdapter(JsonArray jsonArray) {
        setProgress(false);
        if(adapter==null) {
            adapter = new VehiculosAdapter(jsonArray);
            adapter.setView(this);
            listaVehiculos.setHasFixedSize(true);
            listaVehiculos.setAdapter(adapter);
            listaVehiculos.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            listaVehiculos.setItemAnimator(new DefaultItemAnimator());
        }else
            adapter.setData(jsonArray);

    }

    private void setToolbar(){
        appbar.setTitle(getString(R.string.vehiculos));
        setSupportActionBar(appbar);
    }
}
