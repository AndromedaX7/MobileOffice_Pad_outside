package com.mobilepolice.office.ui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hjq.toast.ToastUtils;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyActivity;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.bean.ApprovePersonBean;
import com.mobilepolice.office.bean.DocApplyInfoBean;
import com.mobilepolice.office.bean.SpinnerItem;
import com.mobilepolice.office.mvp.copy.CopyContract;
import com.mobilepolice.office.soap.SoapParams;
import com.mobilepolice.office.soap.WebServiceUtils;
import com.mobilepolice.office.ui.adapter.SelectPopAdapter;
import com.mobilepolice.office.utils.FastJsonHelper;
import com.mobilepolice.office.utils.JsonParseUtils;
import com.mobilepolice.office.utils.TakePictureManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;


/**
 * 公文申请
 */
public class DocumentApplyActivity extends MyActivity implements View.OnClickListener {


    @BindView(R.id.ll_photo)
    LinearLayout ll_photo;
    @BindView(R.id.img_photo)
    ImageView img_photo;
    @BindView(R.id.et_doc_title)
    EditText et_doc_title;

    @BindView(R.id.tv_doc_department)
    TextView tv_doc_department;

    @BindView(R.id.ll_doc_type)
    LinearLayout ll_doc_type;
    @BindView(R.id.tv_doc_type)
    TextView tv_doc_type;

    @BindView(R.id.et_doc_count)
    EditText et_doc_count;

    @BindView(R.id.ll_doc_secretrank)
    LinearLayout ll_doc_secretrank;
    @BindView(R.id.tv_doc_secretrank)
    TextView tv_doc_secretrank;

    @BindView(R.id.ll_doc_EmergencyLevel)
    LinearLayout ll_doc_EmergencyLevel;
    @BindView(R.id.tv_doc_EmergencyLevel)
    TextView tv_doc_EmergencyLevel;
    @BindView(R.id.ll_doc_module)
    LinearLayout ll_doc_module;
    @BindView(R.id.tv_doc_module)
    TextView tv_doc_module;

    @BindView(R.id.tv_doc_approver_title)
    TextView tv_doc_approver_title;
    @BindView(R.id.tv_doc_approver)
    TextView tv_doc_approver;

    @BindView(R.id.view_signer)
    View view_signer;
    @BindView(R.id.ll_signer)
    LinearLayout ll_signer;
    @BindView(R.id.tv_doc_signer)
    TextView tv_doc_signer;

    @BindView(R.id.view_signer2)
    View view_signer2;
    @BindView(R.id.ll_signer2)
    LinearLayout ll_signer2;
    @BindView(R.id.tv_doc_signer2)
    TextView tv_doc_signer2;


    @BindView(R.id.view_signer3)
    View view_signer3;
    @BindView(R.id.ll_signer3)
    LinearLayout ll_signer3;
    @BindView(R.id.tv_doc_signer3)
    TextView tv_doc_signer3;

    @BindView(R.id.view_signer4)
    View view_signer4;
    @BindView(R.id.ll_signer4)
    LinearLayout ll_signer4;
    @BindView(R.id.tv_doc_signer4)
    TextView tv_doc_signer4;
    @BindView(R.id.tv_save)
    TextView tv_save;

    String path;
    DocApplyInfoBean bean;

    List<ApprovePersonBean> listpb;
    private PopupWindow pop;
    private ListView listView = null;
    private SelectPopAdapter mSelectAdapter;
    private List<SpinnerItem> listOrderStatus = new ArrayList<>();
    List<ApprovePersonBean> approvePerson = new ArrayList<>();
    String tv_doc_type_id;
    String tv_doc_secretrank_id;
    String tv_doc_EmergencyLevel_id;
    String tv_doc_module_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_apply;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    protected void initView() {
        setTitle("公文发起");
        ll_doc_type.setOnClickListener(this);
        ll_doc_secretrank.setOnClickListener(this);
        ll_doc_EmergencyLevel.setOnClickListener(this);
        ll_doc_module.setOnClickListener(this);
        tv_save.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        path = getIntent().getStringExtra("path");
        if (!TextUtils.isEmpty(path)) {
            showImage();
        } else {
            toast("拍摄照片异常");
            finish();
        }
        tv_doc_department.setText(MyApplication.getInstance().personDeptName);
        tv_doc_module.setText("审批");
        tv_doc_module_id = "1";
        getFindApprovePerson(tv_doc_module_id);
    }


    @Override
    public void onClick(View v) {
        if (v == ll_doc_type) {
            hintKbTwo();
            DialogDocType(v);
        } else if (v == ll_doc_secretrank) {
            hintKbTwo();
            DialogDocSecretrank(v);
        } else if (v == ll_doc_EmergencyLevel) {
            hintKbTwo();
            DialogDocEmergencyLevel(v);
        } else if (v == ll_doc_module) {
            hintKbTwo();
            DialogDocMode(v);
        } else if (v == tv_save) {
            bean = new DocApplyInfoBean();
            save();
        }
    }

