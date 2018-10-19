package com.xtp.league;

import android.app.Application;

import com.xtp.league.http.HeaderInterceptor;
import com.xtp.library.XLibrary;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XLibrary.init(this, BuildConfig.DEBUG, "http://gank.io/api/", new HeaderInterceptor());
    }
}
