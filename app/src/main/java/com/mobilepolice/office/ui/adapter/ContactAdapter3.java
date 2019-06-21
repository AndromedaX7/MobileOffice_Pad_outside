package com.mobilepolice.office.ui.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.ContactsData;

import java.util.ArrayList;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class ContactAdapter3 extends BaseAdapter {
//    public ContactAdapter3() {
//        super(R.layout.item_contacts_type2, null);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, ContactsData item) {

//        ImageView imageView = helper.getView(R.id.imageView);
//        ImageOptions imageOptions = new ImageOptions.Builder()
//                // .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
//                .setImageScaleType(ImageView.ScaleType.FIT_XY)
//                .setFailureDrawableId(R.mipmap.not_photo)
//                .setLoadingDrawableId(R.mipmap.not_photo)
//                .build();
//        if (!TextUtils.isEmpty(item.getImg())) {
//            x.image().bind(imageView, item.getImg(), imageOptions);
//        }
//        Bitmap bitmap = BitmapFactory.decodeResource(imageView.getResources(), R.mipmap.icon_user_faqi);

//        imageView.setImageBitmap(BitmapUtils.ovalRectangleBitmap(bitmap, bitmap.getWidth() / 2));
//        bitmap.recycle();
//        helper.setText(R.id.tv_department, item.getName());
//    }
//}


    private ArrayList<ContactsData> departmentAlls = new ArrayList<>();

    @Override
    public int getCount() {
        return departmentAlls.size();
    }

    @Override
    public ContactsData getItem(int position) {
        return departmentAlls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vh vh;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_contacts_type2, null);
            vh = new Vh();
            vh.tv_department = convertView.findViewById(R.id.tv_department);
            convertView.setTag(vh);
        } else {
            vh = (Vh) convertView.getTag();
        }
        vh.tv_department.setText(getItem(position).getName());
        return convertView;
    }

    private class Vh {
        private TextView tv_department;
    }

    public void setData(ArrayList<ContactsData> departmentAlls) {
        this.departmentAlls = departmentAlls;
        notifyDataSetChanged();
    }
}