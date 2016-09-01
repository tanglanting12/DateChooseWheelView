package com.eebbk.datechoosewheelviewdemo.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * 
 * 项目名称：office 类名称：DateTimeUtility
 * 
 * 类描述： 时期时间的工具类
 * 
 * 
 * 创建人：Administrator 创建时间：2015-11-24 下午7:15:33 修改人：Administrator 修改时间：2015-11-24
 * 下午7:15:33 修改备注：
 * 
 * @version
 *
 */
public class DateTimeUtility {

	public static final long MILLISECONDS_PER_DAY = 86400000L;
	public static final long MILLISECONDS_PER_HOUR = 3600000L;
	public static final long MILLISECONDS_PER_MIN = 60000L;
	public static final long MILLISECONDS_PER_SECOND = 1000L;
	private static int _localTimeZoneOffsetInMilliseconds = TimeZone
			.getDefault().getRawOffset();
	private static final String _standardFormat = "yyyy-MM-dd HH:mm:ss";
	private static final String _standardFormat_ms = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 转换日期成日历
	 * 
	 * @param paramDate
	 * @return
	 */
	public static Calendar convertDateToCalendar(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		return localCalendar;
	}

	/**
	 * 转换本地到世界时间
	 * 
	 * @param paramDate
	 * @return
	 */
	public static Date convertLocalToUtc(Date paramDate) {
		return new Date(paramDate.getTime()
				- _localTimeZoneOffsetInMilliseconds);
	}

	/**
	 * 转换字符串成国内日期
	 * 
	 * @param paramString1
	 * @param paramString2
	 * @return
	 */
	private static Date convertStringToDateInternal(String paramString1,
			String paramString2) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				paramString2);
		try {
			Date localDate = localSimpleDateFormat.parse(paramString1);
			return localDate;
		} catch (ParseException localParseException) {
			
		}
		return null;
	}

	/**
	 * 转换国际时间成本地
	 * 
	 * @param paramDate
	 * @return
	 */
	public static Date convertUtcToLocal(Date paramDate) {
		return new Date(paramDate.getTime()
				+ _localTimeZoneOffsetInMilliseconds);
	}

	public static Date covertStringFromUtcStringDataToGmtDate(String paramString) {
		return convertUtcToLocal(covertStringToDate(paramString));
	}

	/**
	 * 转换字符串成日期
	 * 
	 * @param paramString
	 * @return
	 */
	public static Date covertStringToDate(String paramString) {
		if (TextUtils.isEmpty(paramString)) {
			return null;
		}
		return convertStringToDateInternal(paramString, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 转换字符串成日期
	 * 
	 * @param paramString1
	 * @param paramString2
	 * @return
	 */
	public static Date covertStringToDate(String paramString1,
			String paramString2) {
		if (TextUtils.isEmpty(paramString1)) {
			return null;
		}
		return convertStringToDateInternal(paramString1, paramString2);
	}

	public static Date covertStringToDateWithMs(String paramString) {
		int i = paramString.length();
		if ((TextUtils.isEmpty(paramString)) && (i <= 4)) {
			return null;
		}
		if (paramString.charAt(i - 4) == '.') {
			return convertStringToDateInternal(paramString,
					"yyyy-MM-dd HH:mm:ss.SSS");
		}
		return convertStringToDateInternal(paramString, "yyyy-MM-dd HH:mm:ss");
	}

	public static long getDateDiffInDays(Date paramDate1, Date paramDate2) {
		return getDateDiffInMilliSeconds(paramDate1, paramDate2) / 86400000L;
	}

	public static long getDateDiffInHours(Date paramDate1, Date paramDate2) {
		return getDateDiffInMilliSeconds(paramDate1, paramDate2) / 3600000L;
	}

	public static long getDateDiffInMilliSeconds(Date paramDate1,
			Date paramDate2) {
		Calendar localCalendar1 = convertDateToCalendar(paramDate1);
		Calendar localCalendar2 = convertDateToCalendar(paramDate2);
		return localCalendar1.getTimeInMillis()
				- localCalendar2.getTimeInMillis();
	}

	public static long getDateDiffInSeconds(Date paramDate1, Date paramDate2) {
		return getDateDiffInMilliSeconds(paramDate1, paramDate2) / 1000L;
	}

	public static String getDateTimeString(Date paramDate) {
		return getDateTimeString(paramDate, "yyyy-MM-dd HH:mm:ss");
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDateTimeString(Date paramDate, String paramString) {
		return new SimpleDateFormat(paramString).format(paramDate);
	}

	public static String getDateTimeStringWithMs(Date paramDate) {
		return getDateTimeString(paramDate, "yyyy-MM-dd HH:mm:ss.SSS");
	}

	public static int getDaysOfCurrentMonth() {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(5, 1);
		localCalendar.roll(5, -1);
		return localCalendar.get(5);
	}

	public static int getDaysOfYearMonth(int paramInt1, int paramInt2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(1, paramInt1);
		localCalendar.set(2, paramInt2 - 1);
		localCalendar.set(5, 1);
		localCalendar.roll(5, -1);
		return localCalendar.get(5);
	}
}
