package com.inteltrack.inteltrack.vehiculos;

import android.content.Context;

import com.google.gson.JsonArray;
import com.inteltrack.inteltrack.domain.BaseInteractor;

/**
 * Created by nestorso on 28/11/2017.
 */

public class VehiculosInteractor extends BaseInteractor {

    public VehiculosInteractor(Context mContext) {
        super(mContext);
    }

    public void buscarData(Callback callback){
        callback.cargaCorrecta(new JsonArray());
    }

    interface Callback{
        void cargaCorrecta(JsonArray jsonArray);
        void cargaIncorrecta(String mensaje);
    }
}
