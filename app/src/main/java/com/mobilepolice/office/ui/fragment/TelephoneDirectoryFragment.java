package com.mobilepolice.office.ui.fragment;


import android.widget.FrameLayout;

import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyLazyFragment;

/**
 * 通讯录
 */
public class TelephoneDirectoryFragment extends MyLazyFragment {

    private FrameLayout sContacts;

    public static TelephoneDirectoryFragment newInstance(){
        return new TelephoneDirectoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tel_directory;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_bar;
    }

    @Override
    protected void initView() {
        sContacts = findViewById(R.id.sContacts);
    }

    @Override
    protected void initData() {
        ContactsView contactsView = new ContactsView(getContext());
        sContacts.addView(contactsView.getView());
    }
}
