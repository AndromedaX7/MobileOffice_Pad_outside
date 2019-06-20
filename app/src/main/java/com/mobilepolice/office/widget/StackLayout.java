package com.mobilepolice.office.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Stack;

public class StackLayout extends FrameLayout {

    Stack<View> viewStack;

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
        viewStack = new Stack<>();
    }

    public void pop() {
        if (getChildCount() > 0) {
            removeViewAt(getChildCount() - 1);
            addView(viewStack.pop());
            top = getChildAt(getChildCount() - 1);
        }
    }

    public void push(View view) {
        if (getChildCount() > 0) {
            viewStack.push(getChildAt(0));
            removeViewAt(0);
        }
        addView(view);
        top = view;
    }

    public void clear() {
        if (getChildCount() > 0) {

            if (viewStack.size() > 0) {
                removeViewAt(getChildCount() - 1);
                addView(viewStack.get(0));
                viewStack.clear();
            }
            top = getChildAt(getChildCount() - 1);
        }
    }
}
