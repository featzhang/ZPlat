package com.zys.plugin.message;


public interface MessageDevice {
	public int SYSTEM_TRAY = 0;
	public int PLUGIN_MESSAGE_TIP = 1;

	public void popup(Message message);

	public int getMessageType();
}
