package io.mashup.exit11.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.mashup.exit11.R;

/**
 * Created by jonghunlee on 2017. 12. 13..
 */

public class ChoiceMenuFragment extends Fragment {

    public static ChoiceMenuFragment newInstance() {
        return new ChoiceMenuFragment();
    }

    @BindViews({R.id.button_pizza, R.id.button_hamburger, R.id.button_chicken, R.id.button_chinese,
                R.id.button_korean, R.id.button_snackbar, R.id.button_porkfeet, R.id.button_japanese,
                R.id.button_etc})
    List<ImageButton> menuButtons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.button_pizza, R.id.button_hamburger, R.id.button_chicken, R.id.button_chinese,
              R.id.button_korean, R.id.button_snackbar, R.id.button_porkfeet, R.id.button_japanese,
              R.id.button_etc})
    void onClickMenuButtons(View view) {
        for (ImageButton ib : menuButtons) {
            ib.setSelected(false);
        }

        menuButtons.get(menuButtons.indexOf(view)).setSelected(true);
    }
}
