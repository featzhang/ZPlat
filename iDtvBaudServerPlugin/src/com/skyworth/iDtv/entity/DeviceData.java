package com.skyworth.iDtv.entity;

import java.io.Serializable;

public class DeviceData implements Serializable {

	private static final long serialVersionUID = -5905196602108044969L;
	private Integer id;
	private String ip;
	private String port;
	private String comment;
	private int usable;

	public String getComment() {
		return comment;
	}

	public int getUsable() {
		return usable;
	}

	public void setUsable(int usable) {
		this.usable = usable;
	}

	public Integer getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getPort() {
		return port;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
