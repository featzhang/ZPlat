package com.skyworth.iDtv.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skyworth.iDtv.entity.UserData;
import com.skyworth.iDtv.net.ClientRequest;

public class UserManager {
	public static void uploadModifies(List<UserData> userDatas) {
		if (userDatas == null || userDatas.size() <= 0) {
			return;
		}
		Map<Integer, UserData> userMap = new HashMap<Integer, UserData>();
		for (int i = 0; i < userDatas.size(); i++) {
			UserData userData = userDatas.get(i);
			Integer id = userData.getId();
			if (id != null) {
				userMap.put(id, userData);
			}
		}

		List<UserData> serverUserDatas = new ClientRequest().getAllUsers();
		for (int i = 0; i < serverUserDatas.size(); i++) {
			UserData userData = serverUserDatas.get(i);
			Integer id = userData.getId();
			if (!userMap.containsKey(id)) {
				userData.setOperate(UserData.DELETE);
				userDatas.add(userData);
			}
		}
		new ClientRequest().addUsers(userDatas);
	}
}
