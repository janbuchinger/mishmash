/*
 * Copyright 2018 Jan Buchinger
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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.text.Document;

/**
 * <code>JTextAreaWithPopUp</code> is a <code>JTextArea</code> that offers the
 * customary text editing functions cut, copy and paste via a
 * <code>JPopupMenu</code>, triggered by right clicking.
 * 
 * @author Jan Buchinger
 * @author
 */
@SuppressWarnings("serial")
public class JTextAreaWithPopUp extends JTextArea implements ActionListener {

	private JMenuItem miCut;
	private JMenuItem miCopy;
	private JMenuItem miPaste;

	/**
	 * From <code>JTextArea</code> Javadoc: Constructs a new <code>JTextArea</code>.
	 * A default model is created, the initial string is null, and the rows/columns
	 * is set to 0.
	 * 
	 * @see JTextArea
	 */
	public JTextAreaWithPopUp() {
		this(null, null, 0, 0);
	}

	/**
	 * From <code>JTextArea</code> Javadoc: Constructs a new <code>JTextArea</code>
	 * with the given document model, and defaults for all of the other arguments
	 * (null, 0, 0).
	 * 
	 * @param doc
	 *            The model to use
	 * @see JTextArea
	 */
	public JTextAreaWithPopUp(Document doc) {
		this(doc, null, 0, 0);
	}

	/**
	 * From <code>JTextArea</code> Javadoc: Constructs a new <code>JTextArea</code>
	 * with the specified text displayed. A default model is created and
	 * rows/columns are set to 0.
	 * 
	 * @param text
	 *            the text to be displayed, or null
	 * @see JTextArea
	 */
	public JTextAreaWithPopUp(String text) {
		this(null, text, 0, 0);
	}

	/**
	 * From <code>JTextArea</code> Javadoc: Constructs a new empty TextArea with the
	 * specified number of rows and columns. A default model is created, and the
	 * initial string is null.
	 * 
	 * @param rows
	 *            the number of rows &gt;= 0
	 * @param columns
	 *            the number of columns &gt;= 0
	 * @exception IllegalArgumentException
	 *                if the rows or columns arguments are negative.
	 * @see JTextArea
	 */
	public JTextAreaWithPopUp(int rows, int columns) {
		this(null, null, rows, columns);
	}

	/**
	 * From <code>JTextArea</code> Javadoc: Constructs a new TextArea with the
	 * specified text and number of rows and columns. A default model is created.
	 *
	 * @param text
	 *            the text to be displayed, or null
	 * @param rows
	 *            the number of rows &gt;= 0
	 * @param columns
	 *            the number of columns &gt;= 0
	 * @exception IllegalArgumentException
	 *                if the rows or columns arguments are negative.
	 * @see JTextArea
	 */
	public JTextAreaWithPopUp(String text, int rows, int columns) {
		this(null, text, rows, columns);
	}

	/**
	 * From <code>JTextArea</code> Javadoc: Constructs a new JTextArea with the
	 * specified number of rows and columns, and the given model. All of the
	 * constructors feed through this constructor.
	 *
	 * @param doc
	 *            the model to use, or create a default one if null
	 * @param text
	 *            the text to be displayed, null if none
	 * @param rows
	 *            the number of rows &gt;= 0
	 * @param columns
	 *            the number of columns &gt;= 0
	 * @exception IllegalArgumentException
	 *                if the rows or columns arguments are negative.
	 * @see JTextArea
	 */
	public JTextAreaWithPopUp(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
		JTextArea ta = new JTextArea(doc, text, rows, columns);
		initPopup();
	}

	private void initPopup() {
		JPopupMenu popup = new JPopupMenu("Text Actions");
		miCut = new JMenuItem("Cut");
		miCut.addActionListener(this);
		popup.add(miCut);
		miCopy = new JMenuItem("Copy");
		miCopy.addActionListener(this);
		popup.add(miCopy);
		miPaste = new JMenuItem("Paste");
		miPaste.addActionListener(this);
		popup.add(miPaste);

		setComponentPopupMenu(popup);
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
}
