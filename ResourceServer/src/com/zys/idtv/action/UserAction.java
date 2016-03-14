package com.zys.idtv.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.zys.idtv.entity.UserEntity;
import com.zys.idtv.entity.UserEntityTransport;
import com.zys.idtv.entity.UserTypeEntity;
import com.zys.idtv.ifc.UserIfc;
import com.zys.idtv.impl.UserImpl;

public class UserAction extends ActionSupport {
	Logger logger = Logger.getLogger(UserAction.class.getName());
	private static final long serialVersionUID = -1352223922589600690L;
	private List<UserEntity> userListJSON;
	private String updateEntityString;
	private List<UserTypeEntity> userTypeList;

	private String postContent;
	private UserEntity userEntityJson;

	public UserEntity getUserEntityJson() {
		return userEntityJson;
	}

	public void setUserEntityJson(UserEntity userEntityJson) {
		this.userEntityJson = userEntityJson;
	}

	public void addUsers() {
		logger.debug("==========addUsers======================");

		String string = postContent;
		logger.debug("InputStream: " + string);
		List<UserEntityTransport> parseArray = JSON.parseArray(string,
				UserEntityTransport.class);
		UserIfc userIfc = new UserImpl();
		if (parseArray != null) {
			for (UserEntityTransport userEntity1 : parseArray) {
				String operate = userEntity1.getOperate();
				if (operate == null) {
					continue;
				}
				if (operate.equals(UserEntityTransport.ADD)) {
					userIfc.addUser(userEntity1.toUserEntity());
				} else if (operate.equals(UserEntityTransport.DELETE)) {
					userIfc.remove(userEntity1.toUserEntity());
				} else if (operate.equals(UserEntityTransport.MODIFY)) {
					userIfc.updateUser(userEntity1.toUserEntity());
				}
				logger.debug("addUsers: " + userEntity1);
			}
		}
	}

	public String checkLogin() {
		if (postContent != null) {
			UserEntity userEntity = JSON.parseObject(postContent,
					UserEntity.class);
			String name = userEntity.getName();
			String password = userEntity.getPassword();
			UserIfc userIfc = new UserImpl();
			userEntity = userIfc.getUserEntity(name, password);
			userEntityJson=userEntity;
		}
		return "success";
	}

	public String getPostContent() {
		return postContent;
	}

	public String getUpdateEntity() {
		InputStream inputStream = null;
		String filePath = obtainTheActualUpdateConfigFilePath();
		File file = new File(filePath);
		if (StringUtils.isEmpty(file.getName()))
			return SUCCESS;
		BufferedReader bufferedReader = null;
		try {
			inputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			String string = "";
			updateEntityString = "";
			while ((string = bufferedReader.readLine()) != null) {
				updateEntityString += string;
			}
		} catch (Exception e) {
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
					bufferedReader.close();
				}

			} catch (Exception e) {
			}
		}
		return "success";
	}

	public String getUpdateEntityString() {
		return updateEntityString;
	}

	public String getUserDetailInfo() {
		return "success";
	}

	public String getUserList() {
		logger.debug("==========getUserList============");
		UserImpl userImpl = new UserImpl();
		userListJSON = userImpl.getAllUsers();
		return "success";
	}
	public String getManagerUsers() {
		logger.debug("=====getManagerUsers========");
		UserImpl userImpl = new UserImpl();
		userListJSON = userImpl.getManagerUsers();
		return "success";
	}

	public List<UserEntity> getUserListJSON() {
		return userListJSON;
	}

	public String getUserType() {
		logger.debug("=======getUserType=========");
		UserIfc userIfc = new UserImpl();
		userTypeList = userIfc.getUserTypeList();
		return "success";
	}

	public List<UserTypeEntity> getUserTypeList() {
		return userTypeList;
	}

	private String obtainTheActualUpdateConfigFilePath() {
		return "D:\\abc.txt";
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public void setUpdateEntityString(String updateEntityString) {
		this.updateEntityString = updateEntityString;
	}

	public void setUserListJSON(List<UserEntity> userListJSON) {
		this.userListJSON = userListJSON;
	}

	public void setUserTypeList(List<UserTypeEntity> userTypeList) {
		this.userTypeList = userTypeList;
	}
}
