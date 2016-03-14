package com.zys.plugin.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import cn.zhangzuofeng.idtv.ui.resource.ZResourceUtils;

public class MessageElementPanel extends JPanel {
	interface ActionProcess {
		public void process();
	}

	class LightingAnimation extends Thread {
		private int colorCount = 0;
		private boolean pause = false;

		public boolean isPause() {
			return pause;
		}

		@Override
		public void run() {
			while (isHasRead()) {
				if (!pause) {
					MessageElementPanel.this
							.setBackground(colors[colorCount++]);
					if (colorCount >= colors.length) {
						colorCount = 0;
					}
				}
				try {
					Thread.sleep(_step);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			setBackground(viewColor);
		}

		public void setPause(boolean pause) {
			// System.out.println(pause);
			this.pause = pause;
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JDialog dialog = new JDialog();
		dialog.setSize(330, 170);
		dialog.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(20, 20));
		Message message = new Message() {

			boolean hasRead;

			@Override
			public String getAbstract() {
				return "黑莓Passport外观和标准尺寸的护照一般大小,名称也和护照的英文单词保持一致,"
						+ "这种1 1的机身设计和市面上的主流智能手机不一样,这实际上也可以看做是风雨飘摇中的黑莓,..";
			}

			@Override
			public String getContent() {
				return "继程守宗正式解开黑莓Passport的神秘面纱之后今天国外Crackberry论坛部分网友分享该机型的最新照片，"
						+ "并同运行Android系统的 Galaxy S4和老款黑莓设备进行的并排比较，此外在曝光的图片中能够看到Android"
						+ "应用在方屏上运行良好，尤其是Instagram程序。黑莓Passport多图曝光 1:1比例堪称创新本次曝光的黑莓"
						+ "Passport来自早期测试版本（可能是运营商），3450mAh容量的电池在是使用1天使用之后依然能够保留70%的电量。"
						+ "此外在图片中还将黑莓Passport同加拿大护照进行了对比，发现机身尺寸基本上相同。"
						+ "黑莓Passport多图曝光 1:1比例堪称创新黑莓Passport外观和标准尺寸的护照一般大小，名称也和护照的英文单词保持一致，"
						+ "这种1:1的机身设计和市面上的主流智能手机不一样，这实际上也可以看做是风雨飘摇中的黑莓，"
						+ "在智能手机的设计创新之路上不苟同的最典型代表。除此之外，尽管外观和机身尺寸比例超出常规，黑莓却并没有忘本，"
						+ "一样引入了加拿大人引以为豪的全键盘，在大屏幕触控设备大行其道的今天，坚守一脉相承下来的传统体验，所以如果用财务标尺"
						+ "来衡量黑莓的健康状况，它在市场上的表现力确实不尽人意，赖以生存的商务市场也逐渐被蚕食殆尽，但这种评判的尺度却不科学，"
						+ "即便是困难重重，黑莓也没有选择卖身，而是在坚守最初的理想，不断的在传统用户体验基础上进行创新。";
			}

			@Override
			public Timestamp getTime() {
				return new Timestamp(new Date().getTime());
			}

			@Override
			public String getTitle() {
				return "黑莓Passport多图曝光 1:1比例堪称创新";
			}

			@Override
			public String getType() {
				return TEXT_TYPE;
			}

			@Override
			public boolean isHasRead() {
				return hasRead;
			}

			@Override
			public void setHasRead(Boolean arg0) {
				hasRead = arg0;
			}
		};
		MessageElementPanel messageElementPanel = new MessageElementPanel(
				message);
		panel.add(messageElementPanel, BorderLayout.CENTER);
		dialog.getContentPane().add(panel);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}

	private ActionProcess showDetailActionProcess;
	private ActionProcess deleteMessageActionProcess;
	private ActionProcess markHasReadMessageActionProcess;
	private ActionProcess markNotReadMessageActionProcess;

	private Message message = null;

	private static final long serialVersionUID = 6246893212262867309L;

	private boolean hasRead = true;

	private Color[] colors = new Color[6];

	private int _step = 800;

	private LightingAnimation lightingAnimation;

	private int _borderWidth = 2;

	private JLabel titleLabel;

	private JLabel timeLabel;
	private JEditorPane contentEditorPane;
	private JLabel readedButton;
	protected Color viewColor = Color.LIGHT_GRAY;
	private JLabel deleteButton;

	public MessageElementPanel(Message message) {
		super();
		this.message = message;
		for (int i = 0; i < colors.length; i++) {
			int r = (int) (Math.random() * 255);
			int g = (int) (Math.random() * 255);
			int b = (int) (Math.random() * 255);
			colors[i] = new Color(r, g, b);
		}
		initComponents();
		lightingAnimation = new LightingAnimation();
		lightingAnimation.start();
		loadData();
		loadAction();
	}

	public ActionProcess getDeleteMessageActionProcess() {
		return deleteMessageActionProcess;
	}

	public ActionProcess getMarkHasReadMessageActionProcess() {
		return markHasReadMessageActionProcess;
	}

	public ActionProcess getMarkNotReadMessageActionProcess() {
		return markNotReadMessageActionProcess;
	}

	public Message getMessage() {
		return message;
	}

	public ActionProcess getShowDetailActionProcess() {
		return showDetailActionProcess;
	}

	private void initComponents() {
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0),
				_borderWidth, true), new BevelBorder(BevelBorder.RAISED, null,
				null, null, null)));
		setLayout(new BorderLayout(0, 0));

		JPanel northPanel = new JPanel();
		northPanel.setOpaque(false);
		add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BorderLayout(0, 0));

		titleLabel = new JLabel();
		titleLabel.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));

		northPanel.add(titleLabel, BorderLayout.CENTER);

		timeLabel = new JLabel("time");
		northPanel.add(timeLabel, BorderLayout.EAST);

		contentEditorPane = new JEditorPane();
		contentEditorPane.setOpaque(false);
		contentEditorPane.setEditable(false);
		add(contentEditorPane, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setOpaque(false);
		add(southPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_southPanel = new GridBagLayout();
		gbl_southPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_southPanel.rowHeights = new int[] { 0 };
		gbl_southPanel.columnWeights = new double[] { 1, 1, 0, 0 };
		gbl_southPanel.rowWeights = new double[] { Double.MIN_VALUE };
		southPanel.setLayout(gbl_southPanel);

		readedButton = new JLabel();
		readedButton.setIcon(ZResourceUtils.getImageIcon("notRead"));
		GridBagConstraints gbc_readedButton = new GridBagConstraints();
		gbc_readedButton.insets = new Insets(0, 0, 0, 5);
		gbc_readedButton.anchor = GridBagConstraints.SOUTHEAST;
		gbc_readedButton.gridwidth = 1;
		gbc_readedButton.gridx = 3;
		gbc_readedButton.gridy = 0;
		southPanel.add(readedButton, gbc_readedButton);

		deleteButton = new JLabel();
		deleteButton.setIcon(ZResourceUtils.getImageIcon("delete"));
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.gridwidth = 1;
		gbc_deleteButton.gridx = 4;
		gbc_deleteButton.gridy = 0;
		southPanel.add(deleteButton, gbc_deleteButton);
	}

	public boolean isHasRead() {
		return hasRead;
	}

	private void loadAction() {
		titleLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				titleLabel.setText("<html><h4><u>" + message.getTitle()
						+ "</u></h4></html>");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				titleLabel.setText("<html><h4>" + message.getTitle()
						+ "</h4></html>");
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (showDetailActionProcess != null) {
					showDetailActionProcess.process();
				}
			}
		});
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lightingAnimation.setPause(true);
				setBackground(viewColor);
				super.mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Point point = e.getPoint();
				double x2 = point.getX();
				double y2 = point.getY();
				int width2 = MessageElementPanel.this.getWidth();
				int height2 = MessageElementPanel.this.getHeight();
				if ((x2 > 0 && x2 < width2) && (y2 > 0 && y2 < height2)) {
				} else {
					lightingAnimation.setPause(false);
				}

				super.mouseExited(e);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (showDetailActionProcess != null) {
					showDetailActionProcess.process();
				}
			}
		};
		addMouseListener(mouseAdapter);
		contentEditorPane.addMouseListener(mouseAdapter);
		readedButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (message.isHasRead()) {
					message.setHasRead(false);
					hasRead = false;
					readedButton.setIcon(ZResourceUtils.getImageIcon("notRead"));
					readedButton.setToolTipText(ZResourceUtils
							.getLabel("clickToMarkUnRead"));
					if (markNotReadMessageActionProcess != null) {
						markNotReadMessageActionProcess.process();
					}
				} else {
					message.setHasRead(true);
					hasRead = true;
					readedButton.setIcon(ZResourceUtils.getImageIcon("hasRead"));
					readedButton.setToolTipText(ZResourceUtils
							.getLabel("clickToMarkHasRead"));
					if (markHasReadMessageActionProcess != null) {
						markHasReadMessageActionProcess.process();
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				readedButton.setBorder(BorderFactory
						.createLineBorder(Color.red));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				readedButton.setBorder(null);
			}
		});
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				deleteButton.setBorder(BorderFactory
						.createLineBorder(Color.red));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				deleteButton.setBorder(null);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (deleteMessageActionProcess != null) {
					deleteMessageActionProcess.process();
				}
			}
		});
	}

	private void loadData() {
		if (message != null) {
			titleLabel.setText("<html><h4>" + message.getTitle()
					+ "</h4></html>");
			String type = message.getType();
			String content = message.getContent();
			String abstract1 = message.getAbstract();
			Timestamp time = message.getTime();
			if (type.equals(Message.HLINK_TYPE)) {
				try {
					contentEditorPane.setContentType("text/html");
					contentEditorPane.setPage(content);
				} catch (Exception e) {
				}
			} else if (type.equals(Message.TEXT_TYPE)) {
				contentEditorPane.setContentType("text/plain");
				contentEditorPane.setText(abstract1);
			} else if (type.equals(Message.MHTML_TYPE)) {
				contentEditorPane.setContentType("text/html");
				contentEditorPane.setText(content);
			}
			long time2Long = time.getTime();
			long timeNow = new Date().getTime();
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //24小时制
			if (timeNow - time2Long < 3600 * 24) {
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				String format = df.format(time);
				timeLabel.setText(format);
			} else {
				SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
				String format = df.format(time);
				timeLabel.setText(format);
			}
			boolean hasRead2 = message.isHasRead();
			if (hasRead2) {
				readedButton.setIcon(ZResourceUtils.getImageIcon("hasRead"));
				readedButton.setToolTipText(ZResourceUtils
						.getLabel("clickToMarkUnRead"));
			} else {
				readedButton.setIcon(ZResourceUtils.getImageIcon("notRead"));
				readedButton.setToolTipText(ZResourceUtils
						.getLabel("clickToMarkHasRead"));
			}
		}
	}

	public void setDeleteMessageActionProcess(
			ActionProcess deleteMessageActionProcess) {
		this.deleteMessageActionProcess = deleteMessageActionProcess;
	}

	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}

	public void setMarkHasReadMessageActionProcess(
			ActionProcess markHasReadMessageActionProcess) {
		this.markHasReadMessageActionProcess = markHasReadMessageActionProcess;
	}

	public void setMarkNotReadMessageActionProcess(
			ActionProcess markNotReadMessageActionProcess) {
		this.markNotReadMessageActionProcess = markNotReadMessageActionProcess;
	}

	public void setShowDetailActionProcess(ActionProcess showDetailActionProcess) {
		this.showDetailActionProcess = showDetailActionProcess;
	}

}