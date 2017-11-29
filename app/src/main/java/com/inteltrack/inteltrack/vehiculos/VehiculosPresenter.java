package com.inteltrack.inteltrack.vehiculos;

import com.google.gson.JsonArray;

/**
 * Created by nestorso on 28/11/2017.
 */

public class VehiculosPresenter  implements VehiculosContract.Presenter, VehiculosInteractor.Callback {

    private final VehiculosContract.View view;
    private final VehiculosInteractor interactor;

    public VehiculosPresenter(VehiculosContract.View view, VehiculosInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        view.setPresenter(this);
    }

    @Override
    public void consultarData() {
        interactor.buscarData(this);
    }

    @Override
    public void cargaCorrecta(JsonArray jsonArray) {
        view.crearAdapter(jsonArray);
    }

    @Override
    public void cargaIncorrecta(String mensaje) {
        view.errorDeConexion();
    }
}
