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
package net.janbuchinger.code.mishmash.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.text.SimpleDateFormat;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * UIFx is an abbreviation for user interface functions.
 * 
 * @author Jan Buchinger
 * 
 */
public class UIFx {

	/**
	 * Formats the specified time in milliseconds as HH:mm:ss.
	 * 
	 * @param millis
	 *            The time to be formatted.
	 * @return The formatted time as HH:mm:ss.
	 */
	public final static String formatMillisAsHoursMinutesSeconds(long millis) {
		long hours;
		long minutes;
		long seconds;

		seconds = millis / 1000;
		minutes = seconds / 60;
		seconds = seconds % 60;
		hours = minutes / 60;
		minutes = minutes % 60;

		return (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
				+ (seconds < 10 ? "0" : "") + seconds;
	}

	/**
	 * Formats the specified time in milliseconds as HH:mm:ss.SSS.
	 * 
	 * @param millis
	 *            The time to be formatted.
	 * @return The formatted time as HH:mm:ss.SSS.
	 */
	public final static String formatMillisAsHoursMinutesSecondsMillis(long millis) {
		long hours;
		long minutes;
		long seconds;

		seconds = millis / 1000;
		millis = millis % 1000;
		minutes = seconds / 60;
		seconds = seconds % 60;
		hours = minutes / 60;
		minutes = minutes % 60;

		return (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":"
				+ (seconds < 10 ? "0" : "") + seconds + "." + (millis < 10 ? "00" : millis < 100 ? "0" : "")
				+ millis;
	}

	/**
	 * Initiates a new <code>GridBagConstraints</code> with gridx = 0, gridy =
	 * 0, gridwidth = 1, gridheight = 1, weightx = 0, weighty = 0, anchor =
	 * center, fill = horizontal, insets = (top = 0, left = 2, bottom = 2, right
	 * = 0), ipadx = 0, ipady = 0
	 * 
	 * @return A default new <code>GridBagConstraints</code>.
	 */
	public final static GridBagConstraints initGridBagConstraints() {
		return new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(0, 2, 2, 0), 0, 0);
	}

	/**
	 * Initializes a Vertical scroll only <code>JScrollPane</code>.
	 * 
	 * @param component
	 *            The <code>JComponent</code> that will be put inside the
	 *            <code>JScrollPane</code>.
	 * @param scrollSpeed
	 *            The value to set the
	 *            <code>ScrollBar.setUnitIncrement(scrollSpeed)</code>.
	 * @return A new <code>JScrollPane</code> containing the supplied component.
	 */
	public final static JScrollPane initVerticalScrollPane(JComponent component, int scrollSpeed) {
		JScrollPane scrp = new JScrollPane(component);
		if (component instanceof JTextArea)
			((JTextArea) component).setLineWrap(true);
		scrp.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
		scrp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return scrp;
	}

	/**
	 * Initializes a Vertical scroll only <code>JScrollPane</code> with a
	 * preferred size.
	 * 
	 * @param component
	 *            The <code>JComponent</code> that will be put inside the
	 *            <code>JScrollPane</code>.
	 * @param scrollSpeed
	 *            The value to set the
	 *            <code>ScrollBar.setUnitIncrement(scrollSpeed)</code>.
	 * @param prefWidth
	 *            Preferred width.
	 * @param prefHeight
	 *            Preferred height.
	 * @return The new <code>JScrollPane</code> containing the supplied
	 *         <code>JComponent</code>.
	 */
	public static Component initVerticalScrollPane(JComponent component, int scrollSpeed, int prefWidth,
			int prefHeight) {
		JScrollPane scrp = new JScrollPane(component);
		if (prefWidth != 0 || prefHeight != 0) {
			scrp.setPreferredSize(new Dimension(prefWidth, prefHeight));
		}
		scrp.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
		scrp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		return scrp;
	}

	/**
	 * Initializes a Horizontal scroll only <code>JScrollPane</code>.
	 * 
	 * @param component
	 *            The <code>JComponent</code> that will be put inside the
	 *            <code>JScrollPane</code>.
	 * @param scrollSpeed
	 *            The value to set the
	 *            <code>ScrollBar.setUnitIncrement(scrollSpeed)</code>.
	 * @return The new <code>JScrollPane</code> containing the supplied
	 *         <code>JComponent</code>.
	 */
	public final static JScrollPane initHorizontalScrollPane(JComponent component, int scrollSpeed) {
		JScrollPane scrp = new JScrollPane(component);
		scrp.getHorizontalScrollBar().setUnitIncrement(scrollSpeed);
		scrp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		return scrp;
	}

	/**
	 * Initializes a <code>JScrollPane</code> That shows scroll bars as needed
	 * with a preferred size.
	 * 
	 * @param component
	 *            The <code>JComponent</code> that will be put inside the
	 *            <code>JScrollPane</code>.
	 * @param scrollSpeed
	 *            The value to set the
	 *            <code>ScrollBar.setUnitIncrement(scrollSpeed)</code>.
	 * @param prefWidth
	 *            Preferred width.
	 * @param prefHeight
	 *            Preferred height.
	 * @return The new <code>JScrollPane</code> containing the supplied
	 *         <code>JComponent</code>.
	 */
	public final static JScrollPane initScrollPane(JComponent component, int scrollSpeed, int prefWidth,
			int prefHeight) {
		JScrollPane scrp = new JScrollPane(component);
		if (prefWidth != 0 || prefHeight != 0) {
			scrp.setPreferredSize(new Dimension(prefWidth, prefHeight));
		}
		scrp.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
		scrp.getHorizontalScrollBar().setUnitIncrement(scrollSpeed);
		scrp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		return scrp;
	}

	/**
	 * Initializes and returns a <code>JScrollPane</code> That shows scroll bars
	 * as needed.
	 * 
	 * @param component
	 *            The <code>JComponent</code> that will be put inside the
	 *            <code>JScrollPane</code>.
	 * @param scrollSpeed
	 *            The value to set the
	 *            <code>ScrollBar.setUnitIncrement(scrollSpeed)</code>.
	 * @return The new <code>JScrollPane</code> containing the supplied
	 *         <code>JComponent</code>.
	 */
	public final static JScrollPane initScrollPane(JComponent component, int scrollSpeed) {
		return initScrollPane(component, scrollSpeed, 0, 0);
	}

	/**
	 * Packs and centers a <code>Window</code> on the screen.
	 * 
	 * @param frame
	 *            The <code>Window</code> to be centered.
	 */
	public static void packAndCenter(Window frame) {
		frame.pack();
		center(frame);
	}

	/**
	 * Packs and centers a <code>Window</code> relative to another
	 * <code>Window</code>.
	 * 
	 * @param subject
	 *            The <code>Window</code> to be centered.
	 * @param parent
	 *            The <code>Window</code> assumed as center.
	 */
	public static void packAndCenter(Window subject, Window parent) {
		subject.pack();
		center(subject, parent);
	}

	/**
	 * Sets the size of a <code>Window</code> and centers it on the screen.
	 * 
	 * @param frame
	 *            The <code>Window</code> to be centered.
	 * @param width
	 *            The <code>Window</code> width.
	 * @param height
	 *            The <code>Window</code> height.
	 */
	public static void sizeAndCenter(Window frame, int width, int height) {
		frame.setSize(width, height);
		center(frame);
	}

	/**
	 * Sets the size of a <code>Window</code> relative to the screen síze and
	 * centers it on the screen.
	 * 
	 * @param subject
	 *            The <code>Window</code> to size and center.
	 * @param relativeWidth
	 *            the relative width between 0 and 1.
	 * @param relativeHeight
	 *            the relative height between 0 and 1.
	 */
	public static void sizeAndCenter(Window subject, double relativeWidth, double relativeHeight) {
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		subject.setSize((int) (relativeWidth * scr.width), (int) (relativeHeight * scr.height));
		subject.setLocation(scr.width / 2 - subject.getWidth() / 2, scr.height / 2 - subject.getHeight() / 2);
	}

	/**
	 * Sets the size of a <code>Window</code> relative to the screen síze and
	 * centers relative to another <code>Window</code>.
	 * 
	 * @param subject
	 *            The <code>Window</code> to size and center.
	 * @param parent
	 *            The <code>Window</code> to center at.
	 * @param relativeWidth
	 *            the relative width between 0 and 1.
	 * @param relativeHeight
	 *            the relative height between 0 and 1.
	 */
	public static void sizeAndCenter(Window subject, Window parent, double relativeWidth, double relativeHeight) {
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		subject.setSize((int) (relativeWidth * scr.width), (int) (relativeHeight * scr.height));
		subject.setLocation(parent.getLocation().x + parent.getWidth() / 2 - subject.getWidth() / 2,
				parent.getLocation().y + parent.getHeight() / 2 - subject.getHeight() / 2);
	}

	/**
	 * Centers a <code>Window</code> on the screen.
	 * 
	 * @param window
	 *            The <code>Window</code> to center.
	 */
	public static void center(Window window) {
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(scr.width / 2 - window.getWidth() / 2, scr.height / 2 - window.getHeight() / 2);
	}

	/**
	 * Centers a <code>Window</code> relative to another <code>Window</code>.
	 * 
	 * @param window
	 *            The <code>Window</code> to center.
	 * @param owner
	 *            The <code>Window</code> to center at.
	 */
	public static void center(Window window, Window owner) {
		window.setLocation(owner.getLocation().x + owner.getWidth() / 2 - window.getWidth() / 2,
				owner.getLocation().y + owner.getHeight() / 2 - window.getHeight() / 2);
	}

	/**
	 * Creates a new <code>SimpleDateFormat</code> with the pattern String
	 * "dd.MM.yyyy HH:mm:ss" (17.02.2002 15:25:45).
	 * 
	 * @return A new <code>SimpleDateFormat</code> for display purposes.
	 */
	public static SimpleDateFormat initPreciseDisplayDateTimeFormat() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	}

	/**
	 * Creates a new <code>SimpleDateFormat</code> with the pattern String
	 * "dd.MM.yyyy HH:mm:ss.SSS" (17.02.2002 15:25:45.125).
	 * 
	 * @return A new <code>SimpleDateFormat</code> for display purposes.
	 */
	public static SimpleDateFormat initVeryPreciseDisplayDateTimeFormat() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
	}

	/**
	 * Creates a new <code>SimpleDateFormat</code> with the pattern String
	 * "dd.MM.yyyy HH:mm" (17.02.2002 15:25).
	 * 
	 * @return A new <code>SimpleDateFormat</code> for display purposes.
	 */
	public static SimpleDateFormat initDisplayDateTimeFormat() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm");
	}

	/**
	 * Creates a new <code>SimpleDateFormat</code> with the pattern String
	 * "dd.MM.yyyy" (17.02.2002).
	 * 
	 * @return A new <code>SimpleDateFormat</code> for display purposes.
	 */
	public static SimpleDateFormat initDisplayDateFormat() {
		return new SimpleDateFormat("dd.MM.yyyy");
	}

	/**
	 * Creates a new <code>SimpleDateFormat</code> with the pattern String
	 * "HH:mm" (15:25).
	 * 
	 * @return A new <code>SimpleDateFormat</code> for display purposes.
	 */
	public static SimpleDateFormat initDisplayTimeFormat() {
		return new SimpleDateFormat("HH:mm");
	}
}
