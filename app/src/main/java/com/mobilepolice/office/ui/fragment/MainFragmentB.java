package com.mobilepolice.office.ui.fragment;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.mGwSize)
    TextView mGwSize;
    @BindView(R.id.showImage)
    ImageView approveImage;
    @BindView(R.id.indicator)
    ProgressBar indicator;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    PendingWorkAdapter pendingWorkAdapter;
    PendingApproveAdapter pendingApprove;
    ApproveAdapter approveAdapter;
    PendingWorkBean bean;
    /*当前显示的tab索引*/
    int whichTab = 1;
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
        mGwSize.setText("(0)");
        initPendingApproveAdapter();
        initApproveAdapter();
        initRecyclerView();
        requirePendingApproveTask(MyApplication.userCode);
        requireApproveTask(MyApplication.userCode);
        refresh.setRefreshing(true);
        refresh.setOnRefreshListener(() -> {
            requirePendingApproveTask(MyApplication.userCode);
            requireApproveTask(MyApplication.userCode);
            current = null;
        });
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
        initPendingWork0();
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
                mRecyclerView.setAdapter(pendingApprove);
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
                if (state == 1) {
                    return;
                }
                v.setTag(1);
                findViewById(R.id.check).setTag(0);
                ((TextView) findViewById(R.id.text2)).setTextColor(Color.parseColor("#1f1f1f"));
                (findViewById(R.id.bar2)).setBackgroundColor(Color.parseColor("#27437f"));
                ((TextView) findViewById(R.id.text1)).setTextColor(Color.parseColor("#6d6d6d"));
                (findViewById(R.id.bar1)).setBackgroundColor(Color.parseColor("#ffffff"));

                mRecyclerView.setAdapter(approveAdapter);
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
                switch (whichTab) {
                    case 0:
                        initPendingWork0();
                        break;
                    case 1:
                        mRecyclerView.setAdapter(pendingApprove);
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
//        initItemData0(); /*业务审批*/
        initItemData1();/*公文审批*/
//        initItemData2(); /*案件审批*/
        /*2019-03-04-end*/
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        approveAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        pendingApprove.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
    }

    private void requireApproveTask(String s) {
        HttpConnectInterface.approveList(s)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::approveList, this::err, this::onComplete)
                .isDisposed();
    }

    private void approveList(ApproveList o) {
        if (o.isSuccess()) {
            approveAdapter.setData(o.getObj());
            list2 = new ArrayList<>(o.getObj());
        }
    }

    private List<ApproveList.ObjBean> list2;
    private List<PendingApprove.ObjBean> list3;

    private void initApproveAdapter() {
        approveAdapter = new ApproveAdapter();
        approveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                startActivity(new Intent(getContext(), ApproveDetailsActivity.class).putExtra("img", approveAdapter.getItem(position).getApproveImage().replaceAll("10.106.12.104:8789", "192.168.20.228:7121")));
//                Glide.with(approveImage).load().into(approveImage);
//                mask.setVisibility(View.VISIBLE);
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

    private void requirePendingApproveTask(String s) {
        HttpConnectInterface.requirePendingApproveTask(s)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::pendingApproveTask, this::err, this::onComplete)
                .isDisposed();
    }

    private void onComplete() {
    }

    private void err(Throwable throwable) {
        throwable.printStackTrace();
        refresh.setRefreshing(false);
    }

    private void pendingApproveTask(PendingApprove s) {
        refresh.setRefreshing(false);
        if (s.isSuccess()) {
            list3=s.getObj();
            pendingApprove.setData(s.getObj());
            mGwSize.setText("(" + s.getObj().size() + ")");
        }

    }

    @Override
    protected void initData() {
    }

    private void initItemData1() {
        mRecyclerView.setAdapter(pendingApprove);
    }


    private void initPendingWork0() {
        ApproveAdapter p = new ApproveAdapter();
        if (list2==null)list2=new ArrayList<>();
        p.setData(list2);
        mRecyclerView.setAdapter(p);
    }


    private void initPendingWork2() {
        PendingApproveAdapter p = new PendingApproveAdapter();
        if (list3==null)list3=new ArrayList<>();
        p.setData(list3);
        mRecyclerView.setAdapter(p);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("onActivityResult: ", "resultCode" + resultCode);
        if (requestCode == 0X7FC1 && resultCode == RESULT_OK && data != null) {
            Log.e("onActivityResult: ", data.getStringExtra(KEY_IMAGE_PATH));

            approvePathCache.put(current.getRequestid(), data.getStringExtra(KEY_IMAGE_PATH));
            if (data.getIntExtra("flag", 0) == 0) {
                HttpConnectInterface.loadFileBase64(data.getStringExtra(KEY_IMAGE_PATH))
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::approveAgree, this::err, this::onComplete)
                        .isDisposed();
            } else {
                HttpConnectInterface.loadFileBase64(data.getStringExtra(KEY_IMAGE_PATH))
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .subscribe((o -> this.approveRejected(o, data.getStringExtra("message"))), this::err, this::onComplete)
                        .isDisposed();
            }
            return;
        } else if (requestCode == 0X7FC1 && resultCode == RESULT_ERROR) {
            Log.e("onActivityResult: ", "获取图片失败");
            current = null;
            return;
        } else if (requestCode == 0X7FC1 && resultCode == RESULT_CANCELED) {
            current = null;
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
            requirePendingApproveTask(MyApplication.userCode);
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

}