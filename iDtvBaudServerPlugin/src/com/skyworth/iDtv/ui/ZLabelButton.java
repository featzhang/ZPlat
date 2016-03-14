package com.skyworth.iDtv.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.border.Border;

import com.zys.utils.ImageUtil;

public class ZLabelButton extends JLabel {
	private Border hoverButtonBorder = BorderFactory
			.createLineBorder(new Color(0x99CCCC));
	private Border normalButtonBorder = BorderFactory
			.createLineBorder(new Color(0XFFFFFF));
	private Color hoverButtonBackground = new Color(0x99CCCC);
	private Color normalButtonBackground = new Color(0XFFFFFF);
	private static final long serialVersionUID = 2137718915209623890L;

	public ZLabelButton() {
		super();
		initComponents();
	}

	public ZLabelButton(String text) {
		super(text);
		initComponents();
	}

	public ZLabelButton(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		initComponents();
	}

	public void initComponents() {

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				setBorder(hoverButtonBorder);
				setBackground(hoverButtonBackground);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(normalButtonBorder);
				setBackground(normalButtonBackground);
			}
		});

		setOpaque(true);
		setBorder(normalButtonBorder);
		setBackground(normalButtonBackground);
	}

	@Override
	public void setIcon(Icon icon) {
		if (icon != null) {
			Icon imageIcon = ImageUtil.resizeIcon(icon, 20, 20);
			super.setIcon(imageIcon);
		} else {
			super.setIcon(icon);
		}

	}
}
