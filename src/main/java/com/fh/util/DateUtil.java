package com.fh.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 说明：日期处理 创建人：WZX Q149156999 修改时间：2015年11月24日
 * 
 * @version
 */
public class DateUtil {

	public final static String sdfYear = "yyyy";
	public final static String sdfDays = "yyyyMMdd";
	public final static String sdfTime = "yyyyMMddHHmmss";

	public final static String DATE_FORMATE = "yyyy-MM-dd";
	public final static String DATEMONTH_FORMATE = "yyyy-MM";
	public final static String DATETIME_FORMATE = "yyyy-MM-dd HH:mm:ss";
	public final static String DATETIME_SHOR_FORMATE = "yyyy-MM-dd HH:mm";
	public final static String TIME_FORMATE = "HH:mm:ss";
	public final static String YW_FORMATE_DATE_CN = "yyyy年MM月dd日";

	/**
	 * 
	 * 获取yyyyMMddHHmmss格式
	 * 
	 * @return
	 */
	public static String getSdfAllDayTime() {
		DateFormat df = new SimpleDateFormat(DATETIME_FORMATE);
		return df.format(new Date());
	}

	/**
	 * 
	 * 获取yyyyMMddHHmmss格式
	 * 
	 * @return
	 */
	public static String getSdfTimes() {
		DateFormat df = new SimpleDateFormat(sdfTime);
		return df.format(new Date());
	}

	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		DateFormat df = new SimpleDateFormat(sdfYear);
		return df.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		DateFormat df = new SimpleDateFormat(DATE_FORMATE);
		return df.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays() {
		DateFormat df = new SimpleDateFormat(sdfDays);
		return df.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		DateFormat df = new SimpleDateFormat(sdfTime);
		return df.format(new Date());
	}

	/**
	 * @title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws @author
	 *             fh
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	/**
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// long aa=0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 得到n天之后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdfd.format(date);

		return dateStr;
	}

	/**
	 * 得到n天之后是周几
	 * 
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * date 转字符串
	 * 
	 * @param date
	 * @param formatType
	 *            ("yyyy-MM-dd HH:mm:ss")
	 * @return
	 */
	public final static String dateToString(Date date, String formatType) {
		if (date == null) {
			return "";
		}
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat(formatType, Locale.CHINA);
		dateFormat.setLenient(false);
		return dateFormat.format(date);
	}

	/**
	 * 获取当天日期字符串 格式为：yyyyMMdd
	 * 
	 * @return 当天日期字符串
	 * @see getCurrDate(Date currDate)
	 */
	public static String getCurrDate() {
		Date currDate = new Date();

		DateFormat df = new SimpleDateFormat(DateUtil.DATETIME_FORMATE);
		String strCurrDate = df.format(currDate);

		return strCurrDate;
	}

