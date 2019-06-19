package com.mobilepolice.office.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.ContactsBean;
import com.mobilepolice.office.bean.ContactsData;
import com.mobilepolice.office.bean.FindDepartmentAll;
import com.mobilepolice.office.http.HttpConnectInterface;
import com.mobilepolice.office.http.HttpTools;
import com.mobilepolice.office.ui.activity.NewsDetailedActivity;
import com.mobilepolice.office.ui.adapter.ContactAdapter2;
import com.mobilepolice.office.ui.adapter.ContactsAdapter;
import com.mobilepolice.office.ui.adapter.NewsAdapter;
import com.mobilepolice.office.ui.adapter.SimpleTextAdapter;
import com.mobilepolice.office.utils.BitmapUtils;
import com.mobilepolice.office.widget.RecycleViewDivider;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目界面跳转示例
 */
public class MainFragmentD extends MyLazyFragment
        implements View.OnClickListener {

    //    @BindView(R.id.tv_department1)
//    TextView tv_department1;
//    @BindView(R.id.tv_department2)
//    TextView tv_department2;
//    @BindView(R.id.tv_department3)
//    TextView tv_department3;
//    @BindView(R.id.tv_department4)
//    TextView tv_department4;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    ContactAdapter2 adapter;

    @BindView(R.id.ll_mess)
    LinearLayout ll_mess;
    @BindView(R.id.tv_contacts_title)
    TextView tv_contacts_title;
    @BindView(R.id.img_cancel)
    ImageView img_cancel;
    @BindView(R.id.img_head)
    ImageView img_head;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_cnotacts_phone)
    TextView et_cnotacts_phone;
    @BindView(R.id.et_cnotacts_mail)
    TextView et_cnotacts_mail;
    @BindView(R.id.class_id_01)
    LinearLayout class_id_01;
    @BindView(R.id.img_phone)
    ImageView img_phone;
    String phone;

    @BindView(R.id.mContactsList)
    ListView mContactsList;
    SimpleTextAdapter simpleTextAdapter = new SimpleTextAdapter();

    TextView tv_service_title;
    List<ContactsBean> list = new ArrayList<ContactsBean>();
    private ArrayList<FindDepartmentAll> findDepartmentAlls;

    public static MainFragmentD newInstance() {
        return new MainFragmentD();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_d2;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {
        initAdapter();

        ll_mess.setVisibility(View.GONE);
        tv_service_title.setText("共0人");
        mRecyclerView.setVisibility(View.INVISIBLE);
        mContactsList.setAdapter(simpleTextAdapter);
        mContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FindDepartmentAll item = simpleTextAdapter.getItem(position);
                ArrayList<ContactsData> contactsData = userInfoCache.get(item.getId());
                adapter.setNewData(contactsData);
                if (contactsData != null)
                    tv_service_title.setText("共" + contactsData.size() + "人");
                else
                    tv_service_title.setText("共0人");
                mRecyclerView.setVisibility(View.VISIBLE);
                ll_mess.setVisibility(View.GONE);

            }
        });
