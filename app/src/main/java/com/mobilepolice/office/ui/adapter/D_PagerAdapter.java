package com.mobilepolice.office.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class D_PagerAdapter extends FragmentPagerAdapter {

    public D_PagerAdapter(ArrayList<Fragment> views, FragmentManager fm) {
        super(fm);
        this.views.addAll(views);
    }

    private ArrayList<Fragment> views = new ArrayList<>();

    public void setViews(ArrayList<Fragment> views) {
        this.views.addAll(views);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Fragment getItem(int i) {
        return views.get(i);
    }


}
