package com.mobilepolice.office.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.Business;

import java.util.List;

public class BusinessAdapter extends BaseQuickAdapter<Business, BaseViewHolder> {

    public BusinessAdapter(List<Business> mData) {
        super(R.layout.item_case, mData);
    }

    @Override
    protected void convert(BaseViewHolder helper, Business item) {

    }

}
