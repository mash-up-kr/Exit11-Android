package io.mashup.exit11.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.mashup.exit11.data.model.AddParty;
import io.mashup.exit11.data.model.PartyDetail;
import io.mashup.exit11.ui.fragment.AddHashTagFragment;
import io.mashup.exit11.ui.fragment.ChoiceMenuFragment;
import io.mashup.exit11.ui.fragment.DetailPartyInfoFragment;
import io.mashup.exit11.ui.fragment.FinishAddPartyFragment;
import io.reactivex.Observable;


/**
 * Created by jonghunlee on 2017. 12. 13..
 */

public class AddPartyViewPagerAdapter extends FragmentPagerAdapter {

    private ChoiceMenuFragment choiceMenuFragment;
    private AddHashTagFragment addHashTagFragment;
    private DetailPartyInfoFragment detailPartyInfoFragment;
    private FinishAddPartyFragment finishAddPartyFragment;

    public AddPartyViewPagerAdapter(FragmentManager fm) {
        super(fm);

        choiceMenuFragment = ChoiceMenuFragment.newInstance();
        addHashTagFragment = AddHashTagFragment.newInstance();
        detailPartyInfoFragment = DetailPartyInfoFragment.newInstance();
        finishAddPartyFragment = FinishAddPartyFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:         // 메뉴 선택
                return choiceMenuFragment;
            case 1:
                return addHashTagFragment;
            case 2:
                return detailPartyInfoFragment;
            case 3:
                return finishAddPartyFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public Observable<Boolean> getLocationChoiceSubject() {
        return detailPartyInfoFragment.getLocationChoiceSubject();
    }

    public Observable<Integer> getMenuChoiceSubject() {
        return choiceMenuFragment.getMenuChoiceSubject();
    }

    public Observable<String> getHashTagSubject() {
        return addHashTagFragment.getHashTagSubject();
    }

    public Observable<PartyDetail> getDetailPartySubject() {
        return detailPartyInfoFragment.getDetailPartySubject();
    }

    public void initAllFragmentData() {
        initChoiceMenuFragment();
        initHashTagFragment();
    }

    private void initHashTagFragment() {
        addHashTagFragment.init();
    }

    private void initChoiceMenuFragment() {
        choiceMenuFragment.init();
    }

    public void setFinishAddParty(AddParty addParty) {
        finishAddPartyFragment.setFinishAddParty(addParty);
    }
}
