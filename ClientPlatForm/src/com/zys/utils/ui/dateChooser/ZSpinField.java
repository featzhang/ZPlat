/*
 *  JSpinField.java  - A spin field using a JSpinner (JDK 1.4)
 *  Copyright (C) 2004 Kai Toedter
 *  kai@toedter.com
 *  www.toedter.com
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package com.zys.utils.ui.dateChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * JSpinField2 is a numeric field with 2 spin buttons to increase or decrease
 * the value. It has the same interface as the "old" JSpinField but uses a JSpinner
 * internally (since J2SE SDK 1.4) rather than a scrollbar for emulating the spin buttons.
 * 
 * @author Kai Toedter
 * @version 1.2
 */
public class ZSpinField extends JPanel implements ChangeListener, CaretListener, ActionListener {
	protected JSpinner spinner;

	/** the text (number) field */
	protected JTextField textField;
	protected int min;
	protected int max;
	protected int value;
	protected Color darkGreen;

	/**
	 * Default JSpinField constructor.
	 */
	public ZSpinField() {
	    this(0,Integer.MAX_VALUE);
	}
	
	/**
	 * JSpinField constructor with given minimum and maximum vaues..
	 */
	public ZSpinField(int min, int max) {
		super();
		this.min = min;
		if(max < min)
		    max = min;
		this.max = max;
		value = 0;
		if(value < min)
		    value = min;
		if(value > max)
		    value = max;
		
		darkGreen = new Color(0, 150, 0);
		setLayout(new BorderLayout());
		textField = new JTextField();
		textField.addCaretListener(this);
		textField.addActionListener(this);
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setBorder(BorderFactory.createEmptyBorder());
		textField.setText(Integer.toString(value));
		spinner = new JSpinner();
		spinner.setEditor(textField);
		spinner.addChangeListener(this);
		add(spinner, BorderLayout.CENTER);
	}

	public void adjustWidthToMaximumValue() {
		JTextField testTextField = new JTextField(Integer.toString(max));
		int width = testTextField.getPreferredSize().width;
		int height = testTextField.getPreferredSize().height;
		textField.setPreferredSize(new Dimension(width,height));
		textField.revalidate();
	}
	
	/**
	 * Is invoked when the spinner model changes
	 * 
	 * @param e
	 *            the ChangeEvent
	 */
	public void stateChanged(ChangeEvent e) {
		SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
		int value = model.getNumber().intValue();
		setValue(value);
	}

	/**
	 * Sets the value attribute of the JSpinField object.
	 * 
	 * @param newValue
	 *            The new value
	 * @param updateTextField
	 *            true if text field should be updated
	 */
	protected void setValue(int newValue, boolean updateTextField, boolean firePropertyChange) {
		int oldValue = value;

		if (newValue < min) {
			value = min;
		} else if (newValue > max) {
			value = max;
		} else {
			value = newValue;
		}

		if (updateTextField) {
			textField.setText(Integer.toString(value));
			textField.setForeground(Color.black);
		}

		if(firePropertyChange) {
			firePropertyChange("value", oldValue, value);
		}
	}

	/**
	 * Sets the value. This is a bound property.
	 * 
	 * @param newValue
	 *            the new value
	 * 
	 * @see #getValue
	 */
	public void setValue(int newValue) {
		setValue(newValue, true, true);
		spinner.setValue(new Integer(value));
	}

	/**
	 * Returns the value.
	 * 
	 * @return the value value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the minimum value.
	 * 
	 * @param newMinimum
	 *            the new minimum value
	 * 
	 * @see #getMinimum
	 */
	public void setMinimum(int newMinimum) {
		min = newMinimum;
	}

	/**
	 * Returns the minimum value.
	 * 
	 * @return the minimum value
	 */
	public int getMinimum() {
		return min;
	}

	/**
	 * Sets the maximum value and adjusts the preferred width.
	 * 
	 * @param newMaximum
	 *            the new maximum value
	 * 
	 * @see #getMaximum
	 */
	public void setMaximum(int newMaximum) {
		max = newMaximum;
	}

	/**
	 * Sets the horizontal alignment of the displayed value.
	 * 
	 * @param alignment
	 *            the horizontal alignment
	 */
	public void setHorizontalAlignment(int alignment) {
		textField.setHorizontalAlignment(alignment);
	}

	/**
	 * Returns the maximum value.
	 * 
	 * @return the maximum value
	 */
	public int getMaximum() {
		return max;
	}

	/**
	 * Sets the font property.
	 * 
	 * @param font
	 *            the new font
	 */
	public void setFont(Font font) {
		if (textField != null) {
			textField.setFont(font);
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param fg
	 *            DOCUMENT ME!
	 */
	public void setForeground(Color fg) {
		if (textField != null) {
			textField.setForeground(fg);
		}
	}

	/**
	 * After any user input, the value of the textfield is proofed. Depending on
	 * being an integer, the value is colored green or red.
	 * 
	 * @param e
	 *            Description of the Parameter
	 */
	public void caretUpdate(CaretEvent e) {
		try {
			int testValue = Integer.valueOf(textField.getText()).intValue();

			if ((testValue >= min) && (testValue <= max)) {
				textField.setForeground(darkGreen);
				setValue(testValue, false, true);
			} else {
				textField.setForeground(Color.red);
			}
		} catch (Exception ex) {
			if (ex instanceof NumberFormatException) {
				textField.setForeground(Color.red);
			}

			// Ignore all other exceptions, e.g. illegal state exception
		}

		textField.repaint();
	}

	/**
	 * After any user input, the value of the textfield is proofed. Depending on
	 * being an integer, the value is colored green or red. If the textfield is
	 * green, the enter key is accepted and the new value is set.
	 * 
	 * @param e
	 *            Description of the Parameter
	 */
	public void actionPerformed(ActionEvent e) {
		if (textField.getForeground().equals(darkGreen)) {
			setValue(Integer.valueOf(textField.getText()).intValue());
		}
	}

	/**
	 * Enable or disable the JSpinField.
	 * 
	 * @param enabled
	 *            The new enabled value
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		textField.setEnabled(enabled);
		spinner.setEnabled(enabled);
	}

	/**
	 * Returns the year chooser's spinner (which allow the focus to be set to
	 * it).
	 * 
	 * @return Component the spinner or null, if the month chooser has no
	 *         spinner
	 */
	public Component getSpinner() {
		return spinner;
	}

    /**
     * Returns "JSpinField".
     *
     * @return the name value
     */
    public String getName() {
        return "JSpinField";
    }

	/**
	 * Creates a JFrame with a JSpinField inside and can be used for testing.
	 * 
	 * @param s
	 *            The command line arguments
	 */
	public static void main(String[] s) {
		JFrame frame = new JFrame("JSpinField2");
		frame.getContentPane().add(new ZSpinField());
		frame.pack();
		frame.setVisible(true);
	}
}