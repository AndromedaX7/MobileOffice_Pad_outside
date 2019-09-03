package com.mobilepolice.office.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyActivity;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.bean.HomeGoodBean;
import com.mobilepolice.office.config.Config;
import com.mobilepolice.office.soap.SoapClient;
import com.mobilepolice.office.soap.SoapParams;
import com.mobilepolice.office.soap.SoapParseUtils;
import com.mobilepolice.office.soap.SoapUtil;
import com.mobilepolice.office.soap.WebServiceUtils;
import com.mobilepolice.office.ui.adapter.HomeFragmentAdapter;
import com.mobilepolice.office.ui.adapter.PagerAdapter;
import com.mobilepolice.office.ui.fragment.MainFragmentA;
import com.mobilepolice.office.ui.fragment.MainFragmentB;
import com.mobilepolice.office.ui.fragment.MainFragmentC;
import com.mobilepolice.office.ui.fragment.MainFragmentD_Final;
import com.mobilepolice.office.ui.fragment.MainFragmentE;
import com.mobilepolice.office.ui.fragment.TelephoneDirectoryFragment;
import com.mobilepolice.office.utils.ActivityStackManager;
import com.mobilepolice.office.utils.JsonParseUtils;
import com.mobilepolice.office.utils.MyCookie;
import com.mobilepolice.office.utils.OnClickUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 主页界面
 */
public class Home2Activity extends MyActivity {

    private MainFragmentB mainFragmentB;

    private ViewPager pager;
    private TabLayout tabView;

    private List<String> titleList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home2;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        pager = findViewById(R.id.pager);
        tabView = findViewById(R.id.tabView);

        // 创建一个集合,装填Fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        titleList = new ArrayList<>();
        List<Drawable> tab_Icon = new ArrayList<>();

        fragments.add(MainFragmentA.newInstance());
        fragments.add(MainFragmentB.newInstance());
        fragments.add(TelephoneDirectoryFragment.newInstance());
        fragments.add(MainFragmentC.newInstance());
        fragments.add(MainFragmentD_Final.newInstance());
        fragments.add(MainFragmentE.newInstance());

        titleList.add("新闻动态");
        titleList.add("待办工作");
        titleList.add("通讯录");
        titleList.add("业务驾驶舱");
        titleList.add("邮箱");
        titleList.add("我的");

//        tab_Icon.add(R.drawable.ic_selector_selectable_home_index);
//        tab_Icon.add(R.drawable.ic_selector_selectable_home_workbench);
//        tab_Icon.add(R.drawable.ic_selector_selectable_home_phone_num);
//        tab_Icon.add(R.drawable.ic_selector_selectable_home_message);
//        tab_Icon.add(R.drawable.ic_selector_selectable_home_contacts);
//        tab_Icon.add(R.drawable.ic_selector_selectable_home_me);


//        tab_Icon.add(getResources().getDrawable(R.mipmap.xinwen));
//        tab_Icon.add(getResources().getDrawable(R.mipmap.daiban));
//        tab_Icon.add(getResources().getDrawable(R.mipmap.tab_ico_contacts_off));
//        tab_Icon.add(getResources().getDrawable(R.mipmap.shujutongji_default));
//        tab_Icon.add(getResources().getDrawable(R.drawable.ic_email_default_cc));
//        tab_Icon.add(getResources().getDrawable(R.mipmap.wode));




        for (int i = 0; i < titleList.size(); i++) {
            tabView.addTab(tabView.newTab().setText(titleList.get(i)));
        }

        PagerAdapter myPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        myPagerAdapter.setFragments(fragments);
        myPagerAdapter.setTitles(titleList);
        pager.setAdapter(myPagerAdapter);
        // 使用 TabLayout 和 ViewPager 相关联
        tabView.setupWithViewPager(pager);
        tabView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("onTabSelected", "-------");
                clearState();
                switch (tab.getPosition()) {
                    case 0:
                        tab.setIcon(R.mipmap.xinwen_selected);
                        break;
                    case 1:
                        tab.setIcon(R.mipmap.daiban_selected);
                        break;
                    case 2:
                        tab.setIcon(R.mipmap.tab_ico_contacts);
                        break;
                    case 3:
                        tab.setIcon(R.mipmap.sjtj_selected);
                        break;
                    case 4:
                        tab.setIcon(R.drawable.ic_email_cc);
                        break;
                    case 5:
                        tab.setIcon(R.mipmap.wode_selected);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //没有办法的办法
        tabView.getTabAt(1).select();
        tabView.getTabAt(0).select();
        mainFragmentB = (MainFragmentB) myPagerAdapter.getItem(1);
    }

    @Override
    protected void initData() {


    }

    private void clearState() {
        tabView.getTabAt(0).setIcon(R.mipmap.xinwen);
        tabView.getTabAt(1).setIcon(R.mipmap.daiban);
        tabView.getTabAt(2).setIcon(R.mipmap.tab_ico_contacts_off);
        tabView.getTabAt(3).setIcon(R.mipmap.shujutongji_default);
        tabView.getTabAt(4).setIcon(R.drawable.ic_email_default_cc);
        tabView.getTabAt(5).setIcon(R.mipmap.wode);
    }


    private void login() {
        //创建JSONObject
        JSONObject jsonObject = new JSONObject();
        //键值对赋值
        try {
            //17600194545
            jsonObject.put("applyPerson", MyCookie.getString("mobile", ""));
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
                    MyApplication.getInstance().personPhone = MyCookie.getString("mobile", "");
//                    showdata();
                } else {
                    toast("读取部门失败,请重新登录");
                    startActivity(LoginNewActivity.class);
                    finish();
                }
            }
        });
    }


    /**
     * {@link ViewPager.OnPageChangeListener}
     */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dispatchOnActivityResult(requestCode, resultCode, data);
    }

    private void dispatchOnActivityResult(int requestCode, int resultCode, Intent data) {
        mainFragmentB.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */


    @Override
    public void onBackPressed() {
        if (OnClickUtils.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            getWindow().getDecorView().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                }
            }, 300);
        } else {
            toast(getResources().getString(R.string.home_exit_hint));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    private String checkRegister() {

        // String wsdl = "http://222.168.10.43:885/YBMessageWeb/LoginWebServicePort?wsdl";
        String wsdl = "http://www.freetk.cn:8789/mobileworkws/services/MobileWorkws?wsdl";//"http://192.168.10.175:8080/YBMessageWeb/LoginWebServicePort?wsdl";
        String namespace = "http://services.webservice.mobilework.com/";
        // 调用方法的名称
        String webmethod = "findApplyPersonDeptName";
        // 创建soapObject对象
        SoapObject soapObject = new SoapObject(namespace, webmethod);
        // 设置参数
        soapObject.addProperty("json", "{\"applyPerson\":\"17600194545\"}");
        // 创建SoapSerializationEnvelope对象，并设置WebService版本号
        SoapSerializationEnvelope serializationEnvelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);

// 设置是否调用的是dotNet开发的WebService
        serializationEnvelope.dotNet = false;
        // 设置serializationEnvelope对象的badyOut属性
        serializationEnvelope.bodyOut = soapObject;
        // 创建HttpTransportSE对象,并且确定wsdl网络地址
        HttpTransportSE httpTransportSE = new HttpTransportSE(wsdl);
        try {
            // httpTransportSE调用Call方法
            httpTransportSE.call(null, serializationEnvelope);
            // 获取返回的结果对象
            if (serializationEnvelope.getResponse() != null) {
                Object obj = serializationEnvelope.getResponse();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(obj.toString());
                    if (jsonObject.get("state").equals("success")) {
//                        telecode = jsonObject.getString("msg");
//                        response = setPushDevice();
                        return "success";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return obj.toString();

            } else {
                return "result-null";
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 获取子列表的数据
     */
    private void getSupSpinerData() {
        SoapUtil soapUtil = SoapUtil.getInstance(mContext);
        soapUtil.setTimeout(Config.TIMEOUT);
        SoapParams params = new SoapParams();
        params.put("applyPerson", "17600194545");//区域ID
        // params.put("arg01",545416022);//区域ID
        // params.put("userId",3);//用户id

        soapUtil.call(Config.SERVICE_URL, Config.SERVICE_NAME_SPACE, "", params, new SoapClient.ISoapUtilCallback() {
            @Override
            public void onSuccess(final SoapSerializationEnvelope envelope) throws Exception {

                SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                final LinkedList<HomeGoodBean> monitorBeens = SoapParseUtils.getMonitorBeans(bodyIn.toString());
                if (monitorBeens == null || monitorBeens.size() == 0) {
                    Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < monitorBeens.size(); i++) {
                    buffer.append("  \n " + monitorBeens.get(i).getContent() + "  \n     ");
                }
                /**
                 ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override public void run() {
                SoapObject bodyIn = (SoapObject) envelope.bodyIn;
                final LinkedList<HomeGoodBean> monitorBeens = SoapParseUtils.getMonitorBeans(bodyIn.toString());
                if (monitorBeens == null || monitorBeens.size() == 0) {
                Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
                return;
                }
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < monitorBeens.size(); i++) {
                buffer.append("  \n " + monitorBeens.get(i).getContent() + "  \n     ");
                }
                //tvResult.setText(buffer);
                }
                });
                 ***/
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(mContext, "访问失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Login() {
        Message msg = new Message();

        try {
//声明Service的空间命名，.net默认为 http://tempuri.org/
            //第二个参数是要调用的方法
            SoapObject so = new SoapObject("http://services.webservice.mobilework.com/", "findApplyPersonDeptName");

            //设置调用Service需要传入的两个参数，闻说参数名可以不正确，但顺序必需要正确
            so.addProperty("applyPerson", "17600194545");
            // so.addProperty("password", psw);

            // 设置调用WebService方法的SOAP请求信息,并指定SOAP的版本
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = so;

// 设置是否调用的是dotNet开发的WebService
            envelope.dotNet = false;


// 设置Service所使用的URL
            String url = "http://www.freetk.cn:8789/mobileworkws/services/MobileWorkws?wsdl";//this.getString(R.string.webservice_domain) + this.getString(R.string.webservice_url_logic);
            HttpTransportSE ht = new HttpTransportSE(url);
            ht.call(null, envelope);
            if (envelope.getResponse() != null) {
//接收返回的对象
                SoapObject responseSO = (SoapObject) envelope.getResponse();
                Boolean succeed = Boolean.parseBoolean(responseSO.getProperty("Succeed").toString());
                if (!succeed) {
                    msg.obj = responseSO.getProperty("Message").toString();
                    msg.what = 0;
                } else {
                    msg.obj = responseSO.getProperty("ReturnObject");
                    msg.what = 1;
                }
            }
        } catch (Exception ex) {
            msg.obj = ex.getMessage();
            msg.what = -1;
        } finally {
            // handler.sendMessage(msg);
        }
    }
}