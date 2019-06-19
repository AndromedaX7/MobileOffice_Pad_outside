package com.mobilepolice.office.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
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
 * 通知公告
 */
public class NoticeListFragment extends MyLazyFragment {
    @BindView(R.id.webView)
    WebView webView;

    String id = "";

    public static NoticeListFragment newInstance() {
        return new NoticeListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notice_list;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        webView.loadUrl("http://www.freetk.cn:8789/mobileworkws/information.html");
    }


    @Override
    protected void initData() {

    }
}