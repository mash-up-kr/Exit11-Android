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
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

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
 * Created by jonghunlee on 2017. 12. 13..
 */

public class ChoiceMenuFragment extends Fragment {


    private PublishSubject<Integer> menuChoice = PublishSubject.create();

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
        initButtons();

        switch (view.getId()) {
            case R.id.button_pizza:
                menuButtons.get(PIZZA).setSelected(true);
                menuChoice.onNext(PIZZA);
                break;
            case R.id.button_hamburger:
                menuButtons.get(HAMBURGER).setSelected(true);
                menuChoice.onNext(HAMBURGER);
                break;
            case R.id.button_chicken:
                menuButtons.get(CHICKEN).setSelected(true);
                menuChoice.onNext(CHICKEN);
                break;
            case R.id.button_chinese:
                menuButtons.get(CHINESE).setSelected(true);
                menuChoice.onNext(CHINESE);
                break;
            case R.id.button_korean:
                menuButtons.get(KOREAN).setSelected(true);
                menuChoice.onNext(KOREAN);
                break;
            case R.id.button_snackbar:
                menuButtons.get(SNACK_BAR).setSelected(true);
                menuChoice.onNext(SNACK_BAR);
                break;
            case R.id.button_porkfeet:
                menuButtons.get(PORK_FEET).setSelected(true);
                menuChoice.onNext(PORK_FEET);
                break;
            case R.id.button_japanese:
                menuButtons.get(JAPANESE).setSelected(true);
                menuChoice.onNext(JAPANESE);
                break;
            case R.id.button_etc:
                menuButtons.get(ETC).setSelected(true);
                menuChoice.onNext(ETC);
                break;
        }

    }

    private void initButtons() {
        for (ImageButton ib : menuButtons) {
            ib.setSelected(false);
        }
    }


    public Observable<Integer> getMenuChoiceSubject() {
        return menuChoice;
    }

    public void init() {
        initButtons();
        menuChoice.onNext(NONE);
    }
}
