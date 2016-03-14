package cn.zhangzuofeng.idtv.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class PluginIconButton extends JButton {

	private static final long serialVersionUID = 1075261564735235661L;

	public PluginIconButton() {
		super();
		initComponents();
	}

	public PluginIconButton(Action a) {
		super(a);
		initComponents();
	}

	public PluginIconButton(Icon icon) {
		super(icon);
		initComponents();
	}

	public PluginIconButton(String text, Icon icon) {
		super(text, icon);
		initComponents();
	}

	public PluginIconButton(String text) {
		super(text);
		initComponents();
	}

	 public static final Color BUTTON_COLOR1 = new Color(205, 255, 205);

	 public static final Color BUTTON_COLOR2 = new Color(51, 154, 47);

//	public static final Color BUTTON_COLOR1 = new Color(125, 161, 237);
//	public static final Color BUTTON_COLOR2 = new Color(91, 118, 173);
	public static final Color BUTTON_FOREGROUND_COLOR = Color.WHITE;

	private boolean hover;
	private int padding = 10;

	private void initComponents() {
		setFont(new Font("system", Font.PLAIN, 12));
		setBorderPainted(false);
		setForeground(BUTTON_COLOR2);
		setFocusPainted(false);
		setContentAreaFilled(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(BUTTON_FOREGROUND_COLOR);
				hover = true;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setForeground(BUTTON_COLOR2);
				hover = false;
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		int h = getHeight() - 2 * padding;
		int w = getWidth() - 2 * padding;
		h = h > w ? w : h;
		w = w > h ? h : w;
		h = h < 100 ? 100 : h;
		w = w < 100 ? 100 : w;
		float tran = 1F;
		if (!hover) {
			tran = 0.3F;
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint p1;
		GradientPaint p2;
		if (getModel().isPressed()) {
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,
					new Color(100, 100, 100));
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3,
					new Color(255, 255, 255, 100));
		} else {
			p1 = new GradientPaint(0, 0, new Color(100, 100, 100), 0, h - 1,
					new Color(0, 0, 0));
			p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0,
					h - 3, new Color(0, 0, 0, 50));
		}
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				tran));
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(padding,
				padding, w - 1, h - 1, 20, 20);
		Shape clip = g2d.getClip();
		g2d.clip(r2d);
		GradientPaint gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F,
				h, BUTTON_COLOR2, true);
		g2d.setPaint(gp);
		g2d.fillRect(padding, padding, w, h);
		g2d.setClip(clip);
		g2d.setPaint(p1);
		g2d.drawRoundRect(padding, padding, w - 1, h - 1, 20, 20);
		g2d.setPaint(p2);
		g2d.drawRoundRect(padding + 1, padding + 1, w - 3, h - 3, 18, 18);
		g2d.dispose();
		super.paintComponent(g);
	}

	public void setPadding(int i) {
		this.padding = i;
	}
}
