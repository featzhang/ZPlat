package com.skyworth.iDtv.ui.timeTable;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

import com.skyworth.iDtv.dao.DeviceStatue;
import com.skyworth.iDtv.entity.TimeTableData;
import com.skyworth.iDtv.entity.DeviceData;
import com.skyworth.iDtv.ui.resource.ZResourceUtils;
import com.zys.utils.ui.ZDialog;
import com.zys.utils.ui.ZPanel;

public class ReserverDialog extends ZDialog {
	class TimeTableDatePanel extends ZPanel {
		private static final long serialVersionUID = 1527669251132285662L;
		private String defaultTaskString = "输入任务";
		private TimeTableData timeTableData;
		private JTextField dayField;
		private JTextField deviceField;
		private JSpinner startTimeField;
		private JSpinner endTimeField;
		private JTextField taskField;
		private SpinnerDateModel startTimeModel;
		private SpinnerDateModel endTimeModel;
		private JButton removeButton;
		private JCheckBox checkBox;

		public TimeTableDatePanel(TimeTableData timeTableData) {
			super();
			this.timeTableData = timeTableData;
			initComponents();
			loadDatas();
			loadAction();
			setMode(mode);
		}

		public TimeTableData getTimeTableData() {
			return timeTableData;
		}

		private void initComponents() {
			checkBox = new JCheckBox();

			dayField = new JTextField();
			dayField.setEditable(false);

			deviceField = new JTextField();
			deviceField.setColumns(15);
			deviceField.setEditable(false);

			String dateFormatString = "HH:mm";
			startTimeModel = new SpinnerDateModel();
			startTimeField = new JSpinner(startTimeModel);
			DateEditor editor = new JSpinner.DateEditor(startTimeField,
					dateFormatString);
			startTimeField.setEditor(editor);
			JComponent comp = startTimeField.getEditor();
			JFormattedTextField field = (JFormattedTextField) comp
					.getComponent(0);
			DefaultFormatter formatter = (DefaultFormatter) field
					.getFormatter();
			formatter.setCommitsOnValidEdit(true);

			JLabel label = new JLabel();
			label.setText("-->");

			endTimeModel = new SpinnerDateModel();
			endTimeField = new JSpinner(endTimeModel);
			editor = new JSpinner.DateEditor(endTimeField, dateFormatString);
			endTimeField.setEditor(editor);
			comp = endTimeField.getEditor();
			field = (JFormattedTextField) comp.getComponent(0);
			formatter = (DefaultFormatter) field.getFormatter();
			formatter.setCommitsOnValidEdit(true);

			taskField = new JTextField();
			taskField.setText("            ");
			taskField.setColumns(20);

			removeButton = new JButton();
			removeButton.setIcon(ZResourceUtils.getImageIcon("delete"));

			add(checkBox);
			add(deviceField);
			add(dayField);
			add(startTimeField);
			add(label);
			add(endTimeField);
			add(taskField);
			add(removeButton);

		}

		public boolean isSelected() {
			return checkBox.isSelected();
		}

		private void loadAction() {
			taskField.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (taskField.getText().trim().equals(defaultTaskString)) {
						taskField.setText("");
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (taskField.getText().trim().equals("")) {
						taskField.setText(defaultTaskString);
					} else if (!taskField.getText().trim()
							.equals(defaultTaskString)) {
						String text = taskField.getText();
						timeTableData.setTask(text);
					}
				}
			});
			removeButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					removeReserverActionPerformed(TimeTableDatePanel.this);
				}
			});
			ChangeListener changeListener = new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					Object source = e.getSource();
					if (source == startTimeField) {
						Date date = startTimeModel.getDate();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						int hour = calendar.get(Calendar.HOUR_OF_DAY);
						int minute = calendar.get(Calendar.MINUTE);

						Timestamp endTime = timeTableData.getStartTime();
						calendar.setTime(endTime);
						int oldHour = calendar.get(Calendar.HOUR_OF_DAY);
						int oldMinute = calendar.get(Calendar.MINUTE);
						if (hour == oldHour && minute == oldMinute) {
							return;
						}
						calendar.set(Calendar.HOUR_OF_DAY, hour);
						calendar.set(Calendar.MINUTE, minute);
						timeTableData.setStartTime(new Timestamp(calendar
								.getTimeInMillis()));
					} else if (source == endTimeField) {
						Date date = endTimeModel.getDate();
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(date);
						int hour = calendar.get(Calendar.HOUR_OF_DAY);
						int minute = calendar.get(Calendar.MINUTE);

						Timestamp endTime = timeTableData.getEndTime();
						calendar.setTime(endTime);
						int oldHour = calendar.get(Calendar.HOUR_OF_DAY);
						int oldMinute = calendar.get(Calendar.MINUTE);
						if (hour == oldHour && minute == oldMinute) {
							return;
						}
						calendar.set(Calendar.HOUR_OF_DAY, hour);
						calendar.set(Calendar.MINUTE, minute);
						timeTableData.setEndTime(new Timestamp(calendar
								.getTimeInMillis()));
					}
				}
			};
			startTimeField.addChangeListener(changeListener);
			endTimeField.addChangeListener(changeListener);

		}

		private void loadDatas() {
			Timestamp startTime = timeTableData.getStartTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日");
			dayField.setText(dateFormat.format(startTime));
			int baudStreamServerId = timeTableData.getBaudStreamServerId();
			DeviceData deviceData = DeviceStatue
					.finDeviceDataById(baudStreamServerId);
			String ip = deviceData.getIp();
			deviceField.setText(ip);

			startTimeModel.setValue(startTime);
			Timestamp endTime = timeTableData.getEndTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endTime);
			calendar.add(Calendar.MILLISECOND, -1);
			endTimeModel.setValue(calendar.getTime());
			String task = timeTableData.getTask();
			if (task == null || task.trim().equals("")) {
				taskField.setText(defaultTaskString);
			} else {
				taskField.setText(task);
			}

		}

		public void setTimeTableData(TimeTableData timeTableData) {
			this.timeTableData = timeTableData;
		}

		public void setMode(int mode) {
			if (mode == ADD_MODE) {
				checkBox.setVisible(false);
				startTimeField.setEnabled(true);
				endTimeField.setEnabled(true);
				taskField.setEditable(true);
				removeButton.setVisible(true);
				sureButton.setVisible(true);
			} else if (mode == DELETE_MODE) {
				checkBox.setVisible(true);
				startTimeField.setEnabled(false);
				endTimeField.setEnabled(false);
				taskField.setEnabled(false);
				removeButton.setVisible(false);
				sureButton.setVisible(true);
			} else if (mode == Detail_MODE) {
				checkBox.setVisible(false);
				startTimeField.setEnabled(false);
				endTimeField.setEnabled(false);
				taskField.setEnabled(false);
				removeButton.setVisible(false);
				sureButton.setVisible(false);
			}
		}
	}

	public static final int ADD_MODE = 0;

	public static final int DELETE_MODE = 1;
	public static final int Detail_MODE = 2;

	private static final long serialVersionUID = -5973579719875564515L;
	public static final int OK_RESULT = 0;
	public static final int CANCEL_RESULT = 1;
	private JPanel contentPanel;
	private JButton sureButton;

	private JButton cancelButton;

	private int result;
	private Frame owner;

	private int mode;

	public ReserverDialog(Frame owner, boolean modal, int mode) {
		super(owner, modal);
		this.owner = owner;
		this.mode = mode;
		initComponents();
		loadAction();
	}

	protected void cancelButtonActionPerformed(ActionEvent e) {
		result = CANCEL_RESULT;
		this.dispose();
	}

	public List<TimeTableData> getDatas() {
		List<TimeTableData> list = new ArrayList<TimeTableData>();
		int componentCount = contentPanel.getComponentCount();
		for (int i = 0; i < componentCount; i++) {
			TimeTableDatePanel component2 = (TimeTableDatePanel) contentPanel
					.getComponent(i);
			TimeTableData timeTableData = component2.getTimeTableData();
			list.add(timeTableData);
		}
		return list;
	}

	public int getResult() {
		return result;
	}

	public List<TimeTableData> getSelectedDatas() {
		List<TimeTableData> list = new ArrayList<TimeTableData>();
		int componentCount = contentPanel.getComponentCount();
		for (int i = 0; i < componentCount; i++) {
			TimeTableDatePanel component2 = (TimeTableDatePanel) contentPanel
					.getComponent(i);
			if (component2.isSelected()) {
				TimeTableData timeTableData = component2.getTimeTableData();
				list.add(timeTableData);
			}
		}
		return list;
	}

	private void initComponents() {
		JScrollPane scrollPane = new JScrollPane();
		contentPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS);
		contentPanel.setLayout(boxLayout);
		scrollPane.setViewportView(contentPanel);

		JPanel buttonPanel = new JPanel();
		sureButton = new JButton();
		sureButton.setText(ZResourceUtils.getLabel("sure"));
		cancelButton = new JButton();
		cancelButton.setText(ZResourceUtils.getLabel("cancel"));

		buttonPanel.add(sureButton);
		buttonPanel.add(cancelButton);

		setLayout(new BorderLayout());

		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void loadAction() {
		sureButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sureButtonActionPerformed(e);
			}
		});
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelButtonActionPerformed(e);
			}
		});
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				result = CANCEL_RESULT;
				super.windowClosing(e);
			}
		});
	}

	protected void removeReserverActionPerformed(
			TimeTableDatePanel tableDatePanel) {
		this.contentPanel.remove(tableDatePanel);
		this.contentPanel.validate();
		this.pack();
		this.setLocationRelativeTo(owner);
	}

	public void setDatas(List<TimeTableData> timeTableDatas) {
		if (timeTableDatas == null) {
			return;
		}
		contentPanel.removeAll();
		for (int i = 0; i < timeTableDatas.size(); i++) {
			contentPanel.add(new TimeTableDatePanel(timeTableDatas.get(i)));
		}
	}

	protected void sureButtonActionPerformed(ActionEvent e) {
		int componentCount = contentPanel.getComponentCount();
		for (int i = 0; i < componentCount; i++) {
			TimeTableDatePanel component2 = (TimeTableDatePanel) contentPanel
					.getComponent(i);
			TimeTableData timeTableData = component2.getTimeTableData();
			long time = timeTableData.getStartTime().getTime();
			long time2 = timeTableData.getEndTime().getTime();
			if (time >= time2) {
				JOptionPane.showMessageDialog(ReserverDialog.this,
						"data input error of row " + (i + 1)
								+ ":\n end time must large than start time!");
				return;
			}
		}
		result = OK_RESULT;
		this.dispose();
	}

	public void setMode(int mode) {

		int componentCount = contentPanel.getComponentCount();
		for (int i = 0; i < componentCount; i++) {
			TimeTableDatePanel component2 = (TimeTableDatePanel) contentPanel
					.getComponent(i);
			component2.setMode(mode);
		}
	}
}
