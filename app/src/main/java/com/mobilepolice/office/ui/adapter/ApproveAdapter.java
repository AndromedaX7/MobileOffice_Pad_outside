package com.mobilepolice.office.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.ApproveList;
import com.mobilepolice.office.bean.PendingApprove;
import com.mobilepolice.office.ui.fragment.MainFragmentB;
import com.mobilepolice.office.utils.DateUtil;

import org.xutils.image.ImageOptions;

import java.util.ArrayList;
import java.util.List;

public class ApproveAdapter extends  BaseQuickAdapter<PendingApprove.ObjBean, BaseViewHolder> {
    private Context context;
    private int surePosition = 0;
    private boolean changePosition = false;
    private String isApproval = "0";//未审批 已审批
    public ApproveAdapter(Context context) {
        super(R.layout.item_grid_pending_work, null);
        this.context = context;
    }

    @Override
    public int getItemCount() {

        return MainFragmentB.getListSize(mData,isApproval);

    }

    @Override
    protected void convert(BaseViewHolder helper, PendingApprove.ObjBean item) {

//        if (!TextUtils.equals(item.getIsApproval(),isApproval)){
//
            if (!changePosition) {
                //正常position
                surePosition = helper.getLayoutPosition();
            }else {
                if (surePosition<MainFragmentB.getListSize(mData,isApproval)){
                    item = mData.get(surePosition++);
                }
            }
            while (!TextUtils.equals(isApproval,item.getIsApproval())) {
                item = mData.get(surePosition++);
                changePosition = true;
            }
//        }

        ImageView imageView = helper.getView(R.id.item_img);
        imageView.setImageResource(R.mipmap.gongwen_img);
//        imageView.setImageResource(item.getSrc());
        ImageOptions imageOptions = new ImageOptions.Builder()
                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setFailureDrawableId(R.mipmap.not_photo)
                .setLoadingDrawableId(R.mipmap.not_photo)
                .build();
//        if (!TextUtils.isEmpty(item.getApplyOffWordFile())) {
//            x.image().bind(imageView, item.getApplyOffWordFile(), imageOptions);
//        }
//        String[] split = item.getApplyOffWordFile().split(",");
//        Glide.with(context).load(split[0]).into(imageView);
        helper.setText(R.id.item_name, item.getTitle());
        helper.setTag(R.id.item_name, item.getId());
//        helper.setText(R.id.item_time, DateUtil.format("MM-dd",DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",item.getCreateDate())));
        helper.setText(R.id.item_time, item.getCreateDate());

        FrameLayout ll_header = helper.getView(R.id.ll_header);
        if (item.getUrgentLevel().equals("1")) {
            helper.setText(R.id.item_grade, "不紧急不重要");
            helper.getView(R.id.item_grade).setBackgroundResource(R.drawable.grade1);
        } else if (item.getUrgentLevel().equals("2")) {
            helper.setText(R.id.item_grade, "重要不紧急");
            helper.getView(R.id.item_grade).setBackgroundResource(R.drawable.grade2);
        } else if (item.getUrgentLevel().equals("3")) {
            helper.setText(R.id.item_grade, "重要紧急");
            helper.getView(R.id.item_grade).setBackgroundResource(R.drawable.grade3);
        } else if (item.getUrgentLevel().equals("4")) {
            helper.setText(R.id.item_grade, "紧急不重要");
            helper.getView(R.id.item_grade).setBackgroundResource(R.drawable.grade4);
        }
//        SimpleRatingBar simpleRatingBar = helper.getView(R.id.ratingbar);
//        String Level = "";
//        if (item.getUrgentLevel().equals("1")) {
//
//            simpleRatingBar.setRating(1f);
//
//            Level = "一级";
//        } else if (item.getUrgentLevel().equals("2")) {
//            simpleRatingBar.setRating(3f);
//            Level = "二级";
//        } else if (item.getUrgentLevel().equals("3")) {
//            Level = "三级";
//            simpleRatingBar.setRating(5f);
//        }
//        helper.setText(R.id.tv_store_addcomment, "紧急程度:"+Level);
//        helper.setText(R.id.tv_person, "发起人：" + item.getApplyPersonName());
//        helper.setText(R.id.tv_datetime, "时间：" + item.getCreateDate());
        //  helper.setText(R.id.tv_dsp, item.getOverFlag());

//        TextView item_content = helper.getView(R.id.item_content);
//        item_content.setVisibility(View.VISIBLE);
//        helper.setText(R.id.item_content, item.getName());
    }

    public void setData(List<PendingApprove.ObjBean> data,String isApproval) {
        mData.clear();
        mData.addAll(data);
        surePosition = 0;
        this.isApproval = isApproval;
        notifyDataSetChanged();
    }

}
