package com.mobilepolice.office.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.DraftsEmailList;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;

public class DraftsEmailListAdapter extends BGARecyclerViewAdapter<DraftsEmailList.ObjBean> {
    /**
     * 当前处于打开状态的item
     */
    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();

    public DraftsEmailListAdapter(RecyclerView recyclerView) {

        super(recyclerView, R.layout.item_drafts_email);
        Log.e("CollectRecyclerV: ", "0");
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
        BGASwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_bgaswipe_root);
        swipeItemLayout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
            @Override
            public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });
        viewHolderHelper.setItemChildClickListener(R.id.tv_item_bgaswipe_delete);
        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_bgaswipe_delete);
        viewHolderHelper.setItemChildClickListener(R.id.tv_item_bgaswipe_more);
//        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_bgaswipe_more);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, DraftsEmailList.ObjBean model) {
        viewHolderHelper.setText(R.id.tv_item_collect_name, model.getReceiver()).setText(R.id.tv_item_collect_datetime, model.getCreateDate().substring(0,model.getCreateDate().indexOf(" "))).setText(R.id.tv_item_collect_title, model.getTitle());
        BGASwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_bgaswipe_root);
        swipeItemLayout.setSwipeAble(true);
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (BGASwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }
}

//public class DraftsEmailListAdapter  extends BaseQuickAdapter<DraftsEmailList.ObjBean, BaseViewHolder> {
//    public DraftsEmailListAdapter() {
//        super(R.layout.item_collect_bgaswipe, null);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, DraftsEmailList.ObjBean item) {
//        helper.setText(R.id.tv_item_collect_name, item.getReceiver());
//        helper.setText(R.id.tv_item_collect_title, item.getTitle());
//        helper.setText(R.id.tv_item_collect_datetime, item.getCreateDate().substring(0,item.getCreateDate().indexOf(" ")));
////        if(!item.isIsSeen()){
////            helper.getView(R.id.noSeen).setVisibility(View.VISIBLE);
////        }
//    }
//}
