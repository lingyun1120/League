package com.xtp.library.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {
    public static final int TYPE_NONE = 0;//no network
    public static final int TYPE_MOBILE = 1;//mobile data
    public static final int TYPE_WIFI = 2;//wifi


    public static int getNetWorkStates(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return TYPE_NONE;
        }

        int type = activeNetworkInfo.getType();
        switch (type) {
            case ConnectivityManager.TYPE_MOBILE:
                return TYPE_MOBILE;
            case ConnectivityManager.TYPE_WIFI:
                return TYPE_WIFI;
            default:
                break;
        }
        return TYPE_NONE;
    }
}
