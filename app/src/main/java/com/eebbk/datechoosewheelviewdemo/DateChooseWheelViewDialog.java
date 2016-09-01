package com.eebbk.datechoosewheelviewdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eebbk.datechoosewheelviewdemo.widget.OnWheelChangedListener;
import com.eebbk.datechoosewheelviewdemo.widget.OnWheelScrollListener;
import com.eebbk.datechoosewheelviewdemo.widget.WheelView;
import com.eebbk.datechoosewheelviewdemo.widget.adapters.AbstractWheelTextAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import  android.media.SoundPool;
import android.media.AudioManager;
/**
 * 使用说明：1.showLongTerm()是否显示长期选项
 * 2.setTimePickerGone隐藏时间选择
 * 3.接口DateChooseInterface
 *
 * 用于时间日期的选择
 * Created by liuhongxia on 2016/4/16.
 */
public class DateChooseWheelViewDialog extends Dialog implements View.OnClickListener {
    //控件
    private WheelView mYearWheelView;
    private WheelView mDateWheelView;
    private WheelView mMonthWheelView;
    private WheelView mDayOnlyWhellView;
    private WheelView mHourWheelView;
    private WheelView mMinuteWheelView;
    private CalendarTextAdapter mDateAdapter;
    private CalendarTextAdapter mHourAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDayOnlyAdapter;
    private CalendarTextAdapter mMinuteAdapter;
    private CalendarTextAdapter mYearAdapter;
    private TextView mTitleTextView;
    private Button mSureButton;
    private Dialog mDialog;
    private Button mCloseDialog;
    private Boolean isHaveSound = true;

    //变量
    private ArrayList<DateObject> arry_date = new ArrayList<DateObject>();
    private ArrayList<DateObject> arry_hour = new ArrayList<DateObject>();
    private ArrayList<DateObject> arry_month = new ArrayList<DateObject>();
    private ArrayList<DateObject> arry_dayonly = new ArrayList<DateObject>();
    private ArrayList<DateObject> arry_minute = new ArrayList<DateObject>();
    private ArrayList<DateObject> arry_year = new ArrayList<DateObject>();

    private int nowDateId = 0;
    private int nowHourId = 0;
    private int nowMinuteId = 0;
    private int nowYearId = 0;
    private int nowMonthId = 0;
    private int nowDayOnlyId = 0;
    private String mYearStr;
    private String mDateStr;
    private String mHourStr;
    private String mMinuteStr;
    private String mMonthStr;
    private String mDayonlyStr;
    private int mYear;
    private int mHour;
    private int mMinute;
    private int mMonth;
    private int mDayonly;
    private boolean mBlnBeLongTerm = false;//是否需要长期
    private boolean mBlnTimePickerGone = false;//时间选择是否显示
    private boolean ifHaveWeek = true;
    //常量
    private final int MAX_TEXT_SIZE = 30;
    private final int MIN_TEXT_SIZE = 20;

    private Context mContext;
    private DateChooseInterface dateChooseInterface;
    private SoundPool sp;
    private int mLockSoundId;
    private  int mLockSoundStreamId;
    private AudioManager mAudioManager;
    private HashMap<Integer, Integer> soundPoolMap;
    private float volume;
    public DateChooseWheelViewDialog(Context context, DateChooseInterface dateChooseInterface) {
        super(context);
        this.mContext = context;
        this.dateChooseInterface = dateChooseInterface;
        mDialog = new Dialog(context,R.style.dialog);
        initView();
        initData();
        initMonth();
        initDayOnly();
    }
    public DateChooseWheelViewDialog(Context context, DateChooseInterface dateChooseInterface,boolean ifHaveWeek){
        super(context);
        this.mContext = context;
        this.dateChooseInterface = dateChooseInterface;
        mDialog = new Dialog(context, R.style.dialog);
        ifHaveWeek = false;
        initView();
        initData();
        initMonth();
        initDayOnly();
    }

    private void initData() {
        initYear();
        initDate();
        initHour();
        initMinute();
        initListener();
    }


