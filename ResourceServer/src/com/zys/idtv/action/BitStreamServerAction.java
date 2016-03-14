package com.zys.idtv.action;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.zys.idtv.entity.BitStreamDeviceEntity;
import com.zys.idtv.ifc.BitStreamDeviceIfc;
import com.zys.idtv.impl.BitStreamImpl;

public class BitStreamServerAction extends ActionSupport {

	private static final long serialVersionUID = -3936695577759668656L;
	private List<BitStreamDeviceEntity> bitStreamServerJson;
	private BitStreamDeviceIfc bitStreamDeviceIfc = new BitStreamImpl();

	private String postContent;

	public void addNewBitStreamServer() {
		if (postContent == null || postContent.trim().length() == 0) {
			return;
		}
		BitStreamDeviceEntity object = JSON.parseObject(postContent,
				BitStreamDeviceEntity.class);
		BitStreamDeviceEntity bitStreamDeviceEntity = new BitStreamDeviceEntity();
		bitStreamDeviceEntity.setComment(object.getComment());
		bitStreamDeviceEntity.setIp(object.getIp());
		bitStreamDeviceEntity.setPort(object.getPort());
		bitStreamDeviceIfc.addDevice(bitStreamDeviceEntity);
	}

	public void updateBitStreamServer() {
		if (postContent == null || postContent.trim().length() == 0) {
			return;
		}
		BitStreamDeviceEntity object = JSON.parseObject(postContent,
				BitStreamDeviceEntity.class);
		bitStreamDeviceIfc.updateDevice(object);
	}

	public void removeBitStreamServer() {
		if (postContent == null || postContent.trim().length() == 0) {
			return;
		}
		BitStreamDeviceEntity object = JSON.parseObject(postContent,
				BitStreamDeviceEntity.class);
		bitStreamDeviceIfc.removeDevice(object);
	}

	public String getAllBitStreamServers() {
		bitStreamServerJson = bitStreamDeviceIfc.getAllDevices();
		return "success";
	}

	public List<BitStreamDeviceEntity> getBitStreamServerJson() {
		return bitStreamServerJson;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setBitStreamServerJson(
			List<BitStreamDeviceEntity> bitStreamServerJson) {
		this.bitStreamServerJson = bitStreamServerJson;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
}
