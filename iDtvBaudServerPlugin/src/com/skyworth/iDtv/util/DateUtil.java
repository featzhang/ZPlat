package com.skyworth.iDtv.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static void main(String[] args) {
		DateUtil dateUtil = new DateUtil();
		// System.out.println(dateUtil.getThisWeekEnd());
	}

	public Date getThisWeekBegin() {
		return calculateWeekBegin(new Date(), 0);
	}

	private Date calculateWeekBegin(Date date, int dayOffSet) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int flap = dayOfWeek - 2;
		calendar.add(Calendar.DAY_OF_WEEK, -flap + dayOffSet);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MILLISECOND, 1);
		Date time = calendar.getTime();
		return time;
	}

	private Date getWeekEnd(Date date, int dayOffSet) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int flap = dayOfWeek - 2;
		calendar.add(Calendar.DAY_OF_WEEK, -flap + 7 + dayOffSet);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MILLISECOND, -1);
		Date time = calendar.getTime();
		return time;
	}

	public Date getThisWeekEnd() {
		return getWeekEnd(new Date(), 0);
	}

	public Date[] getThisWeekDates() {
		// calculate first day of this week
		Date[] code = new Date[7];
		Date thisWeekBegin = getThisWeekBegin();
		code[0] = thisWeekBegin;
		Date date = new Date(thisWeekBegin.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// every day
		for (int i = 0; i < 6; i++) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date time = calendar.getTime();
			code[1 + i] = new Timestamp(time.getTime());
		}
		return code;
	}

}
