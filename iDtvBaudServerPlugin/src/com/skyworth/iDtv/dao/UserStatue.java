package com.skyworth.iDtv.dao;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import com.skyworth.iDtv.entity.UserData;
import com.skyworth.iDtv.entity.UserTypeData;
import com.skyworth.iDtv.net.ClientRequest;
import com.zys.utils.ui.ZLoginDialog;

public class UserStatue {
	private static UserData user = null;
	private static UserTypeData userTypeData;

	public static UserData getCurrentUser() {
		return user;
	}

	public static String getUserName() {
		if (user != null) {
			return user.getName();
		}
		return null;
	}

	public static String getUserQQ() {
		return user == null ? null : user.getQq();
	}

	public static UserTypeData getUserType() {
		return userTypeData;
	}

	public static boolean login(String userName, String password) {
		user = new ClientRequest().login(userName, password);
		if (user == null) {
			return false;
		}
		int typeId = user.getTypeId();
		userTypeData = UserStyle.findByTypeId(typeId);
		return true;
	}

	public static void logout() {
		user = null;
		userTypeData = null;
	}

	public static void setUser(UserData user) {
		UserStatue.user = user;
	}

	public static UserData findById(int userId) {
		UserData user2 = UserStatue.getCurrentUser();
		if (user2 != null) {
			if (user2.getId() == userId) {
				return user2;
			}
		}
		List<UserData> users = UserStatue.getUsersFromServer();
		if (users == null || users.size() == 0) {
			return null;
		}
		for (UserData userData : users) {
			if (userData.getId() == userId) {
				return userData;
			}
		}
		return null;
	}

	private static List<UserData> getUsersFromServer() {
		List<UserData> allUsers = new ClientRequest().getAllUsers();
		return allUsers;
	}

	private static List<UserData> managers;

	public static List<UserData> getManagerUser() {
		if (managers == null) {
			managers = new ClientRequest().getManagerUsers();
		}
		return managers;
	}

	private static boolean loginSuccess = false;

	public static boolean checkLogin(Frame parentFrame) {
		if (UserStatue.getCurrentUser() != null) {
			return true;
		}
		loginSuccess = false;
		final ZLoginDialog loginDialog = new ZLoginDialog(parentFrame, true);
		loginDialog.addLoginButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String userName = loginDialog.getUserName();
				String password = loginDialog.getPassword();
//				System.out.println(userName+" "+password);
				boolean b = UserStatue.login(userName, password);
				if (b) {
					loginDialog.dispose();
					loginSuccess = true;
				} else {
					JOptionPane.showMessageDialog(loginDialog, "login error!");
				}
			}
		});
		loginDialog.setLocationRelativeTo(parentFrame);
		loginDialog.setVisible(true);
		return loginSuccess;
	}

	private static boolean checkUserTypeDataExist(Frame parentFrame) {
		if (userTypeData == null) {
			if (user == null) {
				boolean checkLogin = checkLogin(parentFrame);
				if (!checkLogin) {
					return false;
				}
			}
			int typeId = user.getTypeId();
			userTypeData = UserStyle.findByTypeId(typeId);
			if (userTypeData == null) {
				JOptionPane
						.showMessageDialog(parentFrame,
								"get user type from server error! \n please connect to software developer");
				return false;
			}
		}
		return true;
	}

	public static boolean checkAddDevicePermission(Frame parentFrame) {

		if (!checkUserTypeDataExist(parentFrame)) {
			return false;
		}
		return userTypeData.getAddDevicePermission() == 1;
	}

	public static boolean checkAddUserPermission(Frame parentFrame) {
		if (!checkUserTypeDataExist(parentFrame)) {
			return false;
		}
		return userTypeData.getAddUserPermission() == 1;
	}

	public static boolean checkAddManagerPermission(Frame parentFrame) {
		if (!checkUserTypeDataExist(parentFrame)) {
			return false;
		}
		return userTypeData.getAddManagerPermission() == 1;
	}

}
