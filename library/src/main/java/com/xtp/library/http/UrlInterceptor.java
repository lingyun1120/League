package com.xtp.library.http;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class UrlInterceptor implements Interceptor {

    private String mDefault;

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原始的originalRequest
        Request originalRequest = chain.request();
        //获取老的url
        HttpUrl oldUrl = originalRequest.url();
        //获取originalRequest的创建者builder
        Request.Builder builder = originalRequest.newBuilder();
        List<String> urlList = originalRequest.headers("url");
        if (urlList != null && urlList.size() > 0) {
            //删除原有配置中的值,就是namesAndValues集合里的值
            builder.removeHeader("url");
            String urlname = urlList.get(0);
            HttpUrl baseURL = HttpUrl.parse(urlname);
            //重建新的HttpUrl，需要重新设置的url部分
            HttpUrl newHttpUrl = oldUrl.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .port(baseURL.port())//端口
                    .build();
            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);
        } else {
            return chain.proceed(originalRequest);
        }
    }
}