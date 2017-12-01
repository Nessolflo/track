package com.inteltrack.inteltrack.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.inteltrack.inteltrack.domain.BaseInteractor;

/**
 * Created by NestorSo on 21/11/2017.
 */

public class LoginInteractor extends BaseInteractor {

    public LoginInteractor(Context mContext) {
        super(mContext);
    }

    public void login(String usuario, String clave, final Callback callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (isNetworkAvailable()) {
            mAuth.signInWithEmailAndPassword(usuario, clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        callback.auntenticacionCorrecta();
                    } else
                        callback.auntenticacionIncorrecta();

                }
            });
        } else
            callback.errorDeConexion();
    }

    interface Callback {
        void errorDeConexion();

        void auntenticacionCorrecta();

        void auntenticacionIncorrecta();
    }
}
