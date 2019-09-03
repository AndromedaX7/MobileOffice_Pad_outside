package com.mobilepolice.office.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czy on 2019/6/25 16:55.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private List<String> list_title = new ArrayList<>();

    public void setFragments(ArrayList<Fragment> fragments) {
        mFragmentList = fragments;
    }

    public void setTitles(List<String> list_title) {
        this.list_title = list_title;
    }

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentList.get(position);

        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    /* 重写与TabLayout配合 */

    @Override
    public CharSequence getPageTitle(int position) {
        if (list_title.size() > 0) {
            return list_title.get(position);
        } else {
            return null;
        }
    }
}