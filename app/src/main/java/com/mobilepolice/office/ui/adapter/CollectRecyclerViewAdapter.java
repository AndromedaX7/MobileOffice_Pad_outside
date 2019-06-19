package com.mobilepolice.office.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.CollectEmailBean;
import com.mobilepolice.office.bean.NewsListBean;
import com.mobilepolice.office.bean.NormalModel;

import org.xutils.image.ImageOptions;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;

import static org.litepal.LitePalApplication.getContext;

//
///**
// * 收件箱
// */
//public class CollectRecyclerViewAdapter extends BGARecyclerViewAdapter<NormalModel> {
//    /**
//     * 当前处于打开状态的item
//     */
//    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();
//
//    public CollectRecyclerViewAdapter(RecyclerView recyclerView) {
//
//        super(recyclerView, R.layout.item_collect_bgaswipe);
//        Log.e("CollectRecyclerV: ", "0");
//    }
//
////    @Override
////    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper, int viewType) {
////        BGASwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_bgaswipe_root);
////        swipeItemLayout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
////            @Override
////            public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
////                closeOpenedSwipeItemLayoutWithAnim();
////                mOpenedSil.add(swipeItemLayout);
////            }
////
////            @Override
////            public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {
////                mOpenedSil.remove(swipeItemLayout);
////            }
////
////            @Override
////            public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
////                closeOpenedSwipeItemLayoutWithAnim();
////            }
////        });
////        viewHolderHelper.setItemChildClickListener(R.id.tv_item_bgaswipe_delete);
////        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_bgaswipe_delete);
////        viewHolderHelper.setItemChildClickListener(R.id.tv_item_bgaswipe_more);
////        viewHolderHelper.setItemChildLongClickListener(R.id.tv_item_bgaswipe_more);
////    }
//
//    @Override
//    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, NormalModel model) {
//        Log.e("fillData", model.mDate);
//        viewHolderHelper.setText(R.id.tv_item_collect_name, model.mEmail).setText(R.id.tv_item_collect_datetime, model.mDate).setText(R.id.tv_item_collect_title, model.mTitle).setText(R.id.tv_item_collect_detail, model.mDetail);
//        BGASwipeItemLayout swipeItemLayout = viewHolderHelper.getView(R.id.sil_item_bgaswipe_root);
////        if (position % 3 == 0) {
////            swipeItemLayout.setSwipeAble(false);
////        } else {
//        swipeItemLayout.setSwipeAble(true);
//        //       }
//    }
//
//    public void closeOpenedSwipeItemLayoutWithAnim() {
//        for (BGASwipeItemLayout sil : mOpenedSil) {
//            sil.closeWithAnim();
//        }
//        mOpenedSil.clear();
//    }
//}

public class CollectRecyclerViewAdapter extends BaseQuickAdapter<CollectEmailBean.ObjBean, BaseViewHolder> {

    public CollectRecyclerViewAdapter() {
        super(R.layout.item_collect_bgaswipe, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectEmailBean.ObjBean item) {
        helper.setText(R.id.tv_item_collect_name, item.getSender().replace("&lt;","<").replace("&gt;",">"));
        helper.setText(R.id.tv_item_collect_title, item.getTitle());
        helper.setText(R.id.tv_item_collect_datetime, item.getSendDate().substring(0,item.getSendDate().indexOf(" ")));
//        if(!item.isIsSeen()){
//            helper.getView(R.id.noSeen).setVisibility(View.VISIBLE);
//        }
    }
}