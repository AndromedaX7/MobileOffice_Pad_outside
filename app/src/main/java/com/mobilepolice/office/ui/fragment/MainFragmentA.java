package com.mobilepolice.office.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.LoopImageInfoBean;
import com.mobilepolice.office.bean.LoopImageNewsBean;
import com.mobilepolice.office.bean.NewsListBean;
import com.mobilepolice.office.bean.NoticeListBean;
import com.mobilepolice.office.http.HttpConnectInterface;
import com.mobilepolice.office.http.HttpTools;
import com.mobilepolice.office.ui.activity.MyFavoriteActivity;
import com.mobilepolice.office.ui.activity.NewsDetailedActivity;
import com.mobilepolice.office.ui.adapter.NewsAdapter;
import com.mobilepolice.office.ui.adapter.NoticeAdapter;
import com.mobilepolice.office.ui.change.ViewsAdapter;
import com.mobilepolice.office.widget.RecycleViewDivider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目炫酷效果示例
 */
public class MainFragmentA extends MyLazyFragment {


    @BindView(R.id.tb_title)
    TitleBar tb_title;

    /*2019-03-01-start*/
    private ArrayList<View> views = new ArrayList<>();
    @BindView(R.id.mPager) /*新闻通知的轮播图*/
            ViewPager viewPager;
    /*2019-03-01-end*/

    //
    @BindView(R.id.new_btn)
    LinearLayout tv_news_title;
    //    @BindView(R.id.notice_btn)
//    View line_wating;
    @BindView(R.id.notice_btn)
    LinearLayout tv_notice_title;
    //    @BindView(R.id.line_finished)
//    View line_finished;
    @BindView(R.id.dynamic_btn)
    LinearLayout dynamic_btn;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.my_favorite)
    LinearLayout my_favorite;
    private ArrayList<LoopImageNewsBean> dataLoopImage = new ArrayList<>();
    int newsPage, noticePage, dynamicPage;
    private int toggleFlag = 0; // 新闻动态 0 //  通知公告 1  // 地市动态  2//////
    private NewsAdapter mAdapter;
    private NewsAdapter dynamicAdapter;
    NoticeAdapter adapter;
    List<LoopImageInfoBean> loopImageInfoBeans = new ArrayList<>();
    ArrayList<NewsListBean> list = new ArrayList<>();
    ArrayList<NewsListBean> listDym = new ArrayList<NewsListBean>();
    ArrayList<NoticeListBean> listnotice = new ArrayList<NoticeListBean>();
    boolean flag = false;
    private Handler mHandler;

    public static MainFragmentA newInstance() {
        return new MainFragmentA();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_a;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_title;
    }


    @Override
    protected void initView() {
        mHandler = new Handler();
        toggleFlag = 0;
        initAdapters();
        initRecyclerViewSettings();
        initNewsAdapterOnItemClick();
        initNoticeAdapterItemClick();
        tv_news_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    mAdapter.setData(list);
                    mRecyclerView.setAdapter(mAdapter);
                } else
                    loadNews();
                flag = false;
            }
        });
        tv_notice_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listnotice.size() > 0) {
                    adapter.setData(listnotice);
                    mRecyclerView.setAdapter(adapter);
                } else
                    loadNotice();
                flag = true;
            }
        });
        dynamic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dynamic();
                flag = false;
            }
        });
        /*2019-03-01-start*/
        viewPager.setPageMargin(30);

        loadLoopImage();

       /* viewPager.setAdapter(new ViewsAdapter(views));
        viewPager.setCurrentItem(1);*/
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                current = i;
                /*根据当前banner图的索引，控制bannerPagination的改变*/
//                viewPager.postDelayed(()->viewPager.setCurrentItem((i+1)%dataLoopImage.size()),2000);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        /*2019-03-01-end*/
        loadNewsByToggleFlag(toggleFlag);
    }

    private void initAdapters() {
        mAdapter = new NewsAdapter(0);
        dynamicAdapter = new NewsAdapter(1);
        adapter = new NoticeAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        dynamicAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    private void initRecyclerViewSettings() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRecyclerView.setFocusable(false);

    }

    private void loadNewsByToggleFlag(int toggleFlag) {
        switch (toggleFlag) {
            case 0:
                loadNews();
                break;
            case 1:
                loadNotice();
                break;
            case 2:
                break;
        }

    }

    private void loadNews() {
        HttpTools.build(HttpConnectInterface.class)
                .findTpxwInfo(String.valueOf(newsPage + 1), String.valueOf(newsPage + 5))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(this::newsResult, this::err, this::onComplete)
                .isDisposed();
        newsPage += 5;
    }

    private void loadNotice() {
        HttpTools.build(HttpConnectInterface.class)
                .findTzggInfo(String.valueOf(noticePage + 1), String.valueOf(noticePage + 5), "110754")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(this::noticeResult, this::err, this::onComplete)
                .isDisposed();
        noticePage += 5;
    }

    private void noticeResult(ArrayList<NoticeListBean> o) {
        if (o != null && o.size() > 0) {
            listnotice.addAll(o);
            adapter.setData(listnotice);
            mRecyclerView.setAdapter(adapter);
        }
    }

    private void onComplete() {

    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void newsResult(ArrayList<NewsListBean> o) {
        if (o != null && o.size() > 0) {
            list.addAll(o);
            mAdapter.setData(list);
            mRecyclerView.setAdapter(mAdapter);
        }
    }


    private boolean show;

    @Override
    public void onResume() {
        super.onResume();
        show = true;
        new Thread(() -> {
            while (show) {
                try {
                    Thread.sleep(3000);
                    if (mHandler != null)
                        mHandler.post(() -> {
                            if (dataLoopImage != null && dataLoopImage.size() > 0) {
                                if (viewPager != null) {
                                    viewPager.setCurrentItem(current % dataLoopImage.size());
                                    current++;
                                }
                            }

                        });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onPause() {
        super.onPause();
        show = false;
    }

    private void loadLoopImage() {
        HttpConnectInterface connectInterface = HttpTools.build(HttpConnectInterface.class);
        connectInterface.findTpxwImageInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::imageLoopNews, this::err, this::onComplete)
                .isDisposed();
    }

    private void imageLoopNews(List<LoopImageNewsBean> o) throws NoSuchFieldException, IllegalAccessException {
        dataLoopImage = new ArrayList<>(o);
        for (int i = 0; i < 5; i++) {
            View view = View.inflate(getContext(), R.layout.main_a_banner, null);
            Class<R.drawable> drawableClass = R.drawable.class;
            Field declaredField = drawableClass.getDeclaredField("loop_" + (i + 1));
            int drawableId = (int) declaredField.get(null);
            ImageView img = view.findViewById(R.id.banner_image);
            Glide.with(img).load(drawableId).into(img);
            String id = o.get(i).getId();
            Log.e("imageLoopNews: ", id);
            view.setTag(o.get(i));
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    LoopImageNewsBean bean = (LoopImageNewsBean) v.getTag();
//
//
//                    Intent intent = new Intent(getActivity(), NewsDetailedActivity.class);
//                    intent.putExtra("title", "新闻中心");
//                    intent.putExtra("flag", "NEWS");
//                    intent.putExtra("contentId", ((LoopImageNewsBean) v.getTag()).getId());
//                    intent.putExtra("titleIn", bean.getTitle());
//                    intent.putExtra("img", bean.getImg());
//                    startActivity(intent);
//                }
//            });
            views.add(view);
        }
        viewPager.setAdapter(new ViewsAdapter(views));
//        current = o.size() / 2;
//        viewPager.setCurrentItem(o.size() / 2);


//        viewPager.postDelayed(()->viewPager.setCurrentItem((current+1)%o.size()),1000);


    }


    private int current;

    @Override
    protected void initData() {
    }


    private void dynamic() {
    }


    private void initNewsAdapterOnItemClick() {

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailedActivity.class);
                intent.putExtra("title", "新闻中心");
                intent.putExtra("flag", "NEWS");
                intent.putExtra("contentId", mAdapter.getData().get(position).getId());
                intent.putExtra("titleIn", mAdapter.getData().get(position).getTitle());
                intent.putExtra("img", mAdapter.getData().get(position).getImg());
                intent.putExtra("time", mAdapter.getData().get(position).getTime());
                startActivity(intent);
            }
        });

    }

    private void initNoticeAdapterItemClick() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailedActivity.class);
                intent.putExtra("title", "通知公告");
                intent.putExtra("flag", "NOTICE");
                intent.putExtra("url", "file:///android_asset/news1.html");
                intent.putExtra("contentId", MainFragmentA.this.adapter.getData().get(position).getId());
                intent.putExtra("titleIn", MainFragmentA.this.adapter.getData().get(position).getTitle());
                intent.putExtra("time", MainFragmentA.this.adapter.getData().get(position).getTime());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
//        return !super.isStatusBarEnabled();
        return false;
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }


    /*设置商品列表listView的高度*/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @OnClick(R.id.my_favorite)
    void myFavorite() {
        startActivity(MyFavoriteActivity.class);
    }
}