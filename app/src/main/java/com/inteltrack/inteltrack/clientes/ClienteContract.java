package com.inteltrack.inteltrack.clientes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inteltrack.inteltrack.domain.BaseView;

/**
 * Created by nestorso on 28/11/2017.
 */

public class ClienteContract {
    interface View extends BaseView<ClienteContract.Presenter> {
        void errorDeConexion();
        void abrirFlotilla(JsonObject cliente);
        void crearAdapter(JsonArray jsonArray);
    }

    interface Presenter {
        void consultarData();
    }
}
