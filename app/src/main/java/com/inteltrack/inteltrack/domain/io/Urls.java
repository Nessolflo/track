package com.inteltrack.inteltrack.domain.io;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by NestorSo on 21/11/2017.
 */

public interface Urls {
    @POST(ConstantsUrls.URL_LOGIN)
    Call<JsonElement> login(@Query(ConstantsUrls.Params.USER) String usuario,
                            @Query(ConstantsUrls.Params.PASSWORD) String password);
    @GET(ConstantsUrls.URL_FLOTILLA)
    Call<JsonElement> flotillas();
    @GET(ConstantsUrls.URL_COORDENADAS)
    Call<JsonElement> coordenadas(@Path(ConstantsUrls.Params.PLACA) String placa);
}
