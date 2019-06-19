package com.mobilepolice.office.ui.fragment;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.forward.androids.utils.ImageUtils;
import cn.forward.androids.utils.Util;
import cn.forward.androids.views.MaskImageView;
import cn.forward.androids.views.STextView;
import cn.hzw.doodle.DoodleBitmap;
import cn.hzw.doodle.DoodleColor;
import cn.hzw.doodle.DoodleOnTouchGestureListener;
import cn.hzw.doodle.DoodleParams;
import cn.hzw.doodle.DoodlePen;
import cn.hzw.doodle.DoodleShape;
import cn.hzw.doodle.DoodleText;
import cn.hzw.doodle.DoodleTouchDetector;
import cn.hzw.doodle.DoodleView;
import cn.hzw.doodle.IDoodleListener;
import cn.hzw.doodle.core.IDoodle;
import cn.hzw.doodle.core.IDoodleColor;
import cn.hzw.doodle.core.IDoodlePen;
import cn.hzw.doodle.core.IDoodleSelectableItem;
import cn.hzw.doodle.core.IDoodleShape;
import cn.hzw.doodle.core.IDoodleTouchDetector;
import cn.hzw.doodle.dialog.ColorPickerDialog;
import cn.hzw.doodle.dialog.DialogController;
import cn.hzw.doodle.imagepicker.ImageSelectorView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目炫酷效果示例
 */
public class SignatureFragment extends MyLazyFragment implements View.OnClickListener {


    private FrameLayout mFrameLayout;
    private DoodleView mDoodleView;
    private IDoodle mDoodle;
    private View mView;
    public final static int DEFAULT_COPY_SIZE = 20; // 默认仿制大小
    public final static int DEFAULT_TEXT_SIZE = 17; // 默认文字大小
    public final static int DEFAULT_BITMAP_SIZE = 80; // 默认贴图大小

    public static final int RESULT_ERROR = -111; // 出现错误
    public static final String KEY_PARAMS = "key_doodle_params";
    public static final String KEY_IMAGE_PATH = "key_image_path";

    private String mImagePath;

    public String urlStr;
//    private TextView mPaintSizeView;

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


    ////////
    private MaskImageView doodle_btn_back;
    private STextView doodle_txt_title;
    private MaskImageView doodle_btn_rotate;
    private MaskImageView mBtnHidePanel;
    private MaskImageView doodle_btn_finish;
    //////////////////
    private RelativeLayout mSettingsPanel;
    private ImageView btn_pen_hand;
    private ImageView btn_pen_copy;
    private ImageView btn_pen_eraser;
    private ImageView btn_pen_text;
    private ImageView btn_pen_bitmap;
    private ImageView btn_clear;
    private ImageView btn_undo;
    private ImageView btn_zoomer;  //放大缩小
/////////


    private LinearLayout bar_shape_mode;
    private ImageView btn_hand_write;
    private ImageView btn_arrow;
    private ImageView btn_line;
    private ImageView btn_holl_circle;
    private ImageView btn_fill_circle;
    private ImageView btn_holl_rect;
    private ImageView btn_fill_rect;
    private ImageView doodle_btn_brush_edit;
    /////////////
    private LinearLayout doodle_color_container;
    private FrameLayout btn_set_color_container;
    private ImageView mBtnColor;
    private SeekBar mEditSizeSeekBar;

    private TextView mPaintSizeView;

    private LinearLayout mSelectedTextEditContainer;
    private TextView doodle_selectable_edit;
    private TextView doodle_selectable_bottom;
    private TextView doodle_selectable_top;
    private TextView doodle_selectable_remove;
    private int approveflag;
    private String rejectedText;


    public static SignatureFragment newInstance(int args) {
        SignatureFragment oneFragment = new SignatureFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("someArgs", args);
        oneFragment.setArguments(bundle);
        return oneFragment;
        //return new SignatureFragment();
    }

