package io.mashup.exit11.data.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static io.mashup.exit11.data.model.Menu.CHICKEN;
import static io.mashup.exit11.data.model.Menu.CHINESE;
import static io.mashup.exit11.data.model.Menu.ETC;
import static io.mashup.exit11.data.model.Menu.HAMBURGER;
import static io.mashup.exit11.data.model.Menu.JAPANESE;
import static io.mashup.exit11.data.model.Menu.KOREAN;
import static io.mashup.exit11.data.model.Menu.NONE;
import static io.mashup.exit11.data.model.Menu.PIZZA;
import static io.mashup.exit11.data.model.Menu.PORK_FEET;
import static io.mashup.exit11.data.model.Menu.SNACK_BAR;

/**
 * Created by jonghunlee on 2017. 12. 19..
 */

@IntDef({NONE, PIZZA, HAMBURGER, CHICKEN, CHINESE, KOREAN, SNACK_BAR, PORK_FEET, JAPANESE, ETC})
@Retention(RetentionPolicy.SOURCE)
public @interface Menu {

    int NONE = -1;
    int PIZZA = 0;
    int HAMBURGER = 1;
    int CHICKEN = 2;
    int CHINESE = 3;
    int KOREAN = 4;
    int SNACK_BAR = 5;
    int PORK_FEET = 6;
    int JAPANESE = 7;
    int ETC = 8;
}