package com.skyworth.iDtv.net;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.skyworth.iDtv.entity.DeviceData;
import com.skyworth.iDtv.entity.TimeTableData;
import com.skyworth.iDtv.entity.UserData;
import com.skyworth.iDtv.entity.UserTypeData;


public class ClientRequest {

	public void addUsers(List<UserData> needAddedList) {
		String url = NetConfig.getResourceServer() + "addUsers.action";
		String content = JSON.toJSONString(needAddedList);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		HttptUtil.HttpPostWithoutResult(url, content, parameter);
	}

	public List<UserData> getAllUsers() {
		String url = NetConfig.getResourceServer() + "getUserList.action";
		String content = null;
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		String httpPost = HttptUtil.HttpPost(url, content, parameter);
		List<UserData> parseArray = JSON.parseArray(httpPost, UserData.class);
		return parseArray;
	}

	public List<UserTypeData> getUserTypes() {
		String url = NetConfig.getResourceServer() + "getUserType.action";
		String content = null;
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		String httpPost = HttptUtil.HttpPost(url, content, parameter);
		List<UserTypeData> parseArray = JSON.parseArray(httpPost,
				UserTypeData.class);
		return parseArray;
	}

	public UserData login(String userName, String password) {
		String url = NetConfig.getResourceServer() + "checkLogin.action";
		UserData userData = new UserData();
		userData.setName(userName);
		userData.setPassword(password);
		String content = JSON.toJSONString(userData);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		String httpPost = HttptUtil.HttpPost(url, content, parameter);
		if (httpPost == null || httpPost.trim().length() == 0) {
			return null;
		}
		UserData parseObject = JSON.parseObject(httpPost, UserData.class);
		return parseObject;
	}

	public List<DeviceData> getAllBitStreamServers() {
		String url = NetConfig.getResourceServer()
				+ "getAllBitStreamServers.action";
		String content = null;
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		String httpPost = HttptUtil.HttpPost(url, content, parameter);
		if (httpPost == null || httpPost.trim().length() == 0) {
			return null;
		}
		List<DeviceData> parseArray = JSON.parseArray(httpPost,
				DeviceData.class);
		return parseArray;
	}

	public void addNewBitStreamServer(DeviceData deviceData) {
		String url = NetConfig.getResourceServer()
				+ "addNewBitStreamServer.action";
		String content = JSON.toJSONString(deviceData);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		HttptUtil.HttpPostWithoutResult(url, content, parameter);
	}

	public void removeBaudServerDevice(DeviceData deviceData) {
		String url = NetConfig.getResourceServer()
				+ "removeBitStreamServer.action";
		String content = JSON.toJSONString(deviceData);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		HttptUtil.HttpPostWithoutResult(url, content, parameter);
	}

	public void updateBaudStreamServerData(DeviceData deviceData) {
		String url = NetConfig.getResourceServer()
				+ "updateBitStreamServer.action";
		String content = JSON.toJSONString(deviceData);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		HttptUtil.HttpPostWithoutResult(url, content, parameter);
	}

	public List<TimeTableData> getAllTimeTableDatas() {
		String url = NetConfig.getResourceServer()
				+ "getAllTimeTableDatas.action";
		String content = null;
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		String httpPost = HttptUtil.HttpPost(url, content, parameter);
		if (httpPost == null || httpPost.trim().length() == 0) {
			return null;
		}
		List<TimeTableData> parseArray = JSON.parseArray(httpPost,
				TimeTableData.class);
		return parseArray;
	}

	public void addTimeTableData(TimeTableData timeTableDatas) {
		String url = NetConfig.getResourceServer()
				+ "addNewTimeTableData.action";
		String content = JSON.toJSONString(timeTableDatas);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		HttptUtil.HttpPostWithoutResult(url, content, parameter);
	}

	public List<UserData> getManagerUsers() {
		String url = NetConfig.getResourceServer() + "getManagerUsers.action";
		String content = null;
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		String httpPost = HttptUtil.HttpPost(url, content, parameter);
		List<UserData> parseArray = JSON.parseArray(httpPost, UserData.class);
		return parseArray;
	}

	public void addTimeTableDatas(List<TimeTableData> timeTableDatas) {
		String url = NetConfig.getResourceServer()
				+ "addNewTimeTableDatas.action";
		String content = JSON.toJSONString(timeTableDatas);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		HttptUtil.HttpPostWithoutResult(url, content, parameter);
	}

	public void removeTimeTableDatas(List<TimeTableData> timeTableDatas2) {
		String url = NetConfig.getResourceServer()
				+ "removeTimeTableDataList.action";
		String content = JSON.toJSONString(timeTableDatas2);
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("Content-Type", "application/x-www-form-urlencoded");
		HttptUtil.HttpPostWithoutResult(url, content, parameter);
	}
}
