package com.zys.utils.ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Paint;	

import javax.swing.JPanel;

public class ZGradientPanel extends JPanel {

	private static final long serialVersionUID = -4777857869333812614L;
	private Color color1;
	private Color color2;
	private int orientation;
	public static final int VERTICAL = 0;
	public static final int HORIZONTAL = 1;
	public static final int BOTTOM_LEFT_2_TOP_RIGHT = 2;
	public static final int TOP_LEFT_2_BOTTOM_RIGHT = 3;

	public ZGradientPanel() {
		super();
	}

	public ZGradientPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public ZGradientPanel(LayoutManager layout) {
		super(layout);
	}

	public ZGradientPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public ZGradientPanel(Color color1, Color color2, int orientation) {
		super();
		this.color1 = color1;
		this.color2 = color2;
		this.orientation = orientation;
	}

	public Color getColor1() {
		return color1;
	}

	public Color getColor2() {
		return color2;
	}

	public int getOrientation() {
		return orientation;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isOpaque()) {
			return;
		}
		if (color1 == null || color2 == null) {
			return;
		}
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2d = (Graphics2D) g;
		Paint orginalPaint = g2d.getPaint();
		GradientPaint gradientPaint = null;
		switch (orientation) {
		case VERTICAL:
			// 垂直的
			gradientPaint = new GradientPaint(0, 0, color1, 0, height, color2);
			break;
		case HORIZONTAL:
			// 水平
			gradientPaint = new GradientPaint(0, 0, color1, width, 0, color2);
			break;
		case BOTTOM_LEFT_2_TOP_RIGHT:
			// 倾斜的1
			gradientPaint = new GradientPaint(0, height, color1, width, 0,
					color2);
			break;
		case TOP_LEFT_2_BOTTOM_RIGHT:
			// 倾斜的2
			gradientPaint = new GradientPaint(0, 0, color1, width, height,
					color2);
			break;
		}
		g2d.setPaint(gradientPaint);
		g2d.fillRect(0, 0, width, height);
		g2d.setPaint(orginalPaint);
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
}