    private void showImage() {
        File outFile = new File(path);
        int degree = TakePictureManager.getBitmapDegree(outFile.getAbsolutePath());
        Bitmap photoBmp = null;
        try {
            photoBmp = TakePictureManager.getBitmapFormUri(DocumentApplyActivity.this, Uri.fromFile(outFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 把图片旋转为正的方向
         */
        Bitmap newbitmap = TakePictureManager.rotateBitmapByDegree(photoBmp, degree);
        //Bitmap map = takePictureManager.decodeUriAsBitmap(filePath);
        if (newbitmap != null) {
            img_photo.setImageBitmap(newbitmap);
        }
    }

    private void getFindApprovePerson(String approveType) {
        SoapParams params = new SoapParams();
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //公文模式（1=审批，2=会签）
            jsonObject.put("approveType", approveType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = jsonObject.toString();
        params.put("json", json);
        showProgressDialog(true);
        WebServiceUtils.getPersonDeptName("findApprovePerson", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String obj = JSON.parseObject(result).getString(
                            "obj");
                    listpb = FastJsonHelper.deserializeList(obj,
                            ApprovePersonBean.class);
//                    Collections.reverse(listpb);
                    if (listpb != null && listpb.size() > 0) {
                        approvePerson = new ArrayList<>();
                        view_signer.setVisibility(View.GONE);
                        ll_signer.setVisibility(View.GONE);
                        view_signer2.setVisibility(View.GONE);
                        ll_signer2.setVisibility(View.GONE);
                        view_signer3.setVisibility(View.GONE);
                        ll_signer3.setVisibility(View.GONE);
                        if (listpb.size() > 0) {
                            if (approveType.equals(2)) {
                                tv_doc_approver_title.setText("会签人");
                            }
                            tv_doc_approver.setText(listpb.get(0).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(0).getId(), listpb.get(0).getName()));
                        }
                        if (listpb.size() > 1) {
                            view_signer.setVisibility(View.VISIBLE);
                            ll_signer.setVisibility(View.VISIBLE);
                            tv_doc_signer.setText(listpb.get(1).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(1).getId(), listpb.get(1).getName()));
                        }
                        if (listpb.size() > 2) {
                            view_signer2.setVisibility(View.VISIBLE);
                            ll_signer2.setVisibility(View.VISIBLE);
                            tv_doc_signer2.setText(listpb.get(2).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(2).getId(), listpb.get(2).getName()));
                        }
                        if (listpb.size() > 3) {
                            view_signer3.setVisibility(View.VISIBLE);
                            ll_signer3.setVisibility(View.VISIBLE);
                            tv_doc_signer3.setText(listpb.get(3).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(3).getId(), listpb.get(3).getName()));
                        }
                        if (listpb.size() > 4) {
                            view_signer4.setVisibility(View.VISIBLE);
                            ll_signer4.setVisibility(View.VISIBLE);
                            tv_doc_signer4.setText(listpb.get(4).getName());
                            approvePerson.add(new ApprovePersonBean(listpb.get(4).getId(), listpb.get(4).getName()));
                        }
                    }
                }

            }
        });
    }

    //发文类型
    private void DialogDocType(View view) {
        listOrderStatus.clear();
        listOrderStatus.add(new SpinnerItem("1", "通知"));
        listOrderStatus.add(new SpinnerItem("2", "下发"));
        listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setCacheColorHint(0x00000000);

        mSelectAdapter = new SelectPopAdapter(this, listOrderStatus);
        pop = new PopupWindow(listView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(mSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(1f);//设置屏幕透明度
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                tv_doc_type_id = listOrderStatus.get(position).GetKey();
                String name = listOrderStatus.get(position).GetValue();
                tv_doc_type.setText(name);
                dismissPopWindow();
            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    //秘密等级
    private void DialogDocSecretrank(View view) {
        listOrderStatus.clear();
        listOrderStatus.add(new SpinnerItem("1", "一级"));
        listOrderStatus.add(new SpinnerItem("2", "二级"));
        listOrderStatus.add(new SpinnerItem("3", "三级"));
        listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setCacheColorHint(0x00000000);

        mSelectAdapter = new SelectPopAdapter(this, listOrderStatus);
        pop = new PopupWindow(listView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(mSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(1f);//设置屏幕透明度
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                tv_doc_secretrank_id = listOrderStatus.get(position).GetKey();
                String name = listOrderStatus.get(position).GetValue();
                tv_doc_secretrank.setText(name);
                dismissPopWindow();

            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    //紧急程度
    private void DialogDocEmergencyLevel(View view) {
        listOrderStatus.clear();
        listOrderStatus.add(new SpinnerItem("1", "一级"));
        listOrderStatus.add(new SpinnerItem("2", "二级"));
        listOrderStatus.add(new SpinnerItem("3", "三级"));
        listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setCacheColorHint(0x00000000);

        mSelectAdapter = new SelectPopAdapter(this, listOrderStatus);
        pop = new PopupWindow(listView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(mSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(1f);//设置屏幕透明度
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                tv_doc_EmergencyLevel_id = listOrderStatus.get(position).GetKey();
                String name = listOrderStatus.get(position).GetValue();
                tv_doc_EmergencyLevel.setText(name);
                dismissPopWindow();

            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    //公文模式
    private void DialogDocMode(View view) {
        listOrderStatus.clear();
        listOrderStatus.add(new SpinnerItem("1", "审批"));
        listOrderStatus.add(new SpinnerItem("2", "会签"));
        listView = new ListView(this);
        listView.setDividerHeight(1);
        listView.setCacheColorHint(0x00000000);

        mSelectAdapter = new SelectPopAdapter(this, listOrderStatus);
        pop = new PopupWindow(listView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        listView.setAdapter(mSelectAdapter);
        pop.setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(1f);//设置屏幕透明度
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                tv_doc_module_id = listOrderStatus.get(position).GetKey();
                String name = listOrderStatus.get(position).GetValue();
                tv_doc_module.setText(name);
                dismissPopWindow();
                getFindApprovePerson(tv_doc_module_id);
            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
    }

    public void dismissPopWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
        }
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) this).getWindow().setAttributes(lp);
    }

    private String setBase64Photo(String pathName) {
        String uploadBuffer = "";
        try {
            FileInputStream fis = new FileInputStream(pathName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }
            //进行Base64编码
            uploadBuffer = new String(Base64.encode(baos.toByteArray()));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            uploadBuffer = "";
        }
        return uploadBuffer;
    }

    private void save() {
        if (TextUtils.isEmpty(et_doc_title.getText().toString())) {
            toast("标题不能为空！");
            return;
        }
        if (TextUtils.isEmpty(tv_doc_type.getText().toString())) {
            toast("发文类型不能为空！");
            return;
        }
        if (TextUtils.isEmpty(et_doc_count.getText().toString())) {
            toast("发文份数不能为空！");
            return;
        }
        if (TextUtils.isEmpty(tv_doc_secretrank_id)) {
            toast("秘密等级不能为空！");
            return;
        }
        if (TextUtils.isEmpty(tv_doc_EmergencyLevel_id)) {
            toast("紧急程度不能为空！");
            return;
        }

        showProgressDialog(true);
//        bean.setApplyPerson("17600194545");
//        bean.setTitel(et_doc_title.getText().toString());
//        bean.setRequestFlag(tv_doc_type.getText().toString());
//        bean.setRequestNum(et_doc_count.getText().toString());
//        bean.setSecretLevel(tv_doc_secretrank_id);
//        bean.setUrgentLevel(tv_doc_EmergencyLevel_id);
//        bean.setSchema(tv_doc_module_id);
//        bean.setDepartmentId(MyApplication.getInstance().personDeptid);
//        bean.setApprovePerson(approvePerson);
        //bean.setImg(setBase64Photo(path));
//        String json = JSON.toJSONString(bean);
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("applyPerson", MyApplication.getInstance().personPhone);
            jsonObject.put("titel", et_doc_title.getText().toString());
            jsonObject.put("requestFlag", tv_doc_type.getText().toString());
            jsonObject.put("requestNum", et_doc_count.getText().toString());
            jsonObject.put("secretLevel", tv_doc_secretrank_id);
            jsonObject.put("urgentLevel", tv_doc_EmergencyLevel_id);
            jsonObject.put("schema", tv_doc_module_id);
            jsonObject.put("departmentId", MyApplication.getInstance().personDeptid);
            // String approvePersons = JSON.toJSONString(approvePerson);
            JSONArray array = new JSONArray();
            JSONObject tmpObj = null;
//            Collections.reverse(approvePerson);
            int count = approvePerson.size();
            for (int i = 0; i < count; i++) {
                tmpObj = new JSONObject();
                tmpObj.put("id", approvePerson.get(i).getId());
                tmpObj.put("name", approvePerson.get(i).getName());
                array.put(tmpObj);
            }
            //String personInfos = .toString(); // 将JSONArray转换得到String
//            jsonObject.put("personInfos" , personInfos);   // 获得JSONObject的String
//            String personInfos = jsonArray.toString(); // 将JSONArray转换得到String
            jsonObject.put("approvePerson", array);
            jsonObject.put("img", setBase64Photo(path));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //转化成json字符串
        String jsons = jsonObject.toString();
        SoapParams params = new SoapParams();
        params.put("json", jsons);

        WebServiceUtils.getPersonDeptName("saveWorkFlowApplyInfo", params, new WebServiceUtils.CallBack() {
            @Override
            public void result(String result) {
                showProgressDialog(false);
                if (JsonParseUtils.jsonToBoolean(result)) {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                    finish();
                } else {
                    String msg = JsonParseUtils.jsonToMsg(result);
                    toast(msg);
                }

            }
        });
    }
}
