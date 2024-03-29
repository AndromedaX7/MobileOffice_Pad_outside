package com.mobilepolice.office.ui.adapter;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.NoticeListBean;
import com.mobilepolice.office.utils.DateUtil;

import java.util.ArrayList;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class NoticeAdapter extends BaseQuickAdapter<NoticeListBean, BaseViewHolder> {
    public NoticeAdapter() {
        super(R.layout.item_grid_notice, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeListBean item) {
        helper.setText(R.id.tv_title, item.getTitle().replaceAll("<br>","\n"));
        helper.setText(R.id.tv_content, item.getCon().replaceAll("<br>","\n"));
//        helper.getView(R.id.tv_content).setVisibility(INVISIBLE);
        long ctime = Long.parseLong(item.getTime());
        helper.setText(R.id.tv_datetime_title, DateUtil.format("yyyy-MM\ndd", ctime));
        helper.getView(R.id.tv_count).setVisibility(View.GONE);
        helper.setText(R.id.tv_count, item.getCount() + "");
    }

    public void setData(ArrayList<NoticeListBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }
}
