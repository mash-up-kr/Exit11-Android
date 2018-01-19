package io.mashup.exit11.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.reactivex.annotations.Nullable;

/**
 * Created by jonghunlee on 2018. 1. 19..
 */

public class SharedPreferenceUtil {

    private SharedPreferences preferences;

    public static SharedPreferenceUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Nullable
    public String getString(Context context, String key) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return preferences.getString(key, null);
    }

    public void setString(Context context, String key, String value) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        preferences.edit().putString(key, value).apply();
    }

    @Nullable
    public float getFloat(Context context, String key, float defaultValue) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return preferences.getFloat(key, defaultValue);
    }

    public void setFloat(Context context, String key, float value) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        preferences.edit().putFloat(key, value).apply();
    }

    private static class LazyHolder {

        private static final SharedPreferenceUtil INSTANCE = new SharedPreferenceUtil();
    }
}
