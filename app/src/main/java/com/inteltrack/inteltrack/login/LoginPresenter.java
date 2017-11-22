package com.inteltrack.inteltrack.login;


import android.os.Handler;

/**
 * Created by NestorSo on 21/11/2017.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginInteractor.Callback {


    private LoginContract.View mView;
    private LoginInteractor mInteractor;

    public LoginPresenter(LoginContract.View mView, LoginInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
        mView.setPresenter(this);
    }

    @Override
    public void login(final String usuario, final String clave) {
        mView.setProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mInteractor.login(usuario, clave, LoginPresenter.this);
            }
        },2000);

    }

    @Override
    public void onStart() {

    }

    @Override
    public void errorDeConexion() {
        mView.setProgress(false);
        mView.errorDeConexion();
    }

    @Override
    public void auntenticacionCorrecta() {
        mView.setProgress(false);
        mView.finalizar();
    }

    @Override
    public void auntenticacionIncorrecta(String mensaje) {
        mView.setProgress(false);
        mView.message(mensaje);
    }
}
