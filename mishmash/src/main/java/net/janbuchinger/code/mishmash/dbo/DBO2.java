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

public class DBO2 extends DBO {
	private final int id2;

	/**
	 * Constructs a <code>DBO2</code> without the use of labels (the labels will
	 * be empty <code>String</code>s.
	 * 
	 * @param id1
	 *            The first id.
	 * @param id2
	 *            The second id.
	 */
	public DBO2(int id1, int id2) {
		super(id1);
		this.id2 = id2;
	}

	/**
	 * Gets the second id.
	 * 
	 * @return The second id.
	 */
	public final int getId2() {
		return id2;
	}

}
