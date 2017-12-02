package com.inteltrack.inteltrack.vehiculos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.inteltrack.inteltrack.R;
import com.inteltrack.inteltrack.login.LoginActivity;
import com.inteltrack.inteltrack.vehiculos.adaptadores.VehiculosAdapterFragments;
import com.inteltrack.inteltrack.vehiculos.fragmentos.VehiculosFragment;
import com.inteltrack.inteltrack.vehiculos.models.VehiculoBusqueda;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehiculosActivity extends AppCompatActivity implements VehiculosContract.ViewActivity {

    @BindView(R.id.appbar)
    Toolbar appbar;
    @BindView(R.id.appbartabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private SearchView searchView;
    private VehiculosContract.View vistaListado;
    private VehiculosAdapterFragments adapterFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        ButterKnife.bind(this);
        setToolbar();
        inicializar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buscador, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                EventBus.getDefault().post(new VehiculoBusqueda(s));
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_close_session) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finalizar();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finalizar() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void cambiarStatus(String texto) {
        if(getSupportActionBar()!=null)
            getSupportActionBar().setSubtitle(texto);
    }

    private void inicializar(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(VehiculosFragment.newInstance(true));
        fragments.add(VehiculosFragment.newInstance(false));
        List<String> titulos = new ArrayList<>();
        titulos.add(getString(R.string.activos));
        titulos.add(getString(R.string.inactivos));
        adapterFragments = new VehiculosAdapterFragments(getSupportFragmentManager(),
                fragments,
                titulos);
        viewpager.setAdapter(adapterFragments);
        viewpager.setOffscreenPageLimit(2);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewpager);

    }

    private void setToolbar() {
        appbar.setTitle(getString(R.string.vehiculos));
        setSupportActionBar(appbar);
    }
}
