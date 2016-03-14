package com.zys.plugin.message;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import com.zys.plugin.message.Message;
import com.zys.plugin.message.MessageDevice;
import com.zys.plugin.message.MessageElementPanel;

public class MessageTipPane extends JScrollPane implements MessageDevice {

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					JDialog dialog = new JDialog();

					MessageTipPane messageTipPane = new MessageTipPane();
					for (int i = 0; i < 10; i++) {
						final int count = i;
						Message message = new Message() {
							boolean hasRead = false;

							@Override
							public void setHasRead(Boolean arg0) {
								this.hasRead = arg0;
							}

							@Override
							public boolean isHasRead() {
								if (count % 2 == 0) {
									return true;
								} else {
									return false;
								}
							}

							@Override
							public String getType() {

								return TEXT_TYPE;
							}

							@Override
							public String getTitle() {
								return "魅族副总裁李楠辟谣阿里入股:魅族不差钱 " + count;
							}

							@Override
							public Timestamp getTime() {
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								String s = "2014-07-" + (10 + count)
										+ " 16:30:04";
								Timestamp imTimestamp = null;
								try {
									Date parse = simpleDateFormat.parse(s);
									imTimestamp = new Timestamp(parse.getTime());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return imTimestamp;
							}

							@Override
							public String getContent() {
								return "内容";
							}

							@Override
							public String getAbstract() {
								return "小米公司日前宣布将携一系列旗舰智能机进军印度市场。小米公司联合创始人林斌表示,小米在印度可能会有比中国更大的市场。中国的智能手机市场已经接近饱和,而印度的智..";
							}
						};
						messageTipPane.addMessage(message);
					}

					dialog.add(messageTipPane);
					dialog.setTitle("message test!");
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							System.exit(0);
						}
					});
					dialog.setSize(300, 500);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 4677106284746724733L;

	private JPanel contentPanel;

	private Vector<Message> messages = null;

	public MessageTipPane() {
		super();

		initComponents();
	}

	public MessageTipPane(Component view) {
		super(view);
		initComponents();
	}

	public MessageTipPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		initComponents();
	}

	public MessageTipPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy);
		initComponents();
	}

	public void addMessage(Message message) {
		if (messages == null) {
			messages = new Vector<Message>();
		}
		if (messages.contains(message)) {
			return;
		}
		messages.add(message);
		MessageElementPanel messageElementPanel = new MessageElementPanel(
				message);
		contentPanel.add(messageElementPanel);
		if (messages.size() > 1) {
			sortMessageElementPanel();
		}
	}

	@Override
	public int getMessageType() {
		return PLUGIN_MESSAGE_TIP;
	}

	private void initComponents() {
		contentPanel = new JPanel();
		setViewportView(contentPanel);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	}

	@Override
	public void popup(Message message) {
		addMessage(message);
	}

	private void sortMessageElementPanel() {
		Component[] components = contentPanel.getComponents();
		HashMap<Message, MessageElementPanel> msHashMap = new HashMap<Message, MessageElementPanel>();
		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof MessageElementPanel) {
				MessageElementPanel messageElementPanel = (MessageElementPanel) components[i];
				Message message = messageElementPanel.getMessage();
				msHashMap.put(message, messageElementPanel);
			}
		}
		Collections.sort(messages, new Comparator<Message>() {

			@Override
			public int compare(Message o1, Message o2) {
				int googdValue = 1;
				int badValue = -1;
				if (o1 == null && o2 == null) {
					return 0;
				}
				if (o1 == null) {
					return badValue;
				} else if (o2 == null) {
					return googdValue;
				} else {
					boolean hasRead1 = o1.isHasRead();
					boolean hasRead2 = o2.isHasRead();
					if (hasRead1 || hasRead2) {
						if (hasRead1) {// 第一个已经读过
							return googdValue;
						} else {// 第二个已经读过
							return badValue;
						}
					} else {
						Timestamp time1 = o1.getTime();
						Timestamp time2 = o2.getTime();
						if ((time1 == null || time2 == null)
								|| time1.equals(time2)) {
							return 0;
						} else {
							long timelo1 = time1.getTime();
							long timelo2 = time2.getTime();
							if (timelo1 > timelo2) {
								return badValue;
							} else {
								return googdValue;
							}
						}

					}
				}
			}
		});
		contentPanel.removeAll();
		for (Message message : messages) {
			MessageElementPanel messageElementPanel = msHashMap.get(message);
			contentPanel.add(messageElementPanel);
		}
	}
}
