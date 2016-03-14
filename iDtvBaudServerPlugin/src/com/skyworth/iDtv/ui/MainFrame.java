package com.skyworth.iDtv.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import com.skyworth.iDtv.dao.DeviceStatue;
import com.skyworth.iDtv.dao.TimeTableStatue;
import com.skyworth.iDtv.dao.UserStatue;
import com.skyworth.iDtv.entity.DeviceData;
import com.skyworth.iDtv.ui.baudServer.BaudServerManagerDialog;
import com.skyworth.iDtv.ui.personalInfo.PersonalInfoDialog;
import com.skyworth.iDtv.ui.resource.ZResourceUtils;
import com.skyworth.iDtv.ui.timeTable.TimeTableCellData;
import com.skyworth.iDtv.ui.timeTable.TimeTableScrollPane;
import com.skyworth.iDtv.ui.userManager.UserManagerDialog;
import com.zys.utils.SystemInfo;
import com.zys.utils.ui.ZPanel;
import com.zys.utils.ui.ZScrollLabel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 3990496113905256012L;
	private TimeTableScrollPane baudServerTableScrollPane;
	private ZLabelButton loginButton;
	private Frame fatherFrame;
	private ZScrollLabel statusLabel;

	public MainFrame(JFrame fatherFrame) {
		this.fatherFrame = fatherFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		loadData();
		loadAction();
	}

	public MainFrame() {
		fatherFrame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		loadData();
		loadAction();
	}

	private JPopupMenu createUserButtonPopupMenu() {
		boolean addUserPermission = UserStatue.checkAddUserPermission(this);
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem menuItem;
		if (addUserPermission) {
			menuItem = new JMenuItem();
			menuItem.setText(ZResourceUtils.getLabel("userManagement"));
			menuItem.setIcon(ZResourceUtils.getImageIcon("user"));
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					UserManagerDialog userManagerDialog = new UserManagerDialog(
							fatherFrame, true);
					userManagerDialog.setLocationByPlatform(true);
					userManagerDialog.setLocationRelativeTo(fatherFrame);
					userManagerDialog.setVisible(true);
				}
			});
			popupMenu.add(menuItem);
		}
		if (UserStatue.checkAddDevicePermission(this)) {
			menuItem = new JMenuItem();
			menuItem.setText(ZResourceUtils.getLabel("baudServerManagement"));
			menuItem.setIcon(ZResourceUtils.getImageIcon("serverIcon"));
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					BaudServerManagerDialog dialog = new BaudServerManagerDialog(
							fatherFrame, true);
					dialog.setLocationRelativeTo(fatherFrame);
					dialog.setVisible(true);
					boolean changed=dialog.isChanged();
					if (changed) {
						TimeTableStatue.updateFromServer();
						DeviceStatue.updateFromServer();
						MainFrame.this.baudServerTableScrollPane.fireTableStructureUpdate();
						
					}
				}
			});
			popupMenu.add(menuItem);
		}
		menuItem = new JMenuItem();
		menuItem.setText(ZResourceUtils.getLabel("userInfo"));
		menuItem.setIcon(ZResourceUtils.getImageIcon("userInfo"));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				userInfoActionAdaptor(e);
			}

		});
		popupMenu.add(menuItem);

		menuItem = new JMenuItem();
		menuItem.setText(ZResourceUtils.getLabel("logout"));
		menuItem.setIcon(ZResourceUtils.getImageIcon("logout"));
		menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doLogout();
			}
		});
		popupMenu.add(menuItem);
		return popupMenu;
	}

	private void userInfoActionAdaptor(ActionEvent e) {
		PersonalInfoDialog personalInfoManager = new PersonalInfoDialog(this,
				true);
		personalInfoManager.setUserData(UserStatue.getCurrentUser());
		personalInfoManager.setLocationRelativeTo(this);
		personalInfoManager.setVisible(true);
	}

	private void doLogout() {
		UserStatue.logout();
		loginButton.setText(ZResourceUtils.getLabel("login"));
		loginButton.setIcon(ZResourceUtils.getImageIcon("loginIcon"));
	}

	private void initComponents() {
		String systemStyle = SystemInfo.getSytemStyle();
		if (systemStyle.equals(SystemInfo.OS_WINDOWS_7)) {
			setBounds(100, 100, 700, 440);
		} else if (systemStyle.equals(SystemInfo.OS_WINDOWS_XP)) {
			setBounds(100, 100, 700, 438);
		} else {
			setBounds(100, 100, 700, 438);
		}
		ZPanel contentPane = new ZPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		baudServerTableScrollPane = new TimeTableScrollPane(this);
		contentPane.add(baudServerTableScrollPane, BorderLayout.CENTER);
		JPanel northPanel = new JPanel();
		contentPane.add(northPanel, BorderLayout.NORTH);

		FlowLayout northPanelLayout = new FlowLayout();
		northPanel.setLayout(northPanelLayout);
		northPanelLayout.setAlignOnBaseline(true);
		northPanelLayout.setAlignment(FlowLayout.LEFT);
		northPanelLayout.setVgap(0);
		northPanelLayout.setHgap(0);

		loginButton = new ZLabelButton(ZResourceUtils.getLabel("login"));
		Icon imageIcon = ZResourceUtils.getImageIcon("loginIcon");
		loginButton.setIcon(imageIcon);
		loginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loginButtonActionPerformed(e);
			}
		});
		northPanel.add(loginButton);

		ZLabelButton lastMonthButton = new ZLabelButton();
		lastMonthButton.setText(ZResourceUtils.getLabel("lastMonth"));
		lastMonthButton.setIcon(ZResourceUtils.getImageIcon("lastMonth"));
		lastMonthButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				skipTo("lastMonth");
			}
		});
		northPanel.add(lastMonthButton);

		ZLabelButton lastWeekButton = new ZLabelButton();
		lastWeekButton.setText(ZResourceUtils.getLabel("lastWeek"));
		lastWeekButton.setIcon(ZResourceUtils.getImageIcon("lastWeek"));
		lastWeekButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				skipTo("lastWeek");
			}
		});
		northPanel.add(lastWeekButton);

		ZLabelButton nextWeekButton = new ZLabelButton();
		nextWeekButton.setText(ZResourceUtils.getLabel("nextWeek"));
		nextWeekButton.setIcon(ZResourceUtils.getImageIcon("nextWeek"));
		nextWeekButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				skipTo("nextWeek");
			}
		});
		northPanel.add(nextWeekButton);

		ZLabelButton nextMonthButton = new ZLabelButton();
		nextMonthButton.setText(ZResourceUtils.getLabel("nextMonth"));
		nextMonthButton.setIcon(ZResourceUtils.getImageIcon("nextMonth"));
		nextMonthButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				skipTo("nextMonth");
			}
		});
		northPanel.add(nextMonthButton);
		ZLabelButton thisWeekButton = new ZLabelButton();
		thisWeekButton.setText(ZResourceUtils.getLabel("thisWeek"));
		thisWeekButton.setIcon(ZResourceUtils.getImageIcon("thisWeek"));
		thisWeekButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				skipTo("thisWeek");
			}
		});
		northPanel.add(thisWeekButton);

		ZLabelButton refreshButton = new ZLabelButton();
		refreshButton.setText(ZResourceUtils.getLabel("refresh"));
		refreshButton.setIcon(ZResourceUtils.getImageIcon("refresh"));
		refreshButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				refreshButtonActionPerformed(e);
			}
		});
		northPanel.add(refreshButton);

		// ////////
		JPanel southPanel = new JPanel();
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new BorderLayout(0, 0));

		statusLabel = new ZScrollLabel("message1", "second message");
		southPanel.add(statusLabel);
	}

	protected void refreshButtonActionPerformed(MouseEvent e) {
		List<DeviceData> deviceDatas = DeviceStatue.updateFromServer();
		Map<Integer, Map<String, TimeTableCellData>> timeTableDatas = TimeTableStatue
				.updateFromServer();
		Iterator<DeviceData> it = deviceDatas.iterator();
		Vector<DeviceData> deviceDataVector;
		deviceDataVector = new Vector<DeviceData>();
		while (it.hasNext()) {
			deviceDataVector.add(it.next());
		}

		baudServerTableScrollPane.setDatas(deviceDataVector, timeTableDatas);
		baudServerTableScrollPane.fireTableStructureUpdate();
		baudServerTableScrollPane.getContentTable().redefineTableHead();
	}

	private void skipTo(String string) {
		if (string.equals("thisWeek")) {
			TimeTableStatue.getWeekDate().setDate(new Date());

		} else if (string.equals("lastMonth")) {
			TimeTableStatue.getWeekDate().lastMonth();

		} else if (string.equals("nextMonth")) {
			TimeTableStatue.getWeekDate().nextMonth();

		} else if (string.equals("lastWeek")) {
			TimeTableStatue.getWeekDate().lastWeek();

		} else if (string.equals("nextWeek")) {
			TimeTableStatue.getWeekDate().nextWeek();

		}
		baudServerTableScrollPane.getContentTable().fireTableStructureChanged();
	}

	private void loadAction() {

	}

	private void loginButtonActionPerformed(MouseEvent e) {
		if (UserStatue.getCurrentUser() == null) {
			if (UserStatue.checkLogin(fatherFrame)) {
				doLogin();
			}
		} else {
			Rectangle bounds = loginButton.getBounds().getBounds();
			double x2 = bounds.getX();
			double y2 = bounds.getY() + bounds.getHeight();
			JPopupMenu popupMenu = createUserButtonPopupMenu();
			popupMenu.show(loginButton, (int) x2, (int) y2);
		}
	}

	private void loadData() {
		List<DeviceData> deviceDatas = DeviceStatue.getDeviceDatas();
		Map<Integer, Map<String, TimeTableCellData>> timeTableDatas = TimeTableStatue
				.getTimeTableDatas();
		Iterator<DeviceData> it = deviceDatas.iterator();
		Vector<DeviceData> deviceDataVector;
		deviceDataVector = new Vector<DeviceData>();
		while (it.hasNext()) {
			deviceDataVector.add(it.next());
		}

		baudServerTableScrollPane.setDatas(deviceDataVector, timeTableDatas);
	}

	public void doLogin() {
		String userName = UserStatue.getUserName();
		loginButton.setText(userName);
		loginButton.setIcon(ZResourceUtils.getImageIcon("user"));
	}

	public void setStatusLabel(String string1, String string2) {
		statusLabel.setText(string1, string2);
	}

}
