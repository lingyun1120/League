package com.xtp.league.http;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        try {
            Request.Builder builder = originalRequest.newBuilder();
//            builder.addHeader("Content-Type", "json");
//            builder.addHeader("access-token", "");
            Request newRequest = builder.build();
            Logger.i(newRequest.headers().toString());
            return chain.proceed(newRequest);
        } catch (Exception e) {
            return chain.proceed(originalRequest);
        }

    }
}
