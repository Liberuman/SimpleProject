package com.sxu.baselibrary.commonutils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*******************************************************************************
 * Description: 格式化日期
 *
 * Author: Freeman
 *
 * Date: 2018/1/29
 *
 * Copyright: all rights reserved by Freeman.
 *******************************************************************************/

public class DateTimeUtil {

	private DateTimeUtil() {

	}

	/**
	 * 每秒的毫秒数
	 */
	private final static long MILLI_SECOND_OF_SECOND = 1000;
	/**
	 * 每分钟的毫秒数
	 */
	private final static long MILLI_SECOND_OF_MINUTE = 60 * MILLI_SECOND_OF_SECOND;
	/**
	 * 每小时的毫秒数
	 */
	private final static long MILLI_SECOND_OF_HOUR = 60 * MILLI_SECOND_OF_MINUTE;
	/**
	 * 每天的毫秒数
	 */
	private final static long MILLI_SECOND_OF_DAY = 24 * MILLI_SECOND_OF_HOUR;

	/**
	 * 星期的中文描述
	 */
	private final static String[] WEEKS = {"日", "一", "二", "三", "四", "五", "六"};

	/**
	 * 将秒转换成指定格式的日期
	 * @param second
	 * @param format
	 * @return
	 */
	public static String formatDate(long second, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(second * MILLI_SECOND_OF_SECOND);
		return sdf.format(date);
	}

	/**
	 * 将秒转换成指定格式的日期
	 * @param second
	 * @param format
	 * @return
	 */
	public static String formatDate(String second, String format) {
		return formatDate(ConvertUtil.stringToLong(second), format);
	}

	/**
	 * 将日期格式化指定格式
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将时间段格式化指定格式，并已"-"分割
	 * @param startDate
	 * @param endDate
	 * @param format
	 * @return
	 */
	public static String formatDurationDate(Date startDate, Date endDate, String format) {
		return formatDurationDate(startDate, endDate, format, " - ");
	}

	/**
	 * 将时间段格式化成指定格式，并已指定字符进行分隔
	 * @param startDate
	 * @param endDate
	 * @param format
	 * @param separator
	 * @return
	 */
	public static String formatDurationDate(Date startDate, Date endDate, String format, String separator) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(startDate) + separator + sdf.format(endDate);
	}

	/**
	 * 将时间段格式化指定格式，并已"-"分割
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static String formatDurationDate(String startTime, String endTime, String format) {
		return formatDurationDate(startTime, endTime, format, " - ");
	}

	/**
	 * 将时间段格式化指定格式，并以指定字符分割
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @param separator
	 * @return
	 */
	public static String formatDurationDate(String startTime, String endTime, String format, String separator) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date startDate = new Date(ConvertUtil.stringToLong(startTime) * MILLI_SECOND_OF_SECOND);
		Date endDate = new Date(ConvertUtil.stringToLong(endTime) * MILLI_SECOND_OF_SECOND);
		return sdf.format(startDate) + separator + sdf.format(endDate);
	}

	/**
	 * 获取指定天的星期描述
	 * @param weekDay
	 * @return
	 */
	public static String getWeekText(int weekDay) {
		if (weekDay < 0 || weekDay >= WEEKS.length) {
			return "";
		}

		return "周" + WEEKS[weekDay];
	}

	/**
	 * 根据毫秒获取日期和星期
	 * @param millisecond
	 * @param pattern 日期的格式
	 * @param prefix 星期的前缀，如周，星期等
	 * @return
	 */
	public static String getDateAndWeek(long millisecond, String pattern, String prefix) {
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		builder.append(sdf.format(new Date(millisecond)));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(millisecond));
		builder.append(" ").append(prefix).append(WEEKS[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

		return builder.toString();
	}

	/**
	 * 根据毫秒获取日期和星期
	 * @param second
	 * @param pattern 日期的格式
	 * @param prefix  星期的前缀，如周，星期等
	 * @return
	 */
	public static String getDateAndWeek(String second, String pattern, String prefix) {
		return getDateAndWeek(ConvertUtil.stringToLong(second) * MILLI_SECOND_OF_SECOND, pattern, prefix);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDayCount(Date startDate, Date endDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long startMilliSecond = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long endMilliSecond = calendar.getTimeInMillis();
		return (int) ((endMilliSecond - startMilliSecond) / MILLI_SECOND_OF_DAY);
	}


	/**
	 * 获取倒计时的当前时间
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getCountDownTime(long startTime, long endTime) {
		final long countDownTime = endTime > startTime ? endTime - startTime : 0;
		if (countDownTime == 0) {
			return null;
		}

		final String remindTimeFormat = "%02d:%02d:%02d";
		return String.format(remindTimeFormat, countDownTime / MILLI_SECOND_OF_HOUR,
				countDownTime % MILLI_SECOND_OF_HOUR / MILLI_SECOND_OF_MINUTE,
				countDownTime % MILLI_SECOND_OF_MINUTE / MILLI_SECOND_OF_SECOND);
	}
}
