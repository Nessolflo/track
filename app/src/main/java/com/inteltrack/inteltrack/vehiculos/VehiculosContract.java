package com.inteltrack.inteltrack.vehiculos;

import com.google.gson.JsonArray;
import com.inteltrack.inteltrack.domain.BaseView;

/**
 * Created by nestorso on 28/11/2017.
 */

public class VehiculosContract {
    interface View extends BaseView<VehiculosContract.Presenter> {
        void errorDeConexion();
        void abrirWaze(double latitud, double longitud);
        void abrirMaps(double latitud, double longitud);
        void abrirPlaystore();
        void crearAdapter(JsonArray jsonArray);
    }

    interface Presenter {
        void consultarData();
    }
}
