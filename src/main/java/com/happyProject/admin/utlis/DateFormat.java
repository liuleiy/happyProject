package com.happyProject.admin.utlis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateFormat {

	/**
	 * 将long类型的timestamp转为LocalDateTime
	 * 
	 * @param timestamp
	 * @return
	 */
	public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
		Instant instant = Instant.ofEpochMilli(timestamp);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}

	/**
	 * 将LocalDateTime转为自定义的时间格式的字符串
	 * 
	 * @param localDateTime
	 * @param format
	 * @return
	 */
	public static String getDateTimeAsString(LocalDateTime localDateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return localDateTime.format(formatter);
	}

	/**
	 * 将LocalDateTime转为long类型的timestamp
	 * 
	 * @param localDateTime
	 * @return
	 */
	public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return instant.toEpochMilli();
	}

	/**
	 * 将某时间字符串转为自定义时间格式的LocalDateTime
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static LocalDateTime parseStringToDateTime(String time, String format) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(time, df);
	}

	// 转化今天时间的时区
	public static Date setTimeZone(Date date2, String timeFormat) {
		SimpleDateFormat bjSdf = new SimpleDateFormat(timeFormat); // 北京
		bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String f = bjSdf.format(date2);
		Date date = null;
		try {
			date = new SimpleDateFormat(timeFormat).parse(f);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
		String tiemStr = format.format(date);
		SimpleDateFormat format2 = new SimpleDateFormat(timeFormat);
		Date time = null;
		try {
			time = format2.parse(tiemStr);
		} catch (ParseException e) {
			System.out.println("时间格式有问题");
			e.printStackTrace();
		}
		return time;
	}

	public static Date setTime(Date time, String format) {
		if (time == null) {
			return null;
		}
		try {
			SimpleDateFormat bjSdf = new SimpleDateFormat(format); // 北京
			bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			String f = bjSdf.format(time);
			Date date = new SimpleDateFormat(format).parse(f);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date getNextDay(Date date2, Integer number, String fmt, boolean f) {
		try {
			SimpleDateFormat bjSdf = new SimpleDateFormat(fmt); // 北京
			if (f) {
				bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			}
			String format = bjSdf.format(date2);
			Date date = new SimpleDateFormat(fmt).parse(format);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, number);
			date = calendar.getTime();
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到本周周一
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		// System.out.println("s:"+new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime()));
		// new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		/*
		 * SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd"); // 北京
		 * bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); String format =
		 * bjSdf.format(c.getTime());
		 */
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	public static String lastDayOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date strDateTo = calendar.getTime();
		/*
		 * SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd");
		 * bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); String format =
		 * bjSdf.format(strDateTo);
		 */
		return new SimpleDateFormat("yyyy-MM-dd").format(strDateTo);
	}

	public static Integer lastMonthDay() {// 上个月的天数

		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);
		// 输出上月最后一天日期
		int i = cal.get(Calendar.DAY_OF_MONTH);

		return i;
	}

	public static Date getYearMonth(Date date2, Integer number) {
		SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM"); // 北京
		bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		String format = bjSdf.format(date2);
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM").parse(format);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, number);

		date = calendar.getTime();
		return date;

	}

	public static int getMonthNumber(Date time) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(time);
		int a = ca.get(Calendar.DAY_OF_MONTH);
		return a;
	}

	// 小时
	public static Date fmtTimeZone(Date date2, Integer number, String fmt, boolean f) {

		try {
			SimpleDateFormat bjSdf = new SimpleDateFormat(fmt); // 北京
			if (f) {
				bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			}
			String format = bjSdf.format(date2);

			String[] split = format.split(" ");
			int num = 0;
			if (split.length > 1) {

				String h = split[1];
				int parseInt = Integer.parseInt(h);
				if (parseInt >= 12) {
					int cha = parseInt - 12 - 12;
					num = number - cha;
				} else if (parseInt < 12) {
					int cha = 12 - parseInt - 12;
					num = number + cha;
				}

			}

			Date date = new SimpleDateFormat(fmt).parse(format);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR_OF_DAY, num);
			date = calendar.getTime();
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 为搜索
	public static String getfmt(String format, Integer number, String fmt, boolean f) {

		try {

			Date date = new SimpleDateFormat(fmt).parse(format);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR_OF_DAY, number);
			date = calendar.getTime();

			SimpleDateFormat bjSdf = new SimpleDateFormat(fmt); // 北京
			if (f) {
				bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			}
			String formatStr = bjSdf.format(date);

			return formatStr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
