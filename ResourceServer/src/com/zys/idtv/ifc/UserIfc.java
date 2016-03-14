package com.zys.idtv.ifc;

import com.zys.idtv.entity.UserEntity;
import com.zys.idtv.entity.UserTypeEntity;

import java.util.List;

public interface UserIfc {
	public void addUser(UserEntity user);

	UserEntity findUserById(int id);

	UserEntity findUserByName(String name);

	void remove(UserEntity user);

	void saveUser(UserEntity user);

	void updateUser(UserEntity user);

	List<UserEntity> getAllUsers();

	public List<UserTypeEntity> getUserTypeList();

	public UserEntity getUserEntity(String name, String password);

	public List<UserEntity> getManagerUsers();
}
