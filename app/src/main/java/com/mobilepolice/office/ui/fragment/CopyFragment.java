package com.mobilepolice.office.ui.fragment;


import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public class CopyFragment extends MyLazyFragment {

    public static CopyFragment newInstance() {
        return new CopyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_copy;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}