package com.zys.utils.ui.dateChooser;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ZDateChooser extends JPanel implements ActionListener,
		PropertyChangeListener, ChangeListener {
	protected JButton calendarButton;
	protected JSpinner dateSpinner;
	protected JSpinner.DateEditor editor;
	protected ZCalendar jcalendar;
	protected JPopupMenu popup;
	protected SpinnerDateModel model;
	protected String dateFormatString;
	protected boolean dateSelected;
	protected boolean isInitialized;
	protected Date lastSelectedDate;
	protected boolean startEmpty;

	/**
	 * Creates a new JDateChooser object.
	 */
	public ZDateChooser() {
		this(null, null, false, null);
	}

	/**
	 * Creates a new JDateChooser object.
	 * 
	 * @param icon
	 *            the new icon
	 */
	public ZDateChooser(ImageIcon icon) {
		this(null, null, false, icon);
	}

	/**
	 * Creates a new JDateChooser object.
	 * 
	 * @param startEmpty
	 *            true, if the date field should be empty
	 */
	public ZDateChooser(boolean startEmpty) {
		this(null, null, startEmpty, null);
	}

	/**
	 * Creates a new JDateChooser object with given date format string. The
	 * default date format string is "MMMMM d, yyyy".
	 * 
	 * @param dateFormatString
	 *            the date format string
	 * @param startEmpty
	 *            true, if the date field should be empty
	 */
	public ZDateChooser(String dateFormatString, boolean startEmpty) {
		this(null, dateFormatString, startEmpty, null);
	}

	/**
	 * Creates a new JDateChooser object from a given JCalendar.
	 * 
	 * @param jcalendar
	 *            the JCalendar
	 */
	public ZDateChooser(ZCalendar jcalendar) {
		this(jcalendar, null, false, null);
	}

	/**
	 * Creates a new JDateChooser.
	 * 
	 * @param jcalendar
	 *            the jcalendar or null
	 * @param dateFormatString
	 *            the date format string or null (then "MMMMM d, yyyy" is used)
	 * @param startEmpty
	 *            true, if the date field should be empty
	 * @param icon
	 *            the icon or null (then an internal icon is used)
	 */
	public ZDateChooser(ZCalendar jcalendar, String dateFormatString,
			boolean startEmpty, ImageIcon icon) {
		if (jcalendar == null) {
			jcalendar = new ZCalendar();
		}

		this.jcalendar = jcalendar;

		if (dateFormatString == null) {
			dateFormatString = "yyyy年MM月dd日 hh:mm:ss";
		}

		this.dateFormatString = dateFormatString;
		this.startEmpty = startEmpty;

		setLayout(new BorderLayout());

		jcalendar.getDayChooser().addPropertyChangeListener(this);
		jcalendar.getDayChooser().setAlwaysFireDayProperty(true); // always fire
																	// "day"
																	// property
																	// even if
																	// the user
																	// selects
																	// the
																	// already
																	// selected
																	// day again
		model = new SpinnerDateModel();

		/*
		 * The 2 lines below were moved to the setModel method.
		 * model.setCalendarField(java.util.Calendar.WEEK_OF_MONTH);
		 * model.addChangeListener(this);
		 */

		// Begin Code change by Mark Brown on 24 Aug 2004
		setModel(model);
		dateSpinner = new JSpinner(model) {
			public void setEnabled(boolean enabled) {
				super.setEnabled(enabled);
				calendarButton.setEnabled(enabled);
			}
		};
		// End Code change by Mark Brown

		String tempDateFortmatString = "";

		if (!startEmpty) {
			tempDateFortmatString = dateFormatString;
		}

		editor = new JSpinner.DateEditor(dateSpinner, tempDateFortmatString);
		dateSpinner.setEditor(editor);
		add(dateSpinner, BorderLayout.CENTER);

		// Display a calendar button with an icon
		if (icon == null) {
			URL iconURL = getClass().getResource("images/JDateChooserIcon.gif");
			icon = new ImageIcon(iconURL);
		}

		calendarButton = new JButton(icon);
		calendarButton.setMargin(new Insets(0, 0, 0, 0));
		calendarButton.addActionListener(this);

		// Alt + 'C' selects the calendar.
		calendarButton.setMnemonic(KeyEvent.VK_C);

		add(calendarButton, BorderLayout.EAST);

		calendarButton.setMargin(new Insets(0, 0, 0, 0));
		popup = new JPopupMenu() {
			public void setVisible(boolean b) {
				Boolean isCanceled = (Boolean) getClientProperty("JPopupMenu.firePopupMenuCanceled");

				if (b
						|| (!b && dateSelected)
						|| ((isCanceled != null) && !b && isCanceled
								.booleanValue())) {
					super.setVisible(b);
				}
			}
		};

		popup.setLightWeightPopupEnabled(true);

		popup.add(jcalendar);
		lastSelectedDate = model.getDate();
		isInitialized = true;
	}

	/**
	 * Called when the jalendar button was pressed.
	 * 
	 * @param e
	 *            the action event
	 */
	public void actionPerformed(ActionEvent e) {
		int x = calendarButton.getWidth()
				- (int) popup.getPreferredSize().getWidth();
		int y = calendarButton.getY() + calendarButton.getHeight();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(model.getDate());
		jcalendar.setCalendar(calendar);
		popup.show(calendarButton, x, y);
		dateSelected = false;
	}

	/**
	 * Listens for a "date" property change or a "day" property change event
	 * from the JCalendar. Updates the dateSpinner and closes the popup.
	 * 
	 * @param evt
	 *            the event
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("day")) {
			dateSelected = true;
			popup.setVisible(false);
			setDate(jcalendar.getCalendar().getTime());
			setDateFormatString(dateFormatString);
		} else if (evt.getPropertyName().equals("date")) {
			setDate((Date) evt.getNewValue());
		}
	}

	/**
	 * Updates the UI of itself and the popup.
	 */
	public void updateUI() {
		super.updateUI();

		if (jcalendar != null) {
			SwingUtilities.updateComponentTreeUI(popup);
		}
	}

	/**
	 * Sets the locale.
	 * 
	 * @param l
	 *            The new locale value
	 */
	public void setLocale(Locale l) {
		dateSpinner.setLocale(l);
		editor = new JSpinner.DateEditor(dateSpinner, dateFormatString);
		dateSpinner.setEditor(editor);
		jcalendar.setLocale(l);
	}

	/**
	 * Gets the date format string.
	 * 
	 * @return Returns the dateFormatString.
	 */
	public String getDateFormatString() {
		return dateFormatString;
	}

	/**
	 * Sets the date format string. E.g "MMMMM d, yyyy" will result in
	 * "July 21, 2004" if this is the selected date and locale is English.
	 * 
	 * @param dateFormatString
	 *            The dateFormatString to set.
	 */
	public void setDateFormatString(String dateFormatString) {
		this.dateFormatString = dateFormatString;
		editor.getFormat().applyPattern(dateFormatString);
		invalidate();
	}

	/**
	 * Returns "JDateChooser".
	 * 
	 * @return the name value
	 */
	public String getName() {
		return "JDateChooser";
	}

	/**
	 * Returns the date.
	 * 
	 * @return the current date
	 */
	public Date getDate() {
		return model.getDate();
	}

	/**
	 * Sets the date. Fires the property change "date".
	 * 
	 * @param date
	 *            the new date.
	 */
	public void setDate(Date date) {
		model.setValue(date);
		if (getParent() != null) {
			getParent().validate();
		}
	}

	/**
	 * Fires property "date" changes, recting on the spinner's state changes.
	 * 
	 * @param e
	 *            the change event
	 */
	public void stateChanged(ChangeEvent e) {
		if (isInitialized) {
			firePropertyChange("date", lastSelectedDate, model.getDate());
			lastSelectedDate = model.getDate();
		}
	}

	/*
	 * The methods: public JSpinner getSpinner() public SpinnerDateModel
	 * getModel() public void setModel(SpinnerDateModel mdl)
	 * 
	 * were added by Mark Brown on 24 Aug 2004. They were added to allow the
	 * setting of the SpinnerDateModel from a source outside the JDateChooser
	 * control. This was necessary in order to allow the JDateChooser to be
	 * integrated with applications using persistence frameworks like Oracle's
	 * ADF/BC4J.
	 */

	/**
	 * Return this controls JSpinner control.
	 * 
	 * @return the JSpinner control
	 */
	public JSpinner getSpinner() {
		return dateSpinner;
	}

	/**
	 * Return the SpinnerDateModel associated with this control.
	 * 
	 * @return the SpinnerDateModel
	 */
	public SpinnerDateModel getModel() {
		return model;
	}

	/**
	 * Set the SpinnerDateModel for this control. This method allows the
	 * JDateChooser control to be used with some persistence frameworks (ie.
	 * Oracle ADF) to bind the control to the database Date value.
	 * 
	 * @param mdl
	 *            the SpinnerDateModel
	 */
	public void setModel(SpinnerDateModel mdl) {
		model = mdl;
		model.setCalendarField(java.util.Calendar.WEEK_OF_MONTH);
		model.addChangeListener(this);
		// Begin Code change by Martin Pietruschka on 16 Sep 2004
		if (dateSpinner != null)
			dateSpinner.setModel(model);
		// End Code change by Mark Brown
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame frame = new JFrame();
		frame.add(new ZDateChooser());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);

				super.windowClosing(e);
			}
		});
		frame.setVisible(true);
	}
}
