package com.zys.idtv.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name = "timetable", schema = "", catalog = "idtv")
public class TimeTableEntity {
	private int id;
	private int baudStreamServerId;
	private Timestamp startTime;
	private Timestamp endTime;
	private int userId;
	private String task;

	@Basic
	@Column(name = "task")
	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	@Basic
	@Column(name = "userId")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "endTime")
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Basic
	@Column(name = "startTime")
	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Basic
	@Column(name = "baudStreamServerId")
	public int getBaudStreamServerId() {
		return baudStreamServerId;
	}

	public void setBaudStreamServerId(int baudStreamServerId) {
		this.baudStreamServerId = baudStreamServerId;
	}

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TimeTableEntity that = (TimeTableEntity) o;

		if (id != that.id)
			return false;
		if (baudStreamServerId != that.baudStreamServerId)
			return false;
		if (userId != that.userId)
			return false;
		if (startTime != null ? !startTime.equals(that.startTime)
				: that.startTime != null)
			return false;
		if (endTime != null ? !endTime.equals(that.endTime)
				: that.endTime != null)
			return false;
		if (task != null ? !task.equals(that.task) : that.task != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + baudStreamServerId;
		result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
		result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
		result = 31 * result + userId;
		result = 31 * result + (task != null ? task.hashCode() : 0);
		return result;
	}
}