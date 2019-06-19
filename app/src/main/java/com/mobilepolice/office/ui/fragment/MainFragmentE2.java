package com.mobilepolice.office.ui.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fish.timeline.TaskInfo;
import com.fish.timeline.TimeLineController;
import com.google.gson.Gson;
import com.guojunustb.library.WeekViewEvent;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.CalendarScheduleBean;
import com.mobilepolice.office.bean.CollectEmailBean;
import com.mobilepolice.office.bean.DraftsEmailList;
import com.mobilepolice.office.bean.InsertTaskInfo;
import com.mobilepolice.office.bean.NormalModel;
import com.mobilepolice.office.bean.QueryTaskInfo;
import com.mobilepolice.office.bean.SimpleBean;
import com.mobilepolice.office.bean.UpdataEmail;
import com.mobilepolice.office.bean.sendMailBean;
import com.mobilepolice.office.config.Config;
import com.mobilepolice.office.http.HttpConnectInterface;
import com.mobilepolice.office.http.HttpTools;
import com.mobilepolice.office.http.ResponseMe;
import com.mobilepolice.office.ui.activity.DraftsEmailDetail;
import com.mobilepolice.office.ui.activity.EmailDetail;
import com.mobilepolice.office.ui.activity.MailBoxContactsActivity;
import com.mobilepolice.office.ui.adapter.CollectRecyclerViewAdapter;
import com.mobilepolice.office.ui.adapter.DraftsEmailListAdapter;
import com.mobilepolice.office.ui.adapter.SendRecyclerViewAdapter;
import com.mobilepolice.office.utils.DateUtil;
import com.mobilepolice.office.widget.RecycleViewDivider;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;
import com.othershe.calendarview.weiget.WeekView;

import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目界面跳转示例
 */

@Deprecated
public class MainFragmentE2 extends MyLazyFragment
        implements View.OnClickListener {


    @BindView(R.id.tv_username)
    TextView tv_username;
    /*我的日程*/
    @BindView(R.id.tv_rc)
    TextView tv_rc;
    /*邮箱*/
    @BindView(R.id.tv_mail)
    TextView tv_mail;

    @BindView(R.id.ll_mailbox_main)
    LinearLayout ll_mailbox_main;

    @BindView(R.id.tv_mailbox_main_add)
    TextView tv_mailbox_main_add;  /*新增邮件*/
    @BindView(R.id.tv_count)
    TextView tv_count;

    @BindView(R.id.ll_collect)
    LinearLayout ll_collect;
    @BindView(R.id.ll_send)
    LinearLayout ll_send;
    @BindView(R.id.ll_drafts)
    LinearLayout ll_drafts;
    @BindView(R.id.mTimeLine)
    LinearLayout mRoot;

    @BindView(R.id.ll_mailbox_send)
    LinearLayout ll_mailbox_send;
    @BindView(R.id.tv_mailbox_create_title)
    TextView tv_mailbox_create_title;
    @BindView(R.id.tv_mailbox_send)  /*发送邮件*/
    TextView tv_mailbox_send;
    @BindView(R.id.img_contacts)
    ImageView img_contacts;

    @BindView(R.id.et_mailbox_consignee)
    EditText et_mailbox_consignee;
    @BindView(R.id.et_mailbox_title)
    EditText et_mailbox_title;
    @BindView(R.id.et_mailbox_content)
    EditText et_mailbox_content;

    @BindView(R.id.ll_mailbox_recevice_main)
    LinearLayout ll_mailbox_recevice_main;
    @BindView(R.id.tv_mailbox_receive_title)
    TextView tv_mailbox_receive_title;
    @BindView(R.id.tv_mailbox_receive_add)
    TextView tv_mailbox_receive_add;
    @BindView(R.id.rv_recevice_mRecyclerView)
    RecyclerView rv_recevice_mRecyclerView;


    @BindView(R.id.ll_mailbox_sendlist)
    LinearLayout ll_mailbox_sendlist;
    @BindView(R.id.tv_mailbox_sendlist_title)
    TextView tv_mailbox_sendlist_title;
    @BindView(R.id.tv_mailbox_sendlist_add)
    TextView tv_mailbox_sendlist_add;
    @BindView(R.id.rv_sendlist_mRecyclerView)
    RecyclerView rv_sendlist_mRecyclerView;

    @BindView(R.id.ll_mailbox_drafts)
    LinearLayout ll_mailbox_drafts;
    @BindView(R.id.tv_mailbox_drafts_title)
    TextView tv_mailbox_drafts_title;
    @BindView(R.id.tv_mailbox_drafts_add)
    TextView tv_mailbox_drafts_add;  /*邮箱按钮*/
    @BindView(R.id.rv_drafts_mRecyclerView)
    RecyclerView rv_drafts_mRecyclerView;


    @BindView(R.id.ll_rc_main)
    LinearLayout ll_rc_main;
    @BindView(R.id.lastMonth)
    ImageView lastMonth;
    @BindView(R.id.nextMonth)
    ImageView nextMonth;
    @BindView(R.id.calendar)
    CalendarView calendarView;
//    @BindView(R.id.weekdayview)
//    WeekDayView mWeekView;

    @BindView(R.id.rv_rc_mRecyclerView)
    RecyclerView rv_rc_mRecyclerView;
    @BindView(R.id.tv_rc_title)
    TextView tv_rc_title;
//    @BindView(R.id.tv_rc_add)
//    TextView tv_rc_add;


    @BindView(R.id.ll_rc_create)
    LinearLayout ll_rc_create;
    @BindView(R.id.et_reject)
    EditText et_reject;
    @BindView(R.id.ll_start_time)
    LinearLayout ll_start_time;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.ll_end_time)
    LinearLayout ll_end_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.class_id_11)
    LinearLayout class_id_11;
    @BindView(R.id.class_id_22)
    LinearLayout class_id_22;
    @BindView(R.id.weekView)
    WeekView weekView;

    @BindView(R.id.add_schedule_btn)
    TextView add_schedule_btn;

    private int[] cDate = CalendarUtil.getCurrentDate();
    private String SingleChoose = "";
    private String addScheduleDate = "";
    //    private TimePickerView pvTime;
    private OptionsPickerView pvTime;
    //当前年
    int year = 0;
    //当前月
    int month = 0;
    //当前月的第几天：即当前日
    int day_of_month = 0;
    int day_start_hour = 0;
    int day_end_hour = 0;
    List<WeekViewEvent> mNewEvent = new ArrayList<WeekViewEvent>();

    String title;  //发送邮件标题
    String content; //发送邮件内容
    String receiveMail;//发送邮件收件人
    String sendMailName;//发件人姓名
    String sendMail; //发送人邮箱
    String sendMailPsd; //发送人邮箱密码
    String policeNumber = "123456";  //警员编号

    LinearLayout mask;

    public static MainFragmentE2 newInstance() {
        return new MainFragmentE2();
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
    public void onResume() {
        super.onResume();
        et_mailbox_consignee.setText(MyApplication.getInstance().MailBoxContacts);
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

    @Override
    protected void initView() {
        tv_rc.setOnClickListener(this);
        tv_mail.setOnClickListener(this);
        ll_mailbox_main.setOnClickListener(this);
        tv_mailbox_main_add.setOnClickListener(this);

        tv_mailbox_create_title.setOnClickListener(this);
        tv_mailbox_send.setOnClickListener(this);
        img_contacts.setOnClickListener(this);


        ll_collect.setOnClickListener(this);
        ll_send.setOnClickListener(this);
        ll_drafts.setOnClickListener(this);

        ll_mailbox_recevice_main.setOnClickListener(this);
        ll_mailbox_send.setOnClickListener(this);

        ll_mailbox_sendlist.setOnClickListener(this);
        tv_mailbox_sendlist_title.setOnClickListener(this);
        tv_mailbox_sendlist_add.setOnClickListener(this);

        ll_mailbox_drafts.setOnClickListener(this);
        tv_mailbox_drafts_title.setOnClickListener(this);
        tv_mailbox_drafts_add.setOnClickListener(this);


        tv_mailbox_receive_title.setOnClickListener(this);
        tv_mailbox_receive_add.setOnClickListener(this);

        ll_rc_main.setOnClickListener(this);
        lastMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
//        tv_rc_add.setOnClickListener(this);
        ll_rc_create.setOnClickListener(this);
        ll_start_time.setOnClickListener(this);
        ll_end_time.setOnClickListener(this);
        class_id_11.setOnClickListener(this);
        class_id_22.setOnClickListener(this);
        tv_rc.callOnClick();
        tv_username.setText("测试用户");
        controller = new TimeLineController("1", 8, 17, mRoot);

        TextView saveEmail = findViewById(R.id.saveEmail);
        TextView cancleEmail = findViewById(R.id.cancleEmail);
        mask = findViewById(R.id.mask);
        /*将未发送的邮件保存到草稿箱*/
        saveEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = et_mailbox_title.getText().toString();
                content = et_mailbox_content.getText().toString();
                receiveMail = et_mailbox_consignee.getText().toString();

                ll_mailbox_main.setVisibility(View.VISIBLE);
                ll_mailbox_recevice_main.setVisibility(View.GONE);
                ll_mailbox_send.setVisibility(View.GONE);
                ll_mailbox_sendlist.setVisibility(View.GONE);
                ll_mailbox_drafts.setVisibility(View.GONE);
                ll_rc_main.setVisibility(View.GONE);
                ll_rc_create.setVisibility(View.GONE);
                saveFailedEmail();
            }
        });
        /*不将邮件保存到草稿箱*/
        cancleEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_mailbox_main.setVisibility(View.VISIBLE);
                ll_mailbox_recevice_main.setVisibility(View.GONE);
                ll_mailbox_send.setVisibility(View.GONE);
                ll_mailbox_sendlist.setVisibility(View.GONE);
                ll_mailbox_drafts.setVisibility(View.GONE);
                ll_rc_main.setVisibility(View.GONE);
                ll_rc_create.setVisibility(View.GONE);
                clearMailboxSend();
            }
        });
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        // TODO 调用日程接口
        String today = DateUtil.format("yyyy-MM-dd", System.currentTimeMillis());
        findTimeTaskToday(today);


        add_schedule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 点击添加日程按钮事件绑定
                tv_start_time.setText(SingleChoose);
                tv_end_time.setText(SingleChoose);
                ll_mailbox_main.setVisibility(View.GONE);
                ll_mailbox_recevice_main.setVisibility(View.GONE);
                ll_mailbox_send.setVisibility(View.GONE);
                ll_mailbox_sendlist.setVisibility(View.GONE);
                ll_mailbox_drafts.setVisibility(View.GONE);
                ll_rc_main.setVisibility(View.GONE);
                ll_rc_create.setVisibility(View.VISIBLE);
            }
        });
    }

    private TimeLineController controller;
    //TODO 日程接口
    private void findTimeTaskToday(String time) {
//        String today = DateUtil.format("yyyy-MM-dd", time);
        Log.e("查看日程: ", time );
        String today = time;
        HttpConnectInterface connectInterface = HttpTools.build(HttpConnectInterface.class);
        QueryTaskInfo taskInfo = new QueryTaskInfo();
        taskInfo.setFromDate(today);
        taskInfo.setPoliceNumber(MyApplication.userCode);
        connectInterface.readTimeLineTask(taskInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((o -> this.todayTask(today, o)), this::onErr, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {

    }

    private void onErr(Throwable throwable) {
        throwable.printStackTrace();
    }


    private void todayTask(String today, TaskInfo o) {
        if (o.isSuccess()) {
            taskCache.put(today, o);
            controller.setData(o.getObj());
        }
    }

    private HashMap<String, TaskInfo> taskCache = new HashMap<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mWeekView.goToDate(Calendar.getInstance());
    }

    @Override
    protected void initData() {
//        ll_mailbox_main.setVisibility(View.VISIBLE);
//        ll_mailbox_recevice_main.setVisibility(View.GONE);
//        ll_mailbox_send.setVisibility(View.GONE);
//        ll_mailbox_sendlist.setVisibility(View.GONE);
//        ll_mailbox_drafts.setVisibility(View.GONE);
//        ll_rc_main.setVisibility(View.GONE);
//        ll_rc_create.setVisibility(View.GONE);
        ll_mailbox_main.setVisibility(View.GONE);
        ll_mailbox_recevice_main.setVisibility(View.GONE);
        ll_mailbox_send.setVisibility(View.GONE);
        ll_mailbox_sendlist.setVisibility(View.GONE);
        ll_mailbox_drafts.setVisibility(View.GONE);
        ll_rc_main.setVisibility(View.VISIBLE);
        ll_rc_create.setVisibility(View.GONE);
        try {
            SQLiteDatabase db = Connector.getDatabase();
            Connector.getDatabase();
            //  创建数据库

            HashMap<String, String> map = new HashMap<>();
            map.put("2017.10.30", "qaz");
            map.put("2017.10.1", "wsx");
            map.put("2017.11.12", "yhn");
            map.put("2017.9.15", "edc");
            map.put("2017.11.6", "rfv");
            map.put("2017.11.11", "tgb");
            calendarView
//                .setSpecifyMap(map)
                    .setStartEndDate("1949.1", "2060.12")
                    .setDisableStartEndDate("1997.1.1", "2027.12.31")
                    .setInitDate(cDate[0] + "." + cDate[1])
                    .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                    .init();

        } catch (Exception ex) {
            ex.getLocalizedMessage();
        }
    }
    public static final int DETAIL_CODE = 1;
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
                    clearMailboxSend();
                    toast("发送成功！");
                    ll_mailbox_main.setVisibility(View.VISIBLE);
                    ll_mailbox_recevice_main.setVisibility(View.GONE);
                    ll_mailbox_send.setVisibility(View.GONE);
                    ll_mailbox_sendlist.setVisibility(View.GONE);
                    ll_mailbox_drafts.setVisibility(View.GONE);
                    ll_rc_main.setVisibility(View.GONE);
                    ll_rc_create.setVisibility(View.GONE);
                }else{
//                    Log.e(TAG, "handleMessage: 测试草稿箱2" );
//                    saveFailedEmail();
                    toast("邮件发送失败，请检查收件人信息！");
                }
            }else if(msg.what == 2){
                String response = (String)msg.obj;
                response = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                Gson gson = new Gson();
                CollectEmailBean success = gson.fromJson(response, CollectEmailBean.class);
                List<CollectEmailBean.ObjBean> obj = success.getObj();
                Log.e("handleMessage: ",""+response );
                mAdapter.setNewData(obj);
                rv_recevice_mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CollectEmailBean.ObjBean email = (CollectEmailBean.ObjBean) mAdapter.getData().get(position);
                        Intent intent = new Intent(getContext(), EmailDetail.class);
                        intent.putExtra("detail", email);
//                        intent.putExtra("reciver","崔南南");
                        startActivityForResult(intent,DETAIL_CODE);
                    }
                });
            } else if (msg.what == 3) {
                String response = (String)msg.obj;
                response = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                Gson gson = new Gson();
                UpdataEmail success = gson.fromJson(response, UpdataEmail.class);
            } else if (msg.what == 4) {
                String response = (String)msg.obj;
                Log.e("handleMessage: ", response);
                response = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                Gson gson = new Gson();
                CollectEmailBean success = gson.fromJson(response, CollectEmailBean.class);
                List<CollectEmailBean.ObjBean> obj = success.getObj();
                tv_count.setText(""+obj.size());
            } else if (msg.what == 5) {
                String response = (String)msg.obj;
                Log.e("草稿箱: ", response);
                response = response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
                Gson gson = new Gson();
                sendMailBean success = gson.fromJson(response, sendMailBean.class);
                boolean isSuccess = success.isSuccess();
                if (isSuccess) {
                    toast("邮件保存到草稿箱！");
                    clearMailboxSend();
                    ll_mailbox_main.setVisibility(View.VISIBLE);
                    ll_mailbox_recevice_main.setVisibility(View.GONE);
                    ll_mailbox_send.setVisibility(View.GONE);
                    ll_mailbox_sendlist.setVisibility(View.GONE);
                    ll_mailbox_drafts.setVisibility(View.GONE);
                    ll_rc_main.setVisibility(View.GONE);
                    ll_rc_create.setVisibility(View.GONE);
                }else{
                    toast("邮件发送失败，请重试！");
                }
            } else if (msg.what == 6) {
                String response = (String)msg.obj;
                Log.e("草稿箱: ",""+response );
                Gson gson = new Gson();
                DraftsEmailList success = gson.fromJson(response, DraftsEmailList.class);

                if(!success.isSuccess()){
                    toast(success.getMsg());
                    List<DraftsEmailList.ObjBean> objNull = new ArrayList<>();
                    mDraftsAdapter.setData(objNull);
                }else{
                    List<DraftsEmailList.ObjBean> obj = success.getObj();
                    mDraftsAdapter.setData(obj);

                }
                rv_drafts_mRecyclerView.setAdapter(mDraftsAdapter);

            } else if (msg.what == 7) {
                String response = (String)msg.obj;
                Gson gson = new Gson();
                Log.e("send: ",""+response );
                sendMailBean success = gson.fromJson(response, sendMailBean.class);
                if(success.isSuccess()){
                    mDraftsAdapter.removeItem(deletedEmailIndex);

                    toast("删除成功！");
                }else{
                    toast("删除失败，请检查网络！");
                }

            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case DETAIL_CODE:
                if(resultCode == RESULT_OK ){
                    boolean isSeen = data.getBooleanExtra("isSeen",false);
                    Log.e("isSeen: ", ""+isSeen);
                    if(!isSeen){  //邮件为未读邮件，更新成已读
                        Log.e("isSeen: ", ""+isSeen);
                        String id = data.getStringExtra("id");
                        String username = "cuinan@gat.jl";
                        String password = "cuinan963";
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String result = setSeen(username,password,id);
                                Log.e("result: ", ""+result);
                                Message message = new Message();
                                message.what = 3;
                                message.obj = result;
                                handler.sendMessage(message);
                            }
                        }).start();
                    }else{
                        Log.e("result: ", "else");
                    }

                }

                break;
            case DRAFTCODE:
                if (resultCode == RESULT_OK) {
                    boolean isDelete = data.getBooleanExtra("isDelete", false);
                    Log.e("delete", ""+isDelete);
                    if(isDelete){
                        ll_drafts.callOnClick();
                    }

                }
                break;
        }
    }
    /*将邮件保存到草稿箱*/
    private void saveFailedEmail(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String request = ResponseMe.saveEmail(policeNumber,title,content,receiveMail);
                Message msg = Message.obtain();
                msg.what = 5;
                msg.obj = request;
                handler.sendMessage(msg);
            }
        }).start();

    }
    private String setSeen(String username,String password,String id) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/xml");
        RequestBody body = RequestBody.create(mediaType,

                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.intranetmail.com/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <ser:udMailInfo>" +
                        "         <arg0>{\"username\":\""+username+"\",\"password\":\""+password+"\",\"id\":\""+id+"\"}</arg0>" +
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
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {

        if (v == tv_rc) {
            Drawable drawable = getResources().getDrawable(
                    R.mipmap.rili_selected);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tv_rc.setCompoundDrawables(null, drawable, null, null);

            Drawable drawable1 = getResources().getDrawable(
                    R.mipmap.youxiang_default);
            // / 这一步必须要做,否则不会显示.
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
                    drawable1.getMinimumHeight());
            tv_mail.setCompoundDrawables(null, drawable1, null, null);
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.VISIBLE);
            ll_rc_create.setVisibility(View.GONE);
            initCalendarView();
        } else if (v == tv_mail) {
            Drawable drawable = getResources().getDrawable(
                    R.mipmap.rili_default);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            tv_rc.setCompoundDrawables(null, drawable, null, null);
            Drawable drawable1 = getResources().getDrawable(
                    R.mipmap.youxiang_selected);
            // / 这一步必须要做,否则不会显示.
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(),
                    drawable1.getMinimumHeight());
            tv_mail.setCompoundDrawables(null, drawable1, null, null);
            ll_mailbox_main.setVisibility(View.VISIBLE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
            String username = "cuinan@gat.jl";
            String password = "cuinan963";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = getEmail(username,password);
                    Message message = new Message();
                    message.what = 4;
                    message.obj = result;
                    handler.sendMessage(message);
                }
            }).start();
        } else if (v == ll_mailbox_main) {
            ll_mailbox_main.setVisibility(View.VISIBLE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == ll_mailbox_recevice_main) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.VISIBLE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == ll_mailbox_send) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.VISIBLE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == ll_rc_main) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.VISIBLE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == ll_rc_create) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.VISIBLE);
        } else if (v == tv_mailbox_main_add) {
            //发送邮件
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.VISIBLE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == ll_collect) {
            //收件箱列表
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.VISIBLE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
            initCollectRecyclerView();
        } else if (v == ll_send) {
            //发件箱列表
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.VISIBLE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
            initSendRecyclerView();
        } else if (v == ll_drafts) {
            //草稿箱
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.VISIBLE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
            initdraftsRecyclerView();
        } else if (v == tv_mailbox_create_title) {
            title = et_mailbox_title.getText().toString();
            content = et_mailbox_content.getText().toString();
            receiveMail = et_mailbox_consignee.getText().toString();
            if(!TextUtils.isEmpty(title)||!TextUtils.isEmpty(content)||!TextUtils.isEmpty(receiveMail)){
                Log.e("返回: ","9" );
                mask.setVisibility(View.VISIBLE);
//                saveFailedEmail();
            }else{
                clearMailboxSend();
                ll_mailbox_main.setVisibility(View.VISIBLE);
                ll_mailbox_recevice_main.setVisibility(View.GONE);
                ll_mailbox_send.setVisibility(View.GONE);
                ll_mailbox_sendlist.setVisibility(View.GONE);
                ll_mailbox_drafts.setVisibility(View.GONE);
                ll_rc_main.setVisibility(View.GONE);
                ll_rc_create.setVisibility(View.GONE);
            }

//            clearMailboxSend();

        } else if (v == tv_mailbox_send) {
//            toast("发送成功！");



            title = et_mailbox_title.getText().toString();
            content = et_mailbox_content.getText().toString();
            receiveMail = et_mailbox_consignee.getText().toString();

            sendMailName = "崔南";
            sendMail = "cuinan@gat.jl";
//            sendMail = "";
            sendMailPsd = "cuinan963";
            if(TextUtils.isEmpty(title)){
                toast("主题不能为空");
                return;
            }else if(TextUtils.isEmpty(content)){
                toast("邮件内容不能为空");
                return;
            }else if(TextUtils.isEmpty(receiveMail)){
                toast("收件人不能为空");
                return;
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = sendMail(title,content,receiveMail,sendMailName,sendMail,sendMailPsd);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = result;
                    handler.sendMessage(message);
                }
            }).start();



        } else if (v == img_contacts) {

            Intent intent = new Intent(getActivity(), MailBoxContactsActivity.class);
            startActivity(intent);
        } else if (v == tv_mailbox_receive_add) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.VISIBLE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == tv_mailbox_receive_title) {
            ll_mailbox_main.setVisibility(View.VISIBLE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == tv_mailbox_sendlist_add) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.VISIBLE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == tv_mailbox_sendlist_title) {
            ll_mailbox_main.setVisibility(View.VISIBLE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == tv_mailbox_drafts_add) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.VISIBLE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);
        } else if (v == tv_mailbox_drafts_title) {
            ll_mailbox_main.setVisibility(View.VISIBLE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.GONE);


        } /*else if (tv_rc_add == v) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.GONE);
            ll_rc_create.setVisibility(View.VISIBLE);
        } */ else if (lastMonth == v) {
            calendarView.lastMonth();
        } else if (nextMonth == v) {
            calendarView.nextMonth();
        } else if (ll_start_time == v) {
            initTimePickerStart();
        } else if (ll_end_time == v) {
            initTimePickerEnd();
        } else if (class_id_11 == v) {
            if (TextUtils.isEmpty(et_reject.getText().toString())) {
                toast("日程内容不能为空");
                return;
            }
            if (TextUtils.isEmpty(tv_start_time.getText().toString())) {
                toast("开始时间不能为空");
                return;
            }
            if (TextUtils.isEmpty(tv_end_time.getText().toString())) {
                toast("结束时间不能为空");
                return;
            }
            if (checkTime(tv_start_time.getText().toString(), tv_end_time.getText().toString())) {
                toast("结束时间必须大于开始时间");
                return;
            }

            HttpConnectInterface connectInterface = HttpTools.build(HttpConnectInterface.class);
            InsertTaskInfo insertTaskInfo = new InsertTaskInfo();
            insertTaskInfo.setContent(et_reject.getText().toString());
            insertTaskInfo.setFromDate(DateUtil.format("yyyy-MM-dd HH:mm:ss", DateUtil.parseDate("yyyy年MM月dd日 HH:mm", tv_start_time.getText().toString())));
            insertTaskInfo.setToDate(DateUtil.format("yyyy-MM-dd HH:mm:ss", DateUtil.parseDate("yyyy年MM月dd日 HH:mm", tv_end_time.getText().toString())));
            insertTaskInfo.setPoliceNumber(MyApplication.userCode);
            connectInterface.saveTimeLineTask(insertTaskInfo)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .subscribe(this::commit, this::onErr, this::onComplete)
                    .isDisposed();

            CalendarScheduleBean scheduleBean = new CalendarScheduleBean();
            scheduleBean.setContent(et_reject.getText().toString());
            scheduleBean.setStarttime(tv_start_time.getText().toString());
            scheduleBean.setStarttime(tv_end_time.getText().toString());
            scheduleBean.setYear(year);
            scheduleBean.setMonth(month);
            scheduleBean.setDay_of_month(day_of_month);
            scheduleBean.setDay_start_hour(day_start_hour);
            scheduleBean.setDay_end_hour(day_end_hour);
            scheduleBean.save();
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.VISIBLE);
            ll_rc_create.setVisibility(View.GONE);
            // mWeekView.goToDate(Calendar.getInstance());
            clearRcCreate();
        } else if (class_id_22 == v) {
            ll_mailbox_main.setVisibility(View.GONE);
            ll_mailbox_recevice_main.setVisibility(View.GONE);
            ll_mailbox_send.setVisibility(View.GONE);
            ll_mailbox_sendlist.setVisibility(View.GONE);
            ll_mailbox_drafts.setVisibility(View.GONE);
            ll_rc_main.setVisibility(View.VISIBLE);
            ll_rc_create.setVisibility(View.GONE);
            // mWeekView.goToDate(Calendar.getInstance());
            clearRcCreate();
        }

    }

    private void commit(SimpleBean simpleBean) {
        if (simpleBean.isSuccess()) {
            //TODO 添加日程后，显示被添加的日程
            findTimeTaskToday(addScheduleDate);
        }
    }

    private List<NormalModel> mData;
    CollectRecyclerViewAdapter mAdapter;
    private void initCollectRecyclerView() {

        rv_recevice_mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_recevice_mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new CollectRecyclerViewAdapter();
//        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
//            @Override
//            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
//                 Toast.makeText(getActivity(), "点击了条目 " + mAdapter.getItem(position).mTitle, Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//        mAdapter.setOnRVItemLongClickListener(new BGAOnRVItemLongClickListener() {
//            @Override
//            public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
//                return false;
//            }
//        });
//        mAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(ViewGroup parent, View childView, int position) {
//                if (childView.getId() == R.id.tv_item_bgaswipe_delete) {
//                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
//                    mAdapter.removeItem(position);
//                } else if (childView.getId() == R.id.tv_item_bgaswipe_more) {
//
//                }
//            }
//        });
//        mAdapter.setOnItemChildLongClickListener(new BGAOnItemChildLongClickListener() {
//            @Override
//            public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
//                return false;
//            }
//        });
//
//        rv_recevice_mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
//                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
//                }
//            }
//        });
        String username = "cuinan@gat.jl";
        String password = "cuinan963";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = getEmail(username,password);
                Message message = new Message();
                message.what = 2;
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();
//        mData = loadNormalModelDatas();
//        mAdapter.setData(mData);
//        rv_recevice_mRecyclerView.setAdapter(mAdapter);
    }
    private String getEmail(String username,String password){
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/xml");
        RequestBody body = RequestBody.create(mediaType,
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.webservice.intranetmail.com/\">" +
                        "   <soapenv:Header/>" +
                        "   <soapenv:Body>" +
                        "      <ser:findInboxInfo>" +
                        "         <arg0>{\"username\":\""+username+"\",\"password\":\""+password+"\"}</arg0>" +
                        "      </ser:findInboxInfo>" +
                        "   </soapenv:Body>" +
                        "</soapenv:Envelope>"
        );
        Request request = new Request.Builder()
                .url(Config.REQUEST_URL)
                .post(body)
                .addHeader("Content-Type", "text/xml")
                .addHeader("Cache-Control", "no-cache")
//                .addHeader("Postman-Token", "207ed22f-b5d4-4ca3-b025-fe1873f62a54")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void initSendRecyclerView() {

        rv_sendlist_mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_sendlist_mRecyclerView.setLayoutManager(layoutManager);

        SendRecyclerViewAdapter mAdapter = new SendRecyclerViewAdapter(rv_sendlist_mRecyclerView);
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                // Toast.makeText(getActivity(), "点击了条目 " + mAdapter.getItem(position).mTitle, Toast.LENGTH_SHORT).show();

            }
        });
        mAdapter.setOnRVItemLongClickListener(new BGAOnRVItemLongClickListener() {
            @Override
            public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
                return false;
            }
        });
        mAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_item_bgaswipe_delete) {
                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
                    mAdapter.removeItem(position);
                } else if (childView.getId() == R.id.tv_item_bgaswipe_more) {

                }
            }
        });
        mAdapter.setOnItemChildLongClickListener(new BGAOnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(ViewGroup parent, View childView, int position) {
                return false;
            }
        });

        rv_sendlist_mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {
                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
                }
            }
        });

//        mData = loadNormalModelDatas();
        mAdapter.setData(mData);
        rv_sendlist_mRecyclerView.setAdapter(mAdapter);
    }
    DraftsEmailListAdapter mDraftsAdapter;
    int deletedEmailIndex;
    public static final int DRAFTCODE = 2;
    private void initdraftsRecyclerView() {
        Log.e("删除: ", "0" );
        rv_drafts_mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_drafts_mRecyclerView.setLayoutManager(layoutManager);
        mDraftsAdapter = new DraftsEmailListAdapter(rv_drafts_mRecyclerView);

        mDraftsAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                /*mAdapter.getItem(position)*/
                DraftsEmailList.ObjBean email = mDraftsAdapter.getItem(position);
                Intent intent = new Intent(getContext(), DraftsEmailDetail.class);
                intent.putExtra("detail", email);
                startActivityForResult(intent,DRAFTCODE);
            }
        });
        mDraftsAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                if (childView.getId() == R.id.tv_item_bgaswipe_delete) {
                    String id = mDraftsAdapter.getItem(position).getId();
                    deletedEmailIndex = position;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String result = ResponseMe.deleteDraftEmal(id);
                            Message message = new Message();
                            message.what = 7;
                            message.obj = result;
                            handler.sendMessage(message);
                        }
                    }).start();
                    mDraftsAdapter.closeOpenedSwipeItemLayoutWithAnim();

                } else if (childView.getId() == R.id.tv_item_bgaswipe_more) {

                }
            }
        });



        String policeNumber = "123456";
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = ResponseMe.getDraftsEmail(policeNumber);
                Message message = new Message();
                message.what = 6;
                message.obj = result;
                handler.sendMessage(message);
            }
        }).start();

    }

    public static List<NormalModel> loadNormalModelDatas(String date ,String title, String detail,String email,boolean flag, int type) {
        List<NormalModel> datas = new ArrayList<>();
        NormalModel model = new NormalModel();
        model.mDate = date;
        model.mTitle = title;
        model.mDetail = detail;
        model.mEmail = email;
        model.mFlag = flag;
        model.mType = type;
        datas.add(model);
        return datas;
    }

    /**
     * 发送邮件清空
     */
    private void clearMailboxSend() {
        et_mailbox_consignee.setText("");
        et_mailbox_title.setText("");
        et_mailbox_content.setText("");
        title = "";
        content = "";
        receiveMail = "";
        mask.setVisibility(View.GONE);
    }

    /**
     * 日程添加清空
     */
    private void clearRcCreate() {
        et_reject.setText("");
        tv_start_time.setText("");
        tv_end_time.setText("");
    }


    private void initCalendarView() {


        tv_rc_title.setText(cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");
        // chooseDate.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                tv_rc_title.setText(date[0] + "年" + date[1] + "月" + date[2] + "日");
                year = date[0];
                month = date[1];
                day_of_month = date[2];
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(date[0], date[1], date[2], 0, 0);
                //  mWeekView.goToDate(selectedDate);
            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                tv_rc_title.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                year = date.getSolar()[0];
                month = date.getSolar()[1];
                day_of_month = date.getSolar()[2];
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(date.getSolar()[0], date.getSolar()[1], date.getSolar()[2], 0, 0);
                // mWeekView.goToDate(selectedDate);
                SingleChoose = date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日 00:00";
                tv_start_time.setText(SingleChoose);
                tv_end_time.setText(SingleChoose);
                addScheduleDate = date.getSolar()[0] + "-" + (date.getSolar()[1]<10?"0"+date.getSolar()[1]:date.getSolar()[1]) + "-" + (date.getSolar()[2]<10?"0"+date.getSolar()[2]:date.getSolar()[2]);
                //TODO 添加日程（老版）
//                ll_mailbox_main.setVisibility(View.GONE);
//                ll_mailbox_recevice_main.setVisibility(View.GONE);
//                ll_mailbox_send.setVisibility(View.GONE);
//                ll_mailbox_sendlist.setVisibility(View.GONE);
//                ll_mailbox_drafts.setVisibility(View.GONE);
//                ll_rc_main.setVisibility(View.GONE);
//                ll_rc_create.setVisibility(View.VISIBLE);
                if (date.getType() == 1) {
                    //chooseDate.setText("当前选中的日期：" + date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                }
            }
        });
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     */
//    private void setupDateTimeInterpreter(final boolean shortDate) {
//        final String[] weekLabels = {"日", "一", "二", "三", "四", "五", "六"};
//      mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
//            @Override
//            public String interpretDate(Calendar date) {
//                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
//                String weekday = weekdayNameFormat.format(date.getTime());
//                SimpleDateFormat format = new SimpleDateFormat("d", Locale.getDefault());
//                return format.format(date.getTime());
//            }
//
//            @Override
//            public String interpretTime(int hour) {
//                return String.format("%02d:00", hour);
//
//            }
//
//            @Override
//            public String interpretWeek(int date) {
//                if (date > 7 || date < 1) {
//                    return null;
//                }
//                return weekLabels[date - 1];
//            }
//        });
//    }
    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    private void initTimePickerStart() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

        Calendar selectedDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
