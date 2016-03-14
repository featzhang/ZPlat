package cn.zhangzuofeng.idtv.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import cn.zhangzuofeng.idtv.ui.resource.ZResourceUtils;

import com.zys.plugin.message.Message;
import com.zys.plugin.message.MessageDevice;
import com.zys.utils.ui.ZPanel;

public class ToolTipDialog extends JWindow implements MessageDevice {

	private static final long serialVersionUID = -7069037011928205740L;
	private final ZPanel contentPanel = new ZPanel();
	private JLabel closeLabel;


	private int _width = 400;


	private int _height = 200;

	private int _step = 30;

	private int _stepTime = 30;

	private int _displayTime = 6000;


	private int _countOfToolTip = 0;


	private int _maxToolTip = 0;


	private int _maxToolTipSceen;

	int _gap;

	boolean _useTop = true;
	private JEditorPane contentEditorPane;
	private Message message = null;
	private JLabel iconLabel;
	private JLabel titleLabel;
	private JLabel detailButton;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			ToolTipDialog tip = new ToolTipDialog();
			Message message = new Message() {

				@Override
				public String getAbstract() {
					return "message ժҪ";
				}

				@Override
				public String getType() {
					return MHTML_TYPE;
				}

				@Override
				public String getContent() {
					return "message content";
				}

				@Override
				public Timestamp getTime() {
					return new Timestamp(new Date().getTime());
				}

				@Override
				public String getTitle() {
					return "title";
				}

				@Override
				public boolean isHasRead() {
					return false;
				}

				@Override
				public void setHasRead(Boolean boolea) {
					
				}
			};
			tip.popup(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void popup(Message message) {
		this.message = message;
		initComponents();
		loadData();
		loadAction();
		animate();
	}

	private void loadAction() {
		closeLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				closeLabel.setForeground(Color.red);
				closeLabel.setBorder(BorderFactory.createLineBorder(Color.red));
				closeLabel.setBackground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				closeLabel.setForeground(Color.white);
				closeLabel.setBorder(BorderFactory.createLineBorder(Color.red));
				closeLabel.setBackground(Color.red);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				close();
			}
		});

	}

	private void loadData() {
		if (message != null) {
			titleLabel.setText(message.getTitle());
			String type = message.getType();
			String content = message.getContent();
			String abstract1 = message.getAbstract();
			// Timestamp time = message.getTime();
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
		}
	}

	public ToolTipDialog() {
		try {
			JWindow.class.getMethod("setAlwaysOnTop",
					new Class[] { Boolean.class });
		} catch (Exception e) {
			_useTop = false;
		}
		initComponents();
		loadData();
	}

	private void initComponents() {
		setSize(_width, _height);

		getContentPane().setLayout(new BorderLayout());
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setBorder(BorderFactory.createEtchedBorder());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setIcon(ZResourceUtils.getImageIcon("tipBackground"));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(40, 40));

		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setOpaque(false);
		contentPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel northPanel = new JPanel();
		northPanel.setOpaque(false);
		northPanel.setBorder(null);
		panel.add(northPanel, BorderLayout.NORTH);
		GridBagLayout gbl_northPanel = new GridBagLayout();
		gbl_northPanel.columnWidths = new int[] { 20, 200, 5 };
		gbl_northPanel.rowHeights = new int[] { 15, 0 };
		gbl_northPanel.columnWeights = new double[] { 1.0, 0.0, 1.0 };
		gbl_northPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		northPanel.setLayout(gbl_northPanel);

		iconLabel = new JLabel();
		iconLabel.setText("icon");
		// ImageIcon imageIcon = ZResourceUtils.getAppImageIcon();
		// imageIcon.setImage(imageIcon.getImage().getScaledInstance(20, 20,
		// Image.SCALE_DEFAULT));
		// iconLabel.setIcon(imageIcon);
		iconLabel.setBounds(0, 0, 10, 10);
		GridBagConstraints gbc_iconLabel = new GridBagConstraints();
		gbc_iconLabel.insets = new Insets(0, 0, 0, 5);
		gbc_iconLabel.anchor = GridBagConstraints.WEST;
		gbc_iconLabel.gridx = 0;
		gbc_iconLabel.gridy = 0;
		northPanel.add(iconLabel, gbc_iconLabel);

		closeLabel = new JLabel(" X ");
		closeLabel.setFont(new Font("Dialog", Font.BOLD, 12));

		titleLabel = new JLabel("title");
		titleLabel.setFont(new Font("����", Font.BOLD, 14));
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.anchor = GridBagConstraints.WEST;
		gbc_titleLabel.insets = new Insets(0, 0, 0, 5);
		gbc_titleLabel.gridx = 1;
		gbc_titleLabel.gridy = 0;
		northPanel.add(titleLabel, gbc_titleLabel);
		closeLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		closeLabel.setOpaque(true);
		closeLabel.setBackground(Color.RED);
		closeLabel.setForeground(Color.white);
		GridBagConstraints gbc_closeLabel = new GridBagConstraints();
		gbc_closeLabel.anchor = GridBagConstraints.EAST;
		gbc_closeLabel.gridx = 2;
		gbc_closeLabel.gridy = 0;
		northPanel.add(closeLabel, gbc_closeLabel);

		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		panel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(0, 0));

		contentEditorPane = new JEditorPane();
		contentEditorPane.setOpaque(false);
		contentEditorPane.setEditable(false);
		centerPanel.add(contentEditorPane);

		JPanel southPanel = new JPanel();
		southPanel.setOpaque(false);
		panel.add(southPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_southPanel = new GridBagLayout();
		gbl_southPanel.columnWidths = new int[] { 148, 155, 83, 0 };
		gbl_southPanel.rowHeights = new int[] { 23, 0 };
		gbl_southPanel.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_southPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		southPanel.setLayout(gbl_southPanel);

		detailButton = new JLabel("\u8BE6\u60C5");
		GridBagConstraints gbc_detailButton = new GridBagConstraints();
		gbc_detailButton.insets = new Insets(0, 0, 0, 5);
		gbc_detailButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_detailButton.gridx = 0;
		gbc_detailButton.gridy = 0;
		southPanel.add(detailButton, gbc_detailButton);

		JLabel nextButton = new JLabel("\u4E0B\u6B21\u67E5\u770B");
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.anchor = GridBagConstraints.EAST;
		gbc_nextButton.gridx = 2;
		gbc_nextButton.gridy = 0;
		southPanel.add(nextButton, gbc_nextButton);
	}

	protected void close() {
	}

	/**
	 * ������ʼ
	 */
	public void animate() {
		new Animation(this).start();
	}

	class Animation extends Thread {

		ToolTipDialog _single;

		public Animation(ToolTipDialog single) {
			this._single = single;
		}

		/**
		 * ���ö���Ч���ƶ��������
		 *
		 * @param posx
		 * @param startY
		 * @param endY
		 * @throws InterruptedException
		 */
		private void animateVertically(int posx, int startY, int endY)
				throws InterruptedException {
			ToolTipDialog.this.setLocation(posx, startY);
			if (endY < startY) {
				for (int i = startY; i > endY; i -= _step) {
					_single.setLocation(posx, i);
					Thread.sleep(_stepTime);
				}
			} else {
				for (int i = startY; i < endY; i += _step) {
					_single.setLocation(posx, i);
					Thread.sleep(_stepTime);
				}
			}
			_single.setLocation(posx, endY);
		}

		/**
		 * ��ʼ��������
		 */
		public void run() {
			try {
				boolean animate = true;
				GraphicsEnvironment ge = GraphicsEnvironment
						.getLocalGraphicsEnvironment();
				Rectangle screenRect = ge.getMaximumWindowBounds();
				int screenHeight = (int) screenRect.height;
				int startYPosition;
				int stopYPosition;
				if (screenRect.y > 0) {
					animate = false;
				}
				_maxToolTipSceen = screenHeight / _height;
				int posx = (int) screenRect.width - _width - 1;
				_single.setLocation(posx, screenHeight);
				_single.setVisible(true);
				if (_useTop) {
					_single.setAlwaysOnTop(true);
				}
				if (animate) {
					startYPosition = screenHeight;
					stopYPosition = startYPosition - _height - 1;
					if (_countOfToolTip > 0) {
						stopYPosition = stopYPosition
								- (_maxToolTip % _maxToolTipSceen * _height);
					} else {
						_maxToolTip = 0;
					}
				} else {
					startYPosition = screenRect.y - _height;
					stopYPosition = screenRect.y;

					if (_countOfToolTip > 0) {
						stopYPosition = stopYPosition
								+ (_maxToolTip % _maxToolTipSceen * _height);
					} else {
						_maxToolTip = 0;
					}
				}

				_countOfToolTip++;
				_maxToolTip++;

				animateVertically(posx, startYPosition, stopYPosition);
				Thread.sleep(_displayTime);
				animateVertically(posx, stopYPosition, startYPosition);

				_countOfToolTip--;
				_single.setVisible(false);
				_single.dispose();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public int getMessageType() {
		return PLUGIN_MESSAGE_TIP;
	}

}
