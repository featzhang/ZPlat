package com.zys.idtv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usertype", schema = "", catalog = "idtv")
public class UserTypeEntity implements Serializable {
	private int id;
	private String typeName;
	private int addDevicePermission;

	private int addUserPermission;
	private int addManagerPermission;
	@Id
	@Column(name = "addUserPermission")
	public int getAddUserPermission() {
		return addUserPermission;
	}

	public void setAddUserPermission(int addUserPermission) {
		this.addUserPermission = addUserPermission;
	}
	@Id
	@Column(name = "addManagerPermission")
	public int getAddManagerPermission() {
		return addManagerPermission;
	}

	public void setAddManagerPermission(int addManagerPermission) {
		this.addManagerPermission = addManagerPermission;
	}
	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Id
	@Column(name = "type_name")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Id
	@Column(name = "addDevicePermission")
	public int getAddDevicePermission() {
		return addDevicePermission;
	}

	public void setAddDevicePermission(int addDevicePermission) {
		this.addDevicePermission = addDevicePermission;
	}
}
