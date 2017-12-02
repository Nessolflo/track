package com.inteltrack.inteltrack.domain.io;

/**
 * Created by NestorSo on 21/11/2017.
 */

public class ConstantsUrls {
    public static final String IP = "http://inteltrackgt.com:3000";
    public static final String PATH = "";

    public static final String URL_LOGIN = PATH+"/login";
    public static final String URL_FLOTILLA = PATH+"/flotillas";
    public static final String URL_COORDENADAS = PATH+"/flotillas/{"+Params.PLACA+"}/position";

    public class Params{
        public static final String USER = "user";
        public static final String PASSWORD = "password";
        public static final String PLACA ="placa";
        public static final String STATUS = "status";
    }

    public enum Status{
        active,
        inactive
    }
}
