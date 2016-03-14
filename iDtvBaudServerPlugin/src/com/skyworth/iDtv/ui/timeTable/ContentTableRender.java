package com.skyworth.iDtv.ui.timeTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import com.skyworth.iDtv.dao.DeviceStatue;
import com.skyworth.iDtv.entity.DeviceData;

public class ContentTableRender extends JPanel implements TableCellRenderer,
		Serializable {
	private static final long serialVersionUID = 1579588815345716312L;
	private List<ReserveData> reserveDatas = null;
	private boolean enable = true;
	public static Border selectedBorder = BorderFactory
			.createLineBorder(new Color(0xFF9955));
	public static Border unSelectedBorder = BorderFactory
			.createLineBorder(new Color(0xFFFFCC));
	private int stripOrientation = 0;
	private Border disableBorder = BorderFactory.createLineBorder(new Color(
			0xA02B10));

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public static int HORIZONTAL = 0;

	public static int VERTICAL = 1;

	public static Color underlineColor = new Color(0xFFFF99);
	private int dayStartHour = 0;

	public ContentTableRender() {
		setOpaque(false);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		TimeTableCellData tableCellData = (TimeTableCellData) value;
		int deviceId = tableCellData.getDeviceId();
		DeviceData deviceData = DeviceStatue.finDeviceDataById(deviceId);
		boolean enable1;
		if (deviceData == null) {
			enable1 = true;
		} else {
			enable1 = deviceData.getUsable() == 1;
		}
		List<ReserveData> reserveDatas = tableCellData.getReserveDatas();
		setTimeTableDatas(reserveDatas);
		setEnable(enable1);
		if (!enable1) {
			setBorder(disableBorder);
		} else if (isSelected) {
			setBorder(selectedBorder);
		} else {
			setBorder(unSelectedBorder);
		}

		return this;
	}

	public List<ReserveData> getTimeTableDatas() {
		return reserveDatas;
	}

	@Override
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0, 0, width, height);
		if (!enable) {
			g.fillRect(0, 0, width, height);
			return;
		}
		if (reserveDatas != null) {
			int timeTableDatesSize = reserveDatas.size();
			double[] ds = new double[2 * timeTableDatesSize];
			for (int i = 0; i < timeTableDatesSize; i++) {
				ReserveData timeTableData = reserveDatas.get(i);
				Timestamp startTimestamp = timeTableData.getStartTime();
				Timestamp endTimestamp = timeTableData.getEndTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date(startTimestamp.getTime()));
				int startHours = calendar.get(Calendar.HOUR_OF_DAY);
				calendar.setTime(new Date(endTimestamp.getTime()));
				int endHours = calendar.get(Calendar.HOUR_OF_DAY);
				if (endHours == 0) {
					endHours = 24;
				}
				double startPercent = (startHours - dayStartHour) / 12.0;
				double endPercent = (endHours - dayStartHour) / 12.0;
				ds[i * 2 + 0] = startPercent;
				ds[i * 2 + 1] = endPercent;
			}
			int _width = getWidth();
			int _height = getHeight();
			for (int i = 0; i < ds.length; i++) {
				if (stripOrientation == VERTICAL) {
					ds[i] = ds[i] * _width;
				} else if (stripOrientation == HORIZONTAL) {
					ds[i] = ds[i] * _height;
				}
			}
			Graphics2D graphics2d = (Graphics2D) g;
			Color defaultColor = graphics2d.getColor();
			graphics2d.setColor(underlineColor);
			for (int i = 0; i < timeTableDatesSize; i++) {
				int x = 0;
				int y = 0;
				width = _width;
				height = _height;
				if (stripOrientation == HORIZONTAL) {
					x = 0;
					y = (int) ds[i * 2];
					width = _width;
					height = (int) (ds[i * 2 + 1] - ds[i * 2]);
				} else if (stripOrientation == VERTICAL) {
					x = (int) ds[i * 2];
					width = (int) (ds[i * 2 + 1] - ds[i * 2]);
					y = 0;
					height = _height;
				}
				graphics2d.fillRect(x, y, width, height);
			}
			graphics2d.setColor(defaultColor);
		}
		super.paintComponents(g);
	}

	public void setTimeTableDatas(List<ReserveData> timeTableDatas) {
		this.reserveDatas = timeTableDatas;
	}
}