package io.mashup.exit11.data.remote;

import io.mashup.exit11.BuildConfig;
import io.mashup.exit11.data.remote.api.ApiService;
import io.mashup.exit11.data.remote.util.MainThreadCallAdapter;
import io.mashup.exit11.data.remote.util.NetworkErrorInterceptor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jonghunlee on 2017. 11. 5..
 */

public class NetworkManager {

    private static final String BASE_URL = "";

    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }

        return instance;
    }

    private ApiService service;

    private NetworkManager() {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);
            okhttpBuilder.addInterceptor(logger);
        }

        okhttpBuilder.addInterceptor(new NetworkErrorInterceptor());

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(BASE_URL);
        retrofitBuilder.addCallAdapterFactory(MainThreadCallAdapter.create(AndroidSchedulers.mainThread()));
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.client(okhttpBuilder.build());

        service = retrofitBuilder.build().create(ApiService.class);
    }

    public ApiService getService() {
        return service;
    }
}
