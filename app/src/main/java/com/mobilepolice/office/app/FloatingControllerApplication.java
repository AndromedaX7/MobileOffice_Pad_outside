package com.mobilepolice.office.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyApplication;
import com.mobilepolice.office.ui.activity.ScreenShutActivity;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FloatingControllerApplication extends MyApplication {


    private WindowManager wm;
    private WindowManager.LayoutParams params = new WindowManager.LayoutParams();

    private boolean checked;
    private int req_code = 10001;
    private Activity currentActiveActivity;
    private int appCount = 0;
    private boolean isRunInBackground = true;
    private View view;


    @Override
    public void onCreate() {
        super.onCreate();
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        floatingController();
    }


    private void floatingController() {
        registerActivityLifecycleCallbacks(
                new ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        currentActiveActivity = activity;
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                        appCount++;
                        if (isRunInBackground) {
                            //应用从后台回到前台 需要做的操作
                            back2App(activity);
                            initParam();
                            if (view != null) {
                                addView(view);
                            }
                        }
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                        currentActiveActivity=activity;
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {

                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                        appCount--;
                        if (appCount == 0) {
                            //应用进入后台 需要做的操作
                            leaveApp(activity);
                            remove();
                        }
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                }
        );
    }

    private void leaveApp(Activity activity) {
        isRunInBackground = true;
    }

    private void back2App(Activity activity) {
        isRunInBackground = false;
    }


    private boolean commonROMPermissionCheck(Context context) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class clazz = Settings.class;
                Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                result = (boolean) canDrawOverlays.invoke(null, context);
            } catch (Exception e) {
                Log.e("aaa", Log.getStackTraceString(e));
            }

        }
        return result;
    }

    //申请权限
    private void requestAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:$packageName"));
            currentActiveActivity.startActivityForResult(intent, req_code);
        }
    }

    public void initParam() {
        if (!commonROMPermissionCheck(this)) {
            requestAlertWindowPermission();
        }
        FloatingActionButton fab = (FloatingActionButton) LayoutInflater.from(currentActiveActivity).inflate(R.layout.floating_layout, null);
//        FloatingActionButton fab = new FloatingActionButton(currentActiveActivity);
//        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5187DB")));
//        fab.setCompatElevation(10f);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//            fab.setForegroundGravity(Gravity.CENTER);
//        fab.setImageResource(R.drawable.ic_action_add);
//        GestureDetector.OnGestureListener listener = new GestureDetector.OnGestureListener() {
//            @Override
//            public void onShowPress(MotionEvent e) {
//                Log.e("t1", "1111111");
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent e) {
//                Log.e("t1", "22222");
//                return false;
//            }
//
//            @Override
//            public boolean onDown(MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                return false;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent e) {
//
//            }
//
//            ///http://ccsyc.cn:8789
//
//        };
//
//        GestureDetector detector = new GestureDetector(currentActiveActivity, listener);
//        detector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                Log.e("t2", "1111111");
//                return true;
//            }
//
//            @Override
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                Log.e("t2", "2222222");
//                return true;
//            }
//
//            @Override
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                Log.e("t2", "3333333");
//                return true;
//            }
//        });
//        fab.setOnTouchListener((v, event) -> detector.onTouchEvent(event));

        fab.setOnClickListener(v -> {
            if (currentActiveActivity != null) {
                View decorView = currentActiveActivity.getWindow().getDecorView();
                decorView.setDrawingCacheEnabled(true);
                decorView.buildDrawingCache();
                Bitmap cache = decorView.getDrawingCache();


                String path = "ScreenShut-" + System.currentTimeMillis() + ".png";
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        try {
                            BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(getFileStreamPath(path)));
                            cache.compress(Bitmap.CompressFormat.PNG, 100, buff);
                            buff.flush();
                            buff.close();
                            emitter.onNext(path);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((s) -> this.onSave(s, decorView), this::err, this::onComplete).isDisposed();
            }
        });

        view = fab;
        if (Build.VERSION.SDK_INT >= 26)
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        else
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        params.format = PixelFormat.RGBA_8888;//设置背景图片
        params.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.
                        LayoutParams.FLAG_NOT_FOCUSABLE;//
        params.gravity = Gravity.LEFT | Gravity.TOP;//
        params.x = getResources().getDisplayMetrics().widthPixels - 200;   //设置位置像素
        params.y = getResources().getDisplayMetrics().heightPixels - 300;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT; //设置图片大小
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private void onComplete() {
    }

    private void err(Throwable throwable) {
        view.setDrawingCacheEnabled(false);
        throwable.printStackTrace();
    }

    private void onSave(String path, View view) {
        view.setDrawingCacheEnabled(false);
        Intent screenshut = new Intent(currentActiveActivity, ScreenShutActivity.class);
        screenshut.putExtra("path", path);
        currentActiveActivity.startActivity(screenshut);
    }

    public void addView() {
        addView(view);
    }

    private void addView(View view) {
        this.view = view;
        wm.addView(view, params);
    }

    public void remove() {
        if (view != null)
            remove(view);
    }

    private void remove(View view) {
        wm.removeView(view);
    }

    private void updata(int x, int y, View view) {
        params.x = x;
        params.y = y;
        wm.updateViewLayout(view, params);  //刷新显示
    }
}
