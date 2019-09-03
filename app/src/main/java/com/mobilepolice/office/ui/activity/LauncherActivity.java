package com.mobilepolice.office.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.alibaba.fastjson.JSON;
import com.gyf.barlibrary.BarHide;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyActivity;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.bean.LoginBean;
import com.mobilepolice.office.http.HttpConnectInterface;
import com.mobilepolice.office.http.HttpTools;
import com.mobilepolice.office.soap.SoapParams;
import com.mobilepolice.office.soap.WebServiceUtils;
import com.mobilepolice.office.utils.JsonParseUtils;
import com.mobilepolice.office.utils.MyCookie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 启动界面
 */
public class LauncherActivity extends MyActivity
        implements OnPermission, Animation.AnimationListener {

    @BindView(R.id.iv_launcher_bg)
    View mImageView;
    @BindView(R.id.iv_launcher_icon)
    View mIconView;
    @BindView(R.id.iv_launcher_name)
    View mTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }


    private void loadingUaacApi() {
        startActivityFinish(Home2Activity.class);
    }

    @Override
    protected void initView() {
        loadingUaacApi();
        //初始化动画
        initStartAnim();
        //设置状态栏和导航栏参数
        getStatusBarConfig()
                .fullScreen(true)//有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)//隐藏状态栏
                .transparentNavigationBar()//透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initData() {
    }

    private static final int ANIM_TIME = 1000;
    String mobile = "";

    /**
     * 启动动画
     */
    private void initStartAnim() {
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f);
        aa.setDuration(ANIM_TIME * 2);
        aa.setAnimationListener(this);
        mImageView.startAnimation(aa);

        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(ANIM_TIME);
        mIconView.startAnimation(sa);

        RotateAnimation ra = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(ANIM_TIME);
        mTextView.startAnimation(ra);
    }

    private void requestFilePermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE)
                .permission(Permission.CAMERA)
                .permission(Permission.RECORD_AUDIO)
                .request(this);
    }

    /**
     * {@link OnPermission}
     */

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {

        /////TODO For Test
//        startActivity(HomeActivity.class);
//        if (isLogin()) {
//
//            startActivity(HomeActivity.class);
//        } else {
//            startActivity(LoginNewActivity.class);
//        }
//        startActivity(EmailLoginActivity.class);
//        finish();
    }

    private boolean isLogin() {
        mobile = MyCookie.getString("mobile", "");
        if (!TextUtils.isEmpty(mobile)) {
            return true;
        }
        return false;
    }


    private void login() {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("applyPerson", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        showProgressDialog(true);
        WebServiceUtils.getPersonDeptName("findApplyPersonDeptName", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    String departmentId = JSON.parseObject(obj).getString(
                            "departmentId");
                    String departmentName = JSON.parseObject(obj).getString(
                            "departmentName");
                    MyApplication.getInstance().personDeptid = departmentId;
                    MyApplication.getInstance().personDeptName = departmentName;
                    MyApplication.getInstance().personPhone = mobile;
                    startActivity(HomeActivity.class);
                    finish();
                } else {
                    toast("读取部门失败,请重新登录");
                    startActivity(LoginNewActivity.class);
                }
            }
        });
    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            toast("没有权限访问文件，请手动授予权限");
            XXPermissions.gotoPermissionSettings(LauncherActivity.this, true);
        } else {
            toast("请先授予文件读写权限");
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFilePermission();
                }
            }, 2000);
        }
    }

    @Override
    public void onBackPressed() {
        //禁用返回键
        //super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (XXPermissions.isHasPermission(LauncherActivity.this, Permission.Group.STORAGE)) {
            hasPermission(null, true);
        } else {
            requestFilePermission();
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        //不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    /**
     * {@link Animation.AnimationListener}
     */

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        requestFilePermission();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }


    @Override
    protected void initOrientation() {
        //Android 8.0踩坑记录：Only fullscreen opaque activities can request orientation
        // https://www.jianshu.com/p/d0d907754603
        //super.initOrientation();
    }

    ////////////////////////////// UaacApi ////////////////////////////
    public void login(String token) {
        HttpTools.build(HttpConnectInterface.class)
                .login(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::loginResult, this::err, this::complete)
                .isDisposed();
    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void complete() {
    }

    private void loginResult(LoginBean loginBean) {
        ((MyApplication) getApplication()).setLoginBean(loginBean);
        startActivityFinish(HomeActivity.class);
        finish();
    }
}

