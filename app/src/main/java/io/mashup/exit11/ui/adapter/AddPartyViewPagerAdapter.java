package io.mashup.exit11.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.mashup.exit11.ui.fragment.ChoiceMenuFragment;
import io.mashup.exit11.ui.fragment.DetailPartyInfoFragment;

/**
 * Created by jonghunlee on 2017. 12. 13..
 */

public class AddPartyViewPagerAdapter extends FragmentPagerAdapter {


    public AddPartyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:         // 메뉴 선택
                return ChoiceMenuFragment.newInstance();
            case 1:         // TODO: 2017. 12. 13. 해시 태그 등록
                return new Fragment();
            case 2:         // TODO: 2017. 12. 13. 장소 등록
                return DetailPartyInfoFragment.newInstance();
            case 3:         // TODO: 2017. 12. 13. 세부 내역 등록
                return new Fragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
