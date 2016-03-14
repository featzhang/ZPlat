package com.zys.idtv.ifc;

import java.util.List;

import com.zys.idtv.entity.BitStreamDeviceEntity;

public interface BitStreamDeviceIfc {
	public void addDevice(BitStreamDeviceEntity user);

	public BitStreamDeviceEntity findDeviceById(int id);

	public BitStreamDeviceEntity findDeviceByName(String name);

	public void removeDevice(BitStreamDeviceEntity user);

	public void saveDevice(BitStreamDeviceEntity user);

	public void updateDevice(BitStreamDeviceEntity user);

	public List<BitStreamDeviceEntity> getAllDevices();

}
