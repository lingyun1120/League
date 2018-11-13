package com.xtp.league;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xtp.league.http.HeaderInterceptor;
import com.xtp.library.XLibrary;

public class App extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        XLibrary.init(application, BuildConfig.DEBUG, "http://gank.io/api/", new HeaderInterceptor());
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);
    }

    public static Application getApplication() {
        return application;
    }
}
