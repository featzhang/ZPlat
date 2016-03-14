package com.skyworth.iDtv.ui.timeTable;

import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.skyworth.iDtv.entity.DeviceData;

public class TimeTableScrollPane extends JScrollPane {

	private static final long serialVersionUID = 3249429396764894465L;
	private RowHeaderTableModel rowHeaderTableModel;
	private ContentTableModel contentTableModel;
	private ContentTable contentTable;
	private RowHeaderTable rowHeaderTable;
	private JFrame parentFrame;

	public ContentTableModel getContentTableModel() {
		return contentTableModel;
	}

	public ContentTable getContentTable() {
		return contentTable;
	}

	public TimeTableScrollPane(JFrame mainFrame) {
		this.parentFrame = mainFrame;
		initComponents();
	}

	private void initComponents() {
		contentTableModel = new ContentTableModel();
		contentTable = new ContentTable(parentFrame,contentTableModel);
		setViewportView(contentTable);
		rowHeaderTable = new RowHeaderTable(parentFrame,contentTable);
		rowHeaderTableModel = new RowHeaderTableModel();
		rowHeaderTable.setModel(rowHeaderTableModel);
		setRowHeaderView(rowHeaderTable);
	}

	public void setDatas(Vector<DeviceData> deviceDataList,
			Map<Integer, Map<String, TimeTableCellData>> timeTableDatas) {
		rowHeaderTableModel.setData(deviceDataList);
		rowHeaderTableModel.fireTableStructureChanged();
		contentTableModel.setData(deviceDataList,timeTableDatas);
		contentTableModel.fireTableStructureChanged();
		contentTable.redefineTableHead();
	}
	public void fireTableStructureUpdate(){
		rowHeaderTableModel.fireTableStructureChanged();
		contentTableModel.fireTableStructureChanged();
	}
}
