package com.xtp.library;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.xtp.library.http.RetrofitClient;
import com.xtp.library.logger.CustomFormatStrategy;
import com.xtp.library.logger.FileFormatStrategy;
import com.xtp.library.logger.FileLogAdapter;
import com.xtp.library.logger.LogCatStrategy;

import okhttp3.Interceptor;

public class XLibrary {
    private static Application application;
    private static boolean isDebug = false;

    public static void init(Application app, boolean debug, String baseUrl, Interceptor interceptor) {
        application = app;
        isDebug = debug;

        FormatStrategy formatStrategy = CustomFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .logStrategy(new LogCatStrategy())
                .tag("X-LIB")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return isDebug;
            }
        });

        FileFormatStrategy fileFormatStrategy = FileFormatStrategy
                .newBuilder()
                .tag("X-LIB")
                .build();
        Logger.addLogAdapter(new FileLogAdapter(fileFormatStrategy));

        RetrofitClient.getInstance().init(baseUrl, interceptor);
    }

    public static Application getApplication() {
        return application;
    }
}
