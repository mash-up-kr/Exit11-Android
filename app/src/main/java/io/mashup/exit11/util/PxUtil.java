package io.mashup.exit11.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by jonghunlee on 2018. 2. 3..
 */

public class PxUtil {

    public static float convertPxToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPx(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
