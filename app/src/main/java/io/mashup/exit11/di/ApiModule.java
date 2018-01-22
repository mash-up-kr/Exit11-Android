package io.mashup.exit11.di;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.mashup.exit11.BuildConfig;
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
 * Created by jonghunlee on 2018. 1. 22..
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        builder.addInterceptor(new NetworkErrorInterceptor());

        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(String baseUrl, OkHttpClient client) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(client);
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setPrettyPrinting()
                .create()));
        builder.addCallAdapterFactory(MainThreadCallAdapter.create(AndroidSchedulers.mainThread()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));

        return builder.build();
    }

    @Provides
    String provideBaseUrl() {
        return "http://gangnam11-backend.azurewebsites.net/api/";
    }
}