	/**
	 * 获取当前系统日期
	 * 
	 * @param formate
	 * @return
	 */
	public static String getCurrDate(String formate) {
		Date currDate = new Date();
		DateFormat df = new SimpleDateFormat(formate);
		String strCurrDate = df.format(currDate);
		return strCurrDate;
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (!StringUtil.isEmptyStr(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date, String format) {
		if (!StringUtil.isEmptyStr(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 把时间根据时、分、秒转换为时间段
	 * 
	 * @param StrDate
	 */
	public static String getTimes(String StrDate) {
		String resultTimes = "";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date now;

		try {
			now = new Date();
			java.util.Date date = df.parse(StrDate);
			long times = now.getTime() - date.getTime();
			long day = times / (24 * 60 * 60 * 1000);
			long hour = (times / (60 * 60 * 1000) - day * 24);
			long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

			StringBuffer sb = new StringBuffer();
			// sb.append("发表于：");
			if (hour > 0) {
				sb.append(hour + "小时前");
			} else if (min > 0) {
				sb.append(min + "分钟前");
			} else {
				sb.append(sec + "秒前");
			}

			resultTimes = sb.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultTimes;
	}

	/**
	 * 两个时间相差几天 几分钟 几小时，几秒
	 * 
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @param str
	 * @return
	 */
	public static Long dateDiff(String startTime, String endTime, String format, String str) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0;
		long hour = 0;
		long hourSum = 0;
		long min = 0;
		long minSum = 0;
		long sec = 0;
		long secSum = 0;

		// 获得两个时间的毫秒时间差异
		try {
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh + day * 24;// 计算差多少小时
			hourSum = diff / nh + day * 24;// 计算差多少小时
			min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
			minSum = diff / nm + day * 24 * 60;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			secSum = diff / ns;// 计算差多少分钟
			// 输出结果
			System.out.println(
					"时间相差：" + day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟" + sec + "秒。");
			System.out.println("hour=" + hour + ",min=" + min);
			if (str.equalsIgnoreCase("day")) {
				return day;
			} else if (str.equalsIgnoreCase("h")) {
				return hourSum;
			} else if (str.equalsIgnoreCase("m")) {
				return minSum;
			} else if (str.equalsIgnoreCase("s")) {
				return secSum;
			} else {
				return min;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str.equalsIgnoreCase("day")) {
			return day;
		} else if (str.equalsIgnoreCase("h")) {
			return hour;
		} else if (str.equalsIgnoreCase("m")) {
			return min;
		} else if (str.equalsIgnoreCase("s")) {
			return sec;
		} else {
			return min;
		}
	}

	/**
	 * 获取当前时间的前后时间
	 * 
	 * @param format
	 *            y 年份的前后时间<br>
	 *            m 月份的前后时间<br>
	 *            d 天数的前后时间<br>
	 *            h 年份的前后时间<br>
	 *            min 年份的前后时间<br>
	 *            s 年份的前后时间<br>
	 * @param str
	 *            当前时间之前传负数，当前时间之后传正数
	 * @return
	 */
	public static String getCurrentAround(String format, int str) {
		Calendar calendar = Calendar.getInstance();
		switch (format) {
		case "y":
			calendar.add(Calendar.YEAR, str); // 得到前一天
			break;
		case "m":
			calendar.add(Calendar.MONTH, str); // 得到前一个月
			break;
		case "d":
			calendar.add(Calendar.DATE, str); // 得到前一天
			break;
		case "h":
			calendar.add(Calendar.HOUR, str); // 得到前一个月
			break;
		case "min":
			calendar.add(Calendar.MINUTE, str); // 得到前一个月
			break;
		case "s":
			calendar.add(Calendar.SECOND, str); // 得到前一个月
			break;
		default:
			calendar.add(Calendar.SECOND, str); // 得到前一个月
			break;
		}
		return DateUtil.calendarToString(calendar, DateUtil.DATETIME_FORMATE);
	}

	/**
	 * Calendar 转字符串
	 * 
	 * @param calendar
	 * @param formatType
	 * @return
	 */
	public final static String calendarToString(java.util.Calendar calendar, String formatType) {
		if (calendar == null) {
			return "";
		}
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat(formatType, Locale.CHINA);
		dateFormat.setLenient(false);
		Date date = calendar.getTime();
		return dateToString(date, formatType);
	}

	public static void main(String[] args) {
		Long l = dateDiff("2016-11-21", "2016-11-20", DateUtil.DATE_FORMATE, "day");
		System.out.println(l);

		System.out.println(new Date().after(str2Date("2016-12-20 13:00:00")));
		// System.out.println(getDays());
		// System.out.println(getAfterDayWeek("3"));

	}

	public static boolean isInDate(Date date, Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		if (date.before(beginDate)) {
			return false;
		}
		if (date.after(endDate)) {
			return false;
		}
		return true;
	}

	/**
	 * 获取月份的天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthDay(int year, int month) {
		int days = 0; // 存储当月的天数
		boolean isRn;
		/* 判断是否是闰年 */
		if (year % 4 == 0 && !(year % 100 == 0) || year % 400 == 0) { // 判断是否为闰年
			isRn = true; // 闰年
		} else {
			isRn = false;// 平年
		}
		/* 计算当月的天数 */
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 2:
			if (isRn) {
				days = 29;
			} else {
				days = 28;
			}
			break;
		default:
			days = 30;
			break;
		}
		return days;
	}

	// 日期加一个分钟后的日期代码
	public static String addDateMinut(String day, int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		System.out.println("front:" + format.format(date));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, x);// 24小时制
		date = cal.getTime();
		System.out.println("after:" + format.format(date));
		cal = null;
		return format.format(date);
	}

}
