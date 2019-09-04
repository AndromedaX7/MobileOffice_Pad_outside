package com.mobilepolice.office.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.Case;
import com.mobilepolice.office.mvp.copy.CopyContract;

import java.util.List;

public class CaseAdapter extends BaseQuickAdapter<Case, BaseViewHolder> {



    public CaseAdapter(){
        super(R.layout.item_case,null);
    }

    @Override
    protected void convert(BaseViewHolder helper, Case item) {
        helper.setText(R.id.name,"【"+item.getName()+"】");
        helper.setText(R.id.polices,item.getPolices());
        helper.setText(R.id.type,item.getType());
        helper.setText(R.id.time,item.getTime());
        helper.setText(R.id.state,item.getState());
        View point = helper.getView(R.id.point);
        if (TextUtils.equals(item.getState(),"审批")){
            point.setVisibility(View.VISIBLE);
        }else {
            point.setVisibility(View.INVISIBLE);
        }
    }

    public void setData(List<Case> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
