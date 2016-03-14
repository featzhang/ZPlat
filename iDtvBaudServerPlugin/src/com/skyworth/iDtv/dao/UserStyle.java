package com.skyworth.iDtv.dao;

import java.util.List;

import com.skyworth.iDtv.entity.UserTypeData;
import com.skyworth.iDtv.net.ClientRequest;

public class UserStyle {
	private static List<UserTypeData> uList = null;

	public List<UserTypeData> getUserStyleFromServer() {
		uList = new ClientRequest().getUserTypes();
		return uList;
	}

	public static List<UserTypeData> getUserStyles() {
		if (uList == null) {
			uList = new ClientRequest().getUserTypes();
		}
		return uList;
	}

	public static UserTypeData findByTypeId(int id) {
		List<UserTypeData> userStyles = getUserStyles();
		if (userStyles == null) {
			return null;
		}
		for (UserTypeData userTypeData : userStyles) {
			int id2 = userTypeData.getId();
			if (id2 == id) {
				return userTypeData;
			}
		}
		return null;
	}
}
