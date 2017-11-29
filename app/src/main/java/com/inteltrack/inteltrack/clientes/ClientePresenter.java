package com.inteltrack.inteltrack.clientes;

import com.google.gson.JsonArray;

/**
 * Created by nestorso on 28/11/2017.
 */

public class ClientePresenter implements ClienteContract.Presenter, ClienteInteractor.Callback {

    private final ClienteContract.View view;
    private final ClienteInteractor interactor;

    public ClientePresenter(ClienteContract.View view, ClienteInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        view.setPresenter(this);
    }

    @Override
    public void consultarData() {
        interactor.obtenerData(this);
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
