package com.quantum.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;

/**
 * 
 * 
 * @author zzq
 * 
 */
public class TimeUtils {
	static String[] weekDays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
	// String -> Date
	public static Date getDate(String s, String fmt) {
		DateFormat format = new SimpleDateFormat(fmt);
		Date date = null;
		try {
			date = format.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// Date -> String
	public static String getDateStr(Date date, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.format(date).trim();
	}

	public static long getCurrentMil(){
		return new Date().getTime();
	}
	
	//
	public static Date getLastTm(Date date, int delay) {
		Calendar cal = new GregorianCalendar();
		String now = getDateStr(date, "yyyy-MM-dd HH:mm:ss");
		int seconds = Integer.parseInt(now.substring(17));
		cal.setTime(date);
		cal.add(Calendar.MINUTE, -1 * delay);
		cal.add(Calendar.SECOND, -1 * seconds - 1);
		// String lastTm = sdf.format(cal.getTime());
		String lastTm = getDateStr(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
		System.out.println(lastTm + "\t" + now);
		return cal.getTime();
	}

	// 获取星期几
	public static String getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	// 获取星期
	public static String returnWeekDay(int i) {
		String today = getWeekOfDate(new Date());
		String _week = "";
		int index = 0; 
		for (int j = 0; j < weekDays.length; j++) {
			if (weekDays[j].equals(today)) {
				index = j;
				break;
			}

		}

		if (i == 0)
			_week = "今天";
		else {
			if (index + i >= weekDays.length)
				index = Math.abs(index + i - 7);
			else
				index += i;
			_week = weekDays[index];
		}

		return _week;
	}
}
