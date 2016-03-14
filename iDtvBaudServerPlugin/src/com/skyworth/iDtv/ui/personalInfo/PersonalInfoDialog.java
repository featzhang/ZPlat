package com.skyworth.iDtv.ui.personalInfo;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.skyworth.iDtv.dao.UserStatue;
import com.skyworth.iDtv.entity.UserData;
import com.skyworth.iDtv.net.ClientRequest;
import com.skyworth.iDtv.ui.resource.ZResourceUtils;
import com.zys.utils.ui.ZDialog;

public class PersonalInfoDialog extends ZDialog {
	private static final long serialVersionUID = 4660713581557989942L;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame jFrame = new JFrame();
		PersonalInfoDialog personalInfoManager = new PersonalInfoDialog(jFrame,
				true);
		personalInfoManager.setLocationByPlatform(true);
		UserStatue.login("Aaron", "abc.123");
		UserData user = UserStatue.getCurrentUser();
		personalInfoManager.setUserData(user);
		personalInfoManager.setVisible(true);
	}

	private JPasswordField passwordField;
	private JTextField qqField;
	private JButton cancelButton;
	private JButton sureButton;

	private JTextField userNameField;
	private UserData userData;

	public PersonalInfoDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		initComponents();
	}

	public PersonalInfoDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initComponents();
	}

	private void initComponents() {
		this.setSize(308, 224);
		JPanel backPanel = new JPanel();
		FlowLayout backPanelLayout = new FlowLayout();
		backPanelLayout.setHgap(0);
		backPanelLayout.setVgap(10);
		backPanel.setLayout(backPanelLayout);
		JPanel contentPanel = new JPanel();
		backPanel.add(contentPanel);
		backPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
		contentPanel.setBounds(0, 10, 300, 220);
		contentPanel.setBorder(BorderFactory.createEtchedBorder());
		GridBagLayout contentPanelLayout = new GridBagLayout();
		contentPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		contentPanelLayout.rowHeights = new int[] { 37, 35, 36, 30, 7 };
		contentPanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.1 };
		contentPanelLayout.columnWidths = new int[] { 58, 77, 120, 7 };
		contentPanel.setLayout(contentPanelLayout);
		contentPanel.setPreferredSize(new java.awt.Dimension(276, 169));
		JLabel userNameLabel = new JLabel();
		contentPanel.add(userNameLabel, new GridBagConstraints(0, 0, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		userNameLabel.setText(ZResourceUtils.getLabel("userName"));
		userNameField = new JTextField();
		contentPanel.add(userNameField, new GridBagConstraints(1, 0, 2, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		JLabel passwordLabel = new JLabel();
		contentPanel.add(passwordLabel, new GridBagConstraints(0, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		passwordLabel.setText(ZResourceUtils.getLabel("password"));
		passwordField = new JPasswordField();
		contentPanel.add(passwordField, new GridBagConstraints(1, 1, 2, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		JLabel qqLabel = new JLabel();
		contentPanel.add(qqLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));
		qqLabel.setText("QQ");
		qqField = new JTextField();
		contentPanel.add(qqField, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		sureButton = new JButton();
		contentPanel.add(sureButton, new GridBagConstraints(1, 3, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		sureButton.setText(ZResourceUtils.getLabel("sure"));
		sureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				sureButtonActionPerformed(evt);
			}

		});
		cancelButton = new JButton();
		contentPanel.add(cancelButton, new GridBagConstraints(2, 3, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		cancelButton.setText(ZResourceUtils.getLabel("cancel"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});
		backPanel.add(contentPanel);
		setContentPane(backPanel);
	}

	public void setUserData(UserData userData) {
		if (userData == null) {
			return;
		}
		this.userData = userData;
		userNameField.setText(userData.getName());
		passwordField.setText(userData.getPassword());
		qqField.setText(userData.getQq());
	}

	private void cancelButtonActionPerformed(ActionEvent evt) {
		dispose();
	}

	private void sureButtonActionPerformed(ActionEvent evt) {
		if (userData == null) {
			return;
		}
		String userName = userNameField.getText();
		char[] cs = passwordField.getPassword();
		String password = String.copyValueOf(cs);
		String qq = qqField.getText();
		boolean needUpdate = false;
		if (!userData.getName().equals(userName)) {
			needUpdate = true;
		}
		if (!userData.getPassword().equals(password)) {
			needUpdate = true;
		}
		if (!userData.getQq().equals(qq)) {
			needUpdate = true;
		}
		if (needUpdate) {
			String string = userData.toString();
			int confirm = JOptionPane.showConfirmDialog(this,
					"user data changed to " + string);
			if (confirm == 0) {
				userData.setName(userName);
				userData.setPassword(password);
				userData.setQq(qq);
				ArrayList<UserData> arrayList = new ArrayList<UserData>();
				userData.setOperate(UserData.MODIFY);
				arrayList.add(userData);
				new ClientRequest().addUsers(arrayList);
				userData.setOperate(null);
				UserStatue.setUser(userData);
			}
		}
		dispose();
	}
}
