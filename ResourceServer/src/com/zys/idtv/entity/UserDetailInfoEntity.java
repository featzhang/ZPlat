package com.zys.idtv.entity;

public class UserDetailInfoEntity {
	private int id;
	private String userName;
	private String QQ;
	private boolean addDeviceEnable;
	private boolean addUserEnable;
	private int typeId;

	public boolean isAddUserEnable() {
		return addUserEnable;
	}

	public void setAddUserEnable(boolean addUserEnable) {
		this.addUserEnable = addUserEnable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public boolean isAddDeviceEnable() {
		return addDeviceEnable;
	}

	public void setAddDeviceEnable(boolean addDeviceEnable) {
		this.addDeviceEnable = addDeviceEnable;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
