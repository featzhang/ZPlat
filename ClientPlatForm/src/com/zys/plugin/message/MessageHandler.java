package com.zys.plugin.message;


public interface MessageHandler {
	public void popupSystemTrayMessage(Message message);

	public void popupWarningMessage(Message message);

	public void add2MessageListBar(Message message);

	public void addMessageDevice(MessageDevice messageDevice);

}
