package com.zys.utils.ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JLabel;

/**
 * @author Aaron
 * @email <strong>mail to</strong><br>
 *        <a href="mailto:zhangzuofeng@live.cn">zhangzuofeng@live.cnom</a><br>
 *        <strong>see also</strong><关注我><br>
 *        <a href="http://www.zhangzuofeng.cn">个人主页</a>
 * @date 2013-3-3
 * @function 带有上下滚动效果的Label标签
 * @versions 1.0
 */
public class ZScrollLabel extends JLabel implements Runnable {

	private static final long serialVersionUID = 1891684760189602720L;

	private String textone = null;
	private String texttwo = null;
	private String showtext = null;
	private boolean first = true;

	private Thread thread = null;

	private int x = 0;
	private int y = 0;
	private int fontWidth = 0;
	private int fontHeight = 0;

	public ZScrollLabel(String text) {
		super(text);
		this.textone = text;
		this.texttwo = text;
		this.showtext = text;
		thread = new Thread(this);
		thread.start();
	}

	public ZScrollLabel(String textone, String texttwo) {
		super(textone);
		first = true;
		this.textone = textone;
		this.texttwo = texttwo;
		this.showtext = textone;
		thread = new Thread(this);
		thread.start();
	}

	public String getText() {
		if (first) {
			this.showtext = this.textone;
		} else {
			this.showtext = this.texttwo;
		}
		return this.showtext;
	}

	public void setText(String text) {
		this.showtext = text;
	}

	public void setText(String textone, String texttwo) {
		first = true;
		this.textone = textone;
		this.texttwo = texttwo;
		this.showtext = textone;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		FontMetrics fontMetrics = g.getFontMetrics(new Font("宋体", 0, 12));
		fontWidth = fontMetrics.stringWidth(this.getText());
		fontHeight = fontMetrics.getHeight();
		g.setColor(this.getBackground());
		g.fillRect(0, 0, fontWidth, fontHeight);
		g.setColor(this.getForeground());
		g.setFont(this.getFont());
		g.drawString(this.getText(), x, y + fontHeight);
	}

	public void run() {
		while (true) {
			y -= 2;
			if (y < -fontHeight) {
				if (first) {
					this.setText(textone);
					first = !first;
				} else {
					this.setText(texttwo);
					first = !first;
				}
				y = fontHeight;
			}
			this.repaint();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
