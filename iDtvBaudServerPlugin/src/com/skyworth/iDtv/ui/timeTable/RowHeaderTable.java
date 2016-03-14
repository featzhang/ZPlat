package com.skyworth.iDtv.ui.timeTable;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import com.skyworth.iDtv.dao.DeviceStatue;
import com.skyworth.iDtv.dao.TimeTableStatue;
import com.skyworth.iDtv.dao.UserStatue;
import com.skyworth.iDtv.entity.DeviceData;
import com.skyworth.iDtv.ui.baudServer.BaudServerManagerDialog;
import com.skyworth.iDtv.ui.baudServer.SimpleInfoDialog;
import com.skyworth.iDtv.ui.resource.ZResourceUtils;

public class RowHeaderTable extends JTable {
	private static final long serialVersionUID = -7996638038272740632L;
	private ContentTable contentTable;
	private Frame parentFrame;

	// private JTable refTable;// 需要添加rowHeader的JTable

	public RowHeaderTable(JFrame parentFrame, JTable refTable) {
		super(new RowHeaderTableModel());
		this.parentFrame = parentFrame;
		this.contentTable = (ContentTable) refTable;
		// this.refTable = refTable;
		// this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//不可以调整列宽
		this.setDefaultRenderer(Object.class, new RowHeaderRenderer(refTable,
				this));// 设置渲染器
		this.setPreferredScrollableViewportSize(new Dimension(200, 0));
		setRowHeight(40);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.getColumnModel().getColumn(0).setPreferredWidth(300);
		this.getColumnModel().getColumn(1).setPreferredWidth(100);
		loadAcion();
	}

	private void loadAcion() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// System.out.println("mouseReleased");
				// super.mouseReleased(e);
				if (e.getButton() == MouseEvent.BUTTON3) {
					Point point = e.getPoint();
					int rowAtPoint = rowAtPoint(point);
					RowHeaderTable rowHeaderTable = (RowHeaderTable) e
							.getSource();
					RowHeaderTableModel rowHeaderTableModel = (RowHeaderTableModel) (rowHeaderTable
							.getModel());
					DeviceData valueAt = rowHeaderTableModel
							.getRowData(rowAtPoint);
					JPopupMenu popupMenu = getHeadPopupMenu(valueAt);
					if (popupMenu != null) {
						popupMenu.show(rowHeaderTable, e.getX(), e.getY());
					}
				}
			}
		});

	}

	protected JPopupMenu getHeadPopupMenu(DeviceData valueAt) {
		if (!UserStatue.checkLogin(parentFrame)) {
			return null;
		}
		JPopupMenu popupMenu2 = new JPopupMenu();
		JMenuItem reserverThisWeekMenuItem = new JMenuItem();
		reserverThisWeekMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				contentTable.reserveMenuItemActionPerformed(e);
			}
		});
		reserverThisWeekMenuItem.setText(ZResourceUtils
				.getLabel("reserverThisWeek"));

		popupMenu2.add(reserverThisWeekMenuItem);

		popupMenu2.addSeparator();
		final JMenuItem deviceDetailMenuItem = new JMenuItem();
		deviceDetailMenuItem.setText(ZResourceUtils.getLabel("deviceDetail"));
		deviceDetailMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deviceDetailMenuItemActionPerformed(e);
			}
		});
		popupMenu2.add(deviceDetailMenuItem);

		if (UserStatue.checkAddDevicePermission(parentFrame)) {

			popupMenu2.addSeparator();
			final JCheckBoxMenuItem layupMenuItem = new JCheckBoxMenuItem();
			layupMenuItem.setText(ZResourceUtils.getLabel("commissioned"));
			int selectedRow = getSelectedRow();
			if (selectedRow != -1) {
				RowHeaderTableModel rowHeaderTableModel = (RowHeaderTableModel) getModel();
				DeviceData rowData = rowHeaderTableModel
						.getRowData(selectedRow);
				layupMenuItem.setSelected(rowData.getUsable() == 1);
			}

			layupMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					layupMenuItemActionPerformed(layupMenuItem.isSelected());
				}
			});
			popupMenu2.add(layupMenuItem);

			final JMenuItem deviceManagerMenuItem = new JMenuItem();
			deviceManagerMenuItem.setText(ZResourceUtils
					.getLabel("baudServerManagement"));
			deviceManagerMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deviceManagerMenuItemActionPerformed();
				}
			});
			// popupMenu2.add(deviceManagerMenuItem);

			JMenuItem modifyDeviceMenuItem = new JMenuItem();
			modifyDeviceMenuItem.setText(ZResourceUtils
					.getLabel("modifyDevice"));
			// popupMenu2.add(modifyDeviceMenuItem);

			JMenuItem addDeviceMenuItem = new JMenuItem();
			addDeviceMenuItem.setText(ZResourceUtils.getLabel("addDevice"));
			// popupMenu2.add(addDeviceMenuItem);
		}
		return popupMenu2;
	}

	private void deviceManagerMenuItemActionPerformed() {
		BaudServerManagerDialog dialog = new BaudServerManagerDialog(
				parentFrame, true);
		dialog.setLocationRelativeTo(parentFrame);
		dialog.setVisible(true);
	}

	protected void deviceDetailMenuItemActionPerformed(ActionEvent e) {
		int selectedRow = getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(parentFrame, "Not select any row !");
			return;
		}
		RowHeaderTableModel rowHeaderTableModel = (RowHeaderTableModel) getModel();
		DeviceData deviceData = rowHeaderTableModel.getRowData(selectedRow);
		SimpleInfoDialog simpleInfoDialog = new SimpleInfoDialog(parentFrame,
				true);
		simpleInfoDialog.setDeviceData(deviceData);
		simpleInfoDialog.setLocationRelativeTo(parentFrame);
		simpleInfoDialog.setVisible(true);
	}

	private void layupMenuItemActionPerformed(boolean b) {
		int selectedRow = getSelectedRow();
		RowHeaderTableModel rowTableModel = (RowHeaderTableModel) getModel();
		if (selectedRow == -1) {
			return;
		}
		DeviceData rowData = rowTableModel.getRowData(selectedRow);
		DeviceStatue.setDeviceEnable(rowData, b);
		DeviceStatue.updateFromServer();
		TimeTableStatue.updateFromServer();
		
		rowTableModel.fireTableStructureChanged();
		contentTable.fireTableStructureChanged();
	}
}