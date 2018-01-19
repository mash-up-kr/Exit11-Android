package io.mashup.exit11.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.mashup.exit11.util.SharedPreferenceUtil;

/**
 * Created by jonghunlee on 2018. 1. 19..
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    SharedPreferenceUtil provideSharedPreferenceUtil(Context context) {
        return new SharedPreferenceUtil(context);
    }
}
