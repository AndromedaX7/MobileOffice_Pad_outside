package com.mobilepolice.office.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.HomeGoodBean;
import com.mobilepolice.office.bean.PendingWorkBean;
import com.mobilepolice.office.config.Config;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import static org.litepal.LitePalApplication.getContext;


/**
 * 待审批
 */
public class PendingWorkAdapter extends BaseQuickAdapter<PendingWorkBean, BaseViewHolder> {
    public PendingWorkAdapter() {
        super(R.layout.item_grid_pending_work, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PendingWorkBean item) {

        ImageView imageView = helper.getView(R.id.item_img);
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
        imageView.setImageResource(item.getImage());
        helper.setText(R.id.item_name, item.getTitle());
        helper.setTag(R.id.item_name, item.getId());
        helper.setText(R.id.item_time, item.getCreateDate());

//        String dsp = "待审批";
//        if (item.isFlag()) {
//            dsp = "待审批";
//        } else {
//            dsp = "已审批";
//        }
//        helper.setText(R.id.tv_dsp, dsp);
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


}
