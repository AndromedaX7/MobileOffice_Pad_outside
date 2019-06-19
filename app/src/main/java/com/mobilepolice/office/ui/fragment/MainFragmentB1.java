package com.mobilepolice.office.ui.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.bar.TitleBar;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.ContactsBean;
import com.mobilepolice.office.bean.HomeGoodBean;
import com.mobilepolice.office.bean.PendingWorkBean;
import com.mobilepolice.office.ui.activity.DocumentMainActivity;
import com.mobilepolice.office.ui.activity.EmailLoginActivity;
import com.mobilepolice.office.ui.activity.HandwrittenSignatureActivity;
import com.mobilepolice.office.ui.activity.MailBoxMainActivity;
import com.mobilepolice.office.ui.activity.NewsDetailedActivity;
import com.mobilepolice.office.ui.activity.NewsListActivity;
import com.mobilepolice.office.ui.activity.NotificationNoticeActivity;
import com.mobilepolice.office.ui.activity.ScrawlImageviewActivity;
import com.mobilepolice.office.ui.adapter.ApprovedPendingWorkAdapter;
import com.mobilepolice.office.ui.adapter.NoticeAdapter;
import com.mobilepolice.office.ui.adapter.PendingWorkAdapter;
import com.mobilepolice.office.ui.adapter.WorkMainAdapter;
import com.mobilepolice.office.ui.change.ViewsAdapter;
import com.mobilepolice.office.widget.RecycleViewDivider;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hzw.doodle.DoodleActivity;
import cn.hzw.doodle.DoodleParams;
import cn.hzw.doodle.DoodleView;

import static android.app.Activity.RESULT_OK;


