package io.mashup.exit11.di.fragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import io.mashup.exit11.ui.fragment.MapFragment;

/**
 * Created by jonghunlee on 2018. 1. 19..
 */

@Module
public abstract class MapFragmentProvider {

    @ContributesAndroidInjector(modules = MapFragmentModule.class)
    abstract MapFragment provideMapFragmentFactory();
}
