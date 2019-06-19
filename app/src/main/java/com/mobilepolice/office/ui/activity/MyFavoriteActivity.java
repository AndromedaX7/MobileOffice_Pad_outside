package com.mobilepolice.office.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobilepolice.office.R;
import com.mobilepolice.office.bean.FavoriteBean;
import com.mobilepolice.office.utils.DateUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;

public class MyFavoriteActivity extends AppCompatActivity {

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mList)
    RecyclerView mList;

    private ArrayList<FavoriteBean> beans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favrite);
        ButterKnife.bind(this);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener((v) -> finish());
        Cursor query = getContentResolver().query(Uri.parse("content://com.access.favorite.info"), null, null, null, null);
        if (query != null) {
            while (query.moveToNext()) {
                //   type integer default 0,
                FavoriteBean bean = new FavoriteBean();
                bean.setCid(query.getString(query.getColumnIndex("cid")));
                bean.setTime(query.getString(query.getColumnIndex("time")));
                bean.setImg(query.getString(query.getColumnIndex("img")));
                bean.setTitle(query.getString(query.getColumnIndex("title")));
                bean.setFlag(query.getInt(query.getColumnIndex("type")) == 0 ? "NEWS" : "NOTICE");
                beans.add(bean);
            }
            Log.e("onCreate: ", "Count->" + query.getCount());
            query.close();
            adapter.setData(beans);
        }
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.setAdapter(adapter);

    }

    private MyFavoriteAdapter adapter = new MyFavoriteAdapter();

    class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.VH> implements View.OnClickListener {


        private ArrayList<FavoriteBean> beans = new ArrayList<>();

        public void setData(ArrayList<FavoriteBean> beans) {
            this.beans = new ArrayList<>(beans);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new VH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_favorite, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH vh, int i) {
            vh.itemView.setTag(i);
            vh.itemView.setOnClickListener(this);
            FavoriteBean bean = beans.get(i);
            vh.content.setText(bean.getTitle().replaceAll("<br>", "\n"));
            vh.content.setVisibility(INVISIBLE);
            vh.title.setText(bean.getTitle().replaceAll("<br>", "\n"));
            vh.time.setText(bean.getTime());
            if (beans.get(i).getFlag().equals("NEWS")) {
                vh.img.setVisibility(View.VISIBLE);
                vh.imgTitle.setVisibility(View.GONE);
                vh.time.setText(bean.getTime());
                Glide.with(vh.img).load(bean.getImg().replaceAll("10.106.18.75:8081", "192.168.20.228:7124")).into(vh.img);
            } else {
                vh.img.setVisibility(View.GONE);
                vh.imgTitle.setVisibility(View.VISIBLE);
                vh.imgTitle.setText(DateUtil.format("yyyy-MM\ndd", Long.parseLong(bean.getTime())));
                vh.time.setText(DateUtil.format("yyyy-MM-dd", Long.parseLong(bean.getTime())));
            }
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            FavoriteBean bean = beans.get(position);
            Intent intent = new Intent(v.getContext(), NewsDetailedActivity.class);
            intent.putExtra("flag", bean.getFlag());
            intent.putExtra("contentId", bean.getCid());
            intent.putExtra("titleIn", bean.getTitle());
            intent.putExtra("img", bean.getImg());
            intent.putExtra("time", bean.getTitle());
            startActivity(intent);
        }

        class VH extends RecyclerView.ViewHolder {
            @BindView(R.id.img)
            ImageView img;
            @BindView(R.id.imgTitle)
            TextView imgTitle;
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.content)
            TextView content;
            @BindView(R.id.time)
            TextView time;

            public VH(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
