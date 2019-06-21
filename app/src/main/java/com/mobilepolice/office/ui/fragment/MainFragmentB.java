package com.mobilepolice.office.ui.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.toast.ToastUtils;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.ApproveDetails;
import com.mobilepolice.office.bean.ApproveList;
import com.mobilepolice.office.bean.PendingApprove;
import com.mobilepolice.office.bean.PendingWorkBean;
import com.mobilepolice.office.bean.SimpleBean;
import com.mobilepolice.office.http.HttpConnectInterface;
import com.mobilepolice.office.pdf.PdfSimpleUtil;
import com.mobilepolice.office.ui.activity.ApproveDetailsActivity;
import com.mobilepolice.office.ui.activity.HandwrittenSignatureActivity;
import com.mobilepolice.office.ui.adapter.ApproveAdapter;
import com.mobilepolice.office.ui.adapter.PendingApproveAdapter;
import com.mobilepolice.office.ui.adapter.PendingWorkAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import views.zeffect.cn.scrawlviewlib.panel.Line;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.mobilepolice.office.ui.fragment.SignatureFragment.KEY_IMAGE_PATH;
import static com.mobilepolice.office.ui.fragment.SignatureFragment.RESULT_ERROR;


/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public class MainFragmentB extends MyLazyFragment {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mGwSize1)
    TextView mGwSize1;
    @BindView(R.id.mGwSize2)
    TextView mGwSize2;
    @BindView(R.id.mGwSize3)
    TextView mGwSize3;
    @BindView(R.id.showImage)
    ImageView approveImage;
    @BindView(R.id.indicator)
    ProgressBar indicator;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    PendingWorkAdapter pendingWorkAdapter;
    PendingApproveAdapter pendingApprove;
    ApproveAdapter approveAdapter;
    ApproveAdapter approveAdapter2;
    PendingWorkBean bean;
    /*当前显示的tab索引*/
    int whichTab = 0;
    LinearLayout mask;

    private HashMap<String, ApproveDetails> detailsCache = new HashMap<>();
    private HashMap<String, String> approvePathCache = new HashMap<>();

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
//        initPendingApproveAdapter();
        initApproveAdapter();
        initRecyclerView();
//        requirePendingApproveTask(MyApplication.userCode);
//        requireApproveTask(MyApplication.userCode);
//        refresh.setRefreshing(true);
        refresh.setOnRefreshListener(() -> {
//            requirePendingApproveTask(MyApplication.userCode);
//            requireApproveTask(MyApplication.userCode);
//            current = null;
            approveAdapter.notifyDataSetChanged();
            approveAdapter2.notifyDataSetChanged();
            myRefresh();
            refresh.setRefreshing(false);
        });
        /*2019-03-02-end*/
        /*2019-03-04-start*/
        /*业务审批*/
        LinearLayout tab_2 = findViewById(R.id.tab_2);
        /*公文审批*/
        LinearLayout tab_1 = findViewById(R.id.tab_1);
        /*案件审批*/
        LinearLayout tab3 = findViewById(R.id.tab3);
        /*已审批*/
        LinearLayout finishBtn = findViewById(R.id.checkedFinish);
        /*未审批*/
        LinearLayout check = findViewById(R.id.check);
        /*2019-03-05-start*/
        mask = findViewById(R.id.mask);
        /*2019-03-05-end*/
