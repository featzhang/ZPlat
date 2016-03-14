package com.skyworth.iDtv.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skyworth.iDtv.entity.DeviceData;
import com.skyworth.iDtv.net.ClientRequest;

public class DeviceStatue {
	private static List<DeviceData> deviceDatas = null;
	private static boolean haveChanged;

	public static List<DeviceData> getDeviceDatas() {
		if (deviceDatas == null) {
			updateFromServer();
		}
		return deviceDatas;
	}

	private static Map<Integer, DeviceData> maps = null;

	public static void setDeviceDatas(List<DeviceData> deviceDatas) {
		DeviceStatue.deviceDatas = deviceDatas;
		if (deviceDatas == null || deviceDatas.size() == 0) {
			return;
		}
		if (maps == null) {
			maps = new HashMap<Integer, DeviceData>();
		}
		for (DeviceData deviceData : deviceDatas) {
			if (deviceData == null) {
				continue;
			}
			Integer id = deviceData.getId();
			maps.put(id, deviceData);
		}
	}

	public static boolean isHaveChanged() {
		return haveChanged;
	}

	public static void setHaveChanged(boolean b) {
		haveChanged = b;
	}

	public static DeviceData finDeviceDataById(int i) {
		if (maps == null) {
			return null;
		}
		DeviceData deviceData = maps.get(i);
		return deviceData;
	}

	public static List<DeviceData> updateFromServer() {
		List<DeviceData> allBitStreamServers = new ClientRequest()
				.getAllBitStreamServers();
		setDeviceDatas(allBitStreamServers);
		return allBitStreamServers;
	}

	/**about two step:<br/>
	 * 1. setting deviceData<br/>
	 * 2. upload to server
	 * @param deviceData 
	 * @param b 
	 */
	public static void setDeviceEnable(DeviceData deviceData, boolean b) {
		deviceData.setUsable(b?1:0);
		new ClientRequest().updateBaudStreamServerData(deviceData);
	}
}
