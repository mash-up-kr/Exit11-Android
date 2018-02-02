package io.mashup.exit11.ui.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.mashup.exit11.R;
import io.mashup.exit11.data.model.Party;

import static io.mashup.exit11.data.model.Menu.CHICKEN;
import static io.mashup.exit11.data.model.Menu.CHINESE;
import static io.mashup.exit11.data.model.Menu.ETC;
import static io.mashup.exit11.data.model.Menu.HAMBURGER;
import static io.mashup.exit11.data.model.Menu.JAPANESE;
import static io.mashup.exit11.data.model.Menu.KOREAN;
import static io.mashup.exit11.data.model.Menu.PIZZA;
import static io.mashup.exit11.data.model.Menu.PORK_FEET;
import static io.mashup.exit11.data.model.Menu.SNACK_BAR;

/**
 * Created by jonghunlee on 2018. 2. 3..
 */

public class PartyInfoView extends RelativeLayout {

    private static final String TAG = PartyInfoView.class.getSimpleName();

    @BindView(R.id.image_menu)
    ImageView ivMenu;

    @BindView(R.id.text_hash_tag)
    TextView tvHashTag;

    @BindView(R.id.text_time)
    TextView tvTime;

    @BindView(R.id.text_address)
    TextView tvAddress;

    public PartyInfoView(Context context) {
        this(context, null);
    }

    public PartyInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartyInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_party_info, this);
        ButterKnife.bind(this, view);
    }

    public void setPartyInfo(Party party) {
        setMenuImage(party.getFoodId());
        setHashTag(party.getTags());

        tvTime.setText(party.getMeetTime());

        setAddress(party.getLatitude(), party.getLongitude());
    }

    private void setHashTag(List<String> tags) {
        if (tags == null) {
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (String tag : tags) {
            builder.append("#" + tag + " ");
        }

        tvHashTag.setText(builder.toString());
    }

    private void setMenuImage(int foodId) {
        switch (foodId) {
            case PIZZA:
                ivMenu.setImageResource(R.drawable.menu_pizza);
                break;
            case HAMBURGER:
                ivMenu.setImageResource(R.drawable.menu_hamburger);
                break;
            case CHICKEN:
                ivMenu.setImageResource(R.drawable.menu_chicken);
                break;
            case CHINESE:
                ivMenu.setImageResource(R.drawable.menu_chinese);
                break;
            case KOREAN:
                ivMenu.setImageResource(R.drawable.menu_korean);
                break;
            case SNACK_BAR:
                ivMenu.setImageResource(R.drawable.menu_snackbar);
                break;
            case PORK_FEET:
                ivMenu.setImageResource(R.drawable.menu_porkfeet);
                break;
            case JAPANESE:
                ivMenu.setImageResource(R.drawable.menu_japanese);
                break;
            case ETC:
                ivMenu.setImageResource(R.drawable.menu_etc);
                break;
        }
    }

    private void setAddress(float latitude, float longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        String choiceAddress = "";
        
        try {
            Address address = geocoder.getFromLocation(latitude, longitude, 1).get(0);
            Log.d(TAG, "OnClickChoiceLocation#address : " + address.toString());
            choiceAddress = address.getAddressLine(0);
        }
        catch (IOException e) {
            Log.e(TAG, "OnClickChoiceLocation#getFromLocation", e);
        }

        tvAddress.setText(choiceAddress);
    }

    @OnClick(R.id.button_close)
    void onClickClose() {
        setVisibility(View.GONE);
    }

    @OnClick(R.id.button_join)
    void onClickJoin() {
        // TODO: 2018. 2. 3. 파티 참가
    }
}
