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

import butterknife.BindView;
import butterknife.ButterKnife;
import io.mashup.exit11.R;
import io.mashup.exit11.ui.adapter.AddPartyViewPagerAdapter;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_SETTLING;

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

    private BottomSheetBehavior bottomSheetBehavior;

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

        FragmentActivity activity = (FragmentActivity) getContext();
        AddPartyViewPagerAdapter pagerAdapter = new AddPartyViewPagerAdapter(activity.getSupportFragmentManager());
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

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(rlBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_COLLAPSED:
                        Log.d(TAG, "onStateChanged#STATE_COLLAPSED");
                        break;
                    case STATE_SETTLING:
                        //                        Log.d(TAG, "onStateChanged#STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.d(TAG, "onSlide#slideOffset : " + slideOffset);

                // TODO: 2017. 12. 13. 하단에 출력하는 TextView alpha
                clUp.setAlpha(1f - slideOffset);

                if (slideOffset > 0.8f) {
                    clMain.setAlpha(slideOffset);
                }

                if (slideOffset < 0.7f) {
                    viewBackground.setAlpha(slideOffset);
                }
            }
        });
    }

    public boolean isExpanded() {
        return bottomSheetBehavior.getState() == STATE_EXPANDED;
    }

    public void setCollapsed() {
        bottomSheetBehavior.setState(STATE_COLLAPSED);
    }
}
