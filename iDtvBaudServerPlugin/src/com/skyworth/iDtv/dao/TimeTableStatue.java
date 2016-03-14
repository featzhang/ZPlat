package com.skyworth.iDtv.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skyworth.iDtv.entity.TimeTableData;
import com.skyworth.iDtv.net.ClientRequest;
import com.skyworth.iDtv.ui.timeTable.ReserveData;
import com.skyworth.iDtv.ui.timeTable.TimeTableCellData;
import com.skyworth.iDtv.ui.timeTable.WeekDate;

public class TimeTableStatue implements Serializable {
	private static final long serialVersionUID = 5591830400843466248L;
	private static List<TimeTableData> timeTableDatas = null;

	public static Map<Integer, Map<String, TimeTableCellData>> updateFromServer() {
		timeTableDatas = new ClientRequest().getAllTimeTableDatas();
		transfer2LocalData();
		return map;
	}

	public static Map<Integer, Map<String, TimeTableCellData>> getTimeTableDatas() {
		if (timeTableDatas == null) {
			updateFromServer();
		}
		return map;
	}

	private static Map<Integer, Map<String, TimeTableCellData>> map = null;

	public static void transfer2LocalData() {
		if (timeTableDatas != null) {
			if (map == null) {
				map = new HashMap<Integer, Map<String, TimeTableCellData>>();
			}
			for (TimeTableData timeTableData : timeTableDatas) {
				int devId = timeTableData.getBaudStreamServerId();
				
				Timestamp startTime = timeTableData.getStartTime();
				if (!map.containsKey(devId)) {
					HashMap<String, TimeTableCellData> hashMap = new HashMap<String, TimeTableCellData>();
					map.put(devId, hashMap);
				}
				Map<String, TimeTableCellData> devMap = map.get(devId);
				String weekDay = WeekDate.getDayIdentifyCode(startTime);
				if (!devMap.containsKey(weekDay)) {
					TimeTableCellData timeTableCellData = new TimeTableCellData();
					timeTableCellData.addTimeTableData(timeTableData);
					timeTableCellData.setWeekDay(weekDay);
					timeTableCellData.setDeviceId(devId);
					devMap.put(weekDay, timeTableCellData);
				} else {
					TimeTableCellData timeTableCellData = devMap.get(weekDay);
					timeTableCellData.addTimeTableData(timeTableData);
					// TODO 是否需要 重新put到map ?
				}
			}
		}
	}

	private static WeekDate weekDate = new WeekDate();

	public static WeekDate getWeekDate() {
		return weekDate;
	}

	public static void addTimeTableDatasToServer(List<TimeTableData> datas) {
		new ClientRequest().addTimeTableDatas(datas);
	}

	public static Map<Integer, Map<String, TimeTableCellData>> addTimeTableDatas(
			List<TimeTableData> datas) {
		if (datas != null && datas.size() > 0) {
			for (TimeTableData timeTableData : datas) {
				addTimeTableData(timeTableData);
			}
		}
		return map;
	}

	public static void addTimeTableData(TimeTableData timeTableData) {
		int baudStreamServerId = timeTableData.getBaudStreamServerId();
		Timestamp startTime = timeTableData.getStartTime();
		if (map == null) {
			map = new HashMap<Integer, Map<String, TimeTableCellData>>();
		}
		Map<String, TimeTableCellData> map2 = map.get(baudStreamServerId);
		if (map2 == null) {
			map2 = new HashMap<String, TimeTableCellData>();
			map.put(baudStreamServerId, map2);
		}
		String trance2DayIdentifyCode = WeekDate.getDayIdentifyCode(startTime);
		TimeTableCellData timeTableCellData = map2.get(trance2DayIdentifyCode);
		if (timeTableCellData == null) {
			timeTableCellData = new TimeTableCellData();
			map2.put(trance2DayIdentifyCode, timeTableCellData);
			timeTableCellData.addTimeTableData(timeTableData);
			timeTableCellData.setDeviceId(baudStreamServerId);
			timeTableCellData.setWeekDay(trance2DayIdentifyCode);
		} else {
			timeTableCellData.addTimeTableData(timeTableData);
		}
	}

	public static TimeTableData findById(int timeTableDataId) {
		for (TimeTableData timeTableData : timeTableDatas) {
			int id = timeTableData.getId();
			if (id == timeTableDataId) {
				return timeTableData;
			}
		}
		return null;
	}

	public static void removeTimeTableDataListFromServer(
			List<TimeTableData> timeTableDataList) {
		new ClientRequest().removeTimeTableDatas(timeTableDataList);
	}

	public static Map<Integer, Map<String, TimeTableCellData>> removeTimeTableData(
			TimeTableData timeTableData) {
		int baudStreamServerId = timeTableData.getBaudStreamServerId();
		Timestamp startTime = timeTableData.getStartTime();
		if (map == null) {
			return map;
		}
		Map<String, TimeTableCellData> map2 = map.get(baudStreamServerId);
		if (map2 == null) {
			return map;
		}
		String identifyCode = WeekDate.getDayIdentifyCode(startTime);
		TimeTableCellData timeTableCellData = map2.get(identifyCode);
		int timeTableDataId = timeTableData.getId();
		List<ReserveData> reserveDatas = timeTableCellData.getReserveDatas();
		for (int i = 0; i < reserveDatas.size(); i++) {
			ReserveData reserveData = reserveDatas.get(i);
			int timeTableDataId2 = reserveData.getTimeTableDataId();
			if (timeTableDataId == timeTableDataId2) {
				reserveDatas.remove(i);
				timeTableCellData.setReserveDatas(reserveDatas);
				map2.put(identifyCode, timeTableCellData);
				break;
			}
		}
		return map;
	}

	public static Map<Integer, Map<String, TimeTableCellData>> removeTimeTableDataList(
			List<TimeTableData> timeTableDataList) {
		if (timeTableDataList == null || timeTableDataList.size() == 0) {
			return map;
		}
		for (TimeTableData timeTableData : timeTableDataList) {
			removeTimeTableData(timeTableData);
		}
		return map;
	}
}
