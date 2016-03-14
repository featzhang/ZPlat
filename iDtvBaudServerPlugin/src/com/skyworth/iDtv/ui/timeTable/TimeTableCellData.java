package com.skyworth.iDtv.ui.timeTable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.skyworth.iDtv.entity.TimeTableData;
import com.skyworth.iDtv.dao.TimeTableStatue;
import com.skyworth.iDtv.dao.UserStatue;
import com.skyworth.iDtv.entity.UserData;

public class TimeTableCellData implements Serializable {

	private static final long serialVersionUID = -6018631464389586644L;

	private int deviceId;

	private String weekDay;

	private java.util.List<ReserveData> reserveDatas = null;

	public void addTimeTableData(TimeTableData timeTableData) {
		if (reserveDatas == null) {
			reserveDatas = new ArrayList<ReserveData>();
			ReserveData reserveData = new ReserveData();
			reserveData.copy(timeTableData);
			reserveDatas.add(reserveData);
		} else {
			boolean b = contain(timeTableData);
			if (!b) {
				ReserveData reserveData = new ReserveData();
				reserveData.copy(timeTableData);
				reserveDatas.add(reserveData);
			}
		}
	}

	public boolean contain(TimeTableData timeTableData) {
		if (reserveDatas == null || reserveDatas.size() == 0) {
			return false;
		} else {
			for (int i = 0; i < reserveDatas.size(); i++) {
				if (reserveDatas.get(i).getTimeTableDataId() == timeTableData
						.getId()) {
					return true;
				}
			}
		}
		return false;
	}

	public int countOfReserveDatasBelongToUser(UserData user) {
		if (reserveDatas == null) {
			return 0;
		}
		int count = 0;
		for (int i = 0; i < reserveDatas.size(); i++) {
			UserData user2 = reserveDatas.get(i).getUser();
			if (user.equals(user2)) {
				count++;
			}
		}
		return count;
	}

	public int countOfReserverDatas() {
		if (reserveDatas == null) {
			return 0;
		}
		return reserveDatas.size();
	}

	public int countOfReserverDatasAfterNow() {
		if (reserveDatas == null || reserveDatas.size() == 0) {
			return 0;
		}
		long time = new Date().getTime();
		int count = 0;
		for (ReserveData reserveData : reserveDatas) {
			Timestamp endTime = reserveData.getEndTime();
			long time2 = endTime.getTime();
			count += time2 > time ? 1 : 0;
		}
		return count;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public java.util.List<ReserveData> getReserveDatas() {
		return reserveDatas;
	}

	public List<TimeTableData> getRestTimeTableDatas() {
		if (weekDay == null) {
			return null;
		}
		String dateFormatString = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				dateFormatString);
		Date date = new Date();
		try {
			date = simpleDateFormat.parse(weekDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date dayBeginDate = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		Date dayEndDate = calendar.getTime();
		Vector<Vector<Long>> vectors = new Vector<Vector<Long>>();
		{
			Vector<Long> vector = new Vector<Long>();
			vector.add(dayBeginDate.getTime());
			vector.add(dayEndDate.getTime());
			vectors.add(vector);
		}
		if (reserveDatas != null && reserveDatas.size() > 0) {
			for (int i = 0; i < reserveDatas.size(); i++) {
				ReserveData reserveData = reserveDatas.get(i);
				long startTime = reserveData.getStartTime().getTime();
				long endTime = reserveData.getEndTime().getTime();
				for (int j = 0; j < vectors.size(); j++) {
					Vector<Long> vector = vectors.get(j);
					Long vectorStart = vector.get(0);
					Long vectorEnd = vector.get(1);
					if (startTime >= vectorStart && endTime <= vectorEnd + 1000) {
						vectors.remove(j);
						int oprateIndex = j;
						if (startTime > vectorStart) {
							Vector<Long> vector2 = new Vector<Long>();
							vector2.add(vectorStart);
							vector2.add(startTime);
							vectors.add(oprateIndex++, vector2);
						}
						if (endTime < vectorEnd) {
							Vector<Long> vector2 = new Vector<Long>();
							vector2.add(endTime);
							vector2.add(vectorEnd);
							vectors.add(oprateIndex++, vector2);
						}
						break;
					}
				}
			}
		}

		List<TimeTableData> timeTableDatas = new ArrayList<TimeTableData>();
		for (int c = 0; c < vectors.size(); c++) {
			Vector<Long> vector = vectors.get(c);
			Long startTimeLong = vector.get(0);
			Long endTimeLong = vector.get(1);
			TimeTableData timeTableData = new TimeTableData();
			timeTableData.setStartTime(new Timestamp(startTimeLong));
			timeTableData.setEndTime(new Timestamp(endTimeLong));
			timeTableData.setBaudStreamServerId(deviceId);
			timeTableDatas.add(timeTableData);
		}
		return timeTableDatas;
	}

	public List<UserData> getUsers() {
		if (reserveDatas == null || reserveDatas.size() == 0) {
			return null;
		}
		List<UserData> userDatas = new ArrayList<UserData>();
		for (ReserveData reserveData : reserveDatas) {
			UserData user = reserveData.getUser();
			userDatas.add(user);
		}
		if (userDatas.size() == 0) {
			return null;
		}
		return userDatas;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public boolean isTimeFull() {
		if (reserveDatas == null || reserveDatas.size() == 0) {
			return false;
		}
		long time1 = reserveDatas.get(0).getStartTime().getTime();
		long time2 = reserveDatas.get(reserveDatas.size() - 1).getEndTime()
				.getTime();
		if (time2 - time1 + 1000 >= 24 * 3600 * 1000) {
			return true;
		}
		return false;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public List<TimeTableData> toTimeTableData() {
		if (reserveDatas == null || reserveDatas.size() == 0) {
			return null;
		}
		List<TimeTableData> list = new ArrayList<TimeTableData>();
		for (ReserveData reserveData : reserveDatas) {
			int timeTableDataId = reserveData.getTimeTableDataId();
			TimeTableData timeTableData = TimeTableStatue
					.findById(timeTableDataId);
			if (timeTableData == null) {
				TimeTableStatue.updateFromServer();
				timeTableData = TimeTableStatue.findById(timeTableDataId);
				if (timeTableData == null) {
					JOptionPane
							.showMessageDialog(null,
									"find timetabledate by id with update from server error! ");
					return null;
				}
			}
			list.add(timeTableData);
		}
		return list.size() > 0 ? list : null;
	}

	public List<TimeTableData> toTimeTableDataBelongToCurrentUser() {
		if (reserveDatas == null || reserveDatas.size() == 0) {
			return null;
		}
		UserData user = UserStatue.getCurrentUser();
		List<TimeTableData> list = new ArrayList<TimeTableData>();
		for (ReserveData reserveData : reserveDatas) {
			if (!user.equals(reserveData.getUser())) {
				continue;
			}
			int timeTableDataId = reserveData.getTimeTableDataId();
			TimeTableData timeTableData = TimeTableStatue
					.findById(timeTableDataId);
			if (timeTableData == null) {
				TimeTableStatue.updateFromServer();
				timeTableData = TimeTableStatue.findById(timeTableDataId);
				if (timeTableData == null) {
					JOptionPane
							.showMessageDialog(null,
									"find timetable data by id with update from server error! ");
					return null;
				}
			}
			list.add(timeTableData);
		}
		return list.size() > 0 ? list : null;
	}

	public void setReserveDatas(List<ReserveData> reserveDatas) {
		this.reserveDatas = reserveDatas;
	}
}
