package com.mobilepolice.office.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyActivity;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.bean.DocPendingBean;
import com.mobilepolice.office.bean.PendingApprove;
import com.mobilepolice.office.bean.PendingWorkBean;
import com.mobilepolice.office.utils.FileUtil;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import views.zeffect.cn.scrawlviewlib.panel.SketchPadView;

public class ScrawlImageviewActivity extends MyActivity implements View.OnClickListener {
    @BindView(R.id.scrawlview)
    SketchPadView mSketchPadView;



    @BindView(R.id.leftButton)
    ImageView leftButton;
    @BindView(R.id.class_id_01)
    LinearLayout class_id_01;
    @BindView(R.id.class_id_02)
    LinearLayout class_id_02;
    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.tv_qc)
    TextView tv_qc;

    private List<String> titles = new ArrayList<>();
    String id = "";
    DocPendingBean docPendingBean;
    PopupWindow popupWindow;
    PendingApprove.ObjBean  bean;
    int type = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_document_scraw;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {
        setTitle("公文正文");
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
//        bean = (PendingWorkBean) getIntent().getSerializableExtra("PendingWorkBean");
        bean = getIntent() .getParcelableExtra("data");
        type = getIntent().getIntExtra("type", -1);
        if (type == 0) {
            tv_msg.setVisibility(View.GONE);
            class_id_01.setVisibility(View.GONE);
            class_id_02.setVisibility(View.GONE);
        }
        class_id_01.setOnClickListener(this);
        class_id_02.setOnClickListener(this);
        //mSketchPadView.setPenSize(15);
        mSketchPadView.setViewBackground(BitmapFactory.decodeResource(getResources(), R.mipmap.approval_img));
//        if (bean != null) {
//            mSketchPadView.setViewBackground(BitmapFactory.decodeResource(getResources(), bean.getImage()));
//        }
        tv_qc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSketchPadView.setStrokeType(SketchPadView.PenType.Eraser);
            }
        });

    }

    public String getId() {
        return id;
    }

    public DocPendingBean getDocPendingBean() {
        return docPendingBean;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return false;
    }

    @Override
    protected void initData() {

    }

    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        File file;
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED)) {
//            savePath = SD_PATH;
//        } else {
//            savePath = context.getApplicationContext().getFilesDir()
//                    .getAbsolutePath()
//                    + IN_PATH;
//        }
        try {

            String fileDir = FileUtil.PDF_PATH;
            String path = fileDir + SystemClock.elapsedRealtime() + ".png";
            file = new File(fileDir);
            if (!file.exists()) {
                file.mkdir();
            }

//            filePic = new File(savePath + generateFileName() + ".jpg");
//            if (!filePic.exists()) {
//                filePic.getParentFile().mkdirs();
//                filePic.createNewFile();
//            }
            FileOutputStream fos = new FileOutputStream(path);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return file.getAbsolutePath();
    }

    private void setPopWindow() {
        View customView = View.inflate(this, R.layout.layout_reject, null);

        PopWindow popWindow = new PopWindow.Builder(this)
                .setStyle(PopWindow.PopWindowStyle.PopUp)
                .addItemAction(new PopItemAction("选项"))//注意这里setVIew的优先级最高，及只要执行了setView，其他添加的view都是无效的
                .setView(customView)
                .setIsShowCircleBackground(true)
                .create();
        popWindow.show();
        EditText et_reject = customView.findViewById(R.id.et_reject);
        LinearLayout class_id_04 = customView.findViewById(R.id.class_id_04);
        LinearLayout class_id_05 = customView.findViewById(R.id.class_id_05);
        class_id_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
                toast("保存成功");
                finish();
            }
        });
        class_id_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
                et_reject.setText("");
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (class_id_01 == view) {
            Bitmap bitmap = mSketchPadView.getScreenShot();
            String path = saveBitmap(ScrawlImageviewActivity.this, bitmap);
            MyApplication.getInstance().path_image = path;
            toast("保存成功");
            finish();
        } else if (class_id_02 == view) {
            setPopWindow();
            //showPop(view);
        }
    }

    private void showPop(View view) {

        View popLayout = LayoutInflater.from(this).inflate(R.layout.layout_reject, null);

        EditText et_reject = popLayout.findViewById(R.id.et_reject);
        LinearLayout class_id_04 = popLayout.findViewById(R.id.class_id_04);
        LinearLayout class_id_05 = popLayout.findViewById(R.id.class_id_05);
        class_id_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissPopWindow();
            }
        });
        class_id_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissPopWindow();

            }
        });


        popupWindow = new PopupWindow(popLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(1.0f);
            }
        });
        setBackgroundAlpha(0.2f);//设置屏幕透明度
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setAnimationStyle(R.style.pop_anim);
        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void dismissPopWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) this).getWindow().setAttributes(lp);
    }
}
