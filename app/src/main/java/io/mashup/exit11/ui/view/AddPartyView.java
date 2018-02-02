package io.mashup.exit11.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
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
import io.mashup.exit11.data.model.AddParty;
import io.mashup.exit11.data.model.Menu;
import io.mashup.exit11.data.model.PartyDetail;
import io.mashup.exit11.ui.activity.MainActivity;
import io.mashup.exit11.ui.adapter.AddPartyViewPagerAdapter;

import static android.support.design.widget.BottomSheetBehavior.STATE_COLLAPSED;
import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
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

    @BindView(R.id.text_add_party_message)
    TextView tvAddPartyMessage;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.button_next)
    Button btnNext;

    @BindView(R.id.button_prev)
    Button btnPrev;

    @BindView(R.id.button_finish_add_party)
    Button btnFinishAddParty;

    private AddPartyViewPagerAdapter pagerAdapter;
    private BottomSheetBehavior bottomSheetBehavior;

    private List<String> hashTags = new ArrayList<>();

    private PartyDetail partyDetail;

    @Menu
    private int menu;

    private AddParty addParty;

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
        pagerAdapter = new AddPartyViewPagerAdapter(activity.getSupportFragmentManager());
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        bottomSheetBehavior = BottomSheetBehavior.from(rlBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // No Impl
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
        setLocationSubject();
        setDetailPartySubject();
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

    private void setLocationSubject() {
        pagerAdapter.getLocationChoiceSubject().subscribe(isLocationChoice -> {
            if (isLocationChoice) {
                setBottomSheetCollapsed();
                ((MainActivity) getContext()).setLocationChoice();
            }
            else {
                setBottomSheetExpanded();
            }
        });
    }

    private void setDetailPartySubject() {
        pagerAdapter.getDetailPartySubject().subscribe(partyDetail -> {
            btnNext.setEnabled(true);
            this.partyDetail = partyDetail;
        });
    }

    public boolean isBottomSheetExpanded() {
        return bottomSheetBehavior.getState() == STATE_EXPANDED;
    }

    public void setBottomSheetCollapsed() {
        bottomSheetBehavior.setState(STATE_COLLAPSED);
    }

    public void setBottomSheetExpanded() {
        bottomSheetBehavior.setState(STATE_EXPANDED);
    }

    @OnClick(R.id.button_next)
    void onClickNextButton() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                setHashTagPage();
                break;
            case 1:
                setDetailPartyPage();
                break;
            case 2:
                setFinishAddPartyPage();
                break;
        }
    }

    private void setHashTagPage() {
        progressBar.setProgress(50);

        btnNext.setEnabled(false);
        btnPrev.setText(R.string.prev_step);

        tvAddPartyMessage.setText(R.string.add_party_hash_tag);

        viewPager.setCurrentItem(1);
    }

    private void setDetailPartyPage() {
        progressBar.setProgress(75);

        btnNext.setEnabled(false);

        tvAddPartyMessage.setText(R.string.add_party_detail);

        viewPager.setCurrentItem(2);
    }

    private void setFinishAddPartyPage() {
        this.addParty = new AddParty(menu, hashTags, partyDetail);

        findViewById(R.id.view01).setVisibility(View.INVISIBLE);
        findViewById(R.id.view02).setVisibility(View.INVISIBLE);

        btnPrev.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);

        btnFinishAddParty.setVisibility(View.VISIBLE);

        progressBar.setProgress(100);
        tvAddPartyMessage.setText(R.string.finish_add_party);

        viewPager.setCurrentItem(3);

        pagerAdapter.setFinishAddParty(addParty);
    }

    @OnClick(R.id.button_prev)
    void onClickPrev() {
        switch (viewPager.getCurrentItem()) {
            case 0:
                closeAddPartyView();
                break;
            case 1:
                btnPrev.setText(R.string.close);
                btnNext.setEnabled(true);

                tvAddPartyMessage.setText(R.string.choice_menu);

                progressBar.setProgress(25);
                viewPager.setCurrentItem(0);
                break;
            case 2:
                btnNext.setEnabled(true);

                tvAddPartyMessage.setText(R.string.add_party_hash_tag);

                progressBar.setProgress(50);
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private void closeAddPartyView() {
        pagerAdapter.initAllFragmentData();
        setBottomSheetCollapsed();
    }

    @OnClick(R.id.button_finish_add_party)
    void onClickFinishAddParty() {
        ((MainActivity) getContext()).finishAddParty(addParty);
        closeAddPartyView();
    }
}
