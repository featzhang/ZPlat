package com.zys.utils.ui;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.zys.utils.resource.ZResourceUtils;

public class ZLoginDialog extends ZDialog {
	public static void main(String[] args) {
		ZLoginDialog loginDialog = new ZLoginDialog();
		loginDialog.setLocationRelativeTo(null);
		loginDialog.setVisible(true);
	}

	private static final long serialVersionUID = 6752864464532565077L;
	private int _width = 400;
	private int _height = 400;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JButton resetButton;
	private JButton closeButton;

	private JButton loginButton;

	public ZLoginDialog() {
		super();
		initComponents();
	}

	public ZLoginDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		initComponents();
	}

	public ZLoginDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initComponents();
	}

	private void initComponents() {
		ImageIcon image = ZResourceUtils.getImageIcon("loginBackground");
		Image image2 = image.getImage();
		_width = image2.getWidth(null);
		_height = image2.getHeight(null);
		setSize(_width, _height);
		setUndecorated(true);
		Container contentPane = getContentPane();
		ZPanel zPanel = new ZPanel();
		zPanel.setIcon(image);
		contentPane.add(zPanel);
		zPanel.setLayout(null);

		userNameField = new JTextField();
		userNameField.setBorder(null);
		userNameField.setBounds(185, 122, 214, 21);
		zPanel.add(userNameField);
		userNameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBorder(null);
		passwordField.setBounds(186, 167, 210, 21);
		zPanel.add(passwordField);

		loginButton = new JButton("");
		loginButton.setIcon(ZResourceUtils.getImageIcon("login_loginButton"));

		loginButton.setContentAreaFilled(false);
		loginButton.setBounds(185, 227, 54, 24);
		zPanel.add(loginButton);

		resetButton = new JButton("");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userNameField.setText("");
				passwordField.setText("");
			}
		});
		resetButton.setIcon(ZResourceUtils.getImageIcon("login_resetButton"));
		resetButton.setContentAreaFilled(false);
		resetButton.setBounds(340, 227, 54, 24);
		zPanel.add(resetButton);

		closeButton = new JButton("");
		closeButton.setContentAreaFilled(false);
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ZLoginDialog.this.dispose();
			}
		});
		closeButton.setIcon(ZResourceUtils.getImageIcon("login_closeButton"));
		closeButton.setContentAreaFilled(false);
		closeButton.setBounds(479, 10, 45, 45);
		zPanel.add(closeButton);
		// zPanel.setLayou)
	}

	public String getUserName() {
		return userNameField.getText();
	}

	public void addLoginButtonActionListener(ActionListener l) {
		loginButton.addActionListener(l);
	}

	@SuppressWarnings("deprecation")
	public String getPassword() {
		return passwordField.getText();
	}
}
