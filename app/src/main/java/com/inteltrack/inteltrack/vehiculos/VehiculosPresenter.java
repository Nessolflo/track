package com.inteltrack.inteltrack.vehiculos;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inteltrack.inteltrack.domain.JsonKeys;
import com.inteltrack.inteltrack.domain.io.ConstantsUrls;

/**
 * Created by nestorso on 28/11/2017.
 */

public class VehiculosPresenter implements VehiculosContract.Presenter, VehiculosInteractor.Callback {

    private final VehiculosContract.View view;
    private final VehiculosInteractor interactor;
    private JsonArray jsonData;

    public VehiculosPresenter(VehiculosContract.View view, VehiculosInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        view.setPresenter(this);
    }

    @Override
    public void consultarData(boolean isActive) {
        view.setProgress(true);
        interactor.buscarData( isActive? ConstantsUrls.Status.active: ConstantsUrls.Status.inactive, this);
    }

    @Override
    public void consultarPlaca(String placa) {
        view.setProgress(true);
        interactor.buscarPlaca(placa, this);
    }

    @Override
    public void filtrarInfo(String tag) {
        JsonArray temp = new JsonArray();
        if (tag.isEmpty())
            temp = jsonData;
        else if (jsonData != null && jsonData.size() > 0) {
            for (int i = 0; i < jsonData.size(); i++) {
                final JsonObject data = jsonData.get(i).getAsJsonObject();
                final String placa = data.get(JsonKeys.placa).getAsString().toLowerCase().trim();
                final String nombre = data.get(JsonKeys.nombre).getAsString().toLowerCase().trim();
                final String propietario = data.get(JsonKeys.propietario).getAsString().toLowerCase().trim();
                final String grupo = data.get(JsonKeys.grupo).getAsString().toLowerCase().trim();
                tag = tag.toLowerCase();
                if (placa.contains(tag) || nombre.contains(tag) ||
                        propietario.contains(tag) || grupo.contains(tag)) {
                    temp.add(data);
                }
            }
        }
        view.crearAdapter(temp);
    }

    @Override
    public void datosPlaca(JsonObject jsonObject) {
        view.setProgress(false);
        view.abrirAplicacion(jsonObject);
    }

    @Override
    public void cargaCorrecta(JsonArray jsonArray) {
        this.jsonData = jsonArray;
        view.crearAdapter(jsonArray);

    }

    @Override
    public void cargaIncorrecta(String mensaje) {
        view.errorDeConexion();
    }
}
