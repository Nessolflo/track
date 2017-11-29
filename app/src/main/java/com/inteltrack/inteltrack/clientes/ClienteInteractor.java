package com.inteltrack.inteltrack.clientes;

import android.content.Context;

import com.google.gson.JsonArray;
import com.inteltrack.inteltrack.domain.BaseInteractor;

/**
 * Created by nestorso on 28/11/2017.
 */

public class ClienteInteractor  extends BaseInteractor {

    public ClienteInteractor(Context mContext) {
        super(mContext);
    }

    public void obtenerData(Callback callback){
        callback.cargaCorrecta(new JsonArray());
    }

    interface Callback{
        void cargaCorrecta(JsonArray jsonArray);
        void cargaIncorrecta(String mensaje);
    }
}
