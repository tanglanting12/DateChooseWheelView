package com.eebbk.datechoosewheelviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;

public class MainActivity extends Activity implements View.OnClickListener,
        DialogInterface.OnClickListener {
    private Button mMonthDayWeek_hour_minuteButton;
    private Button mMonthDay_hour_minuteButton;
    private Button mDay_month_dayButton;
    private Button mHour_minuteButton;
    private TextView mShowContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMonthDayWeek_hour_minuteButton = (Button) this.findViewById(R.id.monthDayWeek_hour_minute);
        mMonthDay_hour_minuteButton = (Button) this.findViewById(R.id.monthDay_hour_minute);
        mDay_month_dayButton = (Button) this.findViewById(R.id.day_month_day);
        mHour_minuteButton = (Button) this.findViewById(R.id.hour_minute);

        mShowContentTextView = (TextView) this.findViewById(R.id.show_content_tv);

        mMonthDayWeek_hour_minuteButton.setOnClickListener(this);
        mMonthDay_hour_minuteButton.setOnClickListener(this);
        mDay_month_dayButton.setOnClickListener(this);
        mHour_minuteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

       //     mMonthDayWeek_hour_minuteButton = (Button) this.findViewById(R.id.monthDayWeek_hour_minute);
         //   mMonthDay_hour_minuteButton = (Button) this.findViewById(R.id.monthDay_hour_minute);
           // mDay_month_dayButton = (Button) this.findViewById(R.id.day_month_day);
            //mHour_minuteButton = (Button) this.findViewById(R.id.hour_minute);

            case R.id.monthDayWeek_hour_minute://
                DateChooseWheelViewDialog startDateChooseDialog = new DateChooseWheelViewDialog(MainActivity.this, new DateChooseWheelViewDialog.DateChooseInterface() {
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        mShowContentTextView.setText(time);
                    }
                });
                startDateChooseDialog.playMonthDayWeek_hour_minute();
                startDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.monthDay_hour_minute://
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(MainActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                mShowContentTextView.setText(time);
                            }
                        },false);
                endDateChooseDialog.playMonthDay_hour_minute();
                endDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.day_month_day://身份证有效期
                DateChooseWheelViewDialog DateChooseDialogYearMonthDay = new DateChooseWheelViewDialog(MainActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                mShowContentTextView.setText(time);
                            }
                        },false);
                DateChooseDialogYearMonthDay.playYearmonthday();
                DateChooseDialogYearMonthDay.showDateChooseDialog();

                break;
            case R.id.hour_minute://
                DateChooseWheelViewDialog DateChooseDialogHourMinute = new DateChooseWheelViewDialog(MainActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time, boolean longTimeChecked) {
                                mShowContentTextView.setText(time);
                            }
                        },false);
                DateChooseDialogHourMinute.playHour_minute();
                DateChooseDialogHourMinute.showDateChooseDialog();
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
