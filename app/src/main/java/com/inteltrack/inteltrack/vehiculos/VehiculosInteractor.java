package com.inteltrack.inteltrack.vehiculos;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.inteltrack.inteltrack.domain.BaseInteractor;
import com.inteltrack.inteltrack.domain.io.ConstantsUrls;
import com.inteltrack.inteltrack.domain.io.RetroFitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nestorso on 28/11/2017.
 */

public class VehiculosInteractor extends BaseInteractor {

    public VehiculosInteractor(Context mContext) {
        super(mContext);
    }

    public void buscarData(ConstantsUrls.Status status, final Callback callback){
        RetroFitHelper.getApiServices().flotillas(status.name()).enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(callback!=null && response.body()!=null && response.body() instanceof JsonArray){
                    callback.cargaCorrecta(response.body().getAsJsonArray());
                }else if(callback!=null)
                    callback.cargaIncorrecta("");
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                callback.cargaIncorrecta(t.getLocalizedMessage());
            }
        });
        callback.cargaCorrecta(new JsonArray());
    }
    public void buscarPlaca(String placa, final Callback callback){
        RetroFitHelper.getApiServices().coordenadas(placa.toLowerCase()).enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(callback!=null && response.body()!=null && response.body().getAsJsonObject()!=null){
                    callback.datosPlaca(response.body().getAsJsonObject());
                }else if(callback!=null)
                    callback.datosPlaca(new JsonObject());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                callback.cargaIncorrecta(t.getLocalizedMessage());
            }
        });
    }
    interface Callback{
        void cargaCorrecta(JsonArray jsonArray);
        void datosPlaca(JsonObject jsonObject);
        void cargaIncorrecta(String mensaje);
    }
}
