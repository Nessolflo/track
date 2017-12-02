package com.inteltrack.inteltrack.vehiculos.fragmentos;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inteltrack.inteltrack.R;
import com.inteltrack.inteltrack.domain.JsonKeys;
import com.inteltrack.inteltrack.vehiculos.VehiculosContract;
import com.inteltrack.inteltrack.vehiculos.VehiculosInteractor;
import com.inteltrack.inteltrack.vehiculos.VehiculosPresenter;
import com.inteltrack.inteltrack.vehiculos.adaptadores.VehiculosAdapter;
import com.inteltrack.inteltrack.vehiculos.models.VehiculoBusqueda;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class VehiculosFragment extends Fragment implements VehiculosContract.View, SwipeRefreshLayout.OnRefreshListener {
    private static final String ARG_PARAM1 = "active";
    @BindView(R.id.listaVehiculos)
    RecyclerView listaVehiculos;
    @BindView(R.id.progress)
    ProgressBar progress;
    Unbinder unbinder;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.txtEstatus)
    TextView txtEstatus;
    private boolean isActive;
    private String tag = "";

    private VehiculosContract.Presenter presenter;
    private VehiculosAdapter adapter;
    private VehiculosContract.AppConstant appConstant;


    public VehiculosFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static VehiculosFragment newInstance(boolean isActive) {
        VehiculosFragment fragment = new VehiculosFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, isActive);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isActive = getArguments().getBoolean(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vehiculos, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefresh.setOnRefreshListener(this);
        new VehiculosPresenter(this, new VehiculosInteractor(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        presenter.consultarData(isActive);
    }

    @Override
    public void setPresenter(VehiculosContract.Presenter presenter) {
        this.presenter = presenter;
        presenter.consultarData(isActive);
    }

    @Override
    public void setProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
        listaVehiculos.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void message(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finalizar() {

    }

    @Override
    public void errorDeConexion() {
        setProgress(false);
        message(getString(R.string.conexioninternet));
    }

    @Override
    public void abrirWaze(double latitud, double longitud) {
        try {
            String uri = "waze://?ll=" + latitud + "," + longitud + "&navigate=yes";
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(uri)));
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            startActivity(intent);
        }
    }

    @Override
    public void abrirMaps(double latitud, double longitud) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitud + "," + longitud);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
        if (jsonObject != null && jsonObject.get(JsonKeys.latitud) != null && jsonObject.get(JsonKeys.longitud) != null) {
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
        } else
            message(getString(R.string.nodata));
    }

    @Override
    public void abrirPlaystore() {

    }

    @Override
    public void crearAdapter(JsonArray jsonArray) {
        setProgress(false);
        if (adapter == null) {
            adapter = new VehiculosAdapter(jsonArray);
            adapter.setView(this);
            listaVehiculos.setNestedScrollingEnabled(true);
            listaVehiculos.setHasFixedSize(true);
            listaVehiculos.setAdapter(adapter);
            listaVehiculos.setLayoutManager(
                    new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            listaVehiculos.setItemAnimator(new DefaultItemAnimator());
        } else
            adapter.setData(jsonArray);


    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void filtrarInfo(VehiculoBusqueda tag) {
        this.tag = tag.getTag();
        presenter.filtrarInfo(tag.getTag());
        if (!tag.getTag().isEmpty())
            swipeRefresh.setEnabled(false);
        else
            swipeRefresh.setEnabled(true);
    }

    @Override
    public void actualizarEtiqueta() {
        txtEstatus.setText(String.format(getString(R.string.ultimaactualizacion), getDate()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        return mdformat.format(calendar.getTime());
    }
}
