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

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ComboBoxEditor;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * <code>JTextFieldWithPopUp</code> is a JTextfield that offers the customary
 * text editing functions cut, copy and paste via a <code>JPopupMenu</code>,
 * triggered by right clicking. It also implements <code>ComboBoxEditor</code>
 * for use in <code>JComboBox</code>.
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public class JTextFieldWithPopUp extends JTextField implements ActionListener, ComboBoxEditor {

	private final JMenuItem miCut, miCopy, miPaste;

	private final JPopupMenu menu;

	private boolean isInitiated = false;

	/**
	 * Constructs a new <code>JTextFieldWithPopUp</code>. A default model is
	 * created, the initial string is null, and the number of columns is set to
	 * 0.
	 */
	public JTextFieldWithPopUp() {
		this(0);
	}

	/**
	 * Constructs a new empty <code>JTextFieldWithPopUp</code> with the
	 * specified number of columns. A default model is created and the initial
	 * string is set to null.
	 * 
	 * @param columns
	 *            The number of columns to use to calculate the preferred width;
	 *            if columns is set to zero, the preferred width will be
	 *            whatever naturally results from the component implementation.
	 */
	public JTextFieldWithPopUp(int columns) {
		this(null, columns);
	}

	/**
	 * Constructs a new <code>JTextFieldWithPopUp</code> initialized with the
	 * specified text. A default model is created and the number of columns is
	 * 0.
	 * 
	 * @param text
	 *            The text to be displayed, or <code>null</code>.
	 */
	public JTextFieldWithPopUp(String text) {
		this(text, 0);
	}

	/**
	 * Constructs a new <code>JTextFieldWithPopUp</code> initialized with the
	 * specified text and columns. A default model is created.
	 * 
	 * @param text
	 *            The text to be displayed, or <code>null</code>.
	 * @param columns
	 *            The number of columns to use to calculate the preferred width;
	 *            if columns is set to zero, the preferred width will be
	 *            whatever naturally results from the component implementation.
	 */
	public JTextFieldWithPopUp(String text, int columns) {
		this(null, text, columns);
	}

	/**
	 * Constructs a new <code>JTextFieldWithPopUp</code> that uses the given
	 * text storage model and the given number of columns. This is the
	 * constructor through which the other constructors feed. If the document is
	 * <code>null</code>, a default model is created.
	 * 
	 * @param doc
	 *            The text storage to use; if this is <code>null</code>, a
	 *            default will be provided by calling the
	 *            <code>createDefaultModel</code> method.
	 * @param text
	 *            The text to be displayed, or <code>null</code>.
	 * @param columns
	 *            The number of columns to use to calculate the preferred width;
	 *            if columns is set to zero, the preferred width will be
	 *            whatever naturally results from the component implementation.
	 */
	public JTextFieldWithPopUp(Document doc, String text, int columns) {
		super(doc, text, columns);

		miCut = new JMenuItem("Ausschneiden");
		miCut.addActionListener(this);

		miCopy = new JMenuItem("Kopieren");
		miCopy.addActionListener(this);

		miPaste = new JMenuItem("Einfügen");
		miPaste.addActionListener(this);

		menu = new JPopupMenu();
		menu.add(miCut);
		menu.add(miCopy);
		menu.add(miPaste);

		setComponentPopupMenu(menu);

		isInitiated = true;
	}

	/**
	 * {@inheritDoc} Also the <code>JMenuItem</code>s cut and paste are enabled
	 * or disabled.
	 */
	@Override
	public void setEditable(boolean editable) {
		if (isInitiated) {
			miCut.setEnabled(editable);
			miPaste.setEnabled(editable);
		}
		super.setEditable(editable);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * When <code>setEnabled(false)</code> the popup menu is disabled too.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		if (enabled) {
			setComponentPopupMenu(menu);
		} else {
			setComponentPopupMenu(null);
		}
		super.setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miCut) {
			String selection = getSelectedText();
			if (selection != null) {
				if (selection.length() > 0) {
					StringSelection data = new StringSelection(selection);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(data, data);
					replaceSelection("");
				}
			}
		} else if (e.getSource() == miCopy) {
			String selection = getSelectedText();
			if (selection != null) {
				if (selection.length() > 0) {
					StringSelection data = new StringSelection(selection);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(data, data);
				}
			}
		} else if (e.getSource() == miPaste) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable clipData = clipboard.getContents(clipboard);
			if (clipData != null) {
				try {
					if (clipData.isDataFlavorSupported(DataFlavor.stringFlavor)) {
						String s = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
						replaceSelection(s);
					}
				} catch (UnsupportedFlavorException ufe) {
					System.err.println("Flavor unsupported: " + ufe);
				} catch (IOException ioe) {
					System.err.println("Data not available: " + ioe);
				}
			}
		}
	}

	@Override
	public Component getEditorComponent() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @param anObject
	 *            Preferably a <code>String</code> value. If the
	 *            <code>Object</code> passed is not an instance of
	 *            <code>String</code> the <code>toString</code> method is used.
	 *            If <code>null</code> is passed an empty <code>String</code> is
	 *            set.
	 */
	@Override
	public void setItem(Object anObject) {
		if (anObject instanceof String) {
			setText((String) anObject);
		} else {
			if (anObject != null)
				setText(anObject.toString());
			else
				setText("");
		}
	}

	@Override
	public String getItem() {
		return getText();
	}
}
