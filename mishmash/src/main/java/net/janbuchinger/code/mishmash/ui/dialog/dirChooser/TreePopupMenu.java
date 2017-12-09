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
package net.janbuchinger.code.mishmash.ui.dialog.dirChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * This class is used by <code>DirChooserDialog</code>. It is a pop up menu that
 * lets the user choose whether hidden directories should be displayed.
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public final class TreePopupMenu extends JPopupMenu implements ActionListener {
	private final JMenuItem miShowHiddenFolders;

	private boolean showHiddenFolders;

	private final DirChooserDialog dcd;

	public TreePopupMenu(DirChooserDialog dcd) {
		this.dcd = dcd;
		showHiddenFolders = false;
		miShowHiddenFolders = new JMenuItem("Versteckte Ordner Anzeigen");
		add(miShowHiddenFolders);
		miShowHiddenFolders.addActionListener(this);
	}

	@Override
	public final void actionPerformed(ActionEvent e) {
		if (e.getSource() == miShowHiddenFolders) {
			showHiddenFolders = !showHiddenFolders;
			if (showHiddenFolders) {
				miShowHiddenFolders.setText("Versteckte Ordner nicht Anzeigen");
			} else {
				miShowHiddenFolders.setText("Versteckte Ordner Anzeigen");
			}
			dcd.reload();
		}
	}

	public final boolean showHiddenFolders() {
		return showHiddenFolders;
	}
}
