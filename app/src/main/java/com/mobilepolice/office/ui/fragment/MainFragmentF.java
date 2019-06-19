package com.mobilepolice.office.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.PendingWorkBean;
import com.mobilepolice.office.soap.SoapParams;
import com.mobilepolice.office.soap.WebServiceUtils;
import com.mobilepolice.office.ui.activity.DocumentPendingWorkActivity;
import com.mobilepolice.office.ui.activity.LoginNewActivity;
import com.mobilepolice.office.utils.FastJsonHelper;
import com.mobilepolice.office.utils.JsonParseUtils;
import com.mobilepolice.office.utils.MyCookie;
import com.mobilepolice.office.widget.BadgeView.BadgeTextView;
import com.mobilepolice.office.widget.RoundImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目界面跳转示例
 */
public class MainFragmentF extends MyLazyFragment
        implements View.OnClickListener {


    @BindView(R.id.tb_test_bar)
    TitleBar tb_test_bar;
    @BindView(R.id.Avatar_view)
    RoundImageView Avatar_view;
    @BindView(R.id.tv_username)
    TextView tv_username;
    @BindView(R.id.tv_usernamephone)
    TextView tv_usernamephone;

    @BindView(R.id.ll_charge_module)
    LinearLayout ll_charge_module;
    @BindView(R.id.ll_normal_problem)
    LinearLayout ll_normal_problem;
    @BindView(R.id.ll_legal)
    LinearLayout ll_legal;
    @BindView(R.id.ll_version_introduce)
    LinearLayout ll_version_introduce;

    @BindView(R.id.badge_tv1)
    BadgeTextView badge_tv1;
    @BindView(R.id.badge_tv2)
    BadgeTextView badge_tv2;
    @BindView(R.id.badge_tv3)
    BadgeTextView badge_tv3;


    public static MainFragmentF newInstance() {
        return new MainFragmentF();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_e;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {
        Avatar_view.setOnClickListener(this);
        ll_charge_module.setOnClickListener(this);
        ll_normal_problem.setOnClickListener(this);
        ll_legal.setOnClickListener(this);
        ll_version_introduce.setOnClickListener(this);
        badge_tv1.setOnClickListener(this);
        badge_tv2.setOnClickListener(this);
        badge_tv3.setOnClickListener(this);
        tb_test_bar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                MyCookie.putString("mobile", "");
                startActivity(LoginNewActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        tv_usernamephone.setText(MyApplication.getInstance().personPhone);

    }


    @Override
    public void onResume() {
        super.onResume();
        findApproveTaskInfo();
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        if (v == badge_tv1) {
            Bundle bundle = new Bundle();
            bundle.putString("title", "待办工作");
            bundle.putInt("type", 1);
            startActivity(DocumentPendingWorkActivity.class, bundle);
        } else if (v == badge_tv2) {
//            Bundle bundle = new Bundle();
//            bundle.putString("title", "已办工作");
//            bundle.putInt("type", 2);
//            startActivity(DocumentPendingWorkActivity.class, bundle);
        } else if (v == badge_tv3) {
//            Bundle bundle = new Bundle();
//            bundle.putString("title", "全部工作");
//            bundle.putInt("type", 3);
//            startActivity(DocumentPendingWorkActivity.class, bundle);
        } else if (v == ll_charge_module) {
            //startActivity(WebActivity.class);
        } else if (v == ll_normal_problem) {
            // startActivity(WebActivity.class);
        } else if (v == ll_legal) {
            //  startActivity(WebActivity.class);
        } else if (v == ll_version_introduce) {
            //  startActivity(WebActivity.class);
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        //return !super.isStatusBarEnabled();
        return false;
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }


    //待办工作
    private void findApproveTaskInfo() {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("approvePerson", MyApplication.getInstance().personPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String json = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", json);
        showProgressDialog(true);
        WebServiceUtils.getPersonDeptName("findApproveTaskInfo", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    List<PendingWorkBean> list = FastJsonHelper.deserializeList(obj,
                            PendingWorkBean.class);
                    if (list != null && list.size() > 0) {
                        badge_tv1.setBadgeCount(list.size());
                        badge_tv1.setBadgeShown(true);
                    }
                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                }
            }
        });
    }
}