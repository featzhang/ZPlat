package com.zys.idtv.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bitstreamdevice", schema = "", catalog = "idtv")
public class BitStreamDeviceEntity {
	private int id;
	private String ip;
	private String port;
	private String comment;
	private int usable;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Basic
	@Column(name = "port")
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	@Basic
	@Column(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	@Basic
	@Column(name = "usable")
	public int getUsable() {
		return usable;
	}

	public void setUsable(int enable) {
		this.usable = enable;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (ip != null ? ip.hashCode() : 0);
		result = 31 * result + (port != null ? port.hashCode() : 0);
		result = 31 * result + (comment != null ? comment.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		BitStreamDeviceEntity that = (BitStreamDeviceEntity) o;
		if (id != that.id)
			return false;
		if (ip != null ? !ip.equals(that.ip) : that.ip != null)
			return false;
		if (port != null ? !port.equals(that.port) : that.port != null)
			return false;
		if (comment != null ? !comment.equals(that.comment)
				: that.comment != null)
			return false;

		return true;
	}
}
