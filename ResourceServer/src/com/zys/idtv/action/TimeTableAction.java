package com.zys.idtv.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import com.zys.idtv.entity.TimeTableEntity;
import com.zys.idtv.entity.UserEntityTransport;
import com.zys.idtv.ifc.TimeTableIfc;
import com.zys.idtv.ifc.UserIfc;
import com.zys.idtv.impl.TimeTableImp;
import com.zys.idtv.impl.UserImpl;

public class TimeTableAction extends ActionSupport {
	Logger logger = Logger.getLogger(TimeTableAction.class.getName());
	private static final long serialVersionUID = 3766972647277175738L;
	private TimeTableIfc timeTableIfc = new TimeTableImp();
	private List<TimeTableEntity> allTimeTables;
	private String postContent;

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public List<TimeTableEntity> getAllTimeTables() {
		return allTimeTables;
	}

	public void setAllTimeTables(List<TimeTableEntity> allTimeTables) {
		this.allTimeTables = allTimeTables;
	}

	public String getAllTimeTableDatas() {
		allTimeTables = timeTableIfc.getAllTimeTables();
		return "success";
	}

	public void addNewTimeTableData() {
		logger.debug("==========addNewTimeTableData======================");
		String string = postContent;
		logger.debug("InputStream: " + string);
		TimeTableEntity timetableEntity = JSON.parseObject(string,
				TimeTableEntity.class);
		if (timetableEntity != null) {
			timeTableIfc.addTimeTable(timetableEntity);
			logger.debug("addNewTimeTableData: " + timetableEntity);
		}
	}

	public void addNewTimeTableDatas() {
		logger.debug("==========addNewTimeTableDatas======================");
		String string = postContent;
		logger.debug("InputStream: " + string);
		List<TimeTableEntity> timeTableEntities = JSON.parseArray(string,
				TimeTableEntity.class);
		for (TimeTableEntity timetableEntity : timeTableEntities) {
			if (timetableEntity != null) {
				timeTableIfc.addTimeTable(timetableEntity);
				logger.debug("addNewTimeTableData: " + timetableEntity);
			}
		}

	}
	public void updateTimeTableDatas() {
		logger.debug("==========updateTimeTableDatas======================");
		String string = postContent;
		logger.debug("InputStream: " + string);
		List<TimeTableEntity> timeTableEntities = JSON.parseArray(string,
				TimeTableEntity.class);
		for (TimeTableEntity timetableEntity : timeTableEntities) {
			if (timetableEntity != null) {
				timeTableIfc.updateTimeTable(timetableEntity);
				logger.debug("addNewTimeTableData: " + timetableEntity);
			}
		}
		
	}
	public void removeTimeTableDatas() {
		logger.debug("==========removeTimeTableDatas======================");
		String string = postContent;
		logger.debug("InputStream: " + string);
		List<TimeTableEntity> timeTableEntities = JSON.parseArray(string,
				TimeTableEntity.class);
		for (TimeTableEntity timetableEntity : timeTableEntities) {
			if (timetableEntity != null) {
				timeTableIfc.removeTimeTable(timetableEntity);
				logger.debug("removeTimeTable: " + timetableEntity);
			}
		}
		
	}
}
