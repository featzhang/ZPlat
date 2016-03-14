package com.skyworth.iDtv.ui.timeTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekDate {
	private Date date;
	private Date weekBeginDate;
	private Date weekEndDate;
	private Date[] weekDates;
	private String[] weekIdentifyCodes;
	private String[] weekYMDDString;

	public WeekDate() {
		super();
		date = new Date();
		doCalculate();
	}

	private String calculateYMDDStringOfDay(Date date) {
		String yearLabel = "\u6708";
		String monthLabel = "\u65e5";
		String formatString = "MM" + yearLabel + "dd" + monthLabel + " E";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
		String format = simpleDateFormat.format(date);
		return format;
	}

	private void doCalculate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int flap = dayOfWeek - 2;
		calendar.add(Calendar.DAY_OF_WEEK, -flap);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MILLISECOND, 1);
		Date time = calendar.getTime();
		weekBeginDate = time;
		// //
		weekDates = new Date[7];
		weekDates[0] = time;
		for (int i = 0; i < 6; i++) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			time = calendar.getTime();
			weekDates[1 + i] = time;
		}
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -2);
		weekDates[6] = calendar.getTime();
		weekEndDate = weekDates[6];
		// //
		weekYMDDString = new String[7];
		weekIdentifyCodes = new String[7];
		for (int i = 0; i < weekDates.length; i++) {
			Date array_element = weekDates[i];
			weekYMDDString[i] = calculateYMDDStringOfDay(array_element);
			weekIdentifyCodes[i] = getDayIdentifyCode(array_element);
		}
	}

	public Date getDate() {
		return date;
	}

	public Date getWeekBeginDate() {
		return weekBeginDate;
	}

	public Date[] getWeekDates() {
		return weekDates;
	}

	public Date getWeekEndDate() {
		return weekEndDate;
	}

	public String[] getWeekIdentifyCodes() {
		return weekIdentifyCodes;
	}

	public String[] getWeekYMDDString() {
		return weekYMDDString;
	}

	public void lastWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		date = calendar.getTime();
		doCalculate();
	}

	public void nextWeek() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		date = calendar.getTime();
		doCalculate();
	}

	public void lastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		date = calendar.getTime();
		doCalculate();
	}

	public void nextMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		date = calendar.getTime();
		doCalculate();
	}

	public void setDate(Date date) {
		this.date = date;
		doCalculate();
	}

	public static String getDayIdentifyCode(Date date) {
		if (date == null) {
			return null;
		}
		String dateFormatString = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				dateFormatString);
		return simpleDateFormat.format(date);
	}
}