//        //当前年
//        int year = selectedDate.get(Calendar.YEAR);
//        //当前月
//        int month = (selectedDate.get(Calendar.MONTH));
//        //当前月的第几天：即当前日
//        int day_of_month = selectedDate.get(Calendar.DAY_OF_MONTH);
        selectedDate.set(year, month - 1, day_of_month, 8, 0);
        endDate.set(year, month - 1, day_of_month, 17, 0);
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2013, 0, 23);
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
//        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
//                /*btn_Time.setText(getTime(date));*/
////                Button btn = (Button) v;
////                btn.setText(getTime(date));
//                String attime = getTime(date);
//                day_start_hour = date.getHours();
//                tv_start_time.setText(attime);
//                Log.e("onTimeSelect: ", DateUtil.format("yyyy-MM-dd HH:mm:ss", date.getTime()));
//            }
//        })
//                //年月日时分秒 的显示与否，不设置则默认全部显示
//                .setType(new boolean[]{false, false, false, true, false, false})
//                .setLabel("年", "月", "日", "时", "分", "秒")
//                .setTitleText("日期时间")
//                .isCenterLabel(false)
//                .setDividerColor(Color.DKGRAY)
//                .setContentTextSize(21)
//                .setDate(selectedDate)
////                .setRangDate(selectedDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
//                .setDecorView(null)
//                .build();

        pvTime = new OptionsPickerView.Builder(this.getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_start_time.setText(DateUtil.format("yyyy年MM月dd日",selectedDate.getTimeInMillis())+" "+timeList.get(options1)+":00");
            }
        }).setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
