package com.skyworth.iDtv.ui.timeTable;

import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.skyworth.iDtv.dao.TimeTableStatue;
import com.skyworth.iDtv.entity.DeviceData;

public class ContentTableModel extends DefaultTableModel {
	private static final long serialVersionUID = -3491779899120961183L;
	private Map<Integer, Map<String, TimeTableCellData>> timeTableDatas;
	private Vector<DeviceData> deviceDataList;

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		if (deviceDataList == null)
			return 0;
		return deviceDataList.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		DeviceData deviceData = deviceDataList.get(row);
		String weekidentify = TimeTableStatue.getWeekDate()
				.getWeekIdentifyCodes()[column];
		Integer id = deviceData.getId();
		Map<String, TimeTableCellData> map = timeTableDatas.get(id);
		if (map == null) {
			TimeTableCellData timeTableCellData = new TimeTableCellData();
			timeTableCellData.setDeviceId(id);
			timeTableCellData.setWeekDay(weekidentify);
			return timeTableCellData;
		} else {
			TimeTableCellData timeTableCellData = map.get(weekidentify);
			if (timeTableCellData != null) {
				return timeTableCellData;
			} else {
				timeTableCellData = new TimeTableCellData();
				timeTableCellData.setDeviceId(id);
				timeTableCellData.setWeekDay(weekidentify);
				return timeTableCellData;
			}
		}
	}

	@Override
	public String getColumnName(int column) {
		String[] weekYMDDString = TimeTableStatue.getWeekDate()
				.getWeekYMDDString();

		if (weekYMDDString != null && weekYMDDString.length > 0)
			return weekYMDDString[column];
		return super.getColumnName(column);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return super.getColumnClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void setData(Vector<DeviceData> deviceDataList,
			Map<Integer, Map<String, TimeTableCellData>> timeTableDatas2) {
		this.timeTableDatas = timeTableDatas2;
		this.deviceDataList = deviceDataList;
	}

	public void updateTimeTableData(
			Map<Integer, Map<String, TimeTableCellData>> timeTableDatas2) {
		this.timeTableDatas = timeTableDatas2;
	}
}
