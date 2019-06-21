package com.mobilepolice.office.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mobilepolice.office.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenShutActivity extends AppCompatActivity {
    @BindView(R.id.screenShut)
    ImageView screenShut;
    @BindView(R.id.details)
    LinearLayout details;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shut);
        ButterKnife.bind(this);
        Glide.with(this).load(getFileStreamPath(getIntent().getStringExtra("path"))).into(screenShut);
    }

//    @OnClick(R.id.screenShut)
//    public void onScreenShutClicked() {
//
//    }

    @OnClick(R.id.details)
    public void onDetailsClicked() {
    }
}
