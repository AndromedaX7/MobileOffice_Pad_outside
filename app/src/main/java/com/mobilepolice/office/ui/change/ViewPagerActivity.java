package com.mobilepolice.office.ui.change;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mobilepolice.office.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.mPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main_a);
        ButterKnife.bind(this);
        viewPager.setPageMargin(50);
//TODO 这个是对应Fragment的Adapter
//        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));


        views.add(View.inflate(this, R.layout.main_a_banner, null));
        views.add(View.inflate(this, R.layout.main_a_banner, null));
        views.add(View.inflate(this, R.layout.main_a_banner, null));
        views.add(View.inflate(this, R.layout.main_a_banner, null));
        views.add(View.inflate(this, R.layout.main_a_banner, null));
        views.add(View.inflate(this, R.layout.main_a_banner, null));
        views.add(View.inflate(this, R.layout.main_a_banner, null));
        viewPager.setAdapter(new ViewsAdapter(views));


    }
    private ArrayList<View> views = new ArrayList<>();
}
