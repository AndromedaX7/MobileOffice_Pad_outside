package com.mobilepolice.office.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.ContactsData;
import com.mobilepolice.office.widget.StackLayout;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目界面跳转示例
 */
public class MainFragmentD_Final extends MyLazyFragment
        implements View.OnClickListener {

    @BindView(R.id.viewStack)
    StackLayout viewStack;
    //
    Page_0_ViewHolder p0;
    Page_1_ViewHolder p1;
    Page_2_Sender_ViewHolder p2_sender;
    View p1_View;
    View p2_ViewSender;

    public static MainFragmentD_Final newInstance() {
        return new MainFragmentD_Final();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.email_root;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.email_root_p0, viewStack, false);
        p1_View = LayoutInflater.from(getContext()).inflate(R.layout.email_content_p1, viewStack, false);
        p2_ViewSender = LayoutInflater.from(getContext()).inflate(R.layout.email_sender_p2, viewStack, false);
        p2_sender = new Page_2_Sender_ViewHolder(p2_ViewSender);
        p1 = new Page_1_ViewHolder(p1_View);
        p0 = new Page_0_ViewHolder(view);
        viewStack.push(view);
//        HttpTools.build(HttpConnectInterface.class)
//                .findDepartmentAll()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::all, this::err, this::onComplete)
//                .isDisposed();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onViewCreated2(view, savedInstanceState);
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

    }


    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
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


    class Page_0_ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.tv_mailbox_main_add)
        TextView tvMailboxMainAdd;
        @BindView(R.id.class_id_01)
        LinearLayout classId01;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.ll_collect)
        LinearLayout llCollect;
        @BindView(R.id.ll_send)
        LinearLayout llSend;
        @BindView(R.id.ll_drafts)
        LinearLayout llDrafts;
        @BindView(R.id.ll_mailbox_main)
        LinearLayout llMailboxMain;

        Page_0_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.ll_collect)
        void onCollectPressed() {
            viewStack.push(p1_View);
            p1.set("收件箱");
        }

        @OnClick(R.id.tv_mailbox_main_add)
        void addNew(){
            viewStack.push(p2_ViewSender);
        }

        @OnClick(R.id.ll_send)
        void onSendPressed() {
            viewStack.push(p1_View);
            p1.set("发件箱");
        }

        @OnClick(R.id.ll_drafts)
        void ll_drafts() {
            viewStack.push(p1_View);
            p1.set("草稿箱");
        }
    }


    class Page_1_ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.rv_recevice_mRecyclerView)
        RecyclerView rvReceviceMRecyclerView;
        @BindView(R.id.ll_mailbox_recevice_main)
        LinearLayout llMailboxReceviceMain;

        Page_1_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.title)
        void back() {
            viewStack.pop();
        }

        void set(String title) {
            this.title.setText(title);
        }
    }

    class Page_2_Sender_ViewHolder {
        @BindView(R.id.tv_mailbox_create_title)
        TextView tvMailboxCreateTitle;
        @BindView(R.id.tv_mailbox_send)
        TextView tvMailboxSend;
        @BindView(R.id.et_mailbox_consignee)
        EditText etMailboxConsignee;
        @BindView(R.id.img_contacts)
        ImageView imgContacts;
        @BindView(R.id.et_mailbox_title)
        EditText etMailboxTitle;
        @BindView(R.id.et_mailbox_content)
        EditText etMailboxContent;
        @BindView(R.id.saveEmail)
        TextView saveEmail;
        @BindView(R.id.cancleEmail)
        TextView cancleEmail;
        @BindView(R.id.mask)
        LinearLayout mask;
        @BindView(R.id.ll_mailbox_send)
        LinearLayout llMailboxSend;

        Page_2_Sender_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.tv_mailbox_create_title)
        void onBack(){
            viewStack.pop();
        }
    }
}