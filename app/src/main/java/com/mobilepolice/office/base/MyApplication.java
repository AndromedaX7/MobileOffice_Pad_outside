package com.mobilepolice.office.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.mobilepolice.office.bean.EmailInfo;
import com.mobilepolice.office.bean.LoginBean;
import com.mobilepolice.office.utils.ActivityStackManager;
import com.hjq.toast.ToastUtils;
//import com.hjq.umeng.UmengHelper;
import com.mobilepolice.office.utils.CrashHandler;
import com.mobilepolice.office.utils.FileUtil;
import com.tencent.smtt.sdk.QbSdk;

import org.xutils.x;

import java.io.InputStream;
import java.util.ArrayList;

import javax.mail.Session;
import javax.mail.Store;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目中的Application基类
 */
public class MyApplication extends UIApplication {

    private static MyApplication instance;
    //1输入2手写3录音
    public int path_type = 2;
    public String path_content;
    public String path_pdf;
    public String path_image;
    public String path_record;
    public String personDeptid;
    public String personDeptName;
    public String personPhone;
    public String DocSchema;
    public String path_main_image;
    public String approveNodeId;
    public String MailBoxContacts;
    public static Session session = null;

    public static Store getStore() {
        return store;
    }

    public static void setStore(Store store) {
        MyApplication.store = store;
    }

    public static EmailInfo info = new EmailInfo();

    private static Store store;
    private ArrayList<InputStream> attachmentsInputStreams;

    public ArrayList<InputStream> getAttachmentsInputStreams() {
        return attachmentsInputStreams;
    }

    public void setAttachmentsInputStreams(ArrayList<InputStream> attachmentsInputStreams) {
        this.attachmentsInputStreams = attachmentsInputStreams;
    }

    private LoginBean loginBean;

    public LoginBean getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public static String userCode = "100936";

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.e("onActivityCreated: ", activity.getClass().getName());
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        instance = this;
        // 初始化吐司工具类
        ToastUtils.init(this);

        // 友盟统计
//        UmengHelper.init(this);

        // Activity 栈管理
        ActivityStackManager.init(this);
        x.Ext.init(this);
        FileUtil.init();
//        CrashHandler handler = CrashHandler.getInstance();
//        handler.init(getApplicationContext());
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

//        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
//
//            @Override
//            public void onViewInitFinished(boolean arg0) {
//                // TODO Auto-generated method stub
//                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
//                Log.d("app", " onViewInitFinished is " + arg0);
//            }
//
//            @Override
//            public void onCoreInitFinished() {
//                // TODO Auto-generated method stub
//            }
//        };
//        //x5内核初始化接口
//        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }

    public static MyApplication getInstance() {

        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 使用 Dex分包
        MultiDex.install(this);
    }
}