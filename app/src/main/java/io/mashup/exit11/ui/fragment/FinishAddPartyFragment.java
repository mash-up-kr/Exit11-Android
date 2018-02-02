package io.mashup.exit11.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import io.mashup.exit11.R;
import io.mashup.exit11.data.model.AddParty;

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

public class FinishAddPartyFragment extends Fragment {

    public static FinishAddPartyFragment newInstance() {
        return new FinishAddPartyFragment();
    }

    @BindView(R.id.image_menu)
    ImageView ivMenu;

    @BindViews({R.id.text_hash_tag01, R.id.text_hash_tag02, R.id.text_hash_tag03})
    List<TextView> tvHashTags;

    @BindView(R.id.text_address)
    TextView tvAddress;

    @BindView(R.id.text_time)
    TextView tvTime;

    @BindView(R.id.text_member)
    TextView tvMember;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finish_add_party, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
    }

    public void setFinishAddParty(AddParty addParty) {
        setMenuImage(addParty.getMenu());
        setHashTags(addParty.getHashTags());

        tvAddress.setText(addParty.getPartyDetail().getAddress());
        tvTime.setText(addParty.getPartyDetail().getDate());

        String totalMember = "총" + addParty.getPartyDetail().getPartyMember() + "명";
        tvMember.setText(totalMember);
    }

    private void setMenuImage(int menu) {
        switch (menu) {
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

    private void setHashTags(List<String> hashTags) {
        for (int i = 0; i < 3; i++) {
            String hashTag = "#" + hashTags.get(i);
            tvHashTags.get(i).setText(hashTag);
        }
    }
}
