package com.mobilepolice.office.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyActivity;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.bean.ContactsBean;
import com.mobilepolice.office.ui.adapter.ContactsAdapter;
import com.mobilepolice.office.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 邮箱首页
 */
public class MailBoxContactsActivity extends MyActivity implements View.OnClickListener {


    @BindView(R.id.tv_department1)
    TextView tv_department1;
    @BindView(R.id.tv_department2)
    TextView tv_department2;
    @BindView(R.id.tv_department3)
    TextView tv_department3;
    @BindView(R.id.tv_department4)
    TextView tv_department4;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    TextView tv_service_title;
    ContactsAdapter adapter;
    List<ContactsBean> list = new ArrayList<ContactsBean>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mailbox_contacts;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {

        tv_department1.setOnClickListener(this);
        tv_department2.setOnClickListener(this);
        tv_department3.setOnClickListener(this);
        tv_department4.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initAdapter();
        changeData1();
    }

    private void changeData1() {
        list = new ArrayList<ContactsBean>();
        ContactsBean bean = new ContactsBean();
        bean.setUserid("1");
        bean.setUsername("王莱");
        bean.setImage(R.mipmap.head_img);
        bean.setDepartmentName("指挥中心");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("厅长");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("2");
        bean.setUsername("于航");
        bean.setImage(R.mipmap.head_img1);
        bean.setDepartmentName("指挥中心");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("主任");
        list.add(bean);

        bean = new ContactsBean();
        bean.setUserid("3");
        bean.setUsername("刘达");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("指挥中心");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);

        bean = new ContactsBean();
        bean.setUserid("4");
        bean.setUsername("黄文龙");
        bean.setImage(R.mipmap.head_img);
        bean.setDepartmentName("指挥中心");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        if (list != null) {
            tv_service_title.setText("共" + list.size() + "人");
            adapter.setNewData(list);
        }
    }

    private void changeData2() {
        list = new ArrayList<ContactsBean>();
        ContactsBean bean = new ContactsBean();
        bean.setUserid("1");
        bean.setUsername("赵小柒");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("办公室");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("厅长");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("2");
        bean.setUsername("王红兵");
        bean.setImage(R.mipmap.head_img1);
        bean.setDepartmentName("办公室");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("主任");
        list.add(bean);

        bean = new ContactsBean();
        bean.setUserid("3");
        bean.setUsername("张三飞");
        bean.setImage(R.mipmap.head_img);
        bean.setDepartmentName("办公室");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);

        bean = new ContactsBean();
        bean.setUserid("4");
        bean.setUsername("段云龙");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("办公室");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("5");
        bean.setUsername("刘美玲");
        bean.setImage(R.mipmap.head_img);
        bean.setDepartmentName("办公室");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        if (list != null) {
            tv_service_title.setText("共" + list.size() + "人");
            adapter.setNewData(list);
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
            tv_service_title.setText("共" + list.size() + "人");
            adapter.setNewData(list);
        }
    }

    private void changeData4() {
        list = new ArrayList<ContactsBean>();
        ContactsBean bean = new ContactsBean();
        bean.setUserid("1");
        bean.setUsername("崔南");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("公安厅厅长");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("厅长");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("2");
        bean.setUsername("于志远");
        bean.setImage(R.mipmap.head_img);
        bean.setDepartmentName("公安厅厅长");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("主任");
        list.add(bean);

        bean = new ContactsBean();
        bean.setUserid("3");
        bean.setUsername("张三飞");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("公安厅厅长");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);

        bean = new ContactsBean();
        bean.setUserid("4");
        bean.setUsername("黄文龙");
        bean.setImage(R.mipmap.head_img1);
        bean.setDepartmentName("公安厅厅长");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("5");
        bean.setUsername("崔南");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("公安厅厅长");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("6");
        bean.setUsername("权宏");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("职员");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        bean = new ContactsBean();
        bean.setUserid("7");
        bean.setUsername("钱和");
        bean.setImage(R.mipmap.head_img2);
        bean.setDepartmentName("职员");
        bean.setEmail("cuinan@gat.jl");
        bean.setTelephone("18186867051");
        bean.setTitle("职员");
        list.add(bean);
        if (list != null) {
            tv_service_title.setText("共" + list.size() + "人");
            adapter.setNewData(list);
        }
    }

    private void initRecyclerviewTitle() {

        View headView = getLayoutInflater().inflate(R.layout.item_grid_contacts_bottom, (ViewGroup) mRecyclerView.getParent(), false);
        tv_service_title = headView.findViewById(R.id.tv_bottom_title);
        adapter.addFooterView(headView);
    }

    private void initAdapter() {
        adapter = new ContactsAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        initRecyclerviewTitle();
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ContactsBean bean = (ContactsBean) adapter.getData().get(position);
                if (bean != null) {
                    MyApplication.getInstance().MailBoxContacts = bean.getEmail();
                    finish();
                }
            }
        });

    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {

        if (v == tv_department1) {
            tv_department1.setBackgroundColor(getActivity().getResources().getColor(R.color.white50));
            tv_department2.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            tv_department3.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            tv_department4.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            changeData1();
        } else if (v == tv_department2) {
            tv_department2.setBackgroundColor(getActivity().getResources().getColor(R.color.white50));
            tv_department1.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            tv_department3.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            tv_department4.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            changeData2();
        } else if (v == tv_department3) {

            tv_department3.setBackgroundColor(getActivity().getResources().getColor(R.color.white50));
            tv_department2.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            tv_department1.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            tv_department4.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            changeData3();
        } else if (v == tv_department4) {

            tv_department4.setBackgroundColor(getActivity().getResources().getColor(R.color.white50));
            tv_department2.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            tv_department3.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            tv_department1.setBackgroundColor(getActivity().getResources().getColor(R.color.transparent));
            changeData4();
        }
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }


}
