package com.mobilepolice.office.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.hjq.toast.ToastUtils;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyActivity;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.bean.AttributesBean;
import com.mobilepolice.office.bean.ShowDocInfoBean;
import com.mobilepolice.office.soap.SoapParams;
import com.mobilepolice.office.soap.WebServiceUtils;
import com.mobilepolice.office.utils.FastJsonHelper;
import com.mobilepolice.office.utils.JsonParseUtils;
import com.mobilepolice.office.utils.MyCookie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 登录界面
 */
public class LoginNewActivity extends MyActivity
        implements View.OnClickListener {

    @BindView(R.id.et_register_phone)
    EditText mPhoneView;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_new;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    protected void initView() {

        setTitle("登录");
        btn_login.setOnClickListener(this);
        // mEditTextInputHelper = new EditTextInputHelper(mCommitView, false);
//        mEditTextInputHelper.addViews(mPhoneView, mPasswordView);
    }

    @Override
    protected void initData() {

    }

//    @Override
//    public void onRightClick(View v) {
//        // 跳转到注册界面
//        startActivity(RegisterActivity.class);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void login(String phone) {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("applyPerson", phone);
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
                    MyCookie.putString("mobile", phone);
                    MyApplication.getInstance().personPhone = phone;
                    startActivity(HomeActivity.class);
                    finish();
                } else {
                    ToastUtils.show("获取部门失败！");
                }
            }
        });
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        if (v == btn_login) {
//            if (mPhoneView.getText().toString().length() != 11) {
//                ToastUtils.show(getResources().getString(R.string.phone_input_error));
//                return;
//            }
            login(mPhoneView.getText().toString());
        }
    }


}