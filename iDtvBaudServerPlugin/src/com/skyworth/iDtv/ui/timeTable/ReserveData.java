package com.skyworth.iDtv.ui.timeTable;

import java.io.Serializable;
import java.sql.Timestamp;

import com.skyworth.iDtv.entity.TimeTableData;
import com.skyworth.iDtv.dao.UserStatue;
import com.skyworth.iDtv.entity.UserData;

public class ReserveData implements Serializable {

	private static final long serialVersionUID = -4979807923112757437L;
	private Timestamp startTime = null;
	private Timestamp endTime = null;
	private UserData user = null;
	private String task = null;
	private int timeTableDataId;

	public int getTimeTableDataId() {
		return timeTableDataId;
	}

	public void setTimeTableDataId(int timeTableDataId) {
		this.timeTableDataId = timeTableDataId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void copy(TimeTableData timeTableData) {
		startTime = timeTableData.getStartTime();
		endTime = timeTableData.getEndTime();
		task = timeTableData.getTask();
		user = UserStatue.findById(timeTableData.getUserId());
		timeTableDataId = timeTableData.getId();
	}

}
