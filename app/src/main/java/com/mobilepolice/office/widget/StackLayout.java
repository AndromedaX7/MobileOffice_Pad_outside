package com.mobilepolice.office.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class StackLayout extends FrameLayout {

    private View top;

    public StackLayout(Context context) {
        this(context, null);
    }

    public StackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void pop() {
        if (getChildCount() > 0) {
            removeViewAt(getChildCount() - 1);
            top = getChildAt(getChildCount() - 1);
        }
    }

    public void push(View view) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if(getChildAt(i)==view){
                return;
            }
        }
        addView(view);
        top = view;
    }

}
