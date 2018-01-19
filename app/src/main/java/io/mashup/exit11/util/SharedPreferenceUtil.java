package io.mashup.exit11.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jonghunlee on 2018. 1. 19..
 */

public class SharedPreferenceUtil {

    private final SharedPreferences preferences;

    public SharedPreferenceUtil(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }

    public void setString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public float getFloat(String key, float defaultValue) {
        return preferences.getFloat(key, defaultValue);
    }

    public void setFloat(String key, float value) {
        preferences.edit().putFloat(key, value).apply();
    }
}
