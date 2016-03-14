package com.skyworth.iDtv.ui.timeTable;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class TimeTableCellToolTip extends JPopupMenu {
	private static final long serialVersionUID = 4594453784391514399L;

	public TimeTableCellToolTip(List<ReserveData> timeTableDatas) {
		if (timeTableDatas == null || timeTableDatas.size() == 0) {
			return;
		}
		add(new TimeTablePanel(timeTableDatas));
	}

	public class TimeTablePanel extends JPanel {
		private Color baColor = ContentTableRender.underlineColor;
		private static final long serialVersionUID = 4977814541319089461L;
		public List<ReserveData> timeTableDatas = null;

		public TimeTablePanel(List<ReserveData> timeTableDatas) {
			this.timeTableDatas = timeTableDatas;
			initComponents();
		}

		private void initComponents() {
			setBorder(BorderFactory
					.createLineBorder(ContentTableRender.underlineColor));
			setBackground(baColor);
			setForeground(baColor);
			if (timeTableDatas == null || timeTableDatas.size() <= 0) {
				return;
			}
			int size = timeTableDatas.size();
			BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
			setLayout(boxLayout);
			for (int i = 0; i < size; i++) {
				ReserveData timeTableData = timeTableDatas.get(i);
				JPanel panrl = new JPanel();
				panrl.setOpaque(false);
				BoxLayout boxLayout2 = new BoxLayout(panrl, BoxLayout.X_AXIS);
				panrl.setLayout(boxLayout2);
				JLabel userNameLabel = new JLabel(timeTableData.getUser()
						.getName() + "");
				panrl.add(userNameLabel);
				panrl.add(new JLabel(" 预定了 "));
				JLabel timeLabel = new JLabel();
				Timestamp startTimestamp = timeTableData.getStartTime();
				Timestamp endTimestamp = timeTableData.getEndTime();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm");
				String startTimeString = simpleDateFormat
						.format(startTimestamp);
				startTimeString += "-";
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endTimestamp);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				if (hour == 0) {
					hour = 24;
					calendar.add(Calendar.MILLISECOND, -1);
				}

				String endTimeString = simpleDateFormat.format(calendar
						.getTime());
				timeLabel.setText(startTimeString + endTimeString);
				panrl.add(timeLabel);
				add(panrl);
			}
		}
	}
}
