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
		// �����״Ϊ�ȴ�����֪�û������Ѿ��ں�Ŭ���ļ����ˣ���ʱ���ɲ���
		setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

		// ����ͼƬ
		background = new ImageIcon(SplashDialog.class.getResource(splashPath));
		JLabel label = new JLabel(background);// �ѱ���ͼƬ��ʾ��һ����ǩ����
		// �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������
		label.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		// �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��
		((JPanel) getContentPane()).setOpaque(false);
		// ��ʼ�����岼��
		initUI();
		// ȡ������Ĭ��װ��
		this.setUndecorated(true);
		// �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����
		getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		setSize(background.getIconWidth(), background.getIconHeight());
		// ������Ļ���룬������������
		// SwingUtils.moveToScreenCenter(this);
		this.setLocationRelativeTo(null);
	}

	/**
	 * ��ʼ������UI����������������д������ӵ�UI����
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
