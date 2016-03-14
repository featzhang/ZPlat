package com.skyworth.iDtv.ui.baudServer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.skyworth.iDtv.dao.DeviceStatue;
import com.skyworth.iDtv.entity.DeviceData;
import com.skyworth.iDtv.net.ClientRequest;
import com.skyworth.iDtv.ui.ZLabelButton;
import com.skyworth.iDtv.ui.resource.ZResourceUtils;

public class BaudServerManagerDialog extends com.zys.utils.ui.ZDialog {

	private static final long serialVersionUID = 8217756262791207233L;
	private ZLabelButton deleteButton;
	private ZLabelButton addButton;
	private ZLabelButton modifyButton;
	private JTextArea commentArea;
	private JTextField ipField;
	private JList baudServerList;
	private BaudServerListModel baudServerListModel;
	private JCheckBox enableCheckBox;
	private JTextField portField;
	private ZLabelButton cancelButton;

	public BaudServerManagerDialog(JDialog owner, boolean modal) {
		super(owner, modal);
		initComponents();
		loadData();
		loadAction();
	}

	public BaudServerManagerDialog(Frame owner, boolean modal) {
		super(owner, modal);
		initComponents();
		loadData();
		loadAction();
	}

	private void loadAction() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				List<DeviceData> deviceDatas = baudServerListModel
						.getDeviceDatas();
				DeviceStatue.setDeviceDatas(deviceDatas);
				DeviceStatue.setHaveChanged(true);
				super.windowClosing(e);
			}
		});
	}

	private void loadData() {
		List<DeviceData> datas = new ClientRequest().getAllBitStreamServers();

		baudServerListModel = new BaudServerListModel();
		baudServerListModel.setDatas(datas);
		baudServerList.setModel(baudServerListModel);
		baudServerList.validate();
	}

	private void initComponents() {
		this.setSize(657, 331);
		JPanel bkPanel = new JPanel();
		JPanel contentPanel = new JPanel();

		GridBagLayout contentPanelLayout = new GridBagLayout();
		contentPanelLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		contentPanelLayout.rowHeights = new int[] { 40, 40, 160 };
		contentPanelLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		contentPanelLayout.columnWidths = new int[] { 38, 294, 104, 7 };
		contentPanel.setLayout(contentPanelLayout);
		contentPanel.setPreferredSize(new java.awt.Dimension(485, 268));
		JLabel ipLabel = new JLabel();
		contentPanel.add(ipLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0));
		ipLabel.setText("IP:");
		ipField = new JTextField();
		contentPanel.add(ipField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		enableCheckBox = new JCheckBox();
		contentPanel.add(enableCheckBox, new GridBagConstraints(2, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		enableCheckBox.setText("\u542f\u7528");
		JLabel portLabel = new JLabel();
		portLabel.setText("port:");
		contentPanel.add(portLabel, new GridBagConstraints(0, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		portField = new JTextField();
		portField.setText("port");
		contentPanel.add(portField, new GridBagConstraints(1, 1, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0));
		JLabel commentLabel = new JLabel();
		commentLabel.setText(ZResourceUtils.getLabel("comment") + ":");
		contentPanel.add(commentLabel, new GridBagConstraints(0, 2, 1, 1, 0.0,
				0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 0, 0));
		JScrollPane scrollPane1 = new JScrollPane();
		commentArea = new JTextArea();
		scrollPane1.setViewportView(commentArea);

		contentPanel.add(scrollPane1, new GridBagConstraints(1, 2, 2, 1, 0.0,
				0.0, GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH,
				new Insets(0, 0, 0, 0), 0, 0));

		JSplitPane splitPane = new JSplitPane();
		BorderLayout bkPanelLayout = new BorderLayout();
		bkPanel.setLayout(bkPanelLayout);
		bkPanel.setPreferredSize(new java.awt.Dimension(600, 293));
		bkPanel.add(splitPane, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		FlowLayout jPanel1Layout = new FlowLayout();
		jPanel1Layout.setAlignment(FlowLayout.LEFT);
		jPanel1Layout.setHgap(0);
		jPanel1Layout.setVgap(0);
		buttonPanel.setLayout(jPanel1Layout);
		bkPanel.add(buttonPanel, BorderLayout.NORTH);
		addButton = new ZLabelButton();
		buttonPanel.add(addButton);
		addButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				addButtonMouseClicked(evt);
			}
		});
		addButton.setText(ZResourceUtils.getLabel("add"));
		deleteButton = new ZLabelButton();
		buttonPanel.add(deleteButton);
		deleteButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				deleteButtonMouseClicked(evt);
			}
		});
		deleteButton.setText(ZResourceUtils.getLabel("delete"));
		modifyButton = new ZLabelButton();
		buttonPanel.add(modifyButton);
		modifyButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				modifyButtonMouseClicked(evt);
			}
		});
		modifyButton.setText(ZResourceUtils.getLabel("modify"));
		splitPane.setPreferredSize(new java.awt.Dimension(404, 297));
		splitPane.setDividerSize(1);

		cancelButton = new ZLabelButton();
		cancelButton.setEnabled(false);
		cancelButton.setText(ZResourceUtils.getLabel("cancel"));
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cancelButtonClicked();
			}
		});

		buttonPanel.add(cancelButton);
		baudServerList = new JList();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(80, 120));
		scrollPane.setViewportView(baudServerList);
		baudServerList.setPreferredSize(new java.awt.Dimension(70, 266));
		splitPane.add(scrollPane, JSplitPane.LEFT);

		baudServerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				baudServerListValueChanged(evt);
			}
		});
		splitPane.add(contentPanel, JSplitPane.RIGHT);
		bkPanel.add(splitPane);
		setContentPane(bkPanel);
		ipField.setEditable(false);
		enableCheckBox.setEnabled(false);
		portField.setEditable(false);
		commentArea.setEditable(false);
	}

	private void clearAllField() {
		ipField.setText(null);
		enableCheckBox.setSelected(false);
		portField.setText(null);
		commentArea.setText(null);
	}

	private void setAllFieldEditable(boolean b) {
		ipField.setEditable(b);
		enableCheckBox.setEnabled(b);
		portField.setEditable(b);
		commentArea.setEditable(b);
	}

	private void deleteButtonMouseClicked(MouseEvent evt) {
		int selectedIndex = baudServerList.getSelectedIndex();
		if (selectedIndex == -1) {
			JOptionPane.showMessageDialog(this, "not select list !");
			return;
		}
		int i = JOptionPane.showConfirmDialog(this, "delete ?");
		if (i == 0) {
			DeviceData deviceData = baudServerListModel
					.getDeviceDataAt(selectedIndex);
			new ClientRequest().removeBaudServerDevice(deviceData);
			baudServerListModel.removeElementAt(selectedIndex);
			baudServerList.updateUI();
			changed = true;
		}
	}

	private void addButtonMouseClicked(MouseEvent evt) {
		if (!inAddMode) {
			inAddMode = true;
			addButton.setText(ZResourceUtils.getLabel("save"));
			cancelButton.setEnabled(true);
			modifyButton.setEnabled(false);
			deleteButton.setEnabled(false);

			DeviceData deviceData = new DeviceData();
			baudServerListModel.addElement(deviceData);
			baudServerList.updateUI();
			int size = baudServerListModel.getSize();
			baudServerList.setSelectedIndex(size - 1);
			baudServerList.setEnabled(false);
			setAllFieldEditable(true);
			clearAllField();
		} else {
			inAddMode = false;
			cancelButton.setEnabled(false);
			addButton.setText(ZResourceUtils.getLabel("add"));
			modifyButton.setEnabled(true);
			deleteButton.setEnabled(true);
			setAllFieldEditable(false);
			int index = baudServerList.getSelectedIndex();
			DeviceData deviceData = baudServerListModel.getDeviceDataAt(index);
			String ip = ipField.getText();
			boolean selected = enableCheckBox.isSelected();
			String port = portField.getText();
			String comment = commentArea.getText();
			deviceData.setIp(ip);
			deviceData.setPort(port);
			deviceData.setComment(comment);
			deviceData.setUsable(selected ? 1 : 0);
			baudServerList.setEnabled(true);
			baudServerList.updateUI();
			new ClientRequest().addNewBitStreamServer(deviceData);
			changed = true;
		}
	}

	private boolean inAddMode = false;
	private boolean inModify = false;
	private boolean changed = false;

	private void modifyButtonMouseClicked(MouseEvent evt) {
		if (!inModify) {
			// update list
			int selectedIndex = baudServerList.getSelectedIndex();
			if (selectedIndex == -1) {
				JOptionPane.showMessageDialog(this, "not select list ! ");
				return;
			}
			inModify = true;
			modifyButton.setText(ZResourceUtils.getLabel("save"));
			cancelButton.setEnabled(true);
			addButton.setEnabled(false);
			deleteButton.setEnabled(false);
			setAllFieldEditable(true);
			baudServerList.setEnabled(false);
		} else {
			// save
			inModify = false;
			int selectedIndex = baudServerList.getSelectedIndex();
			DeviceData deviceData = baudServerListModel
					.getDeviceDataAt(selectedIndex);
			String ip = ipField.getText();
			boolean selected = enableCheckBox.isSelected();
			String port = portField.getText();
			String comment = commentArea.getText();
			deviceData.setIp(ip);
			deviceData.setPort(port);
			deviceData.setComment(comment);
			deviceData.setUsable(selected ? 1 : 0);
			new ClientRequest().updateBaudStreamServerData(deviceData);
			baudServerList.validate();
			baudServerList.updateUI();

			cancelButton.setEnabled(false);
			modifyButton.setText(ZResourceUtils.getLabel("modify"));
			addButton.setEnabled(true);
			deleteButton.setEnabled(true);
			clearAllField();
			baudServerList.setEnabled(true);
			setAllFieldEditable(false);
			showDeviceData(deviceData);
			changed = true;
		}
	}

	private void showDeviceData(DeviceData deviceData) {
		if (deviceData != null) {
			ipField.setText(deviceData.getIp());
			enableCheckBox.setSelected(deviceData.getUsable() == 1);
			portField.setText(deviceData.getPort());
			commentArea.setText(deviceData.getComment());
			ipField.setEditable(false);
			enableCheckBox.setEnabled(false);
			portField.setEditable(false);
			commentArea.setEditable(false);
		} else {
			JOptionPane.showMessageDialog(this,
					"device date of selected is empty!");
		}
	}

	private void baudServerListValueChanged(ListSelectionEvent evt) {
		if (evt.getValueIsAdjusting()) {
			JList list = (JList) evt.getSource();
			int lastIndex = list.getSelectedIndex();
			DeviceData deviceData = baudServerListModel
					.getDeviceDataAt(lastIndex);
			showDeviceData(deviceData);
		}
	}

	private void cancelButtonClicked() {
		int selectedIndex = baudServerList.getSelectedIndex();
		if (inAddMode) {
			int index = selectedIndex;
			baudServerListModel.removeElementAt(index);
			baudServerList.validate();
			baudServerList.updateUI();
			inAddMode = false;
			setAllFieldEditable(false);
			clearAllField();
			addButton.setText(ZResourceUtils.getLabel("add"));
			modifyButton.setEnabled(true);
			deleteButton.setEnabled(true);
			baudServerList.setEnabled(true);
		}
		if (inModify) {
			inModify = false;
			addButton.setEnabled(true);
			deleteButton.setEnabled(true);
			baudServerList.setEnabled(true);
			clearAllField();
			setAllFieldEditable(false);
			modifyButton.setText(ZResourceUtils.getLabel("modify"));
			if (selectedIndex != -1) {
				DeviceData deviceData = baudServerListModel
						.getDeviceDataAt(selectedIndex);
				showDeviceData(deviceData);
			}
			cancelButton.setEnabled(false);
		}
	}

	public boolean isChanged() {
		return changed;
	}

}
