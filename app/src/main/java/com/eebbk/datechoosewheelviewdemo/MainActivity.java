package com.eebbk.datechoosewheelviewdemo;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.DialogInterface;

import com.eebbk.datechoosewheelviewdemo.util.ModeConst;

import java.util.HashMap;

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
                    public void getDateTime(String time) {
                        mShowContentTextView.setText(time);
                    }
                }, ModeConst.DAYMONTH_WEEK_HOUR_MINUTE);
                startDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.monthDay_hour_minute://
                DateChooseWheelViewDialog endDateChooseDialog = new DateChooseWheelViewDialog(MainActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time) {
                                mShowContentTextView.setText(time);
                            }
                        },ModeConst.DAYMONTH_HOUR_MINUTE);
                endDateChooseDialog.showDateChooseDialog();
                break;
            case R.id.day_month_day://
                DateChooseWheelViewDialog DateChooseDialogYearMonthDay = new DateChooseWheelViewDialog(MainActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time) {
                                mShowContentTextView.setText(time);
                            }
                        },ModeConst.YEAR_MONTH_DAY);
                DateChooseDialogYearMonthDay.showDateChooseDialog();

                break;
            case R.id.hour_minute://
                DateChooseWheelViewDialog DateChooseDialogHourMinute = new DateChooseWheelViewDialog(MainActivity.this,
                        new DateChooseWheelViewDialog.DateChooseInterface() {
                            @Override
                            public void getDateTime(String time) {
                                mShowContentTextView.setText(time);
                            }
                        },ModeConst.HOUR_MINUTE);
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
