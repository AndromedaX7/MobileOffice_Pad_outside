package com.mobilepolice.office.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.ContactsData;
import com.mobilepolice.office.bean.FindDepartmentAll;
import com.mobilepolice.office.http.HttpConnectInterface;
import com.mobilepolice.office.http.HttpTools;
import com.mobilepolice.office.ui.adapter.ContactAdapter3;
import com.mobilepolice.office.ui.adapter.SimpleTextAdapter;
import com.mobilepolice.office.widget.StackLayout;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ContactsView {

    public ContactsView(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        initView();
    }

    private View view;

    public View getView() {
        return view;
    }

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @BindView(R.id.viewStack)
    StackLayout viewStack;
    ArrayList<ContactsData> contactsData;

    Page_0_ViewHolder p0;
    Page_1_ViewHolder p1;
    Page_2_ViewHolder p2;
    View p1_View;
    View p2_View;


    protected int getLayoutId() {
        return R.layout.fragment_main_d2;
    }


    protected void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.user_info_d2_p0, viewStack, false);
        p1_View = LayoutInflater.from(getContext()).inflate(R.layout.user_info_d2_p1, viewStack, false);
        p2_View = LayoutInflater.from(getContext()).inflate(R.layout.user_info_d2_p2, viewStack, false);
        p2 = new Page_2_ViewHolder(p2_View);
        p1 = new Page_1_ViewHolder(p1_View);
        p0 = new Page_0_ViewHolder(view);
        viewStack.push(view);
        HttpTools.build(HttpConnectInterface.class)
                .findDepartmentAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::all, this::err, this::onComplete)
                .isDisposed();
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void all(ArrayList<FindDepartmentAll> findDepartmentAlls) {
        if (findDepartmentAlls != null) {
            p0.simpleTextAdapter.setDepartmentAlls(findDepartmentAlls);
            for (FindDepartmentAll fa : findDepartmentAlls) {
                HttpTools.build(HttpConnectInterface.class)
                        .findTxlByDepartment(fa.getId(), "0", fa.getCount())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::itemDetails, this::err, this::onComplete)
                        .isDisposed();
            }
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

    private void onComplete() {
    }


    private HashMap<String, ArrayList<ContactsData>> userInfoCache = new HashMap<>();

    class Page_0_ViewHolder {
        @BindView(R.id.mContactsList)
        ListView mContactsList;
        SimpleTextAdapter simpleTextAdapter = new SimpleTextAdapter();

        Page_0_ViewHolder(View view) {
            ButterKnife.bind(this, view);
            mContactsList.setAdapter(simpleTextAdapter);
            mContactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FindDepartmentAll item = simpleTextAdapter.getItem(position);
                    ArrayList<ContactsData> contactsData = userInfoCache.get(item.getId());
                    viewStack.push(p1_View);
                    p1.adapter.setNewData(contactsData);
                    p1_View.invalidate();

                }
            });
        }


    }

    class Page_1_ViewHolder {
        @BindView(R.id.mRecyclerView)
        RecyclerView mRecyclerView;
        ContactAdapter3 adapter = new ContactAdapter3();

        @BindView(R.id.mBack)
        ImageView back;

        @OnClick(R.id.mBack)
        void pop() {
            viewStack.pop();
        }

        Page_1_ViewHolder(View view) {
            ButterKnife.bind(this, view);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ContactsData bean = (ContactsData) adapter.getData().get(position);
                    if (bean != null) {
                        viewStack.push(p2_View);
                        p2.set(bean);
                    }
                }
            });
        }
    }


    class Page_2_ViewHolder {
        @BindView(R.id.tv_contacts_title)
        TextView tvContactsTitle;
        @BindView(R.id.mBack)
        ImageView mBack;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.et_cnotacts_phone)
        TextView etCnotactsPhone;
        @BindView(R.id.ll_mess)
        LinearLayout llMess;

        Page_2_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.mBack})
        void close() {
            viewStack.pop();
        }


        @OnClick(R.id.et_cnotacts_phone)
        void call() {
            callPhone(etCnotactsPhone.getText().toString());
        }

        void set(ContactsData data) {
            tvContactsTitle.setText(data.getDepartmentName() + ">个人信息");
            tvName.setText(data.getName());
            tvTitle.setText(data.getDepartmentName());
            etCnotactsPhone.setText(data.getTelephone());
        }
    }
}