   public void setIsHaveSound(boolean flag){
       isHaveSound = flag;
   }
    public boolean getIsHaveSound(){
        return isHaveSound;
    }
    private void playSound(){
        sp.play(soundPoolMap.get(1), volume, volume, 1, 0, 1f);

    }

    /**
     * 初始化滚动监听事件
     */
    private void initListener() {
        //年份*****************************
        mYearWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                playSound();
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mYearAdapter);
                mYearStr = arry_year.get(wheel.getCurrentItem()).getListItem();
                mYear = arry_year.get(wheel.getCurrentItem()).getYear();
                notifyUpdateDayOnly(mYear,mMonth);

            }
        });

        mYearWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mYearAdapter);
            }
        });

        //日月期********************
        mDateWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                playSound();
                String currentText = (String) mDateAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mDateAdapter);
//                mDateCalendarTextView.setText(" " + arry_date.get(wheel.getCurrentItem()));
                mDateStr = arry_date.get(wheel.getCurrentItem()).getListItem();
            }
        });

        mDateWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mDateAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mDateAdapter);
            }
        });

        //月********************
        mMonthWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                playSound();
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMonthAdapter);
//                mDateCalendarTextView.setText(" " + arry_date.get(wheel.getCurrentItem()));
                mMonthStr = arry_month.get(wheel.getCurrentItem()).getMonth()+"";
                mMonth = arry_month.get(wheel.getCurrentItem()).getMonth();
                notifyUpdateDayOnly(mYear,mMonth);
            }
        });

        mMonthWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mMonthAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMonthAdapter);
            }
        });

        //只有天********************
        mDayOnlyWhellView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                playSound();
                String currentText = (String) mDayOnlyAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mDayOnlyAdapter);
