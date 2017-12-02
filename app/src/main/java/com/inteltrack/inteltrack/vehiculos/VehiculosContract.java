package com.inteltrack.inteltrack.vehiculos;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inteltrack.inteltrack.domain.BaseView;
import com.inteltrack.inteltrack.vehiculos.models.VehiculoBusqueda;

/**
 * Created by nestorso on 28/11/2017.
 */

public class VehiculosContract {
    public interface View extends BaseView<VehiculosContract.Presenter> {
        void errorDeConexion();
        void abrirWaze(double latitud, double longitud);
        void abrirMaps(double latitud, double longitud);
        void obtenerCoordenadas(String placa, AppConstant app);
        void abrirAplicacion(JsonObject jsonObject);
        void filtrarInfo(VehiculoBusqueda tag);
        void actualizarEtiqueta();
        void abrirPlaystore();
        void crearAdapter(JsonArray jsonArray);
    }

    public interface Presenter {
        void consultarData(boolean isActive);
        void consultarPlaca(String placa);
        void filtrarInfo(String tag);
    }

    public interface ViewActivity{
        void finalizar();
        void cambiarStatus(String texto);

    }

    public enum AppConstant{
        WAZE,
        GOOGLE
    }
}
