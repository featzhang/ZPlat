package com.zys.plugin;

import javax.swing.JFrame;

import com.zys.plugin.message.MessageHandler;
import com.zys.utils.ui.ZPanel;

public interface Plugin {
	public ZPanel init(JFrame fatherFrame);

	public void setMessageHandler(MessageHandler messageHandler);
}
