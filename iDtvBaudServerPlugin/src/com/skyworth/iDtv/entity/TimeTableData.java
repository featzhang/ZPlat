package com.skyworth.iDtv.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class TimeTableData implements Serializable {

	private static final long serialVersionUID = 736910052318579972L;
	private int id;
	private int baudStreamServerId;
	private Timestamp startTime;
	private Timestamp endTime;
	private int userId;
	private String task;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		TimeTableData that = (TimeTableData) o;

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

	public int getBaudStreamServerId() {
		return baudStreamServerId;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public int getId() {
		return id;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public String getTask() {
		return task;
	}

	public int getUserId() {
		return userId;
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

	public void setBaudStreamServerId(int baudStreamServerId) {
		this.baudStreamServerId = baudStreamServerId;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public TimeTableData clone()  {
		TimeTableData timeTableData = new TimeTableData();
		timeTableData.setBaudStreamServerId(baudStreamServerId);
		timeTableData.setEndTime(endTime);
		timeTableData.setId(id);
		timeTableData.setStartTime(startTime);
		timeTableData.setTask(task);
		timeTableData.setUserId(userId);
		return timeTableData;
	}
}
