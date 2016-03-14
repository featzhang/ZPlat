package com.zys.utils.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ZSwitchButton extends JToggleButton implements ActionListener {

	private static final long serialVersionUID = -8941502009186389747L;
	private static final int ARCNUMBER = 35;
	private int currentResolution = 50;
	private long cycleStart;
	private Timer timer = null;
	private final int MOVE_TIME = 2000;
	private int moveMinX;
	private int moveMaxX;
	private int buttonX;
	private ActionListener moveActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			startTimer(currentResolution);
		}
	};

	public ZSwitchButton() {
		setBorder(null);
		addActionListener(moveActionListener);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Color oldColor = g2.getColor();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setColor(oldColor);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Shape backgroundShape = new RoundRectangle2D.Float(1, 1,
				getWidth() - 2, getHeight() - 2, ARCNUMBER, ARCNUMBER);

		boolean selected = getModel().isSelected();
		// fill shape
		Paint paint = null;
		if (selected) {
			paint = new GradientPaint(0, 0, new Color(45, 106, 169), 0,
					getHeight(), new Color(135, 183, 245));
		} else {
			paint = new GradientPaint(0, 0, new Color(144, 144, 144), 0,
					getHeight(), new Color(244, 244, 244));
		}
		g2.setPaint(paint);
		g2.fill(backgroundShape);
		// draw shape bounds
		g2.setColor(Color.DARK_GRAY);
		g2.draw(backgroundShape);

		JLabel onLabel = new JLabel("ON");
		JLabel offLabel = new JLabel("OFF");
		Dimension labelSize;
		// draw text. off/on
		if (selected) {
			oldColor = g2.getColor();
			g2.setColor(Color.WHITE);
			labelSize = onLabel.getPreferredSize();
			g2.drawString(onLabel.getText(), getWidth() / 2 / 2
					- labelSize.width / 2, getHeight() / 2 + labelSize.height
					/ 2);
			g2.setColor(oldColor);
		} else {
			oldColor = g2.getColor();
			g2.setColor(Color.BLACK);
			labelSize = offLabel.getPreferredSize();
			g2.drawString(offLabel.getText(), getWidth() / 2 + labelSize.width
					/ 2, getHeight() / 2 + labelSize.height / 2);
			g2.setColor(oldColor);
		}
		// draw button
		int buttonY = 0;
		int buttonWidth = getWidth() / 2;
		int buttonHeight = getHeight();
		Shape buttonShape = new RoundRectangle2D.Float(buttonX, buttonY,
				buttonWidth, buttonHeight, ARCNUMBER, ARCNUMBER);
		paint = new GradientPaint(0, 0, new Color(244, 244, 244), 0,
				getHeight(), new Color(144, 144, 144));
		g2.setPaint(paint);
		g2.fill(buttonShape);
		g2.setColor(new Color(140, 140, 140));
		g2.draw(buttonShape);
	}

	public static void main(String[] args) {
		ZSwitchButton button = new ZSwitchButton();
		button.setBounds(60, 10, 100, 30);

		JPanel mainPane = new JPanel();
		mainPane.setOpaque(true);
		mainPane.setBackground(Color.WHITE);
		mainPane.setLayout(null);
		mainPane.add(button);

		final JFrame frame = new JFrame();
		frame.setTitle("SwitchButton Demo");
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(mainPane, BorderLayout.CENTER);
		frame.setSize(233, 80);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		long currentTime = System.nanoTime() / 1000000;
		long totalTime = currentTime - cycleStart;

		if (totalTime > MOVE_TIME) {
			cycleStart = currentTime;
		}
		float fraction = (float) totalTime / MOVE_TIME;
		fraction = Math.min(1.0f, fraction);
		fraction = 1 - Math.abs(1 - (2 * fraction));
		animate(fraction);
	}

	public void animate(float fraction) {
		float animationFactor = (float) Math
				.sin(fraction * (float) Math.PI / 2);
		animationFactor = Math.min(animationFactor, 1.0f);
		animationFactor = Math.max(animationFactor, 0.0f);
		buttonX = moveMinX
				+ (int) (.5f + animationFactor * (float) (moveMaxX - moveMinX));
		if (isSelected()) {
			if (buttonX >= moveMaxX) {
				timer.stop();
				timer.setInitialDelay(100);
				buttonX = getWidth() / 2 - 1;
			}
		} else {
			if (buttonX - 1 <= moveMaxX) {
				timer.stop();
				timer.setInitialDelay(100);
				buttonX = 0;
			}
		}
		repaint();
	}

	private void startTimer(int resolution) {
		if (timer == null) {
			timer = new Timer(resolution, this);
		}
		if (!timer.isRunning()) {
			if (isSelected()) {
				moveMinX = 0;
				moveMaxX = getWidth() / 2 - 1;
			} else {
				moveMinX = getWidth() / 2 - 1;
				moveMaxX = 0;
			}
			timer.start();
		}

	}
}