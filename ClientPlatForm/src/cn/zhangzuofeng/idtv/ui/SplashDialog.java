package cn.zhangzuofeng.idtv.ui;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.border.LineBorder;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class SplashDialog extends JDialog {
	private ImageIcon background;
	private JProgressBar progressBar;
	private JLabel progressInfo;

	public SplashDialog(String splashPath) {
		super(new JFrame(), true);
		// 鼠标形状为等待，告知用户程序已经在很努力的加载了，此时不可操作
		setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

		// 背景图片
		background = new ImageIcon(SplashDialog.class.getResource(splashPath));
		JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		((JPanel) getContentPane()).setOpaque(false);
		// 初始化窗体布局
		initUI();
		// 取消窗体默认装饰
		this.setUndecorated(true);
		// 把背景图片添加到分层窗格的最底层作为背景
		getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		setSize(background.getIconWidth(), background.getIconHeight());
		// 移至屏幕中央，覆盖闪屏区域
		// SwingUtils.moveToScreenCenter(this);
		this.setLocationRelativeTo(null);
	}

	/**
	 * 初始化窗体UI，可以在这个方法中创建复杂的UI布局
	 */
	private void initUI() {
		progressBar = new JProgressBar();
		progressInfo = new JLabel();
		progressInfo.setText(" ");
		progressInfo.setForeground(new java.awt.Color(255, 255, 255));
		progressInfo.setOpaque(false);
		GroupLayout layout = new GroupLayout(getContentPane());

		getContentPane().setLayout(layout);
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addContainerGap(296, Short.MAX_VALUE)
				.addComponent(progressInfo, GroupLayout.PREFERRED_SIZE, 98,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0,
						GroupLayout.PREFERRED_SIZE)
				.addComponent(progressBar, GroupLayout.PREFERRED_SIZE,
						GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE));
		layout.setHorizontalGroup(layout
				.createParallelGroup()
				.addComponent(progressBar, GroupLayout.Alignment.LEADING, 0,
						458, Short.MAX_VALUE)
				.addComponent(progressInfo, GroupLayout.Alignment.LEADING, 0,
						458, Short.MAX_VALUE));
		JPanel panel = (JPanel) getContentPane();
		panel.setBorder(new LineBorder(new java.awt.Color(192,192,192), 2, true));
		panel.setOpaque(false);
	}

	public void updateProcess(String info, int value) {
		progressInfo.setText(info);
		progressBar.setValue(value);
		this.validate();
	}
}
