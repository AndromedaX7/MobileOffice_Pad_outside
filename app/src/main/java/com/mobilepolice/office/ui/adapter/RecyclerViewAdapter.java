package com.mobilepolice.office.ui.adapter;

import android.support.v7.widget.RecyclerView;

import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.NormalModel;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 16:31
 * 描述:
 */
public class RecyclerViewAdapter extends BGARecyclerViewAdapter<NormalModel> {
    /**
     * 当前处于打开状态的item
     */
    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();

    public RecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_bgaswipe);
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
        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_bgaswipe_more);
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, NormalModel model) {
        viewHolderHelper.setText(R.id.tv_item_bgaswipe_title, model.mTitle).setText(R.id.tv_item_bgaswipe_detail, model.mDetail);

        BGASwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_bgaswipe_root);
//        if (position % 3 == 0) {
//            swipeItemLayout.setSwipeAble(false);
//        } else {
            swipeItemLayout.setSwipeAble(true);
 //       }
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (BGASwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }
}