//        tv_department1.setOnClickListener(this);
//        tv_department2.setOnClickListener(this);
//        tv_department3.setOnClickListener(this);
//        tv_department4.setOnClickListener(this);

        img_cancel.setOnClickListener(this);
        class_id_01.setOnClickListener(this);
        img_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(phone)) {
                    callPhone(phone);
                }
            }
        });
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initData() {
//        initAdapter();
//        changeData1();
        HttpTools.build(HttpConnectInterface.class)
                .findDepartmentAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::all, this::err, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void all(ArrayList<FindDepartmentAll> findDepartmentAlls) {
        mRecyclerView.setVisibility(View.VISIBLE);
        this.findDepartmentAlls = findDepartmentAlls;
        if (findDepartmentAlls != null) {
            simpleTextAdapter.setDepartmentAlls(findDepartmentAlls);
        }
        for (FindDepartmentAll fa : findDepartmentAlls) {
            HttpTools.build(HttpConnectInterface.class)
                    .findTxlByDepartment(fa.getId(), "1", fa.getCount())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::itemDetails, this::err, this::onComplete)
                    .isDisposed();
            ;
        }
    }

    private void itemDetails(ArrayList<ContactsData> o) {
        if (o.size() > 0) {
            ArrayList<ContactsData> contactsData = userInfoCache.get(o.get(0).getDepartment());
            if (contactsData == null) {
                contactsData = new ArrayList<>();
                userInfoCache.put(o.get(0).getDepartment(), contactsData);
            }
            contactsData.addAll(o);

        }
    }

    private void changeData3() {
        list = new ArrayList<ContactsBean>();
        ContactsBean bean = new ContactsBean();
        bean.setUserid("1");
        bean.setUsername("王莱");
        bean.setImage(R.mipmap.head_img1);
        bean.setDepartmentName("科技通信处");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("厅长");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("2");
        bean.setUsername("于志远");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("科技通信处");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("主任");
        list.add(bean);

        bean = new ContactsBean();
        bean.setUserid("3");
        bean.setUsername("张三飞");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("科技通信处");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);

        bean = new ContactsBean();
        bean.setUserid("4");
        bean.setUsername("黄文龙");
        bean.setImage(R.mipmap.head_img1);
        bean.setDepartmentName("科技通信处");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("5");
        bean.setUsername("刘美玲");
        bean.setImage(R.mipmap.head_img);
        bean.setDepartmentName("科技通信处");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("6");
        bean.setUsername("张晓军");
        bean.setImage(R.mipmap.head_img);
        bean.setDepartmentName("科技通信处");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        if (list != null) {

//            adapter.setNewData(list);
        }
    }

    private void changeData4() {
        list = new ArrayList<ContactsBean>();
//        ContactsBean bean = new ContactsBean();
//        bean.setUserid("1");
//        bean.setUsername("崔南");
//        bean.setImage(R.mipmap.head_img2);
//        bean.setDepartmentName("公安厅厅长");
//        bean.setEmail("cuinan@gat.jl");
//        bean.setTelephone("18186867051");
//        bean.setTitle("厅长");
//        list.add(bean);
//        bean = new ContactsBean();
//        bean.setUserid("2");
//        bean.setUsername("于志远");
//        bean.setImage(R.mipmap.head_img);
//        bean.setDepartmentName("公安厅厅长");
//        bean.setEmail("cuinan@gat.jl");
//        bean.setTelephone("18186867051");
//        bean.setTitle("主任");
//        list.add(bean);
//
//        bean = new ContactsBean();
//        bean.setUserid("3");
//        bean.setUsername("张三飞");
//        bean.setImage(R.mipmap.head_img2);
//        bean.setDepartmentName("公安厅厅长");
//        bean.setEmail("cuinan@gat.jl");
//        bean.setTelephone("18186867051");
//        bean.setTitle("职员");
//        list.add(bean);
//
//        bean = new ContactsBean();
//        bean.setUserid("4");
//        bean.setUsername("黄文龙");
//        bean.setImage(R.mipmap.head_img1);
//        bean.setDepartmentName("公安厅厅长");
//        bean.setEmail("cuinan@gat.jl");
//        bean.setTelephone("18186867051");
//        bean.setTitle("职员");
//        list.add(bean);
//        bean = new ContactsBean();
//        bean.setUserid("5");
//        bean.setUsername("崔南");
//        bean.setImage(R.mipmap.head_img2);
//        bean.setDepartmentName("公安厅厅长");
//        bean.setEmail("cuinan@gat.jl");
//        bean.setTelephone("18186867051");
//        bean.setTitle("职员");
//        list.add(bean);
//        bean = new ContactsBean();
//        bean.setUserid("6");
//        bean.setUsername("权宏");
//        bean.setImage(R.mipmap.head_img2);
//        bean.setDepartmentName("职员");
//        bean.setEmail("cuinan@gat.jl");
//        bean.setTelephone("18186867051");
//        bean.setTitle("职员");
//        list.add(bean);
//        bean = new ContactsBean();
//        bean.setUserid("7");
//        bean.setUsername("钱和");
//        bean.setImage(R.mipmap.head_img2);
//        bean.setDepartmentName("职员");
//        bean.setEmail("cuinan@gat.jl");
//        bean.setTelephone("18186867051");
//        bean.setTitle("职员");
//        list.add(bean);
        if (list != null) {
//
//            adapter.setNewData(list);
        }
    }

    private void initRecyclerviewTitle() {

        View headView = getLayoutInflater().inflate(R.layout.item_grid_contacts_bottom, (ViewGroup) mRecyclerView.getParent(), false);
        tv_service_title = headView.findViewById(R.id.tv_bottom_title);
        adapter.addFooterView(headView);
    }

    private void initAdapter() {
        adapter = new ContactAdapter2();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        initRecyclerviewTitle();
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ContactsData bean = (ContactsData) adapter.getData().get(position);
                if (bean != null) {
                    ll_mess.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    tv_contacts_title.setText(bean.getDepartmentName() + ">个人信息");
                    img_head.setImageResource(R.mipmap.icon_user_faqi);
                    tv_title.setText(bean.getDepartmentName() + "  "  /*+bean.getTitle()*/);
                    phone = bean.getTelephone();
                    et_cnotacts_phone.setText(bean.getTelephone());
//                    et_cnotacts_mail.setText(bean.getEmail());
                }
            }
        });

    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {

        if (v == img_cancel) {
            ll_mess.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            clearMessData();
        } else if (v == class_id_01) {
            toast("保存成功！");
            ll_mess.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            clearMessData();
        }
    }

    private void clearMessData() {
        tv_contacts_title.setText("");
        img_head.setImageResource(R.mipmap.img_default);
        tv_name.setText("");
        tv_title.setText("");
        et_cnotacts_phone.setText("");
        et_cnotacts_mail.setText("");
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

    private HashMap<String, ArrayList<ContactsData>> userInfoCache = new HashMap<>();
}