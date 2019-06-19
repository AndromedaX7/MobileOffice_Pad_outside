package com.mobilepolice.office.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.base.BaseFragmentPagerAdapter;
import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;
import com.mobilepolice.office.bean.PendingApprove;
import com.mobilepolice.office.http.HttpConnectInterface;
import com.mobilepolice.office.ui.fragment.SignatureFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.forward.androids.views.MaskImageView;
import cn.forward.androids.views.STextView;
import cn.hzw.doodle.DoodleOnTouchGestureListener;
import cn.hzw.doodle.DoodleParams;
import cn.hzw.doodle.DoodleView;
import cn.hzw.doodle.core.IDoodle;
import cn.hzw.doodle.core.IDoodlePen;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HandwrittenSignatureActivity extends FragmentActivity {

    private FrameLayout mFrameLayout;
    private DoodleView mDoodleView;
    private IDoodle mDoodle;

    public final static int DEFAULT_COPY_SIZE = 20; // 默认仿制大小
    public final static int DEFAULT_TEXT_SIZE = 17; // 默认文字大小
    public final static int DEFAULT_BITMAP_SIZE = 80; // 默认贴图大小

    public static final int RESULT_ERROR = -111; // 出现错误
    public static final String KEY_PARAMS = "key_doodle_params";
    public static final String KEY_IMAGE_PATH = "key_image_path";

    private String mImagePath;
//    private TextView mPaintSizeView;
//
//    private View mBtnHidePanel, mSettingsPanel;
//    private View mSelectedTextEditContainer;
//    private View mBtnColor;
//    private SeekBar mEditSizeSeekBar;

    private AlphaAnimation mViewShowAnimation, mViewHideAnimation; // view隐藏和显示时用到的渐变动画

    private DoodleParams mDoodleParams;

    // 触摸屏幕超过一定时间才判断为需要隐藏设置面板
    private Runnable mHideDelayRunnable;
    // 触摸屏幕超过一定时间才判断为需要显示设置面板
    private Runnable mShowDelayRunnable;

    private DoodleOnTouchGestureListener mTouchGestureListener;
    private Map<IDoodlePen, Float> mPenSizeMap = new HashMap<>(); //保存每个画笔对应的最新大小
    private List<DoodleView> doodleViewList;
    private ViewPager viewpager;
    private List<String> imglist = new ArrayList<>();
    PictureGuidePageAdapter guidePagneAndapter;
    HomeViewPagerAdapter mPagerAdapter;

    private MaskImageView doodle_btn_back;
    private STextView doodle_txt_title;
    private MaskImageView doodle_btn_rotate;
    private MaskImageView mBtnHidePanel;
    private MaskImageView doodle_btn_finish;

    private TextView tv_start;
    private TextView tv_end;
    private TextView tv_page;

    private LinearLayout class_id_01;
    private LinearLayout class_id_02;
    private int currentpage = 0;
    List<MyLazyFragment> fragmentList;

    private PendingApprove.ObjBean bean;
    int type;
    int index;
    String applyOffWordFile;

    private int maxLength=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handweitten_signature);
//        mFrameLayout = (FrameLayout) findViewById(R.id.doodle_container);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 2);
        index = intent.getIntExtra("index", 0);
        bean = intent.getParcelableExtra("data");
        applyOffWordFile = bean.getApplyOffWordFile();
        applyOffWordFile = applyOffWordFile.replaceAll("10.106.12.104:8789", "192.168.20.228:7121");
        String[] split = applyOffWordFile.split(",");
        if (split != null && split.length > 1) {
            maxLength=split.length;
            for (String img:split){
                HttpConnectInterface.getImage(img)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::imageRes, this::error, this::onComplete)
                        .isDisposed();
            }
        } else
            HttpConnectInterface.getImage(applyOffWordFile)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::imageRes, this::error, this::onComplete)
                    .isDisposed();
        Log.e("onCreate: ", applyOffWordFile);
        tv_start = (TextView) findViewById(R.id.tv_start); //上一页
        tv_page = (TextView) findViewById(R.id.tv_page);   //图片页数
        tv_end = (TextView) findViewById(R.id.tv_end);     // 下一页
        class_id_01 = (LinearLayout) findViewById(R.id.class_id_01);
        class_id_02 = (LinearLayout) findViewById(R.id.class_id_02);
        doodle_btn_back = (MaskImageView) findViewById(R.id.doodle_btn_back);  //返回按钮
        doodle_txt_title = (STextView) findViewById(R.id.doodle_txt_title);
        doodle_btn_rotate = (MaskImageView) findViewById(R.id.doodle_btn_rotate);
        mBtnHidePanel = (MaskImageView) findViewById(R.id.doodle_btn_hide_panel);
        doodle_btn_finish = (MaskImageView) findViewById(R.id.doodle_btn_finish);

        viewpager = (ViewPager) findViewById(R.id.viewpager);

//        imglist.add("2");
//        imglist.add("3");
//        imglist.add("4");
//        initPictureGudieView();
        mPagerAdapter = new HomeViewPagerAdapter(this);
        viewpager.setAdapter(mPagerAdapter);
        viewpager.setOffscreenPageLimit(fragmentList.size());
        setListener();
    }

    private void onComplete() {

    }

    private void error(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void imageRes(byte[] bytes) {
        File file = new File(getFilesDir() + "/approve");
        file.mkdirs();
        file = new File(getFilesDir() + "/approve", applyOffWordFile.substring(applyOffWordFile.lastIndexOf("/") + 1));
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            mPagerAdapter.add(file.getAbsolutePath());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        tv_page.setText((currentpage + 1) + "/" + /*imglist.size()*/maxLength);
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentpage == 0) {
                    return;
                }
                currentpage--;
                tv_page.setText((currentpage + 1) + "/" + maxLength);
                viewpager.setCurrentItem(currentpage);
            }
        });
        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((maxLength - 1) == (currentpage)) {
                    return;
                }
                currentpage++;
                viewpager.setCurrentItem(currentpage);
                tv_page.setText((currentpage + 1) + "/" + maxLength);

            }
        });

        class_id_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> urlStr = new ArrayList<>();
                for (int i = 0; i < fragmentList.size(); i++) {
                    SignatureFragment signatureFragment = (SignatureFragment) fragmentList.get(i);
                    signatureFragment.save(0, "");
                    urlStr.add(signatureFragment.urlStr);
                }
