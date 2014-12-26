package com.zeng.yan.taskmanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtils {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static String getDatePeriod(String date, int model) {
		String result = null;
		switch (model) {
		case 0:// �ܵ���
			result = date + ";" + date;
			break;
		case 1:// �ܵ���
			result = getWeekPeriod(date);
			break;
		case 2:// �µ� ��
			result = getMothPeriod(date);
			break;
		case 3:// �����
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
			int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);// 1Ϊ������

			String dat = dateFormat.format(calendar.getTime());
			String week="";
			switch (day_of_week) {
			case 1:
				week="������";
				break;
			case 2:
				week="����һ";
				break;

			case 3:
				week="���ڶ�";
				break;

			case 4:
				week="������";
				break;

			case 5:
				week="������";
				break;

			case 6:
				week="������";
				break;

			case 7:
				week="������";
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
			throw new NullPointerException("��ȱ�����ڵ���1900��С�ڵ���9999��");
		}
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); // ����ÿ�ܵĵ�һ��Ϊ����һ
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// ÿ�ܴ���һ��ʼ
		// �������������ϣ�����ʵ�֣�ÿ��ȵĵ�һ���ܣ��ǰ�����һ������һ���Ǹ��ܡ�
		cal.setMinimalDaysInFirstWeek(7); // ����ÿ������Ϊ7��
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		return dateFormat.format(cal.getTime());
	}

	/**
	 * ����ָ��ʱ�伸��������
	 * 
	 * @param sDate
	 * @param days
	 * @return
	 */
	public static String calculateEndDate(String date, int days) {
		// ����ʼʱ�丳������ʵ��
		Calendar sCalendar = Calendar.getInstance();
		try {
			sCalendar.setTime(dateFormat.parse(date));

			// �������ʱ��
			sCalendar.add(Calendar.DATE, days);
			// ����Date���ͽ���ʱ��
			return dateFormat.format(sCalendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡָ�����������ܵĿ�ʼʱ�������ʱ��,��;�ָ���ʼʱ�������ʱ��
	 */
	public static String getWeekDate2(String date) {
		Calendar calendar = Calendar.getInstance();
		// calendar.setFirstDayOfWeek(Calendar.MONDAY); //����ÿ�ܵĵ�һ��Ϊ����һ
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);//ÿ�ܴ���һ��ʼ
		// // �������������ϣ�����ʵ�֣�ÿ��ȵĵ�һ���ܣ��ǰ�����һ������һ���Ǹ��ܡ�
		// calendar.setMinimalDaysInFirstWeek(7); //����ÿ������Ϊ7��
		// System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));//
		// ������һ������һ��ĵڼ���
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

}