/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public class MainFragmentB1 extends MyLazyFragment {

//    @BindView(R.id.tv_pendingapproval_title)
//    TextView tv_pendingapproval_title;
//    @BindView(R.id.tv_approved_title)
//    TextView tv_approved_title;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
//    @BindView(R.id.line_wating)
//    View line_wating;
//    @BindView(R.id.line_finished)
//    View line_finished;

    /*2019-03-02-start*/
//    private ArrayList<View> views = new ArrayList<>();
//    @BindView(R.id.waiting_job) /*待办工作页面轮播图*/
//            ViewPager viewPager;
    /*2019-03-02-end*/

    PendingWorkAdapter pendingWorkAdapter;
    List<PendingWorkBean> list0 = new ArrayList<PendingWorkBean>();
    List<PendingWorkBean> list1 = new ArrayList<PendingWorkBean>();
    List<PendingWorkBean> list2 = new ArrayList<PendingWorkBean>();

    List<PendingWorkBean> listFinished = new ArrayList<PendingWorkBean>();
    PendingWorkBean bean;
    /*当前显示的tab索引*/
    int whichTab = 1;
    LinearLayout mask;
    public static MainFragmentB newInstance() {
        return new MainFragmentB();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_b;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {
//        tv_pendingapproval_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_pendingapproval_title.setTextSize(30);
//                tv_pendingapproval_title.setBackgroundColor(getResources().getColor(R.color.transparent));
//                line_wating.setVisibility(View.VISIBLE);
//                tv_approved_title.setTextSize(25);
//                tv_approved_title.setBackgroundColor(getResources().getColor(R.color.transparent));
//                line_finished.setVisibility(View.GONE);
//                initPendingWork();
//            }
//        });
//        tv_approved_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                tv_approved_title.setTextSize(30);
//                tv_approved_title.setBackgroundColor(getResources().getColor(R.color.transparent));
//                line_wating.setVisibility(View.GONE);
//                tv_pendingapproval_title.setTextSize(25);
//                tv_pendingapproval_title.setBackgroundColor(getResources().getColor(R.color.transparent));
//                line_finished.setVisibility(View.VISIBLE);
//                initApproved();
//            }
//        });
        /*2019-03-02-start*/
//        viewPager.setPageMargin(0);
//        for(int i=1;i<4;i++){
//            View view = View.inflate(getContext(), R.layout.main_b_tab, null);
//            ImageView img = view.findViewById(R.id.waiting_tab_back);
//            TextView title = view.findViewById(R.id.tab_name);
//            TextView count = view.findViewById(R.id.tab_count);
//            Bitmap bitmap;
//            switch(i){
//                case 1:
//                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.waiting_btn1);
//                    img.setImageBitmap(bitmap);
//                    title.setText("业务审批");
//                    count.setText("("+6+")");
//                    break;
//                case 2:
//                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.waiting_btn2);
//                    img.setImageBitmap(bitmap);
//                    title.setText("公文审批");
//                    count.setText("("+8+")");
//                    break;
//                case 3:
//                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.waiting_btn3);
//                    img.setImageBitmap(bitmap);
//                    title.setText("案件审批");
//                    count.setText("("+7+")");
//                    break;
//            }
//            view.setTag(i);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int index = (int) v.getTag() - 1;
//                    Log.e("click", "" + index);
//                    viewPager.setCurrentItem(index);
//                }
//            });
//            views.add(view);
//        }
//        viewPager.setAdapter(new ViewsAdapter(views));
//        viewPager.setCurrentItem(1);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                /*根据当前banner图的索引，控制bannerPagination的改变*/
//                switch (i){
//                    case 0:
//                        initPendingWork();
//                        break;
//                    case 1:
////                        initPendingWork();
//                        initApproved();
//                        break;
//                    case 2:
//                        initPendingWork();
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });

        /*2019-03-02-end*/
        /*2019-03-04-start*/
        /*业务审批*/
        LinearLayout tab1 = findViewById(R.id.tab1);
        /*公文审批*/
        LinearLayout tab2 = findViewById(R.id.tab2);
        /*案件审批*/
        LinearLayout tab3 = findViewById(R.id.tab3);
        /*已审批*/
        LinearLayout finishBtn = findViewById(R.id.checkedFinish);
        /*未审批*/
        LinearLayout check = findViewById(R.id.check);
        /*2019-03-05-start*/
        mask = findViewById(R.id.mask);
        /*2019-03-05-end*/

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichTab = 0;
                check.setTag(1);
                finishBtn.setTag(0);
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#ffffff"));
                initPendingWork0();
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichTab = 1;
                check.setTag(1);
                finishBtn.setTag(0);
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#ffffff"));
                initPendingWork1();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichTab = 2;
                check.setTag(1);
                finishBtn.setTag(0);
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#ffffff"));
                initPendingWork2();
            }
        });
        /*已审批*/
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = Integer.parseInt(v.getTag().toString());
                if(state == 1){
                    return;
                }
                v.setTag(1);
                findViewById(R.id.check).setTag(0);
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#ffffff"));
                initApproved();
            }
        });
        /*未审批*/
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = Integer.parseInt(v.getTag().toString());
                if(state == 1){
                    return;
                }
                v.setTag(1);
                findViewById(R.id.checkedFinish).setTag(0);
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#ffffff"));
                switch (whichTab){
                    case 0:
                        initPendingWork0();
                        break;
                    case 1:
                        initPendingWork1();
                        break;
                    case 2:
                        initPendingWork2();
                        break;
                }
            }
        });

        /*隐藏遮罩层*/
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        });

        /*常见待审批假数据*/
        initItemData0(); /*业务审批*/
        initItemData1();/*公文审批*/
        initItemData2(); /*案件审批*/
        /*2019-03-04-end*/
    }

    @Override
    protected void initData() {
        initPendingWork1();
    }

    @Override
    public void onResume() {
        super.onResume();
//        list0.remove(bean);
        list1.remove(bean);
//        list2.remove(bean);

    }
    private void initItemData0(){
        PendingWorkBean bean = new PendingWorkBean();
        bean.setId("0");
        bean.setTitle("出入境加急办理护照审批");
        bean.setCreateDate("02-02");
        bean.setFlag(true);
        bean.setUrgentLevel("1");
        bean.setImage(R.mipmap.gongwen_img);
        list0.add(bean);
        bean = new PendingWorkBean();
        bean.setId("1");
        bean.setTitle("出入境签证申请审批");
        bean.setCreateDate("01-10");
        bean.setFlag(true);
        bean.setUrgentLevel("2");
        bean.setImage(R.mipmap.gongwen_img);
        list0.add(bean);
        bean = new PendingWorkBean();
        bean.setId("2");
        bean.setTitle("反电信诈骗平台对李华处置结果审批");
        bean.setCreateDate("01-16");
        bean.setFlag(true);
        bean.setUrgentLevel("3");
        bean.setImage(R.mipmap.gongwen_img);
        list0.add(bean);
        bean = new PendingWorkBean();
        bean.setId("3");
        bean.setTitle("发电信诈骗平台对罗申处置结果审批");
        bean.setCreateDate("01-18");
        bean.setFlag(true);
        bean.setUrgentLevel("1");
        bean.setImage(R.mipmap.gongwen_img);
        list0.add(bean);
    }
    private void initItemData1(){
        PendingWorkBean bean = new PendingWorkBean();
        bean.setId("0");
        bean.setTitle("公安部对“新一代移动警务”任务批示");
        bean.setCreateDate("01-02");
        bean.setFlag(true);
        bean.setUrgentLevel("1");
        bean.setImage(R.mipmap.gongwen_img);
        list1.add(bean);
        bean = new PendingWorkBean();
        bean.setId("1");
        bean.setTitle("省厅对电子公文系统进行升级公文涵");
        bean.setCreateDate("01-08");
        bean.setFlag(true);
        bean.setUrgentLevel("2");
        bean.setImage(R.mipmap.gongwen_img);
        list1.add(bean);
        bean = new PendingWorkBean();
        bean.setId("2");
        bean.setTitle("省厅对2019年工作任务重点指示");
        bean.setCreateDate("01-15");
        bean.setFlag(true);
        bean.setUrgentLevel("3");
        bean.setImage(R.mipmap.gongwen_img);
        list1.add(bean);
        bean = new PendingWorkBean();
        bean.setId("3");
        bean.setTitle("中共仁和地区委员会文件关于以党建促进临河棚改工程的意见");
        bean.setCreateDate("01-20");
        bean.setFlag(true);
        bean.setUrgentLevel("1");
        bean.setImage(R.mipmap.gongwen_img);
        list1.add(bean);
    }
    private void initItemData2(){
        PendingWorkBean bean = new PendingWorkBean();
        bean.setId("0");
        bean.setTitle("李阳被盗案立案审批");
        bean.setCreateDate("01-02");
        bean.setFlag(true);
        bean.setUrgentLevel("1");
        bean.setImage(R.mipmap.gongwen_img);
        list2.add(bean);
        bean = new PendingWorkBean();
        bean.setId("1");
        bean.setTitle("张嘉文被电信诈骗案立案审批");
        bean.setCreateDate("01-08");
        bean.setFlag(true);
        bean.setUrgentLevel("2");
        bean.setImage(R.mipmap.gongwen_img);
        list2.add(bean);
        bean = new PendingWorkBean();
        bean.setId("2");
        bean.setTitle("吉庆小区6-1-602被入室盗窃案审批");
        bean.setCreateDate("01-15");
        bean.setFlag(true);
        bean.setUrgentLevel("3");
        bean.setImage(R.mipmap.gongwen_img);
        list2.add(bean);
        bean = new PendingWorkBean();
        bean.setId("3");
        bean.setTitle("吉林市昌邑区网络诈骗案审批");
        bean.setCreateDate("01-20");
        bean.setFlag(true);
        bean.setUrgentLevel("1");
        bean.setImage(R.mipmap.gongwen_img);
        list2.add(bean);
    }
    private void initPendingWork0() {
        initPendingWorkAdapter(0);
        //list = new ArrayList<>();
//        PendingWorkBean bean = new PendingWorkBean();
//        bean.setTitle("公文待审批");
//        bean.setApplyPersonName("张三");
//        bean.setCreateDate("2018-12-11");
//        bean.setFlag(true);
//        bean.setUrgentLevel("1");
//        bean.setImage(R.mipmap.gongwen_img);
//        list.add(bean);

        //tv_pendingapproval_title.setText("待审批(" + list.size() + ")");
        pendingWorkAdapter.setNewData(list0);
    }
    private void initPendingWork1() {
        initPendingWorkAdapter(1);
        //list = new ArrayList<>();
//        PendingWorkBean bean = new PendingWorkBean();
//        bean.setTitle("公文待审批");
//        bean.setApplyPersonName("张三");
//        bean.setCreateDate("2018-12-11");
//        bean.setFlag(true);
//        bean.setUrgentLevel("1");
//        bean.setImage(R.mipmap.gongwen_img);
//        list.add(bean);

        //tv_pendingapproval_title.setText("待审批(" + list.size() + ")");
        pendingWorkAdapter.setNewData(list1);
    }
    private void initPendingWork2() {
        initPendingWorkAdapter(2);
        //list = new ArrayList<>();
//        PendingWorkBean bean = new PendingWorkBean();
//        bean.setTitle("公文待审批");
//        bean.setApplyPersonName("张三");
//        bean.setCreateDate("2018-12-11");
//        bean.setFlag(true);
//        bean.setUrgentLevel("1");
//        bean.setImage(R.mipmap.gongwen_img);
//        list.add(bean);

        //tv_pendingapproval_title.setText("待审批(" + list.size() + ")");
        pendingWorkAdapter.setNewData(list2);
    }

    private void initPendingWorkAdapter(int type) {
        pendingWorkAdapter = new PendingWorkAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        pendingWorkAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(pendingWorkAdapter);
        pendingWorkAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                 bean = (PendingWorkBean) adapter.getData().get(position);
//                Intent intent = new Intent(getActivity(), ScrawlImageviewActivity.class);
//                intent.putExtra("PendingWorkBean", bean);
//                intent.putExtra("type",1);
//
//                startActivity(intent);
//                // 涂鸦参数
//                DoodleParams params = new DoodleParams();
//                params.mIsFullScreen = true;
//                // 图片路径
//                // params.mImagePath = list.get(0);
//                // 初始画笔大小
//                params.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
//                // 启动涂鸦页面
//                DoodleActivity.startActivityForResult(getActivity(), params, REQ_CODE_DOODLE);


                Intent intent = new Intent(getActivity(), HandwrittenSignatureActivity.class);
//                intent.putExtra("PendingWorkBean", bean);
                intent.putExtra("type",type);
                intent.putExtra("index",Integer.parseInt(view.findViewById(R.id.item_name).getTag().toString()));
                startActivityForResult(intent,0);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        boolean isConfirm = data.getBooleanExtra("isConfirm", false);
        if (!isConfirm) {
            return;
        }
        int type = data.getIntExtra("type",1);
        int index = data.getIntExtra("index",0);
        String imgUrl = data.getStringExtra("url");
        PendingWorkBean bean;
        switch (type){
            case 0:
                bean = list0.get(index);
                bean.setApplyPersonName(imgUrl);
                listFinished.add(bean);
                list0.remove(index);
                whichTab = type;
                Log.e("测试", ""+index);
                initPendingWork0();
                break;
            case 1:
                bean = list1.get(index);
                bean.setApplyPersonName(imgUrl);
                listFinished.add(bean);
                list1.remove(index);
                whichTab = type;
                Log.e("测试", ""+index);
                initPendingWork1();
                break;
            case 2:
                bean = list2.get(index);
                bean.setApplyPersonName(imgUrl);
                listFinished.add(bean);
                list2.remove(index);
                whichTab = type;
                Log.e("测试", ""+index);
                initPendingWork2();
                break;
        }
    }

    private void initApproved() {
        initApprovedAdapter();
//        PendingWorkBean bean = new PendingWorkBean();
//        bean.setTitle("公文待审批");
//        bean.setApplyPersonName("李四");
//        bean.setCreateDate("2018-12-18");
//        bean.setFlag(false);
//        bean.setUrgentLevel("1");
//        bean.setImage(R.mipmap.gongwen_img);
//        listFinished.add(bean);
//        bean = new PendingWorkBean();
//        bean.setTitle("公文待审批");
//        bean.setApplyPersonName("李四");
//        bean.setCreateDate("2018-12-19");
//        bean.setFlag(false);
//        bean.setUrgentLevel("2");
//        bean.setImage(R.mipmap.gongwen_img);
//        listFinished.add(bean);

        pendingWorkAdapter.setNewData(listFinished);
    }

    public static final int REQ_CODE_SELECT_IMAGE = 100;
    public static final int REQ_CODE_DOODLE = 101;

    private void initApprovedAdapter() {
        pendingWorkAdapter = new PendingWorkAdapter();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));

        pendingWorkAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mRecyclerView.setAdapter(pendingWorkAdapter);
        pendingWorkAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bean = (PendingWorkBean) adapter.getData().get(position);
