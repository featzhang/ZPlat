package com.skyworth.iDtv.ui.baudServer;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.skyworth.iDtv.entity.DeviceData;
import com.zys.utils.ui.ZDialog;

import java.awt.BorderLayout;

public class SimpleInfoDialog extends ZDialog {

	private static final long serialVersionUID = 3541119646949805912L;
	private JPanel panel;
	private JLabel commentLabel;
	private JScrollPane jScrollPane1;
	private JTextArea commentArea;
	private JTextField portField;
	private JLabel portLabel;
	private JTextField ipField;
	private JLabel ipLabel;

	public SimpleInfoDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		initComponents();
	}

	public SimpleInfoDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initComponents();
	}

	public void setDeviceData(DeviceData deviceData) {
		ipField.setText(deviceData.getIp());
		portField.setText(deviceData.getPort());
		commentArea.setText(deviceData.getComment());
	}

	private void initComponents() {
		panel = new JPanel();
		GridBagLayout panelLayout = new GridBagLayout();
		getContentPane().add(panel, BorderLayout.CENTER);
		panelLayout.rowWeights = new double[] {0.1, 0.1, 0.1, 0.0, 0.0};
		panelLayout.rowHeights = new int[] {7, 7, 7, 105, 17};
		panelLayout.columnWeights = new double[] {0.0, 0.0, 0.0, 0.0};
		panelLayout.columnWidths = new int[] {61, 183, 109, 26};
		panel.setLayout(panelLayout);
		panel.setOpaque(false);
		panel.setPreferredSize(new java.awt.Dimension(416, 252));
		ipLabel = new JLabel();
		panel.add(ipLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));
		ipLabel.setText("ip:");
		ipField = new JTextField();
		panel.add(ipField, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		ipField.setOpaque(false);
		ipField.setEditable(false);
		portLabel = new JLabel();
		panel.add(portLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));
		portLabel.setText("port:");
		portField = new JTextField();
		panel.add(portField, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		portField.setOpaque(false);
		portField.setEditable(false);
		commentLabel = new JLabel();
		panel.add(commentLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		commentLabel.setText("comment:");
		jScrollPane1 = new JScrollPane();
		panel.add(jScrollPane1, new GridBagConstraints(1, 2, 2, 2, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));
		commentArea = new JTextArea();
		jScrollPane1.setViewportView(commentArea);
		commentArea.setOpaque(false);
		commentArea.setEditable(false);
		this.setSize(428, 290);
	}

}
