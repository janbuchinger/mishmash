/*
 * Copyright 2017 Jan Buchinger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.janbuchinger.code.mishmash.ui.userInput;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * <code>DateTimeField</code> is a text field that guides the user entering time
 * and date. The user only enters the numbers of time or date, all other
 * characters are put automatically.
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public final class DateTimeField extends JFormattedTextField implements KeyListener, FocusListener {

	/**
	 * <code>DATE</code> mode only shows the date ("dd.MM.yyyy").
	 */
	public static final int DATE = 0;

	/**
	 * <code>DATE_TIME</code> mode shows date and time ("dd.MM.yyyy HH:mm").
	 */
	public static final int DATE_TIME = 1;

	/**
	 * <code>TIME</code> mode shows only the time ("HH:mm").
	 */
	public static final int TIME = 2;

	private final static String dateTimeFormat = "dd.MM.yyyy HH:mm";
	private final static String dateFormat = "dd.MM.yyyy";
	private final static String timeFormat = "HH:mm";

	private final SimpleDateFormat sdf;
	private final JLabel label;

	private final int mode;

	private String oldText;

	/**
	 * Constructs a new <code>DateTimeField</code> and initiates a
	 * <code>JLabel</code> with the <code>String</code> "Zeit".
	 * 
	 * @param mode
	 *            The mode this <code>DateTimeField</code> is formatted.
	 *            <p>
	 *            Use either <code>DateTimeField.DATE</code>,
	 *            <code>DateTimeField.DATE_TIME</code> or
	 *            <code>DateTimeField.TIME</code>
	 * 
	 * @see #getLabel() getLabel
	 */
	public DateTimeField(int mode) {
		this(null, null, null, mode);
	}

	/**
	 * Constructs a new <code>DateTimeField</code> and initiates a
	 * <code>JLabel</code> with the <code>String</code> passed in the parameter
	 * label.
	 * 
	 * @param label
	 *            The String to initiate a <code>JLabel</code> with.
	 * @param mode
	 *            The mode this <code>DateTimeField</code> is formatted.
	 *            <p>
	 *            Use either <code>DateTimeField.DATE</code>,
	 *            <code>DateTimeField.DATE_TIME</code> or
	 *            <code>DateTimeField.TIME</code>
	 * 
	 * @see #getLabel()
	 */
	public DateTimeField(String label, int mode) {
		this(null, label, null, mode);
	}

	/**
	 * Constructs a new <code>DateTimeField</code> and initiates a
	 * <code>JLabel</code> with the <code>String</code> "Zeit".
	 * 
	 * @param kl
	 *            A <code>KeyListener</code> that is added to the
	 *            <code>DateTimeField</code> for change detection.
	 * @param mode
	 *            The mode this <code>DateTimeField</code> is formatted.
	 *            <p>
	 *            Use either <code>DateTimeField.DATE</code>,
	 *            <code>DateTimeField.DATE_TIME</code> or
	 *            <code>DateTimeField.TIME</code>
	 * 
	 * @see #getLabel()
	 */
	public DateTimeField(KeyListener kl, int mode) {
		this(null, null, kl, mode);
	}

	/**
	 * Constructs a new <code>DateTimeField</code> and initiates a
	 * <code>JLabel</code> with the <code>String</code> passed in the parameter
	 * label.
	 * 
	 * @param label
	 *            The String to initiate a <code>JLabel</code> with.
	 * @param kl
	 *            A <code>KeyListener</code> that is added to the
	 *            <code>DateTimeField</code> for change detection.
	 * @param mode
	 *            The mode this <code>DateTimeField</code> is formatted.
	 *            <p>
	 *            Use either <code>DateTimeField.DATE</code>,
	 *            <code>DateTimeField.DATE_TIME</code> or
	 *            <code>DateTimeField.TIME</code>
	 * 
	 * @see #getLabel()
	 */
	public DateTimeField(String label, KeyListener kl, int mode) {
		this(null, label, kl, mode);
	}

	/**
	 * Constructs a new <code>DateTimeField</code> and initiates a
	 * <code>JLabel</code> with the <code>String</code> passed in the parameter
	 * label and has the initial value of the passed date.
	 * 
	 * @param date
	 *            A <code>Date</code> object as initial value.
	 * @param label
	 *            The String to initiate a <code>JLabel</code> with.
	 * @param kl
	 *            A <code>KeyListener</code> that is added to the
	 *            <code>DateTimeField</code> for change detection.
	 * @param mode
	 *            The mode this <code>DateTimeField</code> is formatted.
	 *            <p>
	 *            Use either <code>DateTimeField.DATE</code>,
	 *            <code>DateTimeField.DATE_TIME</code> or
	 *            <code>DateTimeField.TIME</code>
	 * 
	 * @see #getLabel()
	 */
	public DateTimeField(Date date, String label, KeyListener kl, int mode) {
		super(new SimpleDateFormat(
				mode >= 0 ? (mode == DATE ? dateFormat : (mode == TIME ? timeFormat : dateTimeFormat))
						: dateTimeFormat));
		this.mode = mode;
		if (mode == TIME)
			this.sdf = new SimpleDateFormat(timeFormat);
		else if (mode == DATE)
			this.sdf = new SimpleDateFormat(dateFormat);
		else if (mode == DATE_TIME)
			this.sdf = new SimpleDateFormat(dateTimeFormat);
		else
			this.sdf = new SimpleDateFormat(dateTimeFormat);
		addKeyListener(this);
		addFocusListener(this);
		setValue(date == null ? new Date() : date);
		if (kl != null)
			addKeyListener(kl);
		if (label != null) {
			if (label.length() > 0)
				this.label = new JLabel(label);
			else
				this.label = new JLabel("Zeit");
		} else {
			this.label = new JLabel("Zeit");
		}
		oldText = "";
	}

	/**
	 * Gets the time parsed from the date/time <code>String</code>.
	 * 
	 * @return The time in milliseconds.
	 */
	public final long getTime() {
		try {
			return sdf.parse(getText()).getTime();
		} catch (ParseException e) {
			return new Date().getTime();
		}
	}

	/**
	 * Gets the time when using <code>TIME</code> mode. Use this method instead of
	 * #getTime() for a more accurate result.
	 * 
	 * @return
	 */
	public final long getTimeOfDay() {
		try {
			Date dateEntered = sdf.parse(getText());
			SimpleDateFormat sdf2 = new SimpleDateFormat(dateFormat);
			return dateEntered.getTime() - sdf2.parse(sdf2.format(dateEntered)).getTime();
		} catch (ParseException e) {
			return 0;
		}
	}

	/**
	 * Sets the time and formats it according to the mode this
	 * <code>DateTimeField</code> is initiated with.
	 * 
	 * @param time
	 *            The time in milliseconds to set.
	 */
	public final void setTime(long time) {
		setValue(new Date(time));
	}

	/**
	 * Gets the <code>JLabel</code> that was initiated during construction.
	 * 
	 * @return A JLabel to identify this <code>DateTimeField</code> with.
	 */
	public final JLabel getLabel() {
		return label;
	}

	/**
	 * Resets the value of this <code>DateTimeField</code> to now.
	 */
	public final void clear() {
		setValue(new Date());
	}

	@Override
	public final void focusGained(FocusEvent e) {
		oldText = getText();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				selectAll();
			}
		});
	}

	@Override
	public final void focusLost(FocusEvent e) {
		String newText = getText();
		String testText = "";
		if (newText.length() < oldText.length()) {
			try {
				testText = newText + oldText.substring(newText.length());
				sdf.parse(testText);
				setText(testText);
			} catch (Exception e2) {

			}
		}
	}

	@Override
	public final void keyReleased(KeyEvent e) {
		if (e.getSource() == this) {
			switch (mode) {
			case DATE:
				if (getText().length() == 2)
					setText(getText() + ".");
				else if (getText().length() == 5)
					setText(getText() + ".");
				else if (getText().length() > 10)
					setText(getText().substring(0, 10));
				break;
			case TIME:
				if (getText().length() == 2)
					setText(getText() + ":");
				break;
			default:
				if (getText().length() == 2)
					setText(getText() + ".");
				else if (getText().length() == 5)
					setText(getText() + ".");
				else if (getText().length() == 10)
					setText(getText() + " ");
				else if (getText().length() == 13)
					setText(getText() + ":");
				else if (getText().length() > 16)
					setText(getText().substring(0, 16));
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}
}
