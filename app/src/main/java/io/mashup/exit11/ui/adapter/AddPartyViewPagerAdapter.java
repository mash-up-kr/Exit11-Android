package io.mashup.exit11.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.mashup.exit11.ui.fragment.AddHashTagFragment;
import io.mashup.exit11.ui.fragment.ChoiceMenuFragment;
import io.reactivex.Observable;

/**
 * Created by jonghunlee on 2017. 12. 13..
 */

public class AddPartyViewPagerAdapter extends FragmentPagerAdapter {

    private ChoiceMenuFragment choiceMenuFragment;
    private AddHashTagFragment addHashTagFragment;

    public AddPartyViewPagerAdapter(FragmentManager fm) {
        super(fm);

        choiceMenuFragment = ChoiceMenuFragment.newInstance();
        addHashTagFragment = AddHashTagFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:         // 메뉴 선택
                return choiceMenuFragment;
            case 1:
                return addHashTagFragment;
            case 2:         // TODO: 2017. 12. 13. 장소 등록
                return new Fragment();
            case 3:         // TODO: 2017. 12. 13. 세부 내역 등록
                return new Fragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public Observable<Integer> getMenuChoiceSubject() {
        return choiceMenuFragment.getMenuChoiceSubject();
    }

    public Observable<String> getHashTagSubject() {
        return addHashTagFragment.getHashTagSubject();
    }

    public void initAllFragmentData() {
        initChoiceMenuFragment();
        initHashTagFragment();
    }

    public void initHashTagFragment() {
        addHashTagFragment.init();
    }

    public void initChoiceMenuFragment() {
        choiceMenuFragment.init();
    }
}
