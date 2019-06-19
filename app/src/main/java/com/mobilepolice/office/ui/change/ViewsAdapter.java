package com.mobilepolice.office.ui.change;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ViewsAdapter extends PagerAdapter {

    private ArrayList<View> views = new ArrayList<>();


    public ViewsAdapter(ArrayList<View> views) {
        this.views = views;
    }


    public void setViews(ArrayList<View> views) {
        this.views = views;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
//        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }

}
