package com.mobilepolice.office.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hmy.popwindow.PopItemAction;
import com.hmy.popwindow.PopWindow;
import com.mobilepolice.office.R;

public class BusinessReviewActivity extends Activity {

    private ImageView leftButton;
    private LinearLayout agree;
    private LinearLayout reject;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_review);
        leftButton = findViewById(R.id.leftButton);
        agree = findViewById(R.id.agree);
        reject = findViewById(R.id.reject);

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopWindow();
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
                Toast.makeText(BusinessReviewActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
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



}
