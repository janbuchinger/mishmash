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
package net.janbuchinger.code.mishmash.demo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import net.janbuchinger.code.mishmash.PropFx;
import net.janbuchinger.code.mishmash.ui.ImagePanel;
import net.janbuchinger.code.mishmash.ui.UIFx;
import net.janbuchinger.code.mishmash.ui.dialog.dirChooser.DirChooserDialog;
import net.janbuchinger.code.mishmash.ui.userInput.DBRadioButton;
import net.janbuchinger.code.mishmash.ui.userInput.DateTimeField;
import net.janbuchinger.code.mishmash.ui.userInput.DoubleTextField;
import net.janbuchinger.code.mishmash.ui.userInput.FolderPathTextField;
import net.janbuchinger.code.mishmash.ui.userInput.JTextFieldWithPopUp;

public final class DemoUI implements ActionListener {

	private final JFrame frm;

	private final DateTimeField dtTime, dtDateTime, dtDate;

	private final DBRadioButton rbOne, rbTwo, rbThree;

	private final DoubleTextField dfDoubleTextField;

	private final FolderPathTextField fpFolderPathTextField;

	private final JTextFieldWithPopUp tfJTextFieldWithPopUp;

	private final JButton btDirChooserDialog;
	private final JButton btDirChooserDialogRelative;

	private final ImagePanel imagePanel;

	public DemoUI() {

		frm = new JFrame();

		/*
		 * Text Panes for user input components
		 */

		JPanel uiPanel = new JPanel(new GridBagLayout());
		JTextPane tpDateTimeField1 = getTextPane(uiPanel);
		tpDateTimeField1
				.setText("<html><h2>DateTimeField</h2><p class=\"last\">DateTimeField has three modes:<br></p>"
						+ "</html>");
		JTextPane tpDateTimeField2 = getTextPane(uiPanel);
		tpDateTimeField2.setText("<html>"
				+ "<p>Input only the numerical values, other characters appear automatically.</p>"
				+ "<p>When focused all text is selected to be overwritten.</p>"
				+ "<p>when the focus is lost, a missing part at the end of the date string is filled in"
				+ " with the previous string.</p>"
				+ "<p>DateTimeField has the functions long getTime() and setTime(long time) to "
				+ "interact with it.</p>"
				+ "<p>Use DateTimeField.getTimeOfDay() when in DateTimeField.TIME mode.</p>"
				+ "<p>DateTimeField can be constructed with optionally a keylistener to be added "
				+ "for registering changes.</p>"
				+ "<p>DateTimeField can be constructed with optionally a label string to construct "
				+ "a JLabel that can later<br>be retrieved via the JLabel getLabel() method.</p>" + "</html>");

		JTextPane tpDBRadioButton = getTextPane(uiPanel);
		tpDBRadioButton.setText("<html><h2>DBRadiobutton, DBRadioButton2</h2>"
				+ "<p>DBRadioButton extends JRadiobutton and enhances it by an ID.</p>"
				+ "<p>DBRadioButton has the function int getId().</p>"
				+ "<p>DBRadioButton2 extends DBRadioButton and enhances it by another ID.</p>"
				+ "<p>DBRadioButton2 has the function int getId2().</p>" + "</html>");

		JTextPane tpDoubleTextField = getTextPane(uiPanel);
		tpDoubleTextField.setText("<html><h2>DoubleTextField</h2>"
				+ "<p>DoubleTextField is a JFormattedTextfield with a number format with 8 "
				+ "integer and 2 fraction digits.</p>"
				+ "<p>DoubleTextField has the functions double getDoubleValue() and "
				+ "setDoubleValue(double value).</p>"
				+ "<p class=\"last\">DoubleTextField has a function clear() that resets it "
				+ "to zero (0.00).</p>" + "</html>");

		JTextPane tpFolderPathTextField = getTextPane(uiPanel);
		tpFolderPathTextField.setText("<html><h2>FolderPathTextField</h2>"
				+ "<p>FolderPathTextField is a TextField with a \"...\"Button to its right.</p>"
				+ "<p>This Button opens a DirChooserDialog to select a directory.</p>" + "</html>");

		JTextPane tpJTextFieldWithPopUp = getTextPane(uiPanel);
		tpJTextFieldWithPopUp.setText("<html><h2>JTextFieldWithPopUp</h2>"
				+ "<p class=\"last\">JTextFieldWithPopUp is a JTextField with a popup menu that "
				+ "lets the user cut, copy and paste text.</p>" + "</html>");

		/*
		 * Text Panes Dialog classes
		 */

		JTextPane tpDialogEscape = getTextPane(uiPanel);
		tpDialogEscape.setText("<html><h2>DialogEscapeHook, EscapeListener</h2>"
				+ "<p>DialogEscapeHook can be instantiated by a JDialog to enable closing "
				+ "the dialog by pressing the escape key.</p>"
				+ "<p>The Interface Escapelistener can be implemented to customize the escape "
				+ "behaviour.</p>" + "</html>");

		JTextPane tpInfoDialog = getTextPane(uiPanel);
		tpInfoDialog.setText(
				"<html><h2>InfoDialog</h2>" + "<p>InfoDialog is a JDialog that displays a html file.</p>"
						+ "<p>Custom CSS rules can be defined.</p>" + "</html>");

		JTextPane tpDirChooserDialog = getTextPane(uiPanel);
		tpDirChooserDialog.setText("<html><h2>DirChooserDialog</h2>"
				+ "<p>DirChooserDialog is a JTree that lets the user browse for a directory to select.</p>"
				+ "<p>It can be relative to a parent directory.</p>" + "</html>");

		/*
		 * Text Panes for FX classes
		 */
		JTextPane tpChaosFx = getTextPane(uiPanel);
		tpChaosFx.setText("<html><h2>ChaosFx</h2>"
				+ "<p>ChaosFx is supposed to be a random number generator producing a base 36 "
				+ "integer number.</p>" + "</html>");

		JTextPane tpDBFx = getTextPane(uiPanel);
		tpDBFx.setText("<html><h2>DBFx</h2>"
				+ "<p>DBFx is supposed to host functions related to database data.</p>" + "</html>");

		JTextPane tpFSFx = getTextPane(uiPanel);
		tpFSFx.setText("<html><h2>FSFx</h2>"
				+ "<p>DBFx is supposed to host functions related to database data.</p>" + "</html>");

		JTextPane tpPropFx = getTextPane(uiPanel);
		tpPropFx.setText("<html><h2>PropFx</h2>"
				+ "<p>PropFx has shortcut functions to System.getProperty(\"...\") for "
				+ "different properties</p>" + "</html>");

		JTextPane tpUIFx = getTextPane(uiPanel);
		tpUIFx.setText("<html><h2>UIFx</h2>"
				+ "<p>UIFx has functions to center and size frames / dialogs, initiating scroll "
				+ "panes on the fly and initiating<br>different SimpleDateFormats.</p>" + "</html>");

		/*
		 * Text Panes for DBO classes
		 */
		JTextPane tpDBO = getTextPane(uiPanel);
		tpDBO.setText("<html><h2>DBO</h2>"
				+ "<p>DBO (DataBase Object) is intendet to be a superclass for modeling database "
				+ "objects with<br>one unique integer key.</p>" + "</html>");

		JTextPane tpDBO2 = getTextPane(uiPanel);
		tpDBO2.setText("<html><h2>DBO2</h2>"
				+ "<p>DBO2 (DataBase Object 2) is a subclass of DBO and enhances it by another integer "
				+ "index.</p>" + "</html>");

		JTextPane tpDBOLabel = getTextPane(uiPanel);
		tpDBOLabel.setText("<html><h2>DBOLabel</h2>"
				+ "<p>DBOLabel can be used as a UI label for showing a summary of database data.</p>"
				+ "<p>DBOLabel subclasses DBO and enhances it by a String and implements toString().</p>"
				+ "</html>");

		JTextPane tpDBO2Label = getTextPane(uiPanel);
		tpDBO2Label.setText("<html><h2>DBO2Label</h2>"
				+ "<p>DBO2Label DBO2Label subclasses DBO2 and enhances it by a String and "
				+ "implements toString().</p>" + "</html>");

		JTextPane tpDBO2Label2 = getTextPane(uiPanel);
		tpDBO2Label2.setText("<html><h2>DBO2Label2</h2>"
				+ "<p>DBO2Label2 subclasses DBO2Label and enhances it by a String.</p>" + "</html>");

		/*
		 * Text Panes for Model classes
		 */
		JTextPane tpDBOLabelListModel = getTextPane(uiPanel);
		tpDBOLabelListModel.setText("<html><h2>DBOLabelListModel</h2>"
				+ "<p>DBOLabelListModel implements AbstractListModel for use with JList of DBOLabels.</p>"
				+ "</html>");

		JTextPane tpDBO2LabelListModel = getTextPane(uiPanel);
		tpDBO2LabelListModel.setText("<html><h2>DBO2LabelListModel</h2>"
				+ "<p>DBO2LabelListModel implements AbstractListModel for use with JList of DBO2Labels.</p>"
				+ "</html>");

		JTextPane tpStringListModel = getTextPane(uiPanel);
		tpStringListModel.setText("<html><h2>StringListModel</h2>"
				+ "<p>StringListModel implements AbstractListModel for use with JList of Strings.</p>"
				+ "</html>");

		/*
		 * Text Pane for ImagePanel
		 */
		JTextPane tpImagePanel = getTextPane(uiPanel);
		tpImagePanel.setText("<html><h2>ImagePanel</h2>"
				+ "<p>ImagePanel is a JPanel that displays an image, make sure the filetype is supported.</p>"
				+ "<p>The image should be set after the component is displayed.</p>"
				+ "<p class=\"last\">Use setPreferredSize() to set the viewport, the image will be scaled "
				+ "and centered accordingly.</p>" + "</html>");

		/*
		 * Text Pane for FileFilters
		 */
		JTextPane tpDirFileFilter = getTextPane(uiPanel);
		tpDirFileFilter.setText("<html><h2>DirectoryFileFilter</h2>"
				+ "<p>File.listFiles(new DirectoryFileFilter()) will list all directories.</p>"
				+ "<p>File.listFiles(new DirectoryFileFilter(false)) will list all directories, excluding hidden.</p>" + "</html>");
		JTextPane tpExtensionFileFilter = getTextPane(uiPanel);
		tpExtensionFileFilter.setText("<html><h2>ExtensionFileFilter</h2>"
				+ "<p>File.listFiles(new ExtensionFileFilter(\".jpg\") will list all files ending with "
				+ "\".jpg\", including hidden files</p>"
				+ "<p>File.listFiles(new ExtensionsFileFilter(new String[] {\".jpg\", \".jpeg\"}, false) will list "
				+ "all files<br>ending with \".jpg\" or \".jpeg\", excluding hidden files.</p>"
				+ "<p>comparison is case insensitive.</p>" + "</html>");

		/*
		 * Components
		 */
		dtTime = new DateTimeField("DateTimeField.TIME: ", DateTimeField.TIME);
		dtDateTime = new DateTimeField("DateTimeField.DATE_TIME: ", DateTimeField.DATE_TIME);
		dtDate = new DateTimeField("DateTimeField.DATE: ", DateTimeField.DATE);

		ButtonGroup bg = new ButtonGroup();
		rbOne = new DBRadioButton("One", 1);
		bg.add(rbOne);

		rbTwo = new DBRadioButton("Two", 2);
		bg.add(rbTwo);

		rbThree = new DBRadioButton("Three", 3);
		bg.add(rbThree);

		dfDoubleTextField = new DoubleTextField(1.23, "DoubleTextField", null);

		tfJTextFieldWithPopUp = new JTextFieldWithPopUp("Text");

		JPanel pnRadioButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnRadioButtons.add(rbOne);
		pnRadioButtons.add(rbTwo);
		pnRadioButtons.add(rbThree);

		fpFolderPathTextField = new FolderPathTextField(frm);

		btDirChooserDialog = new JButton("DirChooserDialog");
		btDirChooserDialog.addActionListener(this);

		btDirChooserDialogRelative = new JButton("DirChooserDialog relative to user.home");
		btDirChooserDialogRelative.addActionListener(this);

		JPanel pnDirChooserDialogButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnDirChooserDialogButtons.add(btDirChooserDialog);
		pnDirChooserDialogButtons.add(btDirChooserDialogRelative);

		imagePanel = new ImagePanel();
		imagePanel.setPreferredSize(new Dimension(300, 300));

		/*
		 * User Input
		 */
		GridBagConstraints c = UIFx.initGridBagConstraints();

		c.weightx = 1;
		c.gridwidth = 4;

		uiPanel.add(tpDateTimeField1, c);

		c.weightx = 0;
		c.gridwidth = 1;
		c.gridy++;

		uiPanel.add(dtTime.getLabel(), c);
		c.gridx++;
		uiPanel.add(dtTime, c);
		c.gridy++;
		c.gridx = 0;
		uiPanel.add(dtDate.getLabel(), c);
		c.gridx++;
		uiPanel.add(dtDate, c);
		c.gridy++;
		c.gridx = 0;
		uiPanel.add(dtDateTime.getLabel(), c);
		c.gridx++;
		uiPanel.add(dtDateTime, c);
		c.gridy++;
		c.gridx = 0;
		c.weightx = 1;
		c.gridwidth = 4;
		uiPanel.add(tpDateTimeField2, c);
		c.gridy++;
		uiPanel.add(tpDBRadioButton, c);
		c.gridy++;
		uiPanel.add(pnRadioButtons, c);
		c.gridy++;
		uiPanel.add(tpDoubleTextField, c);
		c.gridy++;
		c.weightx = 0;
		c.gridwidth = 1;
		uiPanel.add(dfDoubleTextField.getLabel(), c);
		c.gridx++;
		uiPanel.add(dfDoubleTextField, c);
		c.gridx = 0;
		c.gridy++;
		c.weightx = 1;
		c.gridwidth = 4;
		uiPanel.add(tpFolderPathTextField, c);
		c.gridy++;
		c.weightx = 0;
		uiPanel.add(fpFolderPathTextField, c);
		c.gridy++;
		c.weightx = 1;
		uiPanel.add(tpJTextFieldWithPopUp, c);
		c.gridy++;
		c.gridwidth = 2;
		c.weightx = 0;
		uiPanel.add(tfJTextFieldWithPopUp, c);

		/*
		 * User Dialog
		 */
		JPanel dialogPanel = new JPanel(new GridBagLayout());
		c = UIFx.initGridBagConstraints();
		c.weightx = 1;
		c.gridwidth = 4;
		dialogPanel.add(tpDialogEscape, c);
		c.gridy++;
		dialogPanel.add(tpInfoDialog, c);
		c.gridy++;
		dialogPanel.add(tpDirChooserDialog, c);
		c.gridy++;
		dialogPanel.add(pnDirChooserDialogButtons, c);

		/*
		 * FX Classes
		 */
		JPanel fxPanel = new JPanel(new GridBagLayout());
		c = UIFx.initGridBagConstraints();
		c.weightx = 1;
		fxPanel.add(tpChaosFx, c);
		c.gridy++;
		fxPanel.add(tpDBFx, c);
		c.gridy++;
		fxPanel.add(tpFSFx, c);
		c.gridy++;
		fxPanel.add(tpPropFx, c);
		c.gridy++;
		fxPanel.add(tpUIFx, c);

		/*
		 * Database classes
		 */
		JPanel dboPanel = new JPanel(new GridBagLayout());
		c = UIFx.initGridBagConstraints();
		c.weightx = 1;
		dboPanel.add(tpDBO, c);
		c.gridy++;
		dboPanel.add(tpDBOLabel, c);
		c.gridy++;
		dboPanel.add(tpDBO2, c);
		c.gridy++;
		dboPanel.add(tpDBO2Label, c);
		c.gridy++;
		dboPanel.add(tpDBO2Label2, c);

		/*
		 * Models
		 */
		JPanel modelPanel = new JPanel(new GridBagLayout());
		c = UIFx.initGridBagConstraints();
		c.weightx = 1;
		modelPanel.add(tpDBOLabelListModel, c);
		c.gridy++;
		modelPanel.add(tpDBO2LabelListModel, c);
		c.gridy++;
		modelPanel.add(tpStringListModel, c);

		/*
		 * ImagePanel
		 */
		JPanel imgPanel = new JPanel(new GridBagLayout());
		c = UIFx.initGridBagConstraints();
		c.weightx = 1;
		imgPanel.add(tpImagePanel, c);
		c.gridy++;
		imgPanel.add(imagePanel, c);

		/*
		 * FileFiler s
		 */
		JPanel fileFiltersPanel = new JPanel(new GridBagLayout());
		c = UIFx.initGridBagConstraints();
		c.weightx = 1;
		fileFiltersPanel.add(tpDirFileFilter, c);
		c.gridy++;
		fileFiltersPanel.add(tpExtensionFileFilter, c);

		JTabbedPane tpDemo = new JTabbedPane();
		JPanel uiPanelBorder = new JPanel(new BorderLayout());
		uiPanelBorder.add(uiPanel, BorderLayout.NORTH);
		uiPanelBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tpDemo.add(UIFx.initScrollPane(uiPanelBorder, 15), "User Input");
		tpDateTimeField1.setCaretPosition(0);

		JPanel dialogPanelBorder = new JPanel(new BorderLayout());
		dialogPanelBorder.add(dialogPanel, BorderLayout.NORTH);
		dialogPanelBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tpDemo.add(UIFx.initScrollPane(dialogPanelBorder, 15), "User Dialog");
		tpDialogEscape.setCaretPosition(0);

		JPanel fxPanelBorder = new JPanel(new BorderLayout());
		fxPanelBorder.add(fxPanel, BorderLayout.NORTH);
		fxPanelBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tpDemo.add(UIFx.initScrollPane(fxPanelBorder, 15), "Fx Classes");
		tpChaosFx.setCaretPosition(0);

		JPanel dboPanelBorder = new JPanel(new BorderLayout());
		dboPanelBorder.add(dboPanel, BorderLayout.NORTH);
		dboPanelBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tpDemo.add(UIFx.initScrollPane(dboPanelBorder, 15), "Database Classes");
		tpDBO.setCaretPosition(0);

		JPanel modelPanelBorder = new JPanel(new BorderLayout());
		modelPanelBorder.add(modelPanel, BorderLayout.NORTH);
		modelPanelBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tpDemo.add(UIFx.initScrollPane(modelPanelBorder, 15), "Models");
		tpDBOLabelListModel.setCaretPosition(0);

		JPanel imgPanelBorder = new JPanel(new BorderLayout());
		imgPanelBorder.add(imgPanel, BorderLayout.NORTH);
		imgPanelBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tpDemo.add(UIFx.initScrollPane(imgPanelBorder, 15), "ImagePanel");

		JPanel fileFiltersPanelBorder = new JPanel(new BorderLayout());
		fileFiltersPanelBorder.add(fileFiltersPanel, BorderLayout.NORTH);
		fileFiltersPanelBorder.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tpDemo.add(UIFx.initScrollPane(fileFiltersPanelBorder, 15), "FileFilters");

		JPanel pnContent = new JPanel(new BorderLayout());
		pnContent.add(tpDemo, BorderLayout.CENTER);

		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setContentPane(pnContent);
		frm.setTitle("mishmash demo");

		UIFx.sizeAndCenter(frm, 800, 600);
		frm.setVisible(true);
		try (InputStream is = getClass().getResourceAsStream("img.jpeg")) {
			imagePanel.setImage(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final JTextPane getTextPane(JPanel p) {
		JTextPane r = new JTextPane();
		r.setContentType("text/html");
		r.setBackground(p.getBackground());
		r.setEditable(false);
		HTMLEditorKit kit = new HTMLEditorKit();
		StyleSheet style = kit.getStyleSheet();
		style.addRule("h2 {margin-bottom: 0px;}");
		style.addRule("p.last {margin-bottom: 10px;}");
		r.setEditorKit(kit);
		return r;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btDirChooserDialog) {
			DirChooserDialog dcd = new DirChooserDialog(frm, "");
			dcd.setVisible(true);
			JPanel message = new JPanel(new GridLayout(2, 1));
			message.add(new JLabel("DirChooseDialog getAnswer() equals: "
					+ (dcd.getAnswer() == DirChooserDialog.CANCEL ? "DirChooserDialog.CANCEL"
							: (dcd.getAnswer() == DirChooserDialog.OK ? "DirChooserDialog.OK"
									: dcd.getAnswer()))));
			message.add(new JLabel("DirChooseDialog getPath() returned: " + dcd.getPath()));
			JOptionPane.showMessageDialog(frm, message, "DirChooserDialog result",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == btDirChooserDialogRelative) {
			DirChooserDialog dcd = new DirChooserDialog(frm, new File(PropFx.userHome()), "");
			dcd.setVisible(true);
			JPanel message = new JPanel(new GridLayout(3, 1));
			message.add(new JLabel("DirChooseDialog getRAnswer() equals: "
					+ (dcd.getAnswer() == DirChooserDialog.CANCEL ? "DirChooserDialog.CANCEL"
							: (dcd.getAnswer() == DirChooserDialog.OK ? "DirChooserDialog.OK"
									: dcd.getAnswer()))));
			message.add(new JLabel("DirChooseDialog getRelativePath() returned: " + dcd.getRelativePath()));
			message.add(new JLabel("DirChooseDialog getPath() returned: " + dcd.getPath()));
			JOptionPane.showMessageDialog(frm, message, "DirChooserDialog result",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
