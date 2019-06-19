package com.mobilepolice.office.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.flyco.tablayout.SlidingTabLayout;
import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.mobilepolice.office.ui.activity.NotificationNoticeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 项目框架使用示例
 */
public class MainFragmentC extends MyLazyFragment
        implements View.OnClickListener {

//    @BindView(R.id.view_pager)
//    ViewPager view_pager;
//    @BindView(R.id.tab_layout)
//    SlidingTabLayout mSlidingTabLayout;
    private List<String> titles = new ArrayList<>();

    public static MainFragmentC newInstance() {
        return new MainFragmentC();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_c;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    private void fragments() {
//        titles.add("通知公告");
//        titles.add("新闻列表");
//        final ArrayList<MyLazyFragment> fragmentList = new ArrayList<>();
//        fragmentList.add(new NoticeListFragment());
//        fragmentList.add(new NewsListFragment());
//        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
//        view_pager.setAdapter(adapter);
//        view_pager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
//        view_pager.setOffscreenPageLimit(titles.size());
//
//
//        mSlidingTabLayout.setViewPager(view_pager);
//        view_pager.setCurrentItem(0);
//        //  mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(0));
//        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int arg0) {
//                fragmentList.get(arg0);
//                // mScrollLayout.getHelper().setCurrentScrollableContainer(fragmentList.get(arg0));
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//
//            }
//        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<MyLazyFragment> fragmentList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<MyLazyFragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        //return !super.isStatusBarEnabled();
        return false;
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }


    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
//        if (v == mToastView) {
//            ToastUtils.show("我是吐司");
//        }else if (v == mPermissionView) {
//            XXPermissions.with(getSupportActivity())
//                    //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                    //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
//                    .permission(Permission.CAMERA) //不指定权限则自动获取清单中的危险权限
//                    .request(new OnPermission() {
//
//                        @Override
//                        public void hasPermission(List<String> granted, boolean isAll) {
//                            if (isAll) {
//                                ToastUtils.show("获取权限成功");
//                            }else {
//                                ToastUtils.show("获取权限成功，部分权限未正常授予");
//                            }
//                        }
//
//                        @Override
//                        public void noPermission(List<String> denied, boolean quick) {
//                            if(quick) {
//                                ToastUtils.show("被永久拒绝授权，请手动授予权限");
//                                //如果是被永久拒绝就跳转到应用权限系统设置页面
//                                XXPermissions.gotoPermissionSettings(getSupportActivity());
//                            }else {
//                                ToastUtils.show("获取权限失败");
//                            }
//                        }
//                    });
//        }else if (v == mStateBlackView) {
//            UIActivity activity = (UIActivity) getSupportActivity();
//            activity.getStatusBarConfig().statusBarDarkFont(true).init();
//        }else if (v == mStateWhiteView) {
//            UIActivity activity = (UIActivity) getSupportActivity();
//            activity.getStatusBarConfig().statusBarDarkFont(false).init();
//        }else if (v == mSwipeEnabledView) {
//            UIActivity activity = (UIActivity) getSupportActivity();
//            activity.getSwipeBackHelper().setSwipeBackEnable(true);
//            ToastUtils.show("当前界面不会生效，其他界面调用才会有效果");
//        }else if (v == mSwipeDisableView) {
//            UIActivity activity = (UIActivity) getSupportActivity();
//            activity.getSwipeBackHelper().setSwipeBackEnable(false);
//            ToastUtils.show("当前界面不会生效，其他界面调用才会有效果");
//        }
    }
}