//                Intent intent = new Intent();
//                intent.putExtra("isConfirm", true);
//                intent.putExtra("index", index);
//                intent.putExtra("type", type);
//                intent.putExtra("url", urlStr.get(0));
                Toast.makeText(HandwrittenSignatureActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        });
        class_id_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPopWindow();
            }
        });

        doodle_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        doodle_btn_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBtnHidePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        doodle_btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /*弹出层*/
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
                String rejectedText = et_reject.getText().toString();
                for (int i = 0; i < fragmentList.size(); i++) {
                    SignatureFragment signatureFragment = (SignatureFragment) fragmentList.get(i);
                    signatureFragment.save(1, rejectedText);
//                    urlStr.add(signatureFragment.urlStr);
                }
                Toast.makeText(HandwrittenSignatureActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
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


    public final class HomeViewPagerAdapter extends BaseFragmentPagerAdapter<MyLazyFragment> {
        @Override
        public List<MyLazyFragment> getAllFragment() {

            return super.getAllFragment();
        }

        public HomeViewPagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @Override
        protected void init(FragmentManager fm, List<MyLazyFragment> list) {
            for (int i = 0; i < imglist.size(); i++) {
//                if (i == 0) {
//
//                } else if (i == 1) {
//                    list.add(SignatureFragment.newInstance(R.drawable.thelittleprince2));
//                } else if (i == 2) {
//                    list.add(SignatureFragment.newInstance(R.drawable.approval_img));
//                } else if (i == 3) {
//                    list.add(SignatureFragment.newInstance(R.drawable.approval_img));
//                }

                list.add(SignatureFragment.newInstance(imglist.get(i)));
            }
            fragmentList = list;

        }

        public void add(String path) {
            fragmentList.add(SignatureFragment.newInstance(path));
            mPagerAdapter.notifyDataSetChanged();
        }
    }


    private void initPictureGudieView() {
        doodleViewList = new ArrayList<DoodleView>();
        for (int i = 0; i < imglist.size(); i++) {
//            FrameLayout layout = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_signature, null);
//            ImageView iv = (ImageView) layout
//                    .findViewById(R.id.viewpager_item_iv);
//            layout.setTag(b.get(in));
//            doodleViewList.add();
        }


        guidePagneAndapter = new PictureGuidePageAdapter();
        // viewpager
        viewpager.setAdapter(guidePagneAndapter);
        // 起始页为第一页，循环显示的时候，61234561，第0个放第6个页面，第7个放第0个页面
        viewpager.setCurrentItem(0, false);
        viewpager.setOnPageChangeListener(new PictureGuidePageChangeListener());

    }

    // 指引页面数据适配器
    class PictureGuidePageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return doodleViewList.size(); // 原来的
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(doodleViewList.get(arg1));// 原来的
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
//            doodleViewList.get(arg1).setOnClickListener(
//                    new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
////                            ImageNewBean img = (ImageNewBean) v.getTag();
////                            if (!TextUtils.isEmpty(img.getImage())) {
////                                //Constants.startModule(img.getHref(), img.getTitle(), getActivity());
////                            }
//                        }
//                    });
//            ImageView iv = (ImageView) picture_pageViews.get(arg1)
//                    .findViewById(R.id.viewpager_item_iv);
//            ImageOptions imageOptions = new ImageOptions.Builder()
//                    .setIgnoreGif(true)//是否忽略gif图。false表示不忽略。不写这句，默认是true
//                    .setImageScaleType(ImageView.ScaleType.FIT_XY)//CENTER_CROP
//                    .setFailureDrawableId(R.mipmap.not_photo)
//                    .setLoadingDrawableId(R.mipmap.lodingview)
//                    .setUseMemCache(true)
//                    .setIgnoreGif(false)
//                    .build();

            //  x.image().bind(iv, Config.UPDATE_SERVER_PHOTO + imglist.get(arg1).getImage(), imageOptions);
            //          iv.setImageResource(imglist.get(arg1).getClick());
//            ((ViewPager) arg0).addView(doodleViewList.get(arg1));
            return doodleViewList.get(arg1);

        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }

    // 指引页面更改事件监听器
    class PictureGuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            int showIndex = position;
//            if (position == 0) {
//                showIndex = picture_pageViews.size() - 2;
//                picture_viewPager.setCurrentItem(showIndex, false);
//            } else {
//                if (position == picture_pageViews.size() - 1) {
//                    showIndex = 1;
//                    picture_viewPager.setCurrentItem(1, false);
//                }
//            }
//            // 更新当前图片卡号
//            picture_num = showIndex;
//            for (int i = 0; i < picture_imageViews.length; i++) {
//                picture_imageViews[showIndex - 1]
//                        .setBackgroundResource(R.mipmap.red_dot);
//                if ((showIndex - 1) != i) {
//                    picture_imageViews[i]
//                            .setBackgroundResource(R.mipmap.white_dot);
//                }
//            }

            Log.i("图片当前页数", showIndex + "");
        }
    }


}
