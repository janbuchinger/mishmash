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
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
/**
 * <code>DoubleTextField</code> is a text field to input numbers. It always shows 2 fraction digits.
 * 
 * @author Jan Buchinger
 *
 */
@SuppressWarnings("serial")
public final class DoubleTextField extends JFormattedTextField implements FocusListener {

	private final JLabel label;

	private final static NumberFormat getFormat() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		nf.setMaximumIntegerDigits(8);
		return nf;
	}
	
//	private final static NumberFormat getFormat(int nFractionDigits, int nIntegerDigits) {
//		NumberFormat nf = NumberFormat.getInstance();
//		nf.setMinimumFractionDigits(nFractionDigits);
//		nf.setMaximumFractionDigits(nFractionDigits);
//		nf.setMaximumIntegerDigits(nIntegerDigits);
//		return nf;
//	}

	/**
	 * Constructs a new <code>DoubleTextField</code> with an initial value of
	 * zero (0.00).
	 * <p>
	 * A <code>JLabel</code> is initiated with the <code>String</code> passed.
	 * 
	 * @param label
	 *            The label <code>String</code> to identify this
	 *            <code>DateTimeField</code> with.
	 * @see <code>getLabel()</code>
	 */
	public DoubleTextField(String label) {
		this(0.0, label, null);
	}

	/**
	 * Constructs a new <code>DoubleTextField</code> with an initial value of
	 * zero (0.00).
	 * <p>
	 * The supplied <code>KeyListener</code> is added.
	 * 
	 * @param kl
	 *            A <code>KeyListener</code> to register changes.
	 */
	public DoubleTextField(KeyListener kl) {
		this(0.0, null, kl);
	}

	/**
	 * Constructs a new <code>DoubleTextField</code> with the initial value
	 * supplied.
	 * <p>
	 * The supplied <code>KeyListener</code> is added.
	 * 
	 * @param value
	 *            The initial value of this <code>DoubleTextField</code>.
	 * @param kl
	 *            A <code>KeyListener</code> to register changes.
	 */
	public DoubleTextField(double value, KeyListener kl) {
		this(value, null, kl);
	}

	/**
	 * 
	 * Constructs a new <code>DoubleTextField</code> with an initial value of
	 * zero (0.00).
	 * <p>
	 * A <code>JLabel</code> is initiated with the <code>String</code> passed.
	 * <p>
	 * The supplied <code>KeyListener</code> is added.
	 * 
	 * @param label
	 *            The label <code>String</code> to identify this
	 *            <code>DateTimeField</code> with.
	 * @param kl
	 *            A <code>KeyListener</code> to register changes.
	 * @see <code>getLabel()</code>
	 */
	public DoubleTextField(String label, KeyListener kl) {
		this(0.0, label, kl);
	}

	/**
	 * Constructs a new <code>DoubleTextField</code> with the initial value
	 * supplied.
	 * <p>
	 * A <code>JLabel</code> is initiated with the <code>String</code> passed.
	 * <p>
	 * The supplied <code>KeyListener</code> is added.
	 * 
	 * @param value
	 *            The initial value of this <code>DoubleTextField</code>.
	 * @param label
	 *            The label <code>String</code> to identify this
	 *            <code>DateTimeField</code> with.
	 * @param kl
	 *            A <code>KeyListener</code> to register changes.
	 * @see <code>getLabel()</code>
	 */
	public DoubleTextField(double value, String label, KeyListener kl) {
		super(DoubleTextField.getFormat());
		setColumns(10);
		addFocusListener(this);
		setDoubleValue(value);
		if (label == null) {
			this.label = new JLabel("Zahl");
		} else if (label.equals("")) {
			this.label = new JLabel("Zahl");
		} else {
			this.label = new JLabel(label);
		}
		if (kl != null)
			addKeyListener(kl);
	}

	/**
	 * Sets the current value to display.
	 * 
	 * @param value
	 *            The value to display.
	 */
	public final void setDoubleValue(double value) {
		setValue(new Double(value));
	}

	/**
	 * Gets the current value.
	 * 
	 * @return The current value.
	 */
	public final double getDoubleValue() {
		try {
			return DoubleTextField.getFormat().parse(getText()).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0.0;
		}
	}

	/**
	 * Resets the current value to zero (0.00).
	 */
	public final void clear() {
		setValue(new Double(0.0));
	}

	@Override
	public final void focusGained(FocusEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public final void run() {
				selectAll();
			}
		});
	}

	@Override
	public final void focusLost(FocusEvent e) {}

	public final JLabel getLabel() {
		return label;
	}
}
