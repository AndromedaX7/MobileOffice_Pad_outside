package com.mobilepolice.office.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.EmailBean;

public class EmailAdapter extends BaseQuickAdapter<EmailBean, BaseViewHolder> {

    public EmailAdapter() {
        super(R.layout.item_collect_bgaswipe, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmailBean item) {
        helper.setText(R.id.tv_item_collect_name, item.getName().replace("&lt;", "<").replace("&gt;", ">"));
        helper.setText(R.id.tv_item_collect_title, item.getTitle());
        helper.setText(R.id.tv_item_collect_datetime, item.getDate());
//        if(!item.isIsSeen()){
//            helper.getView(R.id.noSeen).setVisibility(View.VISIBLE);
//        }
    }
}


