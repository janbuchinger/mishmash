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
 * DBO is an abbreviation for "database object". It is a mini class that hosts
 * an id. It is designed as superclass for database objects with one id.
 * 
 * @author Jan Buchinger
 * @see DBOLabel
 * @see DBO2
 */
public abstract class DBO {
	private final int id;

	/**
	 * Constructs a <code>DBO</code>.
	 * 
	 * @param id
	 *            The id of this database object.
	 */
	public DBO(int id) {
		this.id = id;
	}

	/**
	 * Gets the id of this database object.
	 * 
	 * @return The id of this database object.
	 */
	public final int getId() {
		return id;
	}
}
