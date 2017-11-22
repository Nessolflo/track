package com.inteltrack.inteltrack.domain;

/**
 * Created by NestorSo on 21/11/2017.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void setProgress(boolean show);
    void message(String mensaje);
    void finalizar();
}