//                Intent intent = new Intent(getActivity(), ScrawlImageviewActivity.class);
//                intent.putExtra("PendingWorkBean", bean);
//                intent.putExtra("type",0);
//                startActivity(intent);
//                Intent intent = new Intent(getActivity(), HandwrittenSignatureActivity.class);
//                intent.putExtra("PendingWorkBean", bean);
//                intent.putExtra("type",1);
//
//                startActivity(intent);
                ImageView imgBox = findViewById(R.id.showImage);
                String url = bean.getApplyPersonName();
                Uri imgUri = getMediaUriFromPath(getContext(), url);
                try{
                    Bitmap bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(imgUri));
                    imgBox.setImageBitmap(bitmap);
                    mask.setVisibility(View.VISIBLE);
                }catch(Exception e){
                    e.printStackTrace();
                }


            }
        });
    }
    public static Uri getMediaUriFromPath(Context context, String path) {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[] {path.substring(path.lastIndexOf("/") + 1)},
                null);

        Uri uri = null;
        if(cursor.moveToFirst()) {
            uri = ContentUris.withAppendedId(mediaUri,
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
        }
        cursor.close();
        return uri;
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


    private List<HomeGoodBean> getListdata() {
        List<HomeGoodBean> listshop = new ArrayList<>();
        HomeGoodBean bean = new HomeGoodBean();
        bean.setId("1");
        bean.setName("发起公文");
        bean.setSrc(R.mipmap.doc1);
        listshop.add(bean);

        bean = new HomeGoodBean();
        bean.setId("2");
        bean.setName("接受公文");
        bean.setSrc(R.mipmap.doc2);
        listshop.add(bean);


        bean = new HomeGoodBean();
        bean.setId("3");
        bean.setName("公文查询");
        bean.setSrc(R.mipmap.doc3);
        listshop.add(bean);


        bean = new HomeGoodBean();
        bean.setId("4");
        bean.setName("邮箱");
        bean.setSrc(R.mipmap.doc4);
        listshop.add(bean);

        bean = new HomeGoodBean();
        bean.setId("5");
        bean.setName("日程管理");
        bean.setSrc(R.mipmap.doc5);
        listshop.add(bean);

        bean = new HomeGoodBean();
        bean.setId("5");
        bean.setName("考勤管理");
        bean.setSrc(R.mipmap.doc6);
        listshop.add(bean);

        bean = new HomeGoodBean();
        bean.setId("6");
        bean.setName("通知通告");
        bean.setSrc(R.mipmap.doc7);
        listshop.add(bean);

        return listshop;
    }

    private void initDiscountShop(final List<HomeGoodBean> listshop) {

        WorkMainAdapter shopAdapter = new WorkMainAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(shopAdapter);
        shopAdapter.setNewData(listshop);
        shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                HomeGoodBean bean = listshop.get(position);
                if (bean.getId().equals("1")) {
                    Intent intent = new Intent(getActivity(), DocumentMainActivity.class);
                    String ids = bean.getId();
                    intent.putExtra("id", ids);
                    startActivity(intent);
                } else if (bean.getId().equals("4")) {

                    startActivity(EmailLoginActivity.class);
                    // startActivity(MailBoxMainActivity.class);
                } else if (bean.getId().equals("6")) {
                    Intent intent = new Intent(getActivity(), NewsListActivity.class);
                    String ids = bean.getId();
                    intent.putExtra("title", "通知公告");
                    intent.putExtra("url", "http://www.freetk.cn:8789/mobileworkws/information.html");
                    startActivity(intent);
                } else {
                    toast("此功能正在维护中");
                }
            }
        });
    }

}