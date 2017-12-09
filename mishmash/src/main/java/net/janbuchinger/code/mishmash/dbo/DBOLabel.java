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
package net.janbuchinger.code.mishmash.dbo;

/**
 * <code>DBOLabel</code> is a basic ui representation of database related data;
 * intended for a list of labels that each have a unique id.
 * 
 * @author Jan Buchinger
 * @see DBO2Label DBO2Label (two ids and one label)
 * @see DBO2Label2 DBO2Label2 (two ids and two labels)
 */
public class DBOLabel extends DBO {

	private final String label;

	/**
	 * Constructs a <code>DBOLabel</code>.
	 * 
	 * @param id
	 *            The unique identifier
	 * @param label
	 *            The String for UI display.
	 */
	public DBOLabel(int id, String label) {
		super(id);
		this.label = label;
	}

	/**
	 * Returns the label <code>String</code> this <code>DBOLabel</code> received
	 * on construction.
	 */
	@Override
	public String toString() {
		return label;
	}
}
