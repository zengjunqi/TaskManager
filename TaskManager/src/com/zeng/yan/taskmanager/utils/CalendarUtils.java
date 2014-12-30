package com.zeng.yan.taskmanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static String getDatePeriod(String date, int model) {
		String result = null;
		switch (model) {
		case 0:// 周调用
			result = date + ";" + date;
			break;
		case 1:// 周调用
			result = getWeekPeriod(date);
			break;
		case 2:// 月调 用
			result = getMothPeriod(date);
			break;
		case 3:// 年调用
			result = getYearPeriod(date);
			break;

		default:
			break;
		}

		return result;
	}

	private static String getYearPeriod(String date) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		try {

			calendar.setTime(dateFormat.parse(date));
			int year = calendar.get(Calendar.YEAR);
			calendar.clear();
			calendar.set(Calendar.YEAR, year);
			String startDate = dateFormat.format(calendar.getTime());
			calendar.roll(Calendar.DAY_OF_YEAR, -1);
			String endDate = dateFormat.format(calendar.getTime());
			String result = startDate + ";" + endDate;
			return result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static String getMothPeriod(String date) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			String startDate = year + "-" + month + "-01";
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			String endDate = dateFormat.format(calendar.getTime());
			String result = startDate + ";" + endDate;
			return result;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private static String getWeekPeriod(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			/*
			 * int curWeek = calendar.get(Calendar.WEEK_OF_YEAR); String endDate
			 * = getYearWeekEndDay(calendar.get(Calendar.YEAR), curWeek - 1); +
			 * calendar.get(Calendar.WEEK_OF_YEAR)); String startDate =
			 * calculateEndDate(endDate, -6);
			 * System.out.println("date:"+date+"curWeek:"
			 * +curWeek+"**startDate"+startDate+"**endate:"+endDate); String
			 * result = startDate + ";" + endDate;
			 */

			int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
			// System.out.println(calendar.get(Calendar.DAY_OF_WEEK)+"==");
			calendar.add(Calendar.DATE, -day_of_week);
			String startDate = dateFormat.format(calendar.getTime());
			calendar.add(Calendar.DATE, 6);
			String endDate = dateFormat.format(calendar.getTime());
			String result = startDate + ";" + endDate;
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getDateAndWeek(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(dateFormat.parse(date));
			int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);// 1为星期天

			String dat = dateFormat.format(calendar.getTime());
			String week="";
			switch (day_of_week) {
			case 1:
				week="星期日";
				break;
			case 2:
				week="星期一";
				break;

			case 3:
				week="星期二";
				break;

			case 4:
				week="星期三";
				break;

			case 5:
				week="星期四";
				break;

			case 6:
				week="星期五";
				break;

			case 7:
				week="星期六";
				break;

			default:
				break;
			}
			return dat+" "+week;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String getYearWeekEndDay(int yearNum, int weekNum) {
		if (yearNum < 1900 || yearNum > 9999) {
			throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
		}
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置每周的第一天为星期一
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 每周从周一开始
		// 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
		cal.setMinimalDaysInFirstWeek(7); // 设置每周最少为7天
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		return dateFormat.format(cal.getTime());
	}

	/**
	 * 计算指定时间几天后的日期
	 * 
	 * @param sDate
	 * @param days
	 * @return
	 */
	public static String calculateEndDate(String date, int days) {
		// 将开始时间赋给日历实例
		Calendar sCalendar = Calendar.getInstance();
		try {
			sCalendar.setTime(dateFormat.parse(date));

			// 计算结束时间
			sCalendar.add(Calendar.DATE, days);
			// 返回Date类型结束时间
			return dateFormat.format(sCalendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	 public static int getWeekOfYear(String date) {
		  Calendar calendar = Calendar.getInstance();
		  try {
		   calendar.setTime(dateFormat.parse(date));
		  int week=calendar.get(Calendar.WEEK_OF_YEAR);
		   return week;
		  } catch (java.text.ParseException e) {
		   e.printStackTrace();
		  }
		  return 0;
	 }

		public static String getDateFormate(String date, boolean needYear) {
			String[] foString = date.split("-");
			String dateString;
			if (needYear) {

				dateString = foString[0] + "年" + foString[1] + "月" + foString[2]
						+ "日";
			} else {

				dateString = foString[1] + "月" + foString[2] + "日";
			}
			return dateString;
		}


	/**
	 * 获取指定日期所在周的开始时间与结束时间,以;分隔开始时间与结束时间
	 */
	/*public static String getWeekDate2(String date) {
		Calendar calendar = Calendar.getInstance();
		// calendar.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//每周从周一开始
		// // 上面两句代码配合，才能实现，每年度的第一个周，是包含第一个星期一的那个周。
		// calendar.setMinimalDaysInFirstWeek(7); //设置每周最少为7天
		// System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));//
		// 返回这一周是这一年的第几周
		try {
			StringBuffer sBuffer = new StringBuffer();
			calendar.setTime(dateFormat.parse(date));

			int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
			// System.out.println(calendar.get(Calendar.DAY_OF_WEEK)+"==");
			calendar.add(Calendar.DATE, -day_of_week);
			sBuffer.append(dateFormat.format(calendar.getTime()));
			calendar.add(Calendar.DATE, 6);
			sBuffer.append(";" + dateFormat.format(calendar.getTime()));
			System.out.println("day_of_week:" + day_of_week + "==="
					+ sBuffer.toString() + "==="
					+ calendar.get(Calendar.WEEK_OF_YEAR));
			return sBuffer.toString();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
*/
}
