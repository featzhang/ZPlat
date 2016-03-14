package com.skyworth.iDtv.ui.timeTable;

import com.skyworth.iDtv.entity.DeviceData;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class RowHeaderTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -349532321138647331L;
	private Vector<DeviceData> deviceDataList = null;

	public DeviceData getRowData(int row) {
		if (deviceDataList == null) {
			return null;
		}
		return deviceDataList.get(row);
	}

	public RowHeaderTableModel() {
	}

	public int getRowCount() {
		if (deviceDataList == null)
			return 0;
		else {
			return deviceDataList.size();
		}
	}

	public int getColumnCount() {
		return 2;
	}

	public Object getValueAt(int row, int column) {
		if (deviceDataList == null) {
			return "";
		}
		if (column == 0) {
			return deviceDataList.get(row).getIp();
		} else {
			String string = deviceDataList.get(row).getPort();
			return string;
		}
	}

	public void setData(Vector<DeviceData> deviceDataList) {
		this.deviceDataList = deviceDataList;
	}
}