//        initPendingWork2();

        tab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichTab = 0;
                check.setTag(1);
                finishBtn.setTag(0);
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#ffffff"));
                mRecyclerView.setAdapter(approveAdapter);
                approveAdapter.setData(list1,"0");
            }
        });
        tab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichTab = 1;
                check.setTag(1);
                finishBtn.setTag(0);
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#ffffff"));
//                initPendingWork2();
                mRecyclerView.setAdapter(approveAdapter);
                approveAdapter.setData(list2,"0");
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
//                initPendingWork2();
                mRecyclerView.setAdapter(approveAdapter);
                approveAdapter.setData(list3,"0");
            }
        });
        /*已审批*/
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = Integer.parseInt(v.getTag().toString());
                if (state == 1) {
                    return;
                }
                v.setTag(1);
                findViewById(R.id.check).setTag(0);
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#ffffff"));

                mRecyclerView.setAdapter(approveAdapter2);
                switch (whichTab) {
                    case 0:
                        approveAdapter2.setData(list1,"1");
                        break;
                    case 1:
                        approveAdapter2.setData(list2,"1");
                        break;
                    case 2:
                        approveAdapter2.setData(list3,"1");
                        break;
                }
            }
        });
        /*未审批*/
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = Integer.parseInt(v.getTag().toString());
                if (state == 1) {
                    return;
                }
                v.setTag(1);
                findViewById(R.id.checkedFinish).setTag(0);
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#ffffff"));
                mRecyclerView.setAdapter(approveAdapter);
                switch (whichTab) {
                    case 0:
                        approveAdapter.setData(list1,"0");
                        break;
                    case 1:
                        approveAdapter.setData(list2,"0");
                        break;
                    case 2:
                        approveAdapter.setData(list3,"0");
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
//        initItemData0(); /*业务审批*/
//        initItemData1();/*公文审批*/
//        initItemData2(); /*案件审批*/
        /*2019-03-04-end*/

        mRecyclerView.setAdapter(approveAdapter);

        setList1Data();
        setList2Data();
        setList3Data();

        approveAdapter.setData(list1,"0");
        myRefresh();
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        approveAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        approveAdapter2.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        pendingApprove.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    /**
     * 业务审批 测试接口
     * @param s
     */
    private void requireApproveTask(String s) {
//        HttpConnectInterface.approveList(s)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::approveList, this::err, this::onComplete)
//                .isDisposed();
    }

//    private void approveList(PendingApprove o) {
//        if (o.isSuccess()) {
//            approveAdapter.setData(o.getObj());
//            list2 = new ArrayList<>(o.getObj());
//            mGwSize2.setText("(" + o.getObj().size() + ")");
//        }
//    }

    private void myRefresh(){
        mGwSize1.setText("(" + getListSize(list1,"0") + ")");
        mGwSize2.setText("(" + getListSize(list2,"0") + ")");
        mGwSize3.setText("(" + getListSize(list3,"0") + ")");
    }

    public static int getListSize(List<PendingApprove.ObjBean> list,String isApproval){
        int n = 0;
        for (int i = 0; i<list.size();i++){
            if (TextUtils.equals(isApproval,list.get(i).getIsApproval())){
                n++;
            }
        }
        return n;
    }

    private List<PendingApprove.ObjBean> list1 = new ArrayList<>();
    private List<PendingApprove.ObjBean> list2 = new ArrayList<>();
    private List<PendingApprove.ObjBean> list3 = new ArrayList<>();


    private void initApproveAdapter() {
        approveAdapter = new ApproveAdapter(getContext());
        approveAdapter2 = new ApproveAdapter(getContext());

        approveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PendingApprove.ObjBean objBean = approveAdapter.getItem(position);
                Intent intent = new Intent(getContext(),HandwrittenSignatureActivity.class);
                intent.putExtra("data",objBean);
                intent.putExtra("position",position);
                intent.putExtra("whichTab",whichTab);
                startActivityForResult(intent,0x100);
//                startActivity(new Intent(getContext(), ApproveDetailsActivity.class).putExtra("img", approveAdapter.getItem(position).getApproveImage().replaceAll("10.106.12.104:8789", "192.168.20.228:7121")));
//                Glide.with(approveImage).load().into(approveImage);
//                mask.setVisibility(View.VISIBLE);
            }
        });
        approveAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String filePath = "";
                switch (whichTab) {
                    case 0:
                        filePath = list1.get(position).getFilepath();
                        break;
                    case 1:
                        filePath = list2.get(position).getFilepath();
                        break;
                    case 2:
                        filePath = list3.get(position).getFilepath();
                        break;
                }
                Intent intent = new Intent(getContext(), ApproveDetailsActivity.class);
                intent.putExtra("img",filePath);
                startActivity(intent);
            }
        });
    }


    private PendingApprove.ObjBean current = null;

    private void initPendingApproveAdapter() {
        pendingApprove = new PendingApproveAdapter();
        pendingApprove.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (current == null) {
                    Intent intent = new Intent(getContext(), HandwrittenSignatureActivity.class);
                    intent.putExtra("data", pendingApprove.getItem(position)).putExtra("type", -1);
                    startActivityForResult(intent, 0X7FC1);
                    current = pendingApprove.getItem(position);//292D6F56B5FE4C23991E2529762CBA57
                    ApproveDetails approveDetails = detailsCache.get(current.getRequestid());
                    if (approveDetails == null)
                        HttpConnectInterface.findApplyInfo(current.getRequestid())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(this::details, this::err, this::onComplete)
                                .isDisposed();
                }

            }

            private void onComplete() {

            }

            private void err(Throwable throwable) {
                throwable.printStackTrace();
            }

            private void details(ApproveDetails approveDetails) {
                if (approveDetails.isSuccess())
                    detailsCache.put(approveDetails.getObj().getId(), approveDetails);
            }
        });
    }