//                mDateCalendarTextView.setText(" " + arry_date.get(wheel.getCurrentItem()));
                mDayonly = arry_dayonly.get(wheel.getCurrentItem()).getDay() ;
                mDayonlyStr = arry_dayonly.get(wheel.getCurrentItem()).getDay()+"";
            }
        });

        mDayOnlyWhellView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mDayOnlyAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mDayOnlyAdapter);
            }
        });


        //小时***********************************
        mHourWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                playSound();
                String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mHourAdapter);
                mHourStr = arry_hour.get(wheel.getCurrentItem()).getHour() + "";
            }
        });

        mHourWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mHourAdapter);
            }
        });

        //分钟********************************************
        mMinuteWheelView.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                playSound();
                String currentText = (String) mMinuteAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMinuteAdapter);
                mMinuteStr = arry_minute.get(wheel.getCurrentItem()).getMinute() + "";
            }
        });

        mMinuteWheelView.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mMinuteAdapter.getItemText(wheel.getCurrentItem());
                setTextViewStyle(currentText, mMinuteAdapter);
            }
        });
    }

    /**
     * 初始化分钟
     */
    private void initMinute() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowMinite = nowCalendar.get(Calendar.MINUTE);
        arry_minute.clear();
        for (int i = 0; i <= 59; i++) {
            DateObject temp = new DateObject(i,i,false);
            arry_minute.add(temp);
            if (nowMinite == i){
                nowMinuteId = arry_minute.size() - 1;
            }
        }

        mMinuteAdapter = new CalendarTextAdapter(mContext, arry_minute, nowMinuteId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mMinuteWheelView.setVisibleItems(5);
        mMinuteWheelView.setCyclic(true);
        mMinuteWheelView.setViewAdapter(mMinuteAdapter);
        mMinuteWheelView.setCurrentItem(nowMinuteId);
        mMinuteStr = arry_minute.get(nowMinuteId).getListItem();
        setTextViewStyle(mMinuteStr, mMinuteAdapter);

    }

    /**
     * 初始化小时
     */
    private void initHour() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowHour = nowCalendar.get(Calendar.HOUR_OF_DAY);
        arry_hour.clear();
        for (int i = 0; i <= 23; i++) {
            DateObject temp = new DateObject(i,i,true);
            arry_hour.add(temp);
            if (nowHour == i){
                nowHourId = arry_hour.size() - 1;
            }
        }

        mHourAdapter = new CalendarTextAdapter(mContext, arry_hour, nowHourId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mHourWheelView.setVisibleItems(5);
        mHourWheelView.setCyclic(true);
        mHourWheelView.setViewAdapter(mHourAdapter);
        mHourWheelView.setCurrentItem(nowHourId);
        mHourStr = arry_hour.get(nowHourId).getListItem();
        setTextViewStyle(mHourStr, mHourAdapter);
    }

    /**
     * 初始化年
     */
    private void initYear() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.YEAR);
        arry_year.clear();
        for (int i = 0; i <= 99; i++) {
            int year = nowYear -30 + i;
            DateObject temp = new DateObject(year);
            arry_year.add(temp);
            if (nowYear == year) {
                nowYearId = arry_year.size() - 1;
            }
        }
        mYearAdapter = new CalendarTextAdapter(mContext, arry_year, nowYearId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mYearWheelView.setVisibleItems(5);
        mYearWheelView.setCyclic(true);
        mYearWheelView.setViewAdapter(mYearAdapter);
        mYearWheelView.setCurrentItem(nowYearId);
        mYearStr = arry_year.get(nowYearId).getListItem();
        setTextViewStyle(mYearStr, mYearAdapter);
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_date_choose, null);
        mDialog.setContentView(view);
        mYearWheelView = (WheelView) view.findViewById(R.id.year_wv);
        mDateWheelView = (WheelView) view.findViewById(R.id.date_wv);
        mMonthWheelView = (WheelView) view.findViewById(R.id.month_wv);
        mDayOnlyWhellView = (WheelView) view.findViewById(R.id.dateonly_wv);
        mHourWheelView = (WheelView) view.findViewById(R.id.hour_wv);
        mMinuteWheelView = (WheelView) view.findViewById(R.id.minute_wv);
        mSureButton = (Button) view.findViewById(R.id.sure_btn);
        mCloseDialog = (Button) view.findViewById(R.id.date_choose_close_btn);
        mSureButton.setOnClickListener(this);
        mCloseDialog.setOnClickListener(this);
        if(getIsHaveSound()) {
            initSound();
        }
    }

    private void  initSound(){
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        //初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
        sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(1, sp.load(mContext, R.raw.lock, 1));
        float currentSound=mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxSound=mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume=currentSound/maxSound;
    }
    /**
     * 初始化日期
     */
    private void initDate() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowYear = nowCalendar.get(Calendar.YEAR);
        arry_date.clear();
        setDate(nowYear);
        mDateAdapter = new CalendarTextAdapter(mContext, arry_date, nowDateId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mDateWheelView.setVisibleItems(5);
        mDateWheelView.setCyclic(true);
        mDateWheelView.setViewAdapter(mDateAdapter);
        mDateWheelView.setCurrentItem(nowDateId);

        mDateStr = arry_date.get(nowDateId).getListItem();
        setTextViewStyle(mDateStr, mDateAdapter);
    }
    /**
     * 初始化月份
     */

    private void initMonth() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowMonth = nowCalendar.get(Calendar.MONTH);
        arry_month.clear();
        for (int i = 1; i <= 12; i++) {
            DateObject temp = new DateObject(i,"month");
            arry_month.add(temp);
            if (nowMonth == i){
                nowMonthId = arry_month.size();
            }
        }
        mMonthAdapter = new CalendarTextAdapter(mContext, arry_month, nowMonthId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mMonthWheelView.setVisibleItems(5);
        mMonthWheelView.setCyclic(true);
        mMonthWheelView.setViewAdapter(mMonthAdapter);
        mMonthWheelView.setCurrentItem(nowMonthId);
        mMonthStr = arry_month.get(nowMonthId).getListItem();
        setTextViewStyle(mMonthStr, mMonthAdapter);
    }

    private void notifyUpdateDayOnly(int year,int month){
        arry_dayonly.clear();
        int num = 0;
        num =getDayByMonth(year,month);
        for (int i = 1; i <= num; i++) {
            DateObject temp = new DateObject(i,"day");
            arry_dayonly.add(temp);
        }
        if(arry_dayonly.size()-1<nowDayOnlyId){
            nowDayOnlyId  = arry_dayonly.size()-1;
        }

        mDayOnlyAdapter = new CalendarTextAdapter(mContext, arry_dayonly, nowDayOnlyId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mDayOnlyWhellView.setVisibleItems(5);
        mMonthWheelView.setCyclic(true);
        mDayOnlyWhellView.setViewAdapter(mDayOnlyAdapter);
        mDayOnlyWhellView.setCurrentItem(nowDayOnlyId);


        mDayonlyStr = arry_dayonly.get(nowDayOnlyId).getListItem();
        setTextViewStyle(mDayonlyStr, mDayOnlyAdapter);
    }

    private void initDayOnly() {
        Calendar nowCalendar = Calendar.getInstance();
        int nowMonth = nowCalendar.get(Calendar.MONTH);
        int nowYear = nowCalendar.get(Calendar.YEAR);
        int nowDay = nowCalendar.get(Calendar.DATE);
        arry_dayonly.clear();
        int num = 0;
        num = getDayByMonth(nowYear,nowMonth);
        for (int i = 1; i <= num; i++) {
            DateObject temp = new DateObject(i,"day");
            arry_dayonly.add(temp);
            if (nowDay == i){
                nowDayOnlyId = arry_dayonly.size() - 1;
            }
        }
        mDayOnlyAdapter = new CalendarTextAdapter(mContext, arry_dayonly, nowDayOnlyId, MAX_TEXT_SIZE, MIN_TEXT_SIZE);
        mDayOnlyWhellView.setVisibleItems(5);
        mDayOnlyWhellView.setCyclic(true);
        mDayOnlyWhellView.setViewAdapter(mDayOnlyAdapter);
        mDayOnlyWhellView.setCurrentItem(nowDayOnlyId);
        mDayonlyStr = arry_dayonly.get(nowDayOnlyId).getListItem();
        setTextViewStyle(mDayonlyStr, mDayOnlyAdapter);
    }



    public void setDateDialogTitle(String title) {
        mTitleTextView.setText(title);
    }


    public void playHour_minute(){
        OnlyPlayHourMinute();
    }
    public void playMonthDay_hour_minute(){
        playMonthDayWeek_hour_minute();
    }
    public void playYearmonthday(){
        mDateWheelView.setVisibility(View.GONE);
        mHourWheelView.setVisibility(View.GONE);
        mMinuteWheelView.setVisibility(View.GONE);

    }
    public void  OnlyPlayHourMinute(){
        mYearWheelView.setVisibility(View.GONE);
        mDateWheelView.setVisibility(View.GONE);
        mMonthWheelView.setVisibility(View.GONE);
        mDayOnlyWhellView.setVisibility(View.GONE);

    }
    public void  playMonthDayWeek_hour_minute(){

            mYearWheelView.setVisibility(View.GONE);
            mMonthWheelView.setVisibility(View.GONE);
            mDayOnlyWhellView.setVisibility(View.GONE);
    }







    /**
     * 将改年的所有日期写入数组
     * @param year
     */
    private void setDate(int year){
        boolean isRun = isRunNian(year);
        Calendar nowCalendar = Calendar.getInstance();
        int nowMonth = nowCalendar.get(Calendar.MONTH) + 1;
        int nowDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        for (int month = 1; month <= 12; month++){
            switch (month){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    for (int day = 1; day <= 31; day++){
                        DateObject dateObject = new DateObject(year,month,day,ifHaveWeek);
                        arry_date.add(dateObject);

                        if (month == nowMonth && day == nowDay){
                            nowDateId = arry_date.size() - 1;
                        }
                    }
                    break;
                case 2:
                    if (isRun){
                        for (int day = 1; day <= 29; day++){
                            DateObject dateObject = new DateObject(year,month,day,ifHaveWeek);
                            arry_date.add(dateObject);
                            if (month == nowMonth && day == nowDay){
                                nowDateId = arry_date.size() - 1;
                            }
                        }
                    }else {
                        for (int day = 1; day <= 28; day++){
                            DateObject dateObject = new DateObject(year,month,day,ifHaveWeek);
                            arry_date.add(dateObject);
                            if (month == nowMonth && day == nowDay){
                                nowDateId = arry_date.size() - 1;
                            }
                        }
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    for (int day = 1; day <= 30; day++){
                        DateObject dateObject = new DateObject(year,month,day,ifHaveWeek);
                        arry_date.add(dateObject);
                        if (month == nowMonth && day == nowDay){
                            nowDateId = arry_date.size() - 1;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
    private int  getDayByMonth(int year,int month ){
        int num = 0;
        boolean isRun = isRunNian(year);
            switch (month){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                       num = 31;
                    break;
                case 2:
                    if (isRun){
                       num = 29;
                    }else {
                       num = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    num =30 ;
                    break;
                default:
                    break;
            }
            return num;
    }

    /**
     * 判断是否是闰年
     * @param year
     * @return
     */
    private boolean isRunNian(int year){
        if(year % 4 == 0 && year % 100 !=0 || year % 400 == 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 设置文字的大小
     * @param curriteItemText
     * @param adapter
     */
    public void setTextViewStyle(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(TypedValue.COMPLEX_UNIT_SP,MAX_TEXT_SIZE);
                textvew.setTextColor(mContext.getResources().getColor(R.color.text_10));
            } else {
                textvew.setTextSize(TypedValue.COMPLEX_UNIT_SP,MIN_TEXT_SIZE);
                textvew.setTextColor(mContext.getResources().getColor(R.color.text_11));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure_btn://确定选择按钮监听
                if (mBlnTimePickerGone) {
                    dateChooseInterface.getDateTime(strTimeToDateFormat(mYearStr, mDateStr), mBlnBeLongTerm);
                } else {
                    dateChooseInterface.getDateTime(strTimeToDateFormat(mYearStr, mDateStr , mHourStr , mMinuteStr), mBlnBeLongTerm);
                }
                dismissDialog();
                break;
            case R.id.date_choose_close_btn://关闭日期选择对话框
                dismissDialog();
                break;

            default:
                break;
        }
    }

    /**
     * 对话框消失
     */
    private void dismissDialog() {

        if (Looper.myLooper() != Looper.getMainLooper()) {

            return;
        }

        if (null == mDialog || !mDialog.isShowing() || null == mContext
                || ((Activity) mContext).isFinishing()) {

            return;
        }

        mDialog.dismiss();
        this.dismiss();
    }

    /**
     * 显示日期选择dialog
     */
    public void showDateChooseDialog() {

        if (Looper.myLooper() != Looper.getMainLooper()) {

            return;
        }

        if (null == mContext || ((Activity) mContext).isFinishing()) {

            // 界面已被销毁
            return;
        }

        if (null != mDialog) {

            mDialog.show();
            return;
        }

        if (null == mDialog) {

            return;
        }

        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    /**
     *  xx年xx月xx日xx时xx分转成yyyy-MM-dd HH:mm
     * @param yearStr
     * @param dateStr
     * @param hourStr
     * @param minuteStr
     * @return
     */
    private String strTimeToDateFormat(String yearStr, String dateStr, String hourStr, String minuteStr) {
        return yearStr.replace("年", "-") + dateStr.replace("月", "-").replace("日", " ")
                + hourStr + ":" + minuteStr;
    }

    private String strTimeToDateFormat(String yearStr, String dateStr) {

        return yearStr.replace("年", "-") + dateStr.replace("月", "-").replace("日", "");
    }

    /**
     * 滚轮的adapter
     */
    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<DateObject> list;

        protected CalendarTextAdapter(Context context, ArrayList<DateObject> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, R.id.tempValue, currentItem, maxsize, minsize);
            this.list = list;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            String str = list.get(index).getListItem() + "";
            return str;
        }
    }

    /**
     * 回调选中的时间（默认时间格式"yyyy-MM-dd HH:mm:ss"）
     */
    public interface DateChooseInterface{
        void getDateTime(String time, boolean longTimeChecked);
    }

}
