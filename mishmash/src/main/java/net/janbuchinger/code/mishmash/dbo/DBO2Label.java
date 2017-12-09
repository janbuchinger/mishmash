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
 * <code>DBO2Label</code> is a variant of <code>DBOLabel</code>. It is enhanced
 * by a dual index and still has one label <code>String</code>.
 * 
 * @author Jan Buchinger
 * 
 * @see DBOLabel DBOLabel (one id and one label)
 * @see DBO2Label2 DBO2Label2 (two ids and two labels)
 * 
 */
public class DBO2Label extends DBO2 {
	private final String label;

	/**
	 * Constructs a <code>DBO2Label</code>.
	 * 
	 * @param id1
	 *            The first id.
	 * @param id2
	 *            The second id.
	 * @param label
	 *            The <code>String</code> to display in the UI.
	 */
	public DBO2Label(int id1, int id2, String label) {
		super(id1, id2);
		this.label = label;
	}

	/**
	 * Returns the label <code>String</code> received on construction.
	 */
	@Override
	public String toString() {
		return label;
	}
}
