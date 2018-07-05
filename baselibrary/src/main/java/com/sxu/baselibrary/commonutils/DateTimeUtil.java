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

	private final static long MILLI_SECOND_OF_DAY = 24 * 60 * 60 * 1000;

	private final static String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};

	/**
	 * 将秒转换成指定格式的日期
	 * @param second
	 * @param format
	 * @return
	 */
	public static String formatDate(long second, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(second * 1000);
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
	 * @param spacer
	 * @return
	 */
	public static String formatDurationDate(Date startDate, Date endDate, String format, String spacer) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return new StringBuilder(sdf.format(startDate))
				.append(spacer)
				.append(sdf.format(endDate))
				.toString();
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
	 * @param spacer
	 * @return
	 */
	public static String formatDurationDate(String startTime, String endTime, String format, String spacer) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date startDate = new Date(ConvertUtil.stringToLong(startTime) * 1000);
		Date endDate = new Date(ConvertUtil.stringToLong(endTime) * 1000);
		return new StringBuilder(sdf.format(startDate))
				.append(spacer)
				.append(sdf.format(endDate))
				.toString();
	}

	/**
	 * 获取指定天的星期描述
	 * @param weekDay
	 * @return
	 */
	public static String getWeekText(int weekDay) {
		if (weekDay < 0 || weekDay >= weeks.length) {
			return "";
		}

		return "周" + weeks[weekDay];
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
		builder.append(" ").append(prefix).append(weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

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
		return getDateAndWeek(ConvertUtil.stringToLong(second) * 1000, pattern, prefix);
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


	//定制订单预约中计时
	public static String getPassedTime(long startTime, long currentTime) {
		long msecPerHour = 60 * 60; // 一小时的秒数
		long msecPerMin = 60; // 一分钟的秒数
		long hour = 0;
		long minute = 0;
		long seconds = 0;
		StringBuffer strPassedTime = new StringBuffer();
		long passedTime = currentTime - startTime;
		if (passedTime > 0) {
			hour = passedTime / msecPerHour;
			minute = (passedTime % msecPerHour) / msecPerMin;
			seconds = (passedTime % msecPerHour % msecPerMin);
			if (hour > 0) {
				if (hour < 10) {
					strPassedTime.append("0");
				}
				strPassedTime.append(hour).append(":");
				if (minute < 10) {
					strPassedTime.append("0");
				}
				strPassedTime.append(minute);
			} else {
				if (minute < 10) {
					strPassedTime.append("0");
				}
				strPassedTime.append(minute).append(":");
				if (seconds < 10) {
					strPassedTime.append("0");
				}
				strPassedTime.append(seconds);
			}
		}

		return strPassedTime.toString();
	}
}
