package com.zys.idtv.ifc;

import java.util.List;

import com.zys.idtv.entity.TimeTableEntity;

public interface TimeTableIfc {

	public void addTimeTable(TimeTableEntity timeTableEntity);

	public TimeTableEntity findTimeTableById(int id);

	public TimeTableEntity findTimeTableByName(String name);

	public void removeTimeTable(TimeTableEntity user);

	public void saveTimeTable(TimeTableEntity user);

	public void updateTimeTable(TimeTableEntity user);

	public List<TimeTableEntity> getAllTimeTables();

}
