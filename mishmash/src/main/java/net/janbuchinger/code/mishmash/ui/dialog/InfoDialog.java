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
package net.janbuchinger.code.mishmash.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import net.janbuchinger.code.mishmash.ui.UIFx;


/**
 * <code>InfoDialog</code> is a simple dialog that displays a HTML page.
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public class InfoDialog extends JDialog implements ActionListener, HyperlinkListener {

	private final JButton btOk;

	/**
	 * Constructs an <code>InfoDialog</code>, modal to the specified JFrame. The
	 * specified <code>URL</code> should point to a HTML file.
	 * 
	 * @param frm
	 *            The <code>JFrame</code> this <code>InfoDialog</code> is modal
	 *            to.
	 * @param url
	 *            The HTML file to display.
	 * @param title
	 *            The dialog title.
	 */
	public InfoDialog(JFrame frm, URL url, String title) {
		this(frm, url, title, 0.3, 0.4, null);
	}

	/**
	 * Constructs an <code>InfoDialog</code>, modal to the specified JFrame. The
	 * specified <code>URL</code> should point to a HTML file.
	 * <p>
	 * The specified HTML document can be styled with rules in a
	 * <code>String[]</code>; like
	 * <code>cssRules = new String[]{"h1 {color: red;}", "h2 {color: green;}"};</code>.
	 * 
	 * @param frm
	 *            The <code>JFrame</code> this <code>InfoDialog</code> is modal
	 *            to.
	 * @param url
	 *            The HTML file to display.
	 * @param title
	 *            The dialog title.
	 * @param width
	 *            The relative width to the screen size between 0 and 1.
	 * @param height
	 *            The relative height to the screen size between 0 and 1.
	 * @param cssRules
	 *            CSS rules that to style the specified HTML document.
	 */
	public InfoDialog(JFrame frm, URL url, String title, double width, double height, String[] cssRules) {
		super(frm, title, true);
		new DialogEscapeHook(this);

		JTextPane tp = null;
		try {
			HTMLEditorKit kit = new HTMLEditorKit();
			StyleSheet styleSheet = kit.getStyleSheet();
			styleSheet.addRule("body {margin: 5px;}");
			styleSheet.addRule("h3 {margin-bottom: 4px; margin-top: 8px;}");
			styleSheet.addRule("p {margin-bottom: 8px; margin-top: 4px;}");

			if (cssRules != null) {
				for (String rule : cssRules) {
					styleSheet.addRule(rule);
				}
			}

			tp = new JTextPane();
			tp.setEditorKit(kit);
			tp.setBackground(getContentPane().getBackground());
			tp.setBorder(BorderFactory.createEmptyBorder(10, 25, 25, 25));
			tp.setContentType("text/html");
			tp.setPage(url);
			tp.setEditable(false);
			tp.addHyperlinkListener(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JPanel pnInfo = new JPanel(new GridLayout(1, 1));

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

		pnInfo.setPreferredSize(new Dimension((int) (width * screensize.width),
				(int) (height * screensize.height)));
		if (tp != null) {
			JScrollPane sp = new JScrollPane(tp);
			sp.setBorder(null);
			pnInfo.add(sp);
		}

		btOk = new JButton("Schliessen");
		btOk.addActionListener(this);
		JPanel pnButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnButtons.add(btOk);

		JPanel pnContent = new JPanel(new BorderLayout());
		pnContent.add(pnInfo, BorderLayout.CENTER);
		pnContent.add(pnButtons, BorderLayout.SOUTH);
		setContentPane(pnContent);

		UIFx.packAndCenter(this, frm);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btOk) {
			setVisible(false);
		}
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType().toString().equals("ACTIVATED")) {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(e.getURL().toURI());
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