    public static SignatureFragment newInstance(String fp) {
        SignatureFragment oneFragment = new SignatureFragment();
        Bundle bundle = new Bundle();
        bundle.putString("data", fp);
        oneFragment.setArguments(bundle);
        return oneFragment;
        //return new SignatureFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_signature;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    int flag;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mView = view;
//        // 步骤1:通过getArgments()获取从Activity传过来的全部值
        Bundle bundle = this.getArguments();
        // 步骤2:获取某一值
        flag = bundle.getInt("someArgs", -1);

        mFrameLayout = (FrameLayout) view.findViewById(R.id.doodle_container);
        startData();
        doodle_btn_back = (MaskImageView) view.findViewById(R.id.doodle_btn_back);  /*返回箭头*/
        doodle_txt_title = (STextView) view.findViewById(R.id.doodle_txt_title);
        doodle_btn_rotate = (MaskImageView) view.findViewById(R.id.doodle_btn_rotate);
        mBtnHidePanel = (MaskImageView) view.findViewById(R.id.doodle_btn_hide_panel);
        doodle_btn_finish = (MaskImageView) view.findViewById(R.id.doodle_btn_finish);


        mSettingsPanel = (RelativeLayout) view.findViewById(R.id.doodle_panel);
        btn_pen_hand = (ImageView) view.findViewById(R.id.btn_pen_hand);
        btn_pen_copy = (ImageView) view.findViewById(R.id.btn_pen_copy);
        btn_pen_eraser = (ImageView) view.findViewById(R.id.btn_pen_eraser);
        btn_pen_text = (ImageView) view.findViewById(R.id.btn_pen_text);
        btn_pen_bitmap = (ImageView) view.findViewById(R.id.btn_pen_bitmap);
        btn_clear = (ImageView) view.findViewById(R.id.btn_clear);
        btn_undo = (ImageView) view.findViewById(R.id.btn_undo);
        btn_zoomer = (ImageView) view.findViewById(R.id.btn_zoomer);


        bar_shape_mode = (LinearLayout) view.findViewById(R.id.bar_shape_mode);
        btn_hand_write = (ImageView) view.findViewById(R.id.btn_hand_write);
        btn_arrow = (ImageView) view.findViewById(R.id.btn_arrow);
        btn_line = (ImageView) view.findViewById(R.id.btn_line);
        btn_holl_circle = (ImageView) view.findViewById(R.id.btn_holl_circle);
        btn_fill_circle = (ImageView) view.findViewById(R.id.btn_fill_circle);
        btn_holl_rect = (ImageView) view.findViewById(R.id.btn_holl_rect);
        btn_fill_rect = (ImageView) view.findViewById(R.id.btn_fill_rect);
        doodle_btn_brush_edit = (ImageView) view.findViewById(R.id.doodle_btn_brush_edit);


        doodle_color_container = (LinearLayout) view.findViewById(R.id.doodle_color_container);
        btn_set_color_container = (FrameLayout) view.findViewById(R.id.btn_set_color_container);
        mBtnColor = (ImageView) view.findViewById(R.id.btn_set_color);
        mEditSizeSeekBar = (SeekBar) view.findViewById(R.id.doodle_seekbar_size);
//        mEditSizeSeekBar.setProgress(3);
//        mEditSizeSeekBar.setMax(5);
        mPaintSizeView = (TextView) view.findViewById(R.id.paint_size_text);
        mPaintSizeView.setText("" + mEditSizeSeekBar.getProgress());
        mSelectedTextEditContainer = (LinearLayout) view.findViewById(R.id.doodle_selectable_edit_container);
        doodle_selectable_edit = (TextView) view.findViewById(R.id.doodle_selectable_edit);
        doodle_selectable_bottom = (TextView) view.findViewById(R.id.doodle_selectable_bottom);
        doodle_selectable_top = (TextView) view.findViewById(R.id.doodle_selectable_top);
        doodle_selectable_remove = (TextView) view.findViewById(R.id.doodle_selectable_remove);
        doodle_selectable_edit.setOnClickListener(this);
        doodle_selectable_bottom.setOnClickListener(this);
        doodle_selectable_top.setOnClickListener(this);
        doodle_selectable_remove.setOnClickListener(this);
        btn_pen_hand.setOnClickListener(this);
        btn_pen_hand.callOnClick();
        btn_pen_eraser.setOnClickListener(this);
        btn_holl_circle.setOnClickListener(this);
        doodle_btn_brush_edit.setOnClickListener(this);
        doodle_btn_finish.setOnClickListener(this);
        btn_arrow.setOnClickListener(this);
        btn_holl_rect.setOnClickListener(this);
        btn_undo.setOnClickListener(this);
        btn_hand_write.setOnClickListener(this);
        doodle_btn_rotate.setOnClickListener(this);
        mBtnHidePanel.setOnClickListener(this);
        btn_set_color_container.setOnClickListener(this);
        btn_undo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!(DoodleParams.getDialogInterceptor() != null
                        && DoodleParams.getDialogInterceptor().onShow(getActivity(), mDoodle, DoodleParams.DialogType.CLEAR_ALL))) {
                    DialogController.showEnterCancelDialog(getActivity(),
                            getString(R.string.doodle_clear_screen), getString(R.string.doodle_cant_undo_after_clearing),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDoodle.clear();
                                }
                            }, null
                    );
                }
                return true;
            }
        });
        mEditSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= 0) {
                    mEditSizeSeekBar.setProgress(0);
                    return;
                }
                if ((int) mDoodle.getSize() == progress) {
                    return;
                }
                mDoodle.setSize(progress);
                if (mTouchGestureListener.getSelectedItem() != null) {
                    mTouchGestureListener.getSelectedItem().setSize(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mDoodleView.setOnTouchListener((v, event) -> {
            // 隐藏设置面板
            if (!mBtnHidePanel.isSelected()  // 设置面板没有被隐藏
                    && mDoodleParams.mChangePanelVisibilityDelay > 0) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        mSettingsPanel.removeCallbacks(mHideDelayRunnable);
                        mSettingsPanel.removeCallbacks(mShowDelayRunnable);
                        //触摸屏幕超过一定时间才判断为需要隐藏设置面板
                        mSettingsPanel.postDelayed(mHideDelayRunnable, mDoodleParams.mChangePanelVisibilityDelay);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mSettingsPanel.removeCallbacks(mHideDelayRunnable);
                        mSettingsPanel.removeCallbacks(mShowDelayRunnable);
                        //离开屏幕超过一定时间才判断为需要显示设置面板
                        mSettingsPanel.postDelayed(mShowDelayRunnable, mDoodleParams.mChangePanelVisibilityDelay);
                        break;
                }
            }

            return false;
        });


        // 长按标题栏显示原图
        doodle_txt_title.setOnTouchListener((v, event) -> {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    v.setPressed(true);
                    mDoodle.setShowOriginal(true);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setPressed(false);
                    mDoodle.setShowOriginal(false);
                    break;
            }
            return true;
        });

        mViewShowAnimation = new AlphaAnimation(0, 1);
        mViewShowAnimation.setDuration(150);
        mViewHideAnimation = new AlphaAnimation(1, 0);
        mViewHideAnimation.setDuration(150);
        mHideDelayRunnable = new Runnable() {
            @Override
            public void run() {
                hideView(mSettingsPanel);
            }

        };
        mShowDelayRunnable = new Runnable() {
            @Override
            public void run() {
                showView(mSettingsPanel);
            }
        };
        btn_pen_hand.post(()->{
            btn_pen_hand.callOnClick();

            btn_pen_hand.callOnClick();
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void save(int flag, String rejectedText) {
        approveflag = flag;
        if (TextUtils.isEmpty(rejectedText)) {
            this.rejectedText = "";
        } else
            this.rejectedText = rejectedText;
        mDoodle.save();
    }

    private void startData() {
        Bundle bundle = this.getArguments();
        // 步骤2:获取某一值
        flag = bundle.getInt("someArgs", -1);

        mDoodleParams = new DoodleParams();
        mDoodleParams.mIsFullScreen = true;
        // 图片路径
//       mDoodleParams.mImagePath = list.get(0);
        // 初始画笔大小
        mDoodleParams.mPaintUnitSize = DoodleView.DEFAULT_SIZE;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), flag);  //获得图片
        Bitmap bitmap = BitmapFactory.decodeFile(bundle.getString("data"));
        mDoodle = mDoodleView = new DoodelViewWrapper(getActivity(), bitmap, new IDoodleListener() {
            @Override
            public void onSaved(IDoodle doodle, Bitmap bitmap, Runnable callback) { // 保存图片为jpg格式
                File doodleFile = null;
                File file = null;
                File savePathInternal = new File(getContext().getFilesDir(), "saved");
                savePathInternal.mkdirs();
                String savePath = /*mDoodleParams.mSavePath*/savePathInternal.getAbsolutePath();  //初试时默认值为null
                boolean isDir = /*mDoodleParams.mSavePathIsDir*/true; //初始时默认值为false
                if (TextUtils.isEmpty(savePath)) {  //如果savePath 是null ,将图片保存在"DCIM/Doodle"目录下
                    File dcimFile = new File(Environment.getExternalStorageDirectory(), "DCIM");
                    doodleFile = new File(dcimFile, "Doodle");
                    //　保存的路径
                    file = new File(doodleFile, System.currentTimeMillis() + ".jpg");
                } else {
                    if (isDir) {
                        doodleFile = new File(savePath);
                        //　保存的路径
                        file = new File(doodleFile, System.currentTimeMillis() + ".jpg");
                    } else {
                        file = new File(savePath);
                        doodleFile = file.getParentFile();
                    }
                }
                doodleFile.mkdirs();

                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream);
                    ImageUtils.addImage(getActivity().getContentResolver(), file.getAbsolutePath());
                    Intent intent = new Intent();
                    intent.putExtra(KEY_IMAGE_PATH, file.getAbsolutePath());
                    Log.e("图片路径", file.getAbsolutePath());
                    urlStr = file.getAbsolutePath();
                    intent.putExtra("flag", approveflag);
                    intent.putExtra("message", rejectedText);

                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    onError(DoodleView.ERROR_SAVE, e.getMessage());
                } finally {
                    Util.closeQuietly(outputStream);
                }
            }

            public void onError(int i, String msg) {
                getActivity().setResult(RESULT_ERROR);
                getActivity().finish();
            }

            @Override
            public void onReady(IDoodle doodle) {
                // mEditSizeSeekBar.setMax(Math.min(mDoodleView.getWidth(), mDoodleView.getHeight()));
//                mEditSizeSeekBar.setMax(3); /*设置画笔的最大宽度*/
                float size = mDoodleParams.mPaintUnitSize > 0 ? mDoodleParams.mPaintUnitSize * mDoodle.getUnitSize() : 0;
                if (size <= 0) {
                    size = mDoodleParams.mPaintPixelSize > 0 ? mDoodleParams.mPaintPixelSize : mDoodle.getSize();
                }
                // 设置初始值
                mDoodle.setSize(size);  /*画笔的初试值*/
                // 选择画笔
                mDoodle.setPen(DoodlePen.BRUSH);  //画笔
                mDoodle.setShape(DoodleShape.HAND_WRITE); //手绘
                if (mDoodleParams.mZoomerScale <= 0) {
                    btn_zoomer.setVisibility(View.GONE);
                }
                mDoodle.setZoomerScale(mDoodleParams.mZoomerScale);

                // 每个画笔的初始值
                mPenSizeMap.put(DoodlePen.BRUSH, mDoodle.getSize());
                mPenSizeMap.put(DoodlePen.COPY, DEFAULT_COPY_SIZE * mDoodle.getUnitSize());
                mPenSizeMap.put(DoodlePen.ERASER, mDoodle.getSize());
                mPenSizeMap.put(DoodlePen.TEXT, DEFAULT_TEXT_SIZE * mDoodle.getUnitSize());
                mPenSizeMap.put(DoodlePen.BITMAP, DEFAULT_BITMAP_SIZE * mDoodle.getUnitSize());
            }
        }, null);

        mTouchGestureListener = new DoodleOnTouchGestureListener(mDoodleView, new DoodleOnTouchGestureListener.ISelectionListener() {
            @Override
            public void onSelectedItem(IDoodle doodle, IDoodleSelectableItem selectableItem, boolean selected) {
                if (selected) {
                    mDoodle.setColor(selectableItem.getColor());
                    mDoodle.setSize(selectableItem.getSize());
                    mEditSizeSeekBar.setProgress((int) selectableItem.getSize());
                    mSelectedTextEditContainer.setVisibility(View.VISIBLE);
                    if (doodle.getPen() == DoodlePen.TEXT || doodle.getPen() == DoodlePen.BITMAP) {
                        doodle_selectable_edit.setVisibility(View.VISIBLE);
                    } else {
                        doodle_selectable_edit.setVisibility(View.GONE);
                    }
                } else {
                    mSelectedTextEditContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCreateSelectableItem(IDoodle doodle, float x, float y) {
                if (mDoodle.getPen() == DoodlePen.TEXT) {
                    createDoodleText(null, x, y);
                } else if (mDoodle.getPen() == DoodlePen.BITMAP) {
                    createDoodleBitmap(null, x, y);
                }
            }
        });

        IDoodleTouchDetector detector = new DoodleTouchDetector(getActivity().getApplicationContext(), mTouchGestureListener);
        mDoodleView.setDefaultTouchDetector(detector);

        mDoodle.setIsDrawableOutside(mDoodleParams.mIsDrawableOutside);

        mFrameLayout.addView(mDoodleView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDoodle.setDoodleMinScale(mDoodleParams.mMinScale);
        mDoodle.setDoodleMaxScale(mDoodleParams.mMaxScale);

        // initViewdata();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    // 添加文字
    private void createDoodleText(final DoodleText doodleText, final float x, final float y) {
        if (getActivity().isFinishing()) {
            return;
        }

        DialogController.showInputTextDialog(getActivity(), doodleText == null ? null : doodleText.getText(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (v.getTag() + "").trim();
                if (TextUtils.isEmpty(text)) {
                    return;
                }
                if (doodleText == null) {
                    IDoodleSelectableItem item = new DoodleText(mDoodle, text, mDoodle.getSize(), mDoodle.getColor().copy(), x, y);
                    mDoodle.addItem(item);
                    mTouchGestureListener.setSelectedItem(item);
                } else {
                    doodleText.setText(text);
                }
                mDoodle.refresh();
            }
        }, null);
        if (doodleText == null) {
            mSettingsPanel.removeCallbacks(mHideDelayRunnable);
        }
    }

    // 添加贴图
    private void createDoodleBitmap(final DoodleBitmap doodleBitmap, final float x, final float y) {
        DialogController.showSelectImageDialog(getActivity(), new ImageSelectorView.ImageSelectorListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onEnter(List<String> pathList) {
                Bitmap bitmap = ImageUtils.createBitmapFromPath(pathList.get(0), mDoodleView.getWidth() / 4, mDoodleView.getHeight() / 4);

                if (doodleBitmap == null) {
                    IDoodleSelectableItem item = new DoodleBitmap(mDoodle, bitmap, mDoodle.getSize(), x, y);
                    mDoodle.addItem(item);
                    mTouchGestureListener.setSelectedItem(item);
                } else {
                    doodleBitmap.setBitmap(bitmap);
                }
                mDoodle.refresh();
            }
        });
    }


    //++++++++++++++++++以下为一些初始化操作和点击监听+++++++++++++++++++++++++++++++++++++++++

    //
    private void initViewdata() {

    }

    private ValueAnimator mRotateAnimator;

    boolean flagClick;

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btn_pen_hand) {
            mEditSizeSeekBar.setProgress(5);
            mEditSizeSeekBar.setMax(5);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.BRUSH);
            mEditSizeSeekBar.setProgress(5);
            mEditSizeSeekBar.setMax(5);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.BRUSH);
            mPaintSizeView.setText("" + mEditSizeSeekBar.getProgress());
            mDoodle.setSize(mEditSizeSeekBar.getProgress());
            if (mTouchGestureListener.getSelectedItem() != null) {
                mTouchGestureListener.getSelectedItem().setSize(mEditSizeSeekBar.getProgress());
            }
        } else if (v.getId() == R.id.btn_pen_copy) {
            mDoodle.setPen(DoodlePen.COPY);
        } else if (v.getId() == R.id.btn_pen_eraser) {
            mEditSizeSeekBar.setProgress(50);
            mEditSizeSeekBar.setMax(100);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.ERASER);
            mEditSizeSeekBar.setProgress(50);
            mEditSizeSeekBar.setMax(100);
            mEditSizeSeekBar.invalidate();
            mDoodle.setPen(DoodlePen.ERASER);
            mDoodle.setSize(mEditSizeSeekBar.getProgress());
            mPaintSizeView.setText("" + mEditSizeSeekBar.getProgress());
            if (mTouchGestureListener.getSelectedItem() != null) {
                mTouchGestureListener.getSelectedItem().setSize(mEditSizeSeekBar.getProgress());
            }

        } else if (v.getId() == R.id.btn_pen_text) {
            mDoodle.setPen(DoodlePen.TEXT);
        } else if (v.getId() == R.id.btn_pen_bitmap) {
            mDoodle.setPen(DoodlePen.BITMAP);
        } else if (v.getId() == R.id.doodle_btn_brush_edit) {
            mDoodleView.setEditMode(!mDoodleView.isEditMode());
        } else if (v.getId() == R.id.btn_undo) {
            mDoodle.undo();
        } else if (v.getId() == R.id.btn_zoomer) {
            mDoodleView.enableZoomer(!mDoodleView.isEnableZoomer());
        } else if (v.getId() == R.id.btn_set_color_container) {
            DoodleColor color = null;
            if (mDoodle.getColor() instanceof DoodleColor) {
                color = (DoodleColor) mDoodle.getColor();
            }
            if (color == null) {
                return;
            }
            if (!(DoodleParams.getDialogInterceptor() != null
                    && DoodleParams.getDialogInterceptor().onShow(getActivity(), mDoodle, DoodleParams.DialogType.COLOR_PICKER))) {
                boolean fullScreen = (getActivity().getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != 0;
                int themeId;
                if (fullScreen) {
                    themeId = android.R.style.Theme_Translucent_NoTitleBar_Fullscreen;
                } else {
                    themeId = android.R.style.Theme_Translucent_NoTitleBar;
                }
                new ColorPickerDialog(getActivity(),
                        new ColorPickerDialog.OnColorChangedListener() {
                            @Override
                            public void colorChanged(int color, int size) {
                                mDoodle.setColor(new DoodleColor(color));
                                mDoodle.setSize(size);
                                if (mTouchGestureListener.getSelectedItem() != null) {
                                    IDoodleColor c = mTouchGestureListener.getSelectedItem().getColor();
                                    if (c instanceof DoodleColor) {
                                        ((DoodleColor) c).setColor(color);
                                    }
                                    mTouchGestureListener.getSelectedItem().setSize(size);
                                }
                                mPaintSizeView.setText("" + size);
                            }

                            @Override
                            public void colorChanged(Drawable color, int size) {
                                Bitmap bitmap = ImageUtils.getBitmapFromDrawable(color);
                                mDoodle.setColor(new DoodleColor(bitmap));
                                mDoodle.setSize(size);
                                if (mTouchGestureListener.getSelectedItem() != null) {
                                    IDoodleColor c = mTouchGestureListener.getSelectedItem().getColor();
                                    if (c instanceof DoodleColor) {
                                        ((DoodleColor) c).setColor(bitmap);
                                    }
                                    mTouchGestureListener.getSelectedItem().setSize(size);
                                }
                                mPaintSizeView.setText("" + size);
                            }
                        }, themeId).show(mDoodleView, mBtnColor.getBackground(), Math.min(mDoodleView.getWidth(), mDoodleView.getHeight()));
            }
        } else if (v.getId() == R.id.doodle_btn_hide_panel) {
            mSettingsPanel.removeCallbacks(mHideDelayRunnable);
            mSettingsPanel.removeCallbacks(mShowDelayRunnable);
            v.setSelected(!v.isSelected());
            if (!mBtnHidePanel.isSelected()) {
                showView(mSettingsPanel);
            } else {
                hideView(mSettingsPanel);
            }
        } else if (v.getId() == R.id.doodle_btn_finish) {
            mDoodle.save();
        } else if (v.getId() == R.id.doodle_btn_back) {
            if (mDoodle.getAllItem() == null || mDoodle.getAllItem().size() == 0) {
                //finish();
                return;
            }
            if (!(DoodleParams.getDialogInterceptor() != null
                    && DoodleParams.getDialogInterceptor().onShow(getActivity(), mDoodle, DoodleParams.DialogType.SAVE))) {
                DialogController.showEnterCancelDialog(getActivity(), getString(cn.hzw.doodle.R.string.doodle_saving_picture), null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDoodle.save();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // finish();
                    }
                });
            }
        } else if (v.getId() == R.id.doodle_btn_rotate) {
            // 旋转图片
            if (mRotateAnimator == null) {
                mRotateAnimator = new ValueAnimator();
                mRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        mDoodle.setDoodleRotation(value);
                    }
                });
                mRotateAnimator.setDuration(250);
            }
            if (mRotateAnimator.isRunning()) {
                return;
            }
            mRotateAnimator.setIntValues(mDoodle.getDoodleRotation(), mDoodle.getDoodleRotation() + 90);
            mRotateAnimator.start();
        } else if (v.getId() == R.id.doodle_selectable_edit) {


            if (mTouchGestureListener.getSelectedItem() instanceof DoodleText) {
                createDoodleText((DoodleText) mTouchGestureListener.getSelectedItem(), -1, -1);
            } else if (mTouchGestureListener.getSelectedItem() instanceof DoodleBitmap) {
                createDoodleBitmap((DoodleBitmap) mTouchGestureListener.getSelectedItem(), -1, -1);
            }


        } else if (v.getId() == R.id.doodle_selectable_remove) {
            mDoodle.removeItem(mTouchGestureListener.getSelectedItem());
            mTouchGestureListener.setSelectedItem(null);
        } else if (v.getId() == R.id.doodle_selectable_top) {
            mDoodle.topItem(mTouchGestureListener.getSelectedItem());
        } else if (v.getId() == R.id.doodle_selectable_bottom) {
            mDoodle.bottomItem(mTouchGestureListener.getSelectedItem());
        } else if (v.getId() == R.id.btn_hand_write) {
            mDoodle.setShape(DoodleShape.HAND_WRITE);
        } else if (v.getId() == R.id.btn_arrow) {
            mDoodle.setShape(DoodleShape.ARROW);
        } else if (v.getId() == R.id.btn_line) {
            mDoodle.setShape(DoodleShape.LINE);
        } else if (v.getId() == R.id.btn_holl_circle) {
            mDoodle.setShape(DoodleShape.HOLLOW_CIRCLE);
        } else if (v.getId() == R.id.btn_fill_circle) {
            mDoodle.setShape(DoodleShape.FILL_CIRCLE);
        } else if (v.getId() == R.id.btn_holl_rect) {
            mDoodle.setShape(DoodleShape.HOLLOW_RECT);
        } else if (v.getId() == R.id.btn_fill_rect) {
            mDoodle.setShape(DoodleShape.FILL_RECT);
        }
    }

//    @Override
//    public void onBackPressed() { // 返回键监听
//        findViewById(R.id.doodle_btn_back).performClick();
//    }

    private void showView(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }

        view.clearAnimation();
        view.startAnimation(mViewShowAnimation);
        view.setVisibility(View.VISIBLE);
    }

    private void hideView(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }
        view.clearAnimation();
        view.startAnimation(mViewHideAnimation);
        view.setVisibility(View.GONE);
    }


    /**
     * 包裹DoodleView，监听相应的设置接口，以改变UI状态
     */
    private class DoodelViewWrapper extends DoodleView {

        public DoodelViewWrapper(Context context, Bitmap bitmap, IDoodleListener listener) {
            super(context, bitmap, listener);
        }

        public DoodelViewWrapper(Context context, Bitmap bitmap, IDoodleListener listener, IDoodleTouchDetector defaultDetector) {
            super(context, bitmap, listener, defaultDetector);
        }

        private Map<IDoodlePen, Integer> mBtnPenIds = new HashMap<>();

        {
            mBtnPenIds.put(DoodlePen.BRUSH, R.id.btn_pen_hand);
            mBtnPenIds.put(DoodlePen.COPY, R.id.btn_pen_copy);
            mBtnPenIds.put(DoodlePen.ERASER, R.id.btn_pen_eraser);
            mBtnPenIds.put(DoodlePen.TEXT, R.id.btn_pen_text);
            mBtnPenIds.put(DoodlePen.BITMAP, R.id.btn_pen_bitmap);
        }

        @Override
        public void setPen(IDoodlePen pen) {
            mPenSizeMap.put(getPen(), getSize()); // save
            super.setPen(pen);
            Float size = mPenSizeMap.get(pen); // restore
            if (size != null) {
                mDoodle.setSize(size);
            }
            mTouchGestureListener.setSelectedItem(null);
            setSingleSelected(mBtnPenIds.values(), mBtnPenIds.get(pen));
            if (pen == DoodlePen.BRUSH) {
                Drawable colorBg = mBtnColor.getBackground();
                if (colorBg instanceof ColorDrawable) {
                    mDoodle.setColor(new DoodleColor(((ColorDrawable) colorBg).getColor()));
                } else {
                    mDoodle.setColor(new DoodleColor(((BitmapDrawable) colorBg).getBitmap()));
                }
            } else if (pen == DoodlePen.COPY) {
                mDoodle.setColor(null);
            } else if (pen == DoodlePen.ERASER) {
                mDoodle.setColor(null);
            } else if (pen == DoodlePen.TEXT) {
                Drawable colorBg = mBtnColor.getBackground();
                if (colorBg instanceof ColorDrawable) {
                    mDoodle.setColor(new DoodleColor(((ColorDrawable) colorBg).getColor()));
                } else {
                    mDoodle.setColor(new DoodleColor(((BitmapDrawable) colorBg).getBitmap()));
                }
            } else if (pen == DoodlePen.BITMAP) {
                Drawable colorBg = mBtnColor.getBackground();
                if (colorBg instanceof ColorDrawable) {
                    mDoodle.setColor(new DoodleColor(((ColorDrawable) colorBg).getColor()));
                } else {
                    mDoodle.setColor(new DoodleColor(((BitmapDrawable) colorBg).getBitmap()));
                }
            }
        }

        private Map<IDoodleShape, Integer> mBtnShapeIds = new HashMap<>();

        {
            mBtnShapeIds.put(DoodleShape.HAND_WRITE, R.id.btn_hand_write);
            mBtnShapeIds.put(DoodleShape.ARROW, R.id.btn_arrow);
            mBtnShapeIds.put(DoodleShape.LINE, R.id.btn_line);
            mBtnShapeIds.put(DoodleShape.HOLLOW_CIRCLE, R.id.btn_holl_circle);
            mBtnShapeIds.put(DoodleShape.FILL_CIRCLE, R.id.btn_fill_circle);
            mBtnShapeIds.put(DoodleShape.HOLLOW_RECT, R.id.btn_holl_rect);
            mBtnShapeIds.put(DoodleShape.FILL_RECT, R.id.btn_fill_rect);

        }

        @Override
        public void setShape(IDoodleShape shape) {
            super.setShape(shape);
            setSingleSelected(mBtnShapeIds.values(), mBtnShapeIds.get(shape));
        }

        //TextView mPaintSizeView = (TextView) view.findViewById(R.id.paint_size_text);

        @Override
        public void setSize(float paintSize) {
            super.setSize(paintSize);
            mEditSizeSeekBar.setProgress((int) paintSize);
            mPaintSizeView.setText("" + (int) paintSize);
        }

        @Override
        public void setColor(IDoodleColor color) {
            if (getPen() == DoodlePen.COPY || getPen() == DoodlePen.ERASER) {
                if ((getColor() instanceof DoodleColor) && ((DoodleColor) getColor()).getBitmap() == mDoodle.getBitmap()) {
                } else {
                    super.setColor(new DoodleColor(mDoodle.getBitmap()));
                }
            } else {
                super.setColor(color);
            }

            DoodleColor doodleColor = null;
            if (color instanceof DoodleColor) {
                doodleColor = (DoodleColor) color;
            }
            if (doodleColor != null) {
                if (doodleColor.getType() == DoodleColor.Type.COLOR) {
                    mBtnColor.setBackgroundColor(doodleColor.getColor());
                } else if (doodleColor.getType() == DoodleColor.Type.BITMAP) {
                    mBtnColor.setBackgroundDrawable(new BitmapDrawable(doodleColor.getBitmap()));
                }
            }
        }

        @Override
        public void enableZoomer(boolean enable) {
            super.enableZoomer(enable);
            btn_zoomer.setSelected(enable);
            if (enable) {
                Toast.makeText(getActivity(), "x" + mDoodleParams.mZoomerScale, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean undo() {
            mTouchGestureListener.setSelectedItem(null);
            return super.undo();
        }

        @Override
        public void clear() {
            super.clear();
            mTouchGestureListener.setSelectedItem(null);
        }

        View mBtnEditMode = doodle_btn_brush_edit;

        @Override
        public void setEditMode(boolean editMode) {
            super.setEditMode(editMode);
            mBtnEditMode.setSelected(editMode);
            if (editMode) {
                Toast.makeText(getActivity(), cn.hzw.doodle.R.string.doodle_edit_mode, Toast.LENGTH_SHORT).show();
            } else {
                mTouchGestureListener.setSelectedItem(null);
            }
        }

        private void setSingleSelected(Collection<Integer> ids, int selectedId) {
            for (int id : ids) {
                if (id == selectedId) {
                    mView.findViewById(id).setSelected(true);
                } else {
                    mView.findViewById(id).setSelected(false);
                }
            }
        }
    }
}