package com.skyworth.iDtv.ui.userManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.table.DefaultTableModel;

import com.skyworth.iDtv.entity.UserData;

public class UserManagerTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 74082395405908246L;
	private List<UserData> userDatas;

	public List<UserData> getUserDatas() {
		return userDatas;
	}

	public void setUserDatas(List<UserData> userDatas) {
		this.userDatas = userDatas;
	}

	private String[] columnNames = { "用户名", "密码", "用户类型", "QQ" };
	private HashMap<String, Integer> typeNameMap;

	public void setUserDataList(List<UserData> userDatas) {
		this.userDatas = userDatas;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		if (userDatas == null) {
			return 0;
		}
		return userDatas.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int row, int column) {
		UserData userData = userDatas.get(row);
		switch (column) {
		case 0:
			return userData.getName();
		case 1:
			return userData.getPassword();
		case 2:
			int typeId = userData.getTypeId();
			if (typeNameMap == null) {
				return typeId + "";
			} else if (!typeNameMap.containsValue(typeId)) {
				return typeId + "";
			} else {
				Set<Entry<String, Integer>> entrySet = typeNameMap.entrySet();
				for (Entry<String, Integer> entry : entrySet) {
					Integer value = entry.getValue();
					if (value == typeId) {
						return entry.getKey();
					}
				}
				return "";
			}
		case 3:
			return userData.getQq();
		default:
			return "";
		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		UserData userData = userDatas.get(row);
		switch (column) {
		case 0:
			String string = (String) aValue;
			userData.setName(string);

			break;
		case 1:
			string = (String) aValue;
			userData.setPassword(string);
			break;
		case 2:
			String string2 = aValue.toString();
			Integer integer = typeNameMap.get(string2);
			userData.setTypeId(integer);
			break;
		case 3:
			string = (String) aValue;
			userData.setQq(string);
			break;
		}
		if (userData.getId() == null) {
			userData.setOperate(UserData.ADD);
		} else {
			userData.setOperate(UserData.MODIFY);
		}
	}

	public void addRow(UserData userData) {
		int row = getRowCount();
		if (userDatas == null) {
			userDatas = new ArrayList<UserData>();
		}
		userDatas.add(userData);
		userData.setOperate(UserData.ADD);
		fireTableRowsInserted(row, row);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return String.class;
		case 1:
			return String.class;
		case 2:
			return Integer.class;
		case 3:
			return String.class;
		}
		return super.getColumnClass(columnIndex);
	}

	public void setTypeNameMaps(HashMap<String, Integer> typeNameMap) {
		this.typeNameMap = typeNameMap;
	}

	@Override
	public void removeRow(int row) {

		int rowCount = getRowCount();
		userDatas.remove(row);
		fireTableRowsDeleted(rowCount, rowCount);
	}
}
