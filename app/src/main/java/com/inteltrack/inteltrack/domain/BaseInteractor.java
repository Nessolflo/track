package com.inteltrack.inteltrack.domain;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by NestorSo on 21/11/2017.
 */

public class BaseInteractor {
    private final Context mContext;

    public BaseInteractor(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connMgr =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public Context getContext() {
        return mContext;
    }
}
