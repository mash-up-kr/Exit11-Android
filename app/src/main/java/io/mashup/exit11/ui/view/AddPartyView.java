package io.mashup.exit11.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.mashup.exit11.R;
import io.mashup.exit11.data.model.Menu;
import io.mashup.exit11.ui.adapter.AddPartyViewPagerAdapter;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_SETTLING;
import static io.mashup.exit11.data.model.Menu.NONE;

/**
 * Created by jonghunlee on 2017. 12. 11..
 */

public class AddPartyView extends RelativeLayout {

    private static final String TAG = AddPartyView.class.getSimpleName();

    @BindView(R.id.view_background)
    View viewBackground;

    @BindView(R.id.layout_bottom_sheet)
    RelativeLayout rlBottomSheet;

    @BindView(R.id.layout_main)
    ConstraintLayout clMain;

    @BindView(R.id.view_pager)
    SwipeViewPager viewPager;

    @BindView(R.id.layout_up)
    ConstraintLayout clUp;

    @BindView(R.id.text01)
    TextView title;

    @BindView(R.id.choice_progress)
    ProgressBar choiceProgress;

    @BindView(R.id.text_add_party_message)
    TextView tvAddPartyMessage;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.button_next)
    Button btnNext;

    @BindView(R.id.button_prev)
    Button btnPrev;

    private AddPartyViewPagerAdapter pagerAdapter;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<String> hashTags = new ArrayList<>();

    @Menu
    private int menu;

    public AddPartyView(Context context) {
        this(context, null);
    }

    public AddPartyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddPartyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_add_party, this);
        ButterKnife.bind(this, view);

        viewBackground.setAlpha(0f);
        clMain.setAlpha(0.8f);
        clUp.setAlpha(1f);
        tvAddPartyMessage.setVisibility(View.INVISIBLE);

        FragmentActivity activity = (FragmentActivity) getContext();
        AddPartyViewPagerAdapter pagerAdapter = new AddPartyViewPagerAdapter(activity.getSupportFragmentManager());
        pagerAdapter = new AddPartyViewPagerAdapter(activity.getSupportFragmentManager());
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPagingEnabled(true);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("asdf", position + "");

                if(position == 2) {
                    title.setText(getResources().getString(R.string.choice_detail_partiy_info));
                    choiceProgress.setProgress(75);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(rlBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_COLLAPSED:
                        Log.d(TAG, "onStateChanged#STATE_COLLAPSED");
                        //                        pagerAdapter.initAllFragmentData();
                        break;
                    case STATE_SETTLING:
                        //                        Log.d(TAG, "onStateChanged#STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d(TAG, "onSlide#slideOffset : " + slideOffset);

                clUp.setAlpha(1f - slideOffset);

                if (slideOffset > 0.8f) {
                    clMain.setAlpha(slideOffset);
                }

                if (slideOffset < 0.7f) {
                    viewBackground.setAlpha(slideOffset);
                }

                if (slideOffset > 0.75f) {
                    tvAddPartyMessage.setVisibility(View.VISIBLE);
                }
                else {
                    tvAddPartyMessage.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnNext.setEnabled(false);

        setChoiceMenuSubject();
        setHashTagSubject();
    }

    private void setHashTagSubject() {
        pagerAdapter.getHashTagSubject().subscribe(hashTag -> {
            hashTags.add(hashTag);
            if (hashTags.size() == 3) {
                btnNext.setEnabled(true);
            }
        });
    }

    private void setChoiceMenuSubject() {
        pagerAdapter.getMenuChoiceSubject().subscribe(menu -> {
            this.menu = menu;

            if (menu == NONE) {
                btnNext.setEnabled(false);
            }
            else if (!btnNext.isEnabled()) {
                btnNext.setEnabled(true);
            }
        });
    }

    public boolean isExpanded() {
        return bottomSheetBehavior.getState() == STATE_EXPANDED;
    }

    public void setCollapsed() {
        bottomSheetBehavior.setState(STATE_COLLAPSED);
    }

    @OnClick(R.id.button_prev)
    void onClickPrev() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                setCollapsed();
                break;
            case 1:
                btnNext.setEnabled(true);
                tvAddPartyMessage.setText(R.string.choice_menu);
                btnPrev.setText(R.string.close);
                progressBar.setProgress(25);
                viewPager.setCurrentItem(0);
                break;
            case 2:
                progressBar.setProgress(50);
                viewPager.setCurrentItem(1);
                break;
            case 3:
                progressBar.setProgress(75);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    @OnClick(R.id.button_next)
    void onClickNextButton() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                setHashTag();
                break;
            case 1:
                progressBar.setProgress(75);
                viewPager.setCurrentItem(2);
                break;
            case 2:
                progressBar.setProgress(100);
                viewPager.setCurrentItem(3);
                break;
            case 3:
                break;
        }

    }

    private void setHashTag() {
        btnNext.setEnabled(false);
        btnPrev.setText(R.string.prev_step);
        progressBar.setProgress(50);
        tvAddPartyMessage.setText(R.string.add_party_hash_tag);

        hashTags.clear();

        viewPager.setCurrentItem(1);
    }
}