//                        .setTitleText("城市选择")//标题
                .setSubCalSize(20)//确定和取消文字大小
//                        .setTitleSize(20)//标题文字大小
//                        .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                        .setContentTextSize(18)//滚轮文字大小
//                        .setTextColorCenter(Color.BLUE)//设置选中项的颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
//                        .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setPicker(timeTitle);
//        pvTime= new   OptionsPickerView (new PickerOptions())
//        pvTime.show();
        pvTime.show();
    }

    private ArrayList<String> timeTitle = new ArrayList<>();
    private ArrayList<String> timeList = new ArrayList<>();

    {
        timeTitle.add("8时");
        timeTitle.add("9时");
        timeTitle.add("10时");
        timeTitle.add("11时");
        timeTitle.add("12时");
        timeTitle.add("13时");
        timeTitle.add("14时");
        timeTitle.add("15时");
        timeTitle.add("16时");
        timeTitle.add("17时");

        timeList.add("08");
        timeList.add("09");
        timeList.add("10");
        timeList.add("11");
        timeList.add("12");
        timeList.add("13");
        timeList.add("14");
        timeList.add("15");
        timeList.add("16");
        timeList.add("17");

    }

    private void initTimePickerEnd() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11

        Calendar selectedDate = Calendar.getInstance();
