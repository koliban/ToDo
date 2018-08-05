package com.example.chenhaonan.todo;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecordscheduleActivity extends Activity implements
        View.OnClickListener{
    private Button bt_back;
    private Button bt_save;
    private TextView tv_title;
    private SQLiteDatabase db;//数据库操作类
    //  private DatabaseOperation dop;//自定义数据库
    private LineEditText et_Notes;
    private GridView bottomMenu;
    // 底部按钮菜单按钮图片集合
    private int[] bottomItems = {R.drawable.tabbar_handwrite,
            R.drawable.tabbar_paint, R.drawable.tabbar_microphone,
            R.drawable.tabbar_photo, R.drawable.tabbar_camera,
            R.drawable.tabbar_appendix};
    InputMethodManager imm;//控制手机键盘
    Intent intent;
    String editModel = null;
    int item_Id;
    String title;
    String time;
    String context;
    public String datatype = "0";// 判断是否开启记录开启了提醒功能
    public String datatime = "0";// 提醒时间
    private RelativeLayout datarl;
    private TextView datatv;
    private ScrollView sclv;
    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_schedule);
        bt_back = (Button) findViewById(R.id.bt_back);
        bt_back.setOnClickListener(this);
        bt_save = (Button) findViewById(R.id.bt_save);
        bt_save.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        et_Notes = (LineEditText) findViewById(R.id.et_note);
   //     bottomMenu = (GridView) findViewById(R.id.bottomMenu);
   //     datarl = (RelativeLayout) findViewById(R.id.datarl);
   //     datatv = (TextView) findViewById(R.id.datatv);
        sclv = (ScrollView) findViewById(R.id.sclv);

        selectTime = (RelativeLayout) findViewById(R.id.selectTime);
        selectTime.setOnClickListener(this);
        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(this);
        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);

        initDatePicker();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectDate:
                // 日期格式为yyyy-MM-dd
                customDatePicker1.show(currentDate.getText().toString());
                break;
            case R.id.selectTime:
                // 日期格式为yyyy-MM-dd HH:mm
                customDatePicker2.show(currentTime.getText().toString());
                break;
            case R.id.bt_back:
                finish();
                break;
              //  Intent intent = new Intent(RecordscheduleActivity.this, MainActivity.class);
               // startActivity(intent);
            case R.id.bt_save:
                finish();
                break;
              //  Intent intent1 = new Intent(RecordscheduleActivity.this, MainActivity.class);
               // startActivity(intent1);
        }
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);
        currentTime.setText(now);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentTime.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

}
