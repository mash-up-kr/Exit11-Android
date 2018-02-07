package io.mashup.exit11.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.mashup.exit11.di.fragment.MapFragmentProvider;
import io.mashup.exit11.ui.activity.MainActivity;

/**
 * Created by jonghunlee on 2018. 1. 19..
 */

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = MapFragmentProvider.class)
    abstract MainActivity bindMainActivity();

}
