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
 * <code>DBO2Label2</code> is a variant of <code>DBO2Label</code> with dual index and
 * two labels.
 * 
 * @author Jan Buchinger
 * 
 * @see DBO2Label DBO2Label (two ids and one label)
 * @see DBOLabel DBOLabel (one id and one label)
 */
public abstract class DBO2Label2 extends DBO2Label {
	private final String labelId2;

	/**
	 * Constructs a <code>DBO2Label2</code> with two labels.
	 * 
	 * @param id1
	 *            The first id.
	 * @param id2
	 *            The second id.
	 * @param label1
	 *            The first label.
	 * @param label2
	 *            The second label.
	 */
	public DBO2Label2(int id1, int id2, String label1, String label2) {
		super(id1, id2, label1);
		this.labelId2 = label2;
	}

	/**
	 * Gets the first label.
	 * 
	 * @return The first label.
	 */
	public String getLabel1() {
		return super.toString();
	}

	/**
	 * 
	 * Gets the second label.
	 * 
	 * @return The second label.
	 */
	public String getLabel2() {
		return labelId2;
	}
}
