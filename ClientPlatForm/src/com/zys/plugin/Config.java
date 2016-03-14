package com.zys.plugin;

import javax.swing.Icon;

public interface Config {
	public String INDEPENDENCE_TYPE = "independence";
	public String EMBEDDED_TYPE = "embedded";

	public String getAppName();

	public String getAppType();

	public Plugin getPlugin();

	public String getResourcePath();

	public Icon getIcon();

	public int getVision();
}
