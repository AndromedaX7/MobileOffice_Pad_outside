package com.mobilepolice.office.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mobilepolice.office.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApproveDetailsActivity extends AppCompatActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    ImageView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_details);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener((v) -> finish());
        String path = getIntent().getStringExtra("img");
        Glide.with(this).load(Uri.fromFile(new File(path))).into(content);
    }

    @OnClick(R.id.content)
    void onContentClick() {
        finish();
    }
}
