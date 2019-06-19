package com.mobilepolice.office.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.CollectEmailBean;
import com.mobilepolice.office.bean.DraftsEmailList;
import com.mobilepolice.office.bean.UpdataEmail;
import com.mobilepolice.office.bean.sendMailBean;
import com.mobilepolice.office.config.Config;
import com.mobilepolice.office.http.ResponseMe;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class DraftsEmailDetail extends AppCompatActivity {
    TextView title;
    TextView time;
    TextView collecter;
    WebView webView;
    ImageView goBack;
    Button resend;
    DraftsEmailList.ObjBean detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts_email_detail);
        title = findViewById(R.id.title);
        time = findViewById(R.id.time);
        collecter = findViewById(R.id.collecter);
        webView = findViewById(R.id.email_con);
        goBack = findViewById(R.id.go_back);
        resend = findViewById(R.id.resend);
        Intent intent = getIntent();
        detail = intent.getParcelableExtra("detail");
        title.setText(detail.getTitle());
        time.setText(detail.getCreateDate());
        collecter.setText(detail.getReceiver());
        String html = detail.getContent();
        String complete = createHtmlPage(html);
        Log.e( "onCreate: ", complete);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(null,complete,"text/html","utf-8", null);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        /*返回到上一个活动*/
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("isDelete", false);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        /*重新发送邮件*/
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emialTitle = detail.getTitle();
                String content = detail.getContent();
                String receiver = detail.getReceiver();
                String sendMailName = "崔南";
                String sendMail = "cuinan@gat.jl";
                String sendMailPsd = "cuinan963";
                if(TextUtils.isEmpty(emialTitle)){
                    toast("主题不能为空！");
                    return;
                }else if(TextUtils.isEmpty(receiver)){
                    toast("收件人不能为空！");
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result = sendMail(emialTitle,content,receiver,sendMailName,sendMail,sendMailPsd);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = result;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String response = (String)msg.obj;
                response = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                Gson gson = new Gson();
                Log.e("send: ",""+response );
                sendMailBean success = gson.fromJson(response, sendMailBean.class);
                if(success.isSuccess()){
//                    Log.e(TAG, "handleMessage: 测试草稿箱1" );
                    deleteEmail(detail.getId());
                    toast("发送成功！");
                }else{
//                    Log.e(TAG, "handleMessage: 测试草稿箱2" );
//                    saveFailedEmail();
                    toast("邮件发送失败，请检查收件人信息！");
                }
            }else if (msg.what == 2){
                String response = (String)msg.obj;
                Gson gson = new Gson();
                Log.e("send: ",""+response );
                sendMailBean success = gson.fromJson(response, sendMailBean.class);
                Intent intent = new Intent();
                intent.putExtra("isDelete", true);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    };
    private void deleteEmail(String id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = ResponseMe.deleteDraftEmal(id);
                Message message = new Message();
                message.what = 2;
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
    }
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }
    private  String sendMail(String title,String content, String receiveMail, String sendMailName,String sendMail, String sendMailPsd){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/xml");
        RequestBody body = RequestBody.create(mediaType,

                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.intranetmail.com/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <ser:sendMail>" +
                        "         <arg0>{\"title\":\""+title+"\",\"content\":\""+content+"\",\"receiveMail\":\""+receiveMail+"\",\"sendMailName\":\""+sendMailName+"\",\"sendMail\":\""+sendMail+"\",\"sendMailPsd\":\""+sendMailPsd+"\"}</arg0>" +
                        "      </ser:sendMail>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>");
        Request request = new Request.Builder()
                .url(Config.REQUEST_URL)
                .post(body)
                .addHeader("Content-Type", "text/xml")
                .addHeader("Cache-Control", "no-cache")
//                .addHeader("Postman-Token", "207ed22f-b5d4-4ca3-b025-fe1873f62a54")
                .build();
        Log.e(TAG, "sendMail: "+ "{\"title\":\""+title+"\",\"content\":\""+content+"\",\"receiveMail\":\""+receiveMail+"\",\"sendMailName\":\""+sendMailName+"\",\"sendMail\":\""+sendMail+"\",\"sendMailPsd\":\""+sendMailPsd+"\"}");
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    private String createHtmlPage(String html) {
        String completed = "";
        completed = "<!DOCTYPE html>"+html.replaceAll("&lt;","<").replaceAll("&gt;",">").replaceAll("&amp;nbsp;"," ");
        return completed;
    }
}
