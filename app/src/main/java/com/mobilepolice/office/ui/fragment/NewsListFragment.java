package com.mobilepolice.office.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.PendingWorkDetailedBean;
import com.mobilepolice.office.soap.SoapParams;
import com.mobilepolice.office.soap.WebServiceUtils;
import com.mobilepolice.office.ui.activity.DocumentExamineActivity;
import com.mobilepolice.office.ui.activity.DocumentPendingWorkDetailedActivity;
import com.mobilepolice.office.utils.FastJsonHelper;
import com.mobilepolice.office.utils.JsonParseUtils;
import com.mobilepolice.office.widget.scrollable.ScrollableHelper;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import butterknife.BindView;

//import com.tencent.smtt.sdk.TbsReaderView;

/**
 * 新闻列表
 */
public class NewsListFragment extends MyLazyFragment {

//    @BindView(R.id.webView)
    WebView webView;

    String id = "";

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WebView webView=view.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    protected void initView() {


    }


    @Override
    protected void initData() {

    }
}