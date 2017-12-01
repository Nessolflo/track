package com.inteltrack.inteltrack.login;

import com.google.firebase.auth.FirebaseUser;
import com.inteltrack.inteltrack.domain.BaseView;

/**
 * Created by NestorSo on 21/11/2017.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        boolean usuarioValido();
        boolean claveValida();
        void errorDeConexion();
        void autenticacionIncorrecta();
        void login();
    }

    interface Presenter {
        void login(String usuario, String clave);
        void onStart(FirebaseUser user);
    }
}
