package com.skyworth.iDtv.ui.timeTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RowHeaderRenderer extends JLabel implements TableCellRenderer,
		ListSelectionListener {
	private static final long serialVersionUID = -7166614928830806114L;
	JTable reftable;// 需要添加rowHeader的JTable
	JTable tableShow;// 用于显示rowHeader的JTable

	public RowHeaderRenderer(final JTable contentTable, final JTable tableShow) {
		this.reftable = contentTable;
		this.tableShow = tableShow;
		// 增加监听器，实现当在reftable中选择行时，RowHeader会发生颜色变化
		ListSelectionModel listModel = contentTable.getSelectionModel();
		listModel.addListSelectionListener(this);
		ListSelectionModel selectionModel = tableShow.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = tableShow.getSelectedRow();
				if (selectedRow == -1) {
					return;
				}
				contentTable.setRowSelectionInterval(selectedRow, selectedRow);
				contentTable.setColumnSelectionInterval(0, 6);
			}
		});
	}

	public Component getTableCellRendererComponent(JTable table, Object obj,
			boolean isSelected, boolean hasFocus, int row, int col) {
		JTableHeader header = reftable.getTableHeader();
		this.setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));// 设置为TableHeader的边框类型
		setHorizontalAlignment(CENTER);// 让text居中显示
		setBackground(header.getBackground());// 设置背景色为TableHeader的背景色
		if (isSelect(row)) // 当选取单元格时,在row header上设置成选取颜色
		{
			setForeground(Color.white);
			setBackground(Color.lightGray);
		} else {
			setForeground(header.getForeground());
		}
		setFont(header.getFont());
		setText(obj + "");
		setSize(100, 40);
		return this;
	}

	public void valueChanged(ListSelectionEvent e) {
		this.tableShow.repaint();
	}

	private boolean isSelect(int row) {
		int[] sel = reftable.getSelectedRows();
		for (int aSel : sel)
			if (aSel == row)
				return true;
		return false;
	}
}
