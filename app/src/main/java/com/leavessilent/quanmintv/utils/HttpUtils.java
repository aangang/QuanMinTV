package com.leavessilent.quanmintv.utils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.leavessilent.quanmintv.common.api.QuanMinService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/2/16.
 */

public class HttpUtils {
    private static Retrofit sRetrofit = null;
    private static QuanMinService sService = null;

    public static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (HttpUtils.class) {
                if (sRetrofit == null) {
                    Retrofit.Builder builder = new Retrofit.Builder();
                    sRetrofit = builder.baseUrl("http://www.quanmin.tv/json/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    public static QuanMinService getService() {
        if (sService == null) {
            synchronized (HttpUtils.class) {
                if (sService == null) {
                    sService = getRetrofit().create(QuanMinService.class);
                }
            }
        }
        return sService;
    }
}
