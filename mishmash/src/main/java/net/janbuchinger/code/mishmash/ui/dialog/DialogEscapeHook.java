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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

/**
 * <code>DialogEscapeHook</code> is a class to enable closing a
 * <code>JDialog</code> (<code>dialog.setVisible(false)</code>) by pressing the
 * escape key.
 * <p>
 * To implement a custom escaping behavior instantiate
 * <code>DialogEscapeHook</code> and implement <code>EscapeListener</code>.
 * 
 * @author Jan Buchinger
 * @see EscapeListener
 * 
 */
public class DialogEscapeHook implements ActionListener {
	private final JDialog w;

	/**
	 * Constructs a <code>DialogEscapeHook</code>. This enables a
	 * <code>JDialog</code> to close by pressing the escape key.
	 * <p>
	 * To implement custom escape behavior implement <code>EscapeListener</code>.
	 * @param dialog
	 *            The dialog to make escapable.
	 */
	public DialogEscapeHook(JDialog dialog) {
		this.w = dialog;
		dialog.getRootPane().registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (w instanceof EscapeListener)
			((EscapeListener) w).escaping();
		else
			w.setVisible(false);
	}

}
