package com.inteltrack.inteltrack.login;

import android.content.Context;

import com.inteltrack.inteltrack.domain.BaseInteractor;

/**
 * Created by NestorSo on 21/11/2017.
 */

public class LoginInteractor extends BaseInteractor {

    public LoginInteractor(Context mContext) {
        super(mContext);
    }

    public void login(String usuario, String clave, Callback callback){
        if(isNetworkAvailable()){
            if(usuario.equalsIgnoreCase("admin"))
                callback.auntenticacionCorrecta();
            else
                callback.auntenticacionIncorrecta("Error desde retrofit");
        }
        else
            callback.errorDeConexion();
    }

    interface Callback{
        void errorDeConexion();
        void auntenticacionCorrecta();
        void auntenticacionIncorrecta(String mensaje);
    }
}
