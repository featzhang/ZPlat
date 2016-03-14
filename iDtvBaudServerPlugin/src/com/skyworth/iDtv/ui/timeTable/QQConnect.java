package com.skyworth.iDtv.ui.timeTable;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.skyworth.iDtv.entity.UserData;
import com.skyworth.iDtv.ui.resource.ZResourceUtils;
import com.zys.utils.ui.ZDialog;

public class QQConnect {
	public static void chatWithQQ(String qqNumber) {
		String ss = "cmd /c @start tencent://message/?uin=" + qqNumber;
		try {
			Runtime.getRuntime().exec(ss);
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}
	}

	private static int result = -1;
	public static final int OK_RESULT = 0;
	public static final int CANCEL_RESULT = -1;

	public static int show(Frame frame, String title, List<UserData> userDatas) {
		result = CANCEL_RESULT;
		Vector<Vector<String>> strings = new Vector<Vector<String>>();
		Vector<String> column = new Vector<String>();
		column.add("name");
		column.add("qq");
		for (int i = 0; i < userDatas.size(); i++) {
			UserData userData = userDatas.get(i);
			String name2 = userData.getName();
			String qq = userData.getQq();
			Vector<String> strings2 = new Vector<String>();
			strings2.add(name2);
			strings2.add(qq);
			strings.add(strings2);
		}
		final JTable jTable = new JTable(strings, column);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(jTable);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		JButton sureButton = new JButton();
		sureButton.setText(ZResourceUtils.getLabel("sure"));
		JPanel southPanel = new JPanel();
		southPanel.add(sureButton);
		mainPanel.add(southPanel, BorderLayout.SOUTH);

		final ZDialog dialog = new ZDialog(frame, true);
		sureButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = jTable.getSelectedRow();
				if (selectedRow == -1) {
					selectedRow = 0;
				}
				String valueAt = (String) jTable.getValueAt(selectedRow, 1);
				if (valueAt == null || valueAt.trim().length() == 0) {
					return;
				}
				chatWithQQ(valueAt);
				result = OK_RESULT;
				dialog.dispose();
			}
		});
		dialog.setTitle(title);
		dialog.pack();
		dialog.setSize(300, 300);
		dialog.setLocationRelativeTo(frame);
		dialog.add(mainPanel);
		dialog.setVisible(true);
		return result;
	}

	public static int show(Frame parentFrame, List<UserData> userDatas) {
		return show(parentFrame, "chat with qq", userDatas);
	}
}
