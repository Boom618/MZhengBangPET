package com.ty.zbpet.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author PVer on 2018/12/21.
 * <p>
 * 主页 待办/已办 列表
 */
public class MainListAdapter extends FragmentPagerAdapter {

    private final String[] mTitles = {"待办", "已办"};
    private List<Fragment> mFragments;


    public MainListAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
