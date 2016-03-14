package com.skyworth.iDtv.ui.baudServer;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.skyworth.iDtv.entity.DeviceData;

public class BaudServerListModel extends DefaultComboBoxModel {

	private static final long serialVersionUID = -8686626485064692371L;
	private List<DeviceData> deviceDatas;

	public BaudServerListModel(List<DeviceData> v) {
		this.deviceDatas = v;
	}

	public BaudServerListModel() {
	}

	public List<DeviceData> getDeviceDatas() {
		return deviceDatas;
	}

	@Override
	public int getSize() {
		return deviceDatas == null ? 0 : deviceDatas.size();
	}

	public DeviceData getDeviceDataAt(int index) {
		if (deviceDatas == null) {
			return null;
		}
		DeviceData deviceData = deviceDatas.get(index);
		return deviceData;
	}

	@Override
	public Object getElementAt(int index) {
		if (deviceDatas == null) {
			return null;
		}
		DeviceData deviceData = deviceDatas.get(index);
		String ip = deviceData.getIp();
		String port = deviceData.getPort();
		String string = "" + ip + ":";
		string += port;

		return string;
	}

	@Override
	public void removeElementAt(int index) {
		if (deviceDatas == null) {
			return;
		}
		deviceDatas.remove(index);
	}

	@Override
	public void addElement(Object anObject) {
		if (anObject == null || !(anObject instanceof DeviceData)) {
			return;
		}
		deviceDatas.add((DeviceData) anObject);
	}

	public void setDatas(List<DeviceData> datas) {
		this.deviceDatas = datas;
	}

}
