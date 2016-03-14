package com.zys.plugin.message;

import java.sql.Timestamp;

public interface Message {
	public String TEXT_TYPE = "text";
	public String HLINK_TYPE = "hlink";
	public String MHTML_TYPE = "mhtml";

	public String getAbstract();

	// public String setAbstract();

	public String getType();

	// public String setType();

	public String getContent();

	// public String setContent();

	public Timestamp getTime();

	// public Timestamp setTime();
	public String getTitle();

	public boolean isHasRead();

	public void setHasRead(Boolean boolea);

}
