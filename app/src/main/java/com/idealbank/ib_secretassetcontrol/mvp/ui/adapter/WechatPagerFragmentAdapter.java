package com.idealbank.ib_secretassetcontrol.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.ChildCheckFragment;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.ChildNoCheckFragment;


/**
 * Created by YoKeyword on 16/6/5.
 */
public class WechatPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitles={"未盘点","已盘点"};
    String id;
    public WechatPagerFragmentAdapter(FragmentManager fm,String id) {
        super(fm);
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return ChildNoCheckFragment.newInstance(id);
        } else {
            return ChildCheckFragment.newInstance(id);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
