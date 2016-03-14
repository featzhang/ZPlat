package com.skyworth.iDtv;

import javax.swing.JFrame;

import com.skyworth.iDtv.ui.MainFrame;
import com.zys.plugin.Plugin;
import com.zys.plugin.message.MessageHandler;
import com.zys.utils.ui.ZPanel;

public class DataRateServerManager implements Plugin {

	private MessageHandler messageHandler = null;

	@Override
	public ZPanel init(JFrame fatherFrame) {
		MainFrame mainFrame = new MainFrame(fatherFrame);
		ZPanel contentPane = (ZPanel) mainFrame.getContentPane();

		System.out.println("一体机资源管理启动了");
		return contentPane;
	}

	@Override
	public void setMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

}