//        //当前年
//        int year = selectedDate.get(Calendar.YEAR);
//        //当前月
//        int month = (selectedDate.get(Calendar.MONTH));
//        //当前月的第几天：即当前日
//        int day_of_month = selectedDate.get(Calendar.DAY_OF_MONTH);
        selectedDate.set(year, month - 1, day_of_month, 0, 0);
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2013, 0, 23);
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2019, 11, 28);
        //时间选择器
//        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
//                /*btn_Time.setText(getTime(date));*/
////                Button btn = (Button) v;
////                btn.setText(getTime(date));
//                String attime = getTime(date);
//                day_end_hour = date.getHours();
//                tv_end_time.setText(attime);
//            }
//        })
//                //年月日时分秒 的显示与否，不设置则默认全部显示
//                .setType(new boolean[]{false, false, false, true, false, false})
//                .setLabel("年", "月", "日", "时", "分", "秒")
//                .setTitleText("日期时间")
//                .isCenterLabel(false)
//                .setDividerColor(Color.DKGRAY)
//                .setContentTextSize(21)
//                .setDate(selectedDate)
////                .setRangDate(selectedDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
//                .setDecorView(null)
//                .build();
//        pvTime.show();
        pvTime = new OptionsPickerView.Builder(this.getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_end_time.setText(DateUtil.format("yyyy年MM月dd日",selectedDate.getTimeInMillis())+" "+timeList.get(options1)+":00");
            }
        }).setSubmitText("确定")//确定按钮文字
