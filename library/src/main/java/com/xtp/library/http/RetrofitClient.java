package com.xtp.library.http;

import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private HashMap<String, Object> mServiceCache = new HashMap<>();

    private static RetrofitClient mInstance;

    public RetrofitClient() {

    }

    public synchronized static RetrofitClient getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                mInstance = new RetrofitClient();
            }
        }
        return mInstance;
    }

    public void init(String baseUrl, Interceptor customInterceptor) {
        if (mRetrofit == null) {
            if (mOkHttpClient == null) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NonNull String message) {
                        Logger.i(message);
                    }
                });

                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                mOkHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .addInterceptor(customInterceptor)
                        .build();
            }

            mRetrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    public <T> T obtainService(Class<T> apiService) {
        T retrofitService = (T) mServiceCache.get(apiService.getCanonicalName());
        if (retrofitService == null) {
            retrofitService = mRetrofit.create(apiService);
            mServiceCache.put(apiService.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    public static RequestBody jsonToRequestBody(JSONObject json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
    }
}
