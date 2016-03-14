package com.skyworth.iDtv.ui.timeTable;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.skyworth.iDtv.entity.TimeTableData;
import com.skyworth.iDtv.dao.UserStatue;

public class ReserveAssist {
	public static final int OK_RESULT = ReserverDialog.OK_RESULT;
	public static final int CANCEL_RESULT = ReserverDialog.CANCEL_RESULT;
	private int result = 0;

	private List<TimeTableData> reserverDatas;

	public List<TimeTableData> getReserverDatas() {
		return reserverDatas;
	}

	public int showAddResrverDialog(Frame parentFrame, String title,
			List<TimeTableCellData> timeTableCellDatas) {
		List<TimeTableData> timeTableDatas = new ArrayList<TimeTableData>();
		for (int i = 0; i < timeTableCellDatas.size(); i++) {
			TimeTableCellData timeTableCellData = timeTableCellDatas.get(i);
			List<TimeTableData> restTimeTableDatas = timeTableCellData
					.getRestTimeTableDatas();
			if (restTimeTableDatas != null && restTimeTableDatas.size() > 0) {
				timeTableDatas.addAll(restTimeTableDatas);
			}
		}
		if (timeTableDatas.size() == 0) {
			JOptionPane.showMessageDialog(parentFrame, "time full!");
			return CANCEL_RESULT;
		}
		ReserverDialog reserverDialog = new ReserverDialog(parentFrame, true,
				ReserverDialog.ADD_MODE);
		reserverDialog.setTitle(title);
		reserverDialog.setDatas(timeTableDatas);
		reserverDialog.pack();
		reserverDialog.setMaximumSize(new Dimension(500, 1000));
		reserverDialog.setLocationRelativeTo(parentFrame);
		reserverDialog.setVisible(true);
		result = reserverDialog.getResult();
		reserverDatas = reserverDialog.getDatas();
		return result;
	}

	public int showDeleteResrverDialog(Frame parentFrame, String title,
			List<TimeTableCellData> timeTableCellDatas) {
		UserStatue.checkLogin(parentFrame);
		List<TimeTableData> timeTableDatas = new ArrayList<TimeTableData>();
		for (int i = 0; i < timeTableCellDatas.size(); i++) {
			TimeTableCellData timeTableCellData = timeTableCellDatas.get(i);
			List<TimeTableData> restTimeTableDatas = timeTableCellData
					.toTimeTableDataBelongToCurrentUser();

			if (restTimeTableDatas != null && restTimeTableDatas.size() > 0) {
				timeTableDatas.addAll(restTimeTableDatas);
			}
		}
		if (timeTableDatas.size() == 0) {
			JOptionPane.showMessageDialog(parentFrame,
					"no reserver information to cancel!");
			return CANCEL_RESULT;
		}
		ReserverDialog reserverDialog = new ReserverDialog(parentFrame, true,
				ReserverDialog.DELETE_MODE);
		reserverDialog.setTitle(title);
		reserverDialog.setDatas(timeTableDatas);
		reserverDialog.pack();
		reserverDialog.setMaximumSize(new Dimension(500, 1000));
		reserverDialog.setLocationRelativeTo(parentFrame);
		reserverDialog.setVisible(true);
		result = reserverDialog.getResult();
		reserverDatas = reserverDialog.getSelectedDatas();
		if (reserverDatas == null || reserverDatas.size() == 0) {
			result = CANCEL_RESULT;
		}
		return result;
	}

	public void showDetailResrverDialog(JFrame parentFrame, String title,
			List<TimeTableCellData> timeTableCellDatas) {
		UserStatue.checkLogin(parentFrame);
		List<TimeTableData> timeTableDatas = new ArrayList<TimeTableData>();
		for (int i = 0; i < timeTableCellDatas.size(); i++) {
			TimeTableCellData timeTableCellData = timeTableCellDatas.get(i);
			List<TimeTableData> restTimeTableDatas = timeTableCellData
					.toTimeTableDataBelongToCurrentUser();

			if (restTimeTableDatas != null && restTimeTableDatas.size() > 0) {
				timeTableDatas.addAll(restTimeTableDatas);
			}
		}
		if (timeTableDatas.size() == 0) {
			JOptionPane.showMessageDialog(parentFrame,
					"no reserver information to cancel!");
		} else {
			ReserverDialog reserverDialog = new ReserverDialog(parentFrame,
					true, ReserverDialog.Detail_MODE);
			reserverDialog.setTitle(title);
			reserverDialog.setDatas(timeTableDatas);
			reserverDialog.pack();
			reserverDialog.setMaximumSize(new Dimension(500, 1000));
			reserverDialog.setLocationRelativeTo(parentFrame);
			reserverDialog.setVisible(true);
		}
	}
}
