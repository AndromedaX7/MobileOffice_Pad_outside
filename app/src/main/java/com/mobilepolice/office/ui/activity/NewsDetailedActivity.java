package com.mobilepolice.office.ui.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyActivity;
import com.mobilepolice.office.bean.LoopImageNewsBean;
import com.mobilepolice.office.bean.NewsDetailBean;
import com.mobilepolice.office.http.HttpConnectInterface;
import com.mobilepolice.office.http.HttpTools;
import com.mobilepolice.office.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 新闻列表
 */
public class NewsDetailedActivity extends MyActivity {


    @BindView(R.id.leftButton)
    ImageView leftButton;
    @BindView(R.id.mWebConetent)
    WebView webView;
    private int type;

    @BindView(R.id.title_top)
    TextView title_top;
    @BindView(R.id.news_time)
    TextView news_time;

    @BindView(R.id.forward)
    ImageView forward;
    private String titleIn;
    private String img = "";
    private String time=DateUtil.format("yyyy-MM-dd", System.currentTimeMillis());


    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detailed;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }


    @Override
    protected void initView() {

        initWebView();
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        leftButton.setOnClickListener(v -> finish());
        ImageView agreeBtn = findViewById(R.id.agree);
        agreeBtn.setOnClickListener(v -> {
            int state = Integer.parseInt(v.getTag().toString());
            ImageView imgBtn = (ImageView) v;
            if (state == 0) {
                imgBtn.setImageResource(R.drawable.admire_active);
                v.setTag(1);
            } else {
                imgBtn.setImageResource(R.drawable.admire);
                v.setTag(0);
            }
        });
    }

    private void initWebView() {

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);// 隐藏滚动条webView.requestFocus();
        webView.requestFocusFromTouch();

        WebSettings mWebSettings = webView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);// 支持JS
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        webView.getSettings().setLoadWithOverviewMode(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口
        mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染等级
        mWebSettings.setBuiltInZoomControls(false);// 设置支持缩放
        mWebSettings.setDomStorageEnabled(true);//使用localStorage则必须打开
        mWebSettings.setBlockNetworkImage(true);// 首先阻塞图片，让图片不显示
        mWebSettings.setBlockNetworkImage(false);//  页面加载好以后，在放开图片：
        mWebSettings.setSupportMultipleWindows(false);// 设置同一个界面
        mWebSettings.setBlockNetworkImage(false);
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebSettings.setNeedInitialFocus(false);// 禁止webview上面控件获取焦点(黄色边框)
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //页面开始加载时
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //页面加载结束时
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if (url.startsWith("tenvideo2:")) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    return true;
//                }
                view.loadUrl(url);
                /**
                 * 网页跳转：
                 * 1.在当前的webview跳转到新连接
                 * view.loadUrl(url);
                 * 2.调用系统浏览器跳转到新网页
                 * Intent i = new Intent(Intent.ACTION_VIEW);
                 * i.setData(Uri.parse(url));
                 * startActivity(i);
                 */
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    protected void initData() {
        getIntentData();
        HttpConnectInterface httpConnectInterface = HttpTools.build(HttpConnectInterface.class);
        if (flag != null)
            switch (flag) {
                case "NEWS":
                    type = 0;
                    httpConnectInterface.findTpxwInfoDetails(contentId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::newsResult, this::err, this::onComplete)
                            .isDisposed();
                    break;
                case "NOTICE":
                    type = 1;
                    httpConnectInterface.findTzggInfoDetails(contentId)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                            .subscribe(this::newsResult, this::err, this::onComplete)
                            .isDisposed();
                    break;

            }
        else {
            webView.loadUrl("file:///android_asset/news1.html");
        }
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void newsResult(NewsDetailBean o) {
        title_top.setText(o.getTitle().replaceAll("<br>", "\n"));
        news_time.setText(o.getTime());
//        cached(o.getCon())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::cacheHtml, this::err, this::onComplete)
//                .isDisposed();

        webView.loadDataWithBaseURL(null, marginData(o.getCon()), "text/html", "utf-8", null);
    }

//    private void cacheHtml(String s) {
//        Log.e("cacheHtml: ", s);
//        webView.loadUrl(s);
//    }

//    private Observable<String> cached(String content) {
//        return Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                File html = new File(getFilesDir().getAbsolutePath(), "html");
//                html.mkdirs();
//                File current = new File(html, System.currentTimeMillis() + ".html");
//                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(current));
//                out.write(marginData(content).getBytes());
//                out.flush();
//                out.close();
//                emitter.onNext("file://" + current.getAbsolutePath());
//            }
//        });
//    }

    private String marginData(String content) {

//        if ("NEWS".equals(flag))
//        content = "<p align=\"\"center\"\"><img alt=\"\"\"\" width=\"\"400\"\" height=\"\"267\"\" src=\"\"http://192.168.20.228:7124/UserFiles/Image/201205/1337780063828.jpg\"\" /></p>\n" +
//                "<p align=\"\"center\"\"><img alt=\"\"\"\" width=\"\"400\"\" height=\"\"267\"\" src=\"\"http://192.168.20.228:7124/UserFiles/Image/201205/1337780074890.jpg\"\" /></p>\n" +
//                "<p>&nbsp;&nbsp;&nbsp; 2012年5月22日，副省长、公安厅长马明会见了以韩国江原道地方警察厅长赵吉衡为团长的韩国江原道地方警察厅代表团一行6人，就两省道国际警务交流与合作等相关事宜进行了友好交流和磋商。省政府信访督查专员、公安厅副厅长史历，省公安厅刑侦局局长杨维林、指挥中心（办公室）指挥长（主任）吴跃岩、警务保障部副主任张忠诚、出入境管理局局长方保仁、政委段于建参加了会见活动。<br />\n" +
//                "&nbsp;&nbsp;&nbsp; &nbsp;副省长、公安厅长马明对韩国江原道地方警察厅代表团到访我省表示热烈欢迎，向代表团简要介绍了吉林省情和我省公安工作情况，对近年来两省道警方在打击跨国刑事犯罪、情报信息共享、双方公民境外利益保护、双边警务交流与合作等方面取得的成果给予了充分肯定，就保护双方公民境外合法权益、为对方公民提供出入境便利服务、支持对方企业境外投资发展以及进一步巩固和加强双边国际警务交流与合作等方面提出了意见和建议。<br />\n" +
//                "&nbsp;&nbsp;&nbsp;&nbsp; 韩国江原道地方警察厅长赵吉衡对吉林省公安厅的盛情接待表示衷心感谢，就加大双方公民和企业境外合法权益保护力度，增进双方在打击金融犯罪和毒品犯罪等跨国刑事犯罪领域合作，拓展两国警方的警务交流与合作渠道等事宜交换了意见。<br />\n" +
//                "&nbsp;&nbsp;&nbsp;&nbsp; 会见结束后，代表团一行参观了吉林省公安厅指挥中心。（厅出入境管理局供稿）</p>";
        String c = ("<!DOCTYPE2 html><html><head><meta charset=\"utf-8\">" +
                "<meta content=\"width=device-width, initial-scale=1.0," +
                "maximum-scale=1.0, user-scalable=0;\" name=\"viewport\" />" +
                "</head><?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<body>" + content + "</body></html>")
//                .replaceAll("10.106.18.75:8081", "192.168.20.228:7124")
                .replaceAll("/u/cms/www", "http://192.168.20.228:7124/u/cms/www");
        Log.e("marginData: ", c);
        return c;

    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    private void getIntentData() {
        contentId = getIntent().getStringExtra("contentId");
        titleIn = getIntent().getStringExtra("titleIn");
        img = getIntent().getStringExtra("img");
        flag = getIntent().getStringExtra("flag");
        time = getIntent().getStringExtra("time");
    }

    private String contentId;
    private String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.forward)
    public void onInsertItem() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid", contentId);
        contentValues.put("title", titleIn);
        contentValues.put("img", img);
        contentValues.put("type", type);
        contentValues.put("time", time);
        Cursor query = getContentResolver().query(Uri.parse("content://com.access.favorite.info"), null, "cid=?", new String[]{contentId}, null);
        if (query != null && query.moveToFirst()) {
            String cid = query.getString(query.getColumnIndex("cid"));
            int type = query.getInt(query.getColumnIndex("type"));
            Log.e("onInsertItem: ", type + "::" + cid);
            Toast.makeText(this, "您已收藏过该新闻", Toast.LENGTH_SHORT).show();
        } else {
            getContentResolver().insert(Uri.parse("content://com.access.favorite.info"), contentValues);
            Toast.makeText(this, "已收藏成功", Toast.LENGTH_SHORT).show();
        }
    }
}