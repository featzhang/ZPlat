package com.zys.utils.ui;

import javax.swing.*;

public class ModifyPasswordDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = -2076622215332327531L;

	private JPanel outer;

	private JLabel one;

	private JLabel three;

	private JPasswordField pw3;

	private JPasswordField pw2;

	private JPasswordField pw1;

	private JLabel two;

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				ModifyPasswordDialog inst = new ModifyPasswordDialog(null);

				inst.setResizable(false);

				inst.setLocationRelativeTo(null);

				inst.setVisible(true);

			}

		});

	}

	public ModifyPasswordDialog(JFrame frame) {

		super(frame);

		initGUI();

	}

	private void initGUI() {

		try {

			setTitle("修改密码：");

			getContentPane().setLayout(null);

			{

				outer = new JPanel();

				getContentPane().add(outer);

				outer.setBounds(41, 34, 313, 194);

				outer.setBorder(BorderFactory.createTitledBorder("修改信息:"));

				outer.setLayout(null);

				{

					one = new JLabel();

					outer.add(one);

					one.setText("输入原密码:");

					one.setBounds(44, 56, 69, 15);

				}

				{

					two = new JLabel();

					outer.add(two);

					two.setText("输入新密码:");

					two.setBounds(44, 90, 69, 15);

				}

				{

					three = new JLabel();

					outer.add(three);

					three.setText("重复原密码:");

					three.setBounds(44, 126, 69, 15);

				}

				{

					pw1 = new JPasswordField();

					outer.add(pw1);

					pw1.setText("");

					pw1.setBounds(119, 52, 133, 22);

				}

				{

					pw2 = new JPasswordField();

					outer.add(pw2);

					pw2.setText("");

					pw2.setBounds(119, 86, 133, 22);

				}

				{

					pw3 = new JPasswordField();

					outer.add(pw3);

					pw3.setText("");

					pw3.setBounds(119, 122, 133, 22);

				}

			}

			setSize(400, 300);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}