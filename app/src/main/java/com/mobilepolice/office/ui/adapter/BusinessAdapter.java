package com.mobilepolice.office.ui.adapter;

import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.Business;

import java.util.List;

public class BusinessAdapter extends BaseQuickAdapter<Business, BaseViewHolder> {

    public BusinessAdapter() {
        super(R.layout.item_business, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Business item) {
        helper.setText(R.id.name, item.getName());
        helper.setText(R.id.time, item.getTime());
        ConstraintLayout leaveCL = helper.getView(R.id.leave);
        ConstraintLayout accountCL = helper.getView(R.id.account);
        switch (item.getType()) {
            case "0":
                helper.setText(R.id.type,"外出请假申请");
                leaveCL.setVisibility(View.VISIBLE);
                accountCL.setVisibility(View.GONE);
                helper.setText(R.id.duration,item.getLeave().getDuration());
                helper.setText(R.id.reason,item.getLeave().getReason());
                helper.setText(R.id.leave_time,item.getLeave().getLeaveTime());
                break;
            case "1":
                helper.setText(R.id.type,"发起报销申请");
                leaveCL.setVisibility(View.GONE);
                accountCL.setVisibility(View.VISIBLE);
                helper.setText(R.id.money,item.getAccount().getMoney());
                helper.setText(R.id.reason2,item.getAccount().getReason());
                break;

        }

    }

    public void setData(List<Business> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

}
