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

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.janbuchinger.code.mishmash.ui.UIFx;
import net.janbuchinger.code.mishmash.ui.dialog.dirChooser.DirChooserDialog;


/**
 * <code>FolderPathTextField</code> combines <code>DirChooserDialog</code> with
 * a <code>JTextFieldWithPopUp</code> to enable easy directory choice.
 * 
 * @author Jan Buchinger
 * 
 * @see JTextFieldWithPopUp
 * @see net.janbuchinger.code.mishmash.ui.dialog.dirChooser.DirChooserDialog
 */
@SuppressWarnings("serial")
public class FolderPathTextField extends JPanel implements ActionListener {
	private final JTextFieldWithPopUp tfPath;
	private final JButton btSearch;
	private final Window w;

	/**
	 * Constructs a new <code>FolderPathTextField</code> with text field width =
	 * 35.
	 * 
	 * @param w
	 *            should be instance of <code>Frame</code> or
	 *            <code>Dialog</code>. If this doesn't apply the
	 *            <code>DirChooserDialog</code> will not show.
	 *            <p>
	 *            The <code>Window</code> is required for the modality of
	 *            <code>DirChooserDialog</code>.
	 */
	public FolderPathTextField(Window w) {
		this(w, 35);
	}

	/**
	 * Constructs a new <code>FolderPathTextField</code>.
	 * 
	 * @param w
	 *            should be instance of <code>Frame</code> or
	 *            <code>Dialog</code>. If this doesn't apply the
	 *            <code>DirChooserDialog</code> will not show.
	 *            <p>
	 *            The <code>Window</code> is required for the modality of
	 *            <code>DirChooserDialog</code>.
	 * @param textFieldWidth
	 *            The width of the path text field.
	 */
	public FolderPathTextField(Window w, int textFieldWidth) {
		super(new BorderLayout());
		this.w = w;
		tfPath = new JTextFieldWithPopUp(textFieldWidth);
		btSearch = new JButton("...");
		btSearch.addActionListener(this);
		GridBagConstraints c = UIFx.initGridBagConstraints();
		c.weightx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		JPanel pnc = new JPanel(new GridBagLayout());
		pnc.add(tfPath, c);
		add(pnc, BorderLayout.CENTER);
		add(btSearch, BorderLayout.EAST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DirChooserDialog dcd = null;
		if (e.getSource() == btSearch) {
			if (w instanceof Dialog) {
				dcd = new DirChooserDialog((Dialog) w, getPath());
			} else if (w instanceof Frame) {
				dcd = new DirChooserDialog((Frame) w, getPath());
			}
			if (dcd == null)
				return;
			dcd.setVisible(true);
			if (dcd.getAnswer() == DirChooserDialog.OK)
				tfPath.setText(dcd.getPath());
		}
	}

	/**
	 * Gets rid of any trailing slashes and checks if the path is not a file. If
	 * the path is a file then its parent directory is set and returned.
	 * 
	 * @return The path to the selected directory without trailing slashes.
	 */
	public final String getPath() {
		File fTest = new File(tfPath.getText());
		if (fTest.isFile())
			fTest = fTest.getParentFile();
		tfPath.setText(fTest.getPath());
		return tfPath.getText();
	}

	/**
	 * Sets the path in the text field.
	 * 
	 * @param path
	 *            The path to be set.
	 */
	public void setPath(String path) {
		tfPath.setText(path);
	}
}