//    private void requirePendingApproveTask(String s) {
//        HttpConnectInterface.requirePendingApproveTask(s)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(this::pendingApproveTask, this::err, this::onComplete)
//                .isDisposed();
//    }

    private void onComplete() {
    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
        refresh.setRefreshing(false);
    }

//    private void pendingApproveTask(PendingApprove s) {
//        refresh.setRefreshing(false);
//        if (s.isSuccess()) {
//            list.clear();
//            for (int i = 0;i<s.getObj().size();i++){
//                String url = s.getObj().get(i).getApplyOffWordFile();
//                url = url.replaceAll("E:/zhhl/apache-tomcat-secretPhone/webapps", "http://ccsyc.cn:8789");
//                s.getObj().get(i).setApplyOffWordFile(url);
//            }
//            list.addAll(s.getObj());
//            pendingApprove.setData(list);
//            mGwSize1.setText("(" + s.getObj().size() + ")");
//            setList();
//        }
//
//    }


    @Override
    protected void initData() {
    }

    private void initItemData1() {
        mRecyclerView.setAdapter(approveAdapter);
    }


    private void initPendingWork(List<PendingApprove.ObjBean> list,String isApproval) {
//        ApproveAdapter p = new ApproveAdapter(getContext());
        approveAdapter.setData(list,isApproval);
    }


//    private void initPendingWork2() {
//        PendingApproveAdapter p = new PendingApproveAdapter();
//        if (list==null)list=new ArrayList<>();
//        p.setData(list);
//        mRecyclerView.setAdapter(p);
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("onActivityResult: ", "resultCode" + resultCode);
//        if (requestCode == 0X7FC1 && resultCode == RESULT_OK && data != null) {
//            Log.e("onActivityResult: ", data.getStringExtra(KEY_IMAGE_PATH));
//
//            approvePathCache.put(current.getRequestid(), data.getStringExtra(KEY_IMAGE_PATH));
//            if (data.getIntExtra("flag", 0) == 0) {
//                HttpConnectInterface.loadFileBase64(data.getStringExtra(KEY_IMAGE_PATH))
//                        .observeOn(Schedulers.io())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe(this::approveAgree, this::err, this::onComplete)
//                        .isDisposed();
//            } else {
//                HttpConnectInterface.loadFileBase64(data.getStringExtra(KEY_IMAGE_PATH))
//                        .observeOn(Schedulers.io())
//                        .subscribeOn(Schedulers.io())
//                        .subscribe((o -> this.approveRejected(o, data.getStringExtra("message"))), this::err, this::onComplete)
//                        .isDisposed();
//            }
//            return;
//        } else if (requestCode == 0X7FC1 && resultCode == RESULT_ERROR) {
//            Log.e("onActivityResult: ", "获取图片失败");
//            current = null;
//            return;
//        } else if (requestCode == 0X7FC1 && resultCode == RESULT_CANCELED) {
//            current = null;
//        }

        if (resultCode == RESULT_OK){
            switch (requestCode){

                case 0x100:
                    int position = data.getIntExtra("position",0);
                    int whichTab = data.getIntExtra("whichTab",0);
                    String filepath = data.getStringExtra("filepath");
                    Log.e("position + whichTab",position+ "------" + whichTab);
                    switch (whichTab){
                        case 0:
                            list1.get(position).setIsApproval("1");
                            list1.get(position).setFilepath(filepath);
                            approveAdapter.setData(list1,"0");
                            break;
                        case 1:
                            list2.get(position).setIsApproval("1");
                            approveAdapter.setData(list2,"0");
                            list2.get(position).setFilepath(filepath);
                            break;
                        case 2:
                            list3.get(position).setIsApproval("1");
                            approveAdapter.setData(list3,"0");
                            list3.get(position).setFilepath(filepath);
                            break;
                    }

                    break;
            }


            myRefresh();
        }
    }

    private void approveRejected(String base64, String rejectedData) {
        String reqId = current.getRequestid();
        HttpConnectInterface.rejectedWorkFlow(current.getRequestid(), current.getApprovePerson(), current.getApplyPerson(), rejectedData, base64)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((o -> this.genPdf(o, reqId)), this::err, this::onComplete)
                .isDisposed();
    }

    private void approveAgree(String base64) {
        String schema = detailsCache.get(current.getRequestid()).getObj().getSchema();
        String reqId = current.getRequestid();
        HttpConnectInterface.approve(current.getRequestid(), current.getApproveNodeId(), current.getApprovePerson(), current.getApplyPerson(), schema, "2", base64)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((o -> this.genPdf(o, reqId)), this::err, this::onComplete)
                .isDisposed();
        current = null;
    }

    private void genPdf(SimpleBean o, String reqId) {
        if (o.isSuccess()) {
            File file = new File(getContext().getFilesDir(), "/pdf");
            file.mkdirs();
            File pdf = new File(file, reqId + ".pdf");
            PdfSimpleUtil.imgToPdf(approvePathCache.get(reqId), pdf.getAbsolutePath())
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .subscribe((s) -> this.pdfChangedBase64(s, reqId), this::err, this::onComplete)
                    .isDisposed();
        }
    }

    private void pdfChangedBase64(String pdfPath, String reqId) {
        HttpConnectInterface.loadFileBase64(pdfPath)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe((base64 -> this.uploadBase64Pdf(base64, reqId)), this::err, this::onComplete)
                .isDisposed();
    }

    private void uploadBase64Pdf(String base64, String reqId) {
        HttpConnectInterface.uploadBase64Pdp(base64, reqId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::uploadBase64PdfResult, this::err, this::onComplete)
                .isDisposed();
    }

    private void uploadBase64PdfResult(SimpleBean o) {
        if (o.isSuccess()) {
            ToastUtils.show("提交成功");
//            requirePendingApproveTask(MyApplication.userCode);
            requireApproveTask(MyApplication.userCode);
        }
        Log.e("uploadBase64PdfResult: ", "====END====");
    }


    public static final int REQ_CODE_SELECT_IMAGE = 100;
    public static final int REQ_CODE_DOODLE = 101;


    public static Uri getMediaUriFromPath(Context context, String path) {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[]{path.substring(path.lastIndexOf("/") + 1)},
                null);

        Uri uri = null;
        if (cursor.moveToFirst()) {
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


    private void setList1Data(){
        PendingApprove.ObjBean approval1 = new PendingApprove.ObjBean();
        approval1.setTitle("移动警务上架商店");
        approval1.setCreateDate("04/27");
        approval1.setIsApproval("0");
        approval1.setUrgentLevel("3");
        approval1.setApplyOffWordFile("img1");
        approval1.setOverFlag("1");
        list1.add(approval1);

        PendingApprove.ObjBean approval2 = new PendingApprove.ObjBean();
        approval2.setTitle("办公用品申请");
        approval2.setCreateDate("04/25");
        approval2.setIsApproval("0");
        approval2.setUrgentLevel("3");
        approval2.setApplyOffWordFile("img1");
        approval2.setOverFlag("2");
        list1.add(approval2);

        PendingApprove.ObjBean approval3 = new PendingApprove.ObjBean();
        approval3.setTitle("企业管理通知");
        approval3.setCreateDate("04/21");
        approval3.setIsApproval("0");
        approval3.setUrgentLevel("3");
        approval3.setApplyOffWordFile("img1");
        approval3.setOverFlag("3");
        list1.add(approval3);

        PendingApprove.ObjBean approval4 = new PendingApprove.ObjBean();
        approval4.setTitle("整合平台资源共享审批");
        approval4.setCreateDate("04/19");
        approval4.setIsApproval("0");
        approval4.setUrgentLevel("1");
        approval4.setApplyOffWordFile("img1");
        approval4.setOverFlag("1");
        list1.add(approval4);

        PendingApprove.ObjBean approval5 = new PendingApprove.ObjBean();
        approval5.setTitle("申请警务云服务器的审批");
        approval5.setCreateDate("04/15");
        approval5.setIsApproval("0");
        approval5.setUrgentLevel("1");
        approval5.setApplyOffWordFile("img1");
        approval5.setOverFlag("2");
        list1.add(approval5);

    }

    private void setList2Data(){
        PendingApprove.ObjBean approval1 = new PendingApprove.ObjBean();
        approval1.setTitle("补(换)发证件身份核查");
        approval1.setCreateDate("05/26");
        approval1.setIsApproval("0");
        approval1.setUrgentLevel("3");
        approval1.setApplyOffWordFile("img1");
        approval1.setOverFlag("1");
        list2.add(approval1);

        PendingApprove.ObjBean approval2 = new PendingApprove.ObjBean();
        approval2.setTitle("户口薄遗失补发");
        approval2.setCreateDate("05/24");
        approval2.setIsApproval("0");
        approval2.setUrgentLevel("3");
        approval2.setApplyOffWordFile("img1");
        approval2.setOverFlag("2");
        list2.add(approval2);

        PendingApprove.ObjBean approval3 = new PendingApprove.ObjBean();
        approval3.setTitle("首次申领居民身份证");
        approval3.setCreateDate("05/21");
        approval3.setIsApproval("0");
        approval3.setUrgentLevel("3");
        approval3.setApplyOffWordFile("img1");
        approval3.setOverFlag("3");
        list2.add(approval3);

        PendingApprove.ObjBean approval4 = new PendingApprove.ObjBean();
        approval4.setTitle("普通护照首次申领");
        approval4.setCreateDate("05/18");
        approval4.setIsApproval("0");
        approval4.setUrgentLevel("1");
        approval4.setApplyOffWordFile("img1");
        approval4.setOverFlag("1");
        list2.add(approval4);

        PendingApprove.ObjBean approval5 = new PendingApprove.ObjBean();
        approval5.setTitle("企业注册申请");
        approval5.setCreateDate("04/28");
        approval5.setIsApproval("0");
        approval5.setUrgentLevel("1");
        approval5.setApplyOffWordFile("img1");
        approval5.setOverFlag("2");
        list2.add(approval5);

    }

    private void setList3Data(){
        PendingApprove.ObjBean approval1 = new PendingApprove.ObjBean();
        approval1.setTitle("拟报立刑事案件");
        approval1.setCreateDate("03/19");
        approval1.setIsApproval("0");
        approval1.setUrgentLevel("3");
        approval1.setApplyOffWordFile("img1");
        approval1.setOverFlag("1");
        list3.add(approval1);

        PendingApprove.ObjBean approval2 = new PendingApprove.ObjBean();
        approval2.setTitle("呈请立案报告书");
        approval2.setCreateDate("03/10");
        approval2.setIsApproval("0");
        approval2.setUrgentLevel("3");
        approval2.setApplyOffWordFile("img1");
        approval2.setOverFlag("2");
        list3.add(approval2);

        PendingApprove.ObjBean approval3 = new PendingApprove.ObjBean();
        approval3.setTitle("疑似被拐卖立案申请");
        approval3.setCreateDate("02/18");
        approval3.setIsApproval("0");
        approval3.setUrgentLevel("3");
        approval3.setApplyOffWordFile("img1");
        approval3.setOverFlag("3");
        list3.add(approval3);

        PendingApprove.ObjBean approval4 = new PendingApprove.ObjBean();
        approval4.setTitle("行政处罚书");
        approval4.setCreateDate("02/10");
        approval4.setIsApproval("0");
        approval4.setUrgentLevel("1");
        approval4.setApplyOffWordFile("img1");
        approval4.setOverFlag("1");
        list3.add(approval4);

        PendingApprove.ObjBean approval5 = new PendingApprove.ObjBean();
        approval5.setTitle("疑似抢劫申请");
        approval5.setCreateDate("02/04");
        approval5.setIsApproval("0");
        approval5.setUrgentLevel("1");
        approval5.setApplyOffWordFile("img1");
        approval5.setOverFlag("2");
        list3.add(approval5);
    }

}