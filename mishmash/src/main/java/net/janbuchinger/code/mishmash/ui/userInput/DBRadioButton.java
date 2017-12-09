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

import javax.swing.JRadioButton;

/**
 * <code>DBRadioButton</code> is a <code>JRadioButton</code> that is enhanced by
 * an id for use with database data.
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public class DBRadioButton extends JRadioButton {
	private final int id;

	/**
	 * Constructs a <code>DBRadioButton</code> with the specified parameters.
	 * 
	 * @param label
	 *            The <code>String</code> to display as
	 *            <code>JRadioButton</code> label.
	 * @param id
	 *            The unique identifier this choice is associated with.
	 */
	public DBRadioButton(String label, int id) {
		super(label);
		this.id = id;
	}

	/**
	 * Gets the id of this choice.
	 * 
	 * @return The unique identifier of this choice.
	 */
	public final int getId() {
		return id;
	}
}
