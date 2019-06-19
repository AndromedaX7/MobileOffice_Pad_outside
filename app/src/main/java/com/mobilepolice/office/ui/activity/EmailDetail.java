package com.mobilepolice.office.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.CollectEmailBean;
import com.mobilepolice.office.ui.adapter.AttachAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class EmailDetail extends AppCompatActivity {
    TextView title;
    TextView time;
    TextView sender;
    TextView collecter;
    WebView webView;
    LinearLayout attachBox;
    ImageView goBack;
    CollectEmailBean.ObjBean detail;
    RecyclerView attachRecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_detail);
        title = findViewById(R.id.title);
        time = findViewById(R.id.time);
        sender = findViewById(R.id.sender);
        webView = findViewById(R.id.email_con);
        attachBox = findViewById(R.id.attach_box);
        goBack = findViewById(R.id.go_back);
        attachRecycle = findViewById(R.id.attachRecycle);


        Intent intent = getIntent();
        detail = intent.getParcelableExtra("detail");
        title.setText(detail.getTitle());
        time.setText(detail.getSendDate().substring(0,detail.getSendDate().indexOf(" ")));
        sender.setText(detail.getSender().replace("&lt;","<").replace("&gt;",">"));
//        collecter.setText(intent.getStringExtra("reciver"));
        Log.e("onCreate: ", detail.getContent().substring(detail.getContent().indexOf("&lt;br&gt;")+10,detail.getContent().length()));
        String html = detail.getContent();
        String complete = createHtmlPage(html);
        Log.e( "onCreate: ", complete);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(null,complete,"text/html","utf-8", null);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        /*附件*/
        if(detail.isIsAttach()){
            /*附件*/
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            attachRecycle.setLayoutManager(layoutManager);
            AttachAdapter mAdapter= new AttachAdapter();
            List<CollectEmailBean.ObjBean.AttachBean> data = detail.getAttach();
            Log.e("附件: ",data.size()+"" );
            Iterator<CollectEmailBean.ObjBean.AttachBean> iterator = data.iterator();
            while (iterator.hasNext()){
                CollectEmailBean.ObjBean.AttachBean next = iterator.next();
                if (next.getFileName()==null){
                    iterator.remove();
                }
            }
            ((TextView) findViewById(R.id.attach_count)).setText("(" + data.size() + ")");
            mAdapter.setNewData(data);
            attachRecycle.setAdapter(mAdapter);
        }else{
            attachBox.setVisibility(View.GONE);
        }
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSeen = detail.isIsSeen();
                Intent intent = new Intent();
                intent.putExtra("isSeen", isSeen);
                intent.putExtra("id", detail.getId());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

        boolean isSeen = detail.isIsSeen();
        Intent intent = new Intent();
        intent.putExtra("isSeen", isSeen);
        intent.putExtra("id", detail.getId());
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }

    private String createHtmlPage(String html) {
        String completed = "";
        completed = "<!DOCTYPE html>"+html.replaceAll("&lt;","<").replaceAll("&gt;",">").replaceAll("&amp;nbsp;"," ");
        return completed;
    }


}