//                        .setCancelText("取消")//取消按钮文字
//                        .setTitleText("城市选择")//标题
                .setSubCalSize(20)//确定和取消文字大小
//                        .setTitleSize(20)//标题文字大小
//                        .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                        .setContentTextSize(18)//滚轮文字大小
//                        .setTextColorCenter(Color.BLUE)//设置选中项的颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
//                        .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setPicker(timeTitle);
        pvTime.show();
    }

    public static String getHour(Date currentTime) {
//        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00", Locale.CHINA);
        String dateString = sdf.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        return hour;
    }

    public static int getHour(String str) {
        int result = 0;

        if (str.equals("01")) {

        }
        return result;
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:00");
        return format.format(date);
    }

    private boolean checkTime(String start, String end) {
        Log.e("date", "checkTime: " + start + "::" + end);
        boolean flag = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");//年-月-日 时-分
        try {
            Date date1 = dateFormat.parse(start);//开始时间
            //  String EndTime = getTime(new Date());
            Date date2 = dateFormat.parse(end);//结束时间
            if (date2.getTime() < date1.getTime()) {
                //showToast("结束时间小于开始时间");
                flag = true;
            } else if (date2.getTime() == date1.getTime()) {
                //   showToast("开始时间与结束时间相同");
                //flag = true;
            } else if (date2.getTime() > date1.getTime()) {
                //正常情况下的逻辑操作.
                // flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
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
}