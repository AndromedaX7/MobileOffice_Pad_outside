package com.mobilepolice.office.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilepolice.office.R;
import com.mobilepolice.office.base.MyActivity;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnMultiChooseListener;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;
import com.tencent.smtt.sdk.WebView;

import org.litepal.tablemanager.Connector;

import java.util.HashMap;

import butterknife.BindView;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public class ScheduleMainActivity extends MyActivity implements View.OnClickListener {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.lastMonth)
    ImageView lastMonth;
    @BindView(R.id.nextMonth)
    ImageView nextMonth;

    @BindView(R.id.calendar)
    CalendarView calendarView;
    private int[] cDate = CalendarUtil.getCurrentDate();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schedule_main;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_copy_title;
    }

    @Override
    protected void initView() {
        //  创建数据库
        SQLiteDatabase db = Connector.getDatabase();
        Connector.getDatabase();

        HashMap<String, String> map = new HashMap<>();
        map.put("2017.10.30", "qaz");
        map.put("2017.10.1", "wsx");
        map.put("2017.11.12", "yhn");
        map.put("2017.9.15", "edc");
        map.put("2017.11.6", "rfv");
        map.put("2017.11.11", "tgb");
        calendarView
//                .setSpecifyMap(map)
                .setStartEndDate("1949.1", "2028.12")
                .setDisableStartEndDate("1997.1.1", "2027.12.31")
                .setInitDate(cDate[0] + "." + cDate[1])
                .setSingleDate(cDate[0] + "." + cDate[1] + "." + cDate[2])
                .init();


        title.setText(cDate[0] + "年" + cDate[1] + "月");
        // chooseDate.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");

        calendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                title.setText(date[0] + "年" + date[1] + "月");
            }
        });

        calendarView.setOnSingleChooseListener(new OnSingleChooseListener() {
            @Override
            public void onSingleChoose(View view, DateBean date) {
                title.setText(date.getSolar()[0] + "年" + date.getSolar()[1] + "月");
                if (date.getType() == 1) {
                    //chooseDate.setText("当前选中的日期：" + date.getSolar()[0] + "年" + date.getSolar()[1] + "月" + date.getSolar()[2] + "日");
                }
            }
        });


        //  点击按钮添加日程事项
//        add_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddItems.class);
//                DateBean date = calendarView.getSingleDate();
//                intent.putExtra("now_Year", String.valueOf(date.getSolar()[0]));
//                intent.putExtra("now_Month", String.valueOf(date.getSolar()[1]));
//                //  int ttt = date.getSolar()[1];
//                intent.putExtra("now_Day", String.valueOf(date.getSolar()[2]));
//                startActivity(intent);
//            }
//        });
    }


    //  选择某天
//    public void someday(View v) {
//        View view = LayoutInflater.from(ScheduleMainActivity.this).inflate(R.layout.input_layout, null);
//        final EditText year = (EditText) view.findViewById(R.id.year);
//        final EditText month = (EditText) view.findViewById(R.id.month);
//        final EditText day = (EditText) view.findViewById(R.id.day);
//
//        new AlertDialog.Builder(this)
//                .setView(view)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (TextUtils.isEmpty(year.getText())
//                                || TextUtils.isEmpty(month.getText())
//                                || TextUtils.isEmpty(day.getText())) {
//                            Toast.makeText(ScheduleMainActivity.this, "请完善日期！", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        boolean result = calendarView.toSpecifyDate(Integer.valueOf(year.getText().toString()),
//                                Integer.valueOf(month.getText().toString()),
//                                Integer.valueOf(day.getText().toString()));
//                        if (!result) {
//                            Toast.makeText(ScheduleMainActivity.this, "日期越界！", Toast.LENGTH_SHORT).show();
//                        } else {
//                           // chooseDate.setText("当前选中的日期：" + year.getText() + "年" + month.getText() + "月" + day.getText() + "日");
//                        }
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("取消", null).show();
//    }

    //  选择今天
    public void today(View view) {
        calendarView.today();
        // chooseDate.setText("当前选中的日期：" + cDate[0] + "年" + cDate[1] + "月" + cDate[2] + "日");
    }

    public void lastMonth(View view) {

    }

    public void nextMonth(View view) {

    }

    public void start(View view) {
        calendarView.toStart();
    }

    public void end(View view) {
        calendarView.toEnd();
    }

    public void lastYear(View view) {
        calendarView.lastYear();
    }

    public void nextYear(View view) {
        calendarView.nextYear();
    }

    public void multiChoose(View view) {
        // startActivity(new Intent(ScheduleMainActivity.this, MultiChooseActivity.class));
    }

    @Override
    protected void initData() {
        lastMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
        calendarView.setOnMultiChooseListener(new OnMultiChooseListener() {
            @Override
            public void onMultiChoose(View view, DateBean date, boolean flag) {
                String d = date.getSolar()[0] + "." + date.getSolar()[1] + "." + date.getSolar()[2] + ".";
              StringBuffer sb=new StringBuffer();
                if (flag) {//选中
                    sb.append("选中：" + d + "\n");
                } else {//取消选中
                    sb.append("取消：" + d + "\n");
                }
               // chooseDate.setText(sb.toString());

                //test
                if (flag) {
                    for (DateBean db : calendarView.getMultiDate()) {
                        Log.e("date:", "" + db.getSolar()[0] + db.getSolar()[1] + db.getSolar()[2]);
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        if (lastMonth == view) {
            calendarView.lastMonth();
        } else if (nextMonth == view) {
            calendarView.nextMonth();
        }
    }
}
