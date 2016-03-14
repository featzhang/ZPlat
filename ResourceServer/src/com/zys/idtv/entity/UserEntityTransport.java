package com.zys.idtv.entity;

public class UserEntityTransport {
	private Integer id;
	private String name;
	private String password;
	private int typeId;
	private String qq;
	private String operate;
	public static final String ADD = "add";
	public static final String DELETE = "delete";
	public static final String MODIFY = "modify";

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		UserEntityTransport userData = (UserEntityTransport) obj;
		if (!name.equals(userData.getName())) {
			return false;
		}
		if (!password.equals(userData.getPassword())) {
			return false;
		}
		if (typeId != userData.getTypeId()) {
			return false;
		}
		if (qq.equals(userData.getQq())) {
			return false;
		}
		return super.equals(obj);
	}

	@Override
	public String toString() {
		String string = "";
		string += " name:" + name;
		string += " password:" + password;
		string += " typeId:" + typeId;
		string += " QQ:" + qq;
		return string;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qqNumber) {
		this.qq = qqNumber;
	}

	public boolean CheckDataValidity() {
		if (name == null || name.trim().length() == 0) {
			return false;
		}
		if (password == null || password.trim().length() < 6) {
			return false;
		}
		return true;
	}

	public UserEntity toUserEntity() {
		UserEntity userEntity = new UserEntity();
		if (id!=null) {
			userEntity.setId(id);
		}
		userEntity.setName(name);
		userEntity.setPassword(password);
		userEntity.setQq(qq);
		userEntity.setTypeId(typeId);
		return userEntity;
	}
}
