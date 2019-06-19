package com.mobilepolice.office.config;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import com.mobilepolice.office.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class Config {
    /*//包括常用全局静态方法*/
    public final static String BASE_PATH_SERVICE="http://59.110.42.63/";
    public final static String BASE_SOAP_SERVER="http://ccsyc.cn:8789";
    public static final String UPDATE_SERVER = BASE_PATH_SERVICE+"app/";
    public static final String UPDATE_SERVER_PHOTO ="http://pwm.about5.com/";
    public static final String UPDATE_SERVER_URL="pwm.about5.com";
    public static final String UPDATE_APKNAME = "mp.apk";//APK名称
    public static final String UPDATE_VERJSON = "mp.json"; //版本信息json
    public static final String UPDATE_SAVENAME = "mp.apk"; //APK存储名称
    public static final String AB_PATH=Environment.getExternalStorageDirectory().getAbsolutePath();//绝对路径
    public static final String PACKAGE_NAME="com.powercat.client";//应用包名
    public static final String PREFS_FILE = "device_id.xml";
    public static final String PREFS_DEVICE_ID = "device_id";
    public static final int TIMEOUT=5000;
    public static final String SERVICE_URL=BASE_SOAP_SERVER+"/services/MobileWorkws?wsdl";
//    public static final String SERVICE_URL="http://www.freetk.cn:8789/mobileworkws/services/MobileWorkws?wsdl";
    public static final String SERVICE_NAME_SPACE="http://services.webservice.intranetmail.com/";
    public static final String REQUEST_URL=BASE_SOAP_SERVER+"/intranetmailws/services/IntranetMailws?wsdl";


    /**
     *  get version number
     * @param context
     * @return
     */
    public static int getVerCode(Context context){
        int verCode=-1;
        try {
            verCode=context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0).versionCode;
        } catch (NameNotFoundException e) {
            // TODO: handle exception
            //Log.e(TAG, e.getMessage());
        }
        return verCode;
    }
    /**
     * get version number
     * @param context
     * @return
     */
    public static String getVerName(Context context){
        String verName="";
        try {
            verName=context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0).versionName;
        } catch (NameNotFoundException e) {
            // TODO: handle exception
            //Log.e(TAG, e.getMessage());
        }
        return verName;
    }
    /**
     * get apkname
     * @param context
     * @return
     */
    public static String getAppName(Context context){
        String verName=context.getResources().getText(R.string.app_name).toString();
        return verName;
    }

}