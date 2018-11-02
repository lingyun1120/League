package com.xtp.league;

import android.app.Application;

import com.xtp.league.http.HeaderInterceptor;
import com.xtp.library.XLibrary;

public class App extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        XLibrary.init(application, BuildConfig.DEBUG, "http://gank.io/api/", new HeaderInterceptor());
    }

    public static Application getApplication() {
        return application;
    }
}
