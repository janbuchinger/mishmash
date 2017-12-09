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
package net.janbuchinger.code.mishmash;

import java.text.SimpleDateFormat;

/**
 * <code>DBFx</code> is an abbreviation for "database functions".
 * 
 * @author Jan Buchinger
 * 
 */
public final class DBFx {

	/**
	 * Gets a <code>SimpleDateFormat</code> with the MySQL date time pattern
	 * ("yyyy-MM-dd HH:mm:ss").
	 * 
	 * @return A <code>SimpleDateFormat</code> for parsing and formatting MySQL
	 *         date+times.
	 */
	public final static SimpleDateFormat getMySQLDateTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * Gets a <code>SimpleDateFormat</code> with the MySQL date only pattern
	 * ("yyyy-MM-dd").
	 * 
	 * @return A <code>SimpleDateFormat</code> for parsing and formatting MySQL
	 *         dates.
	 */
	public final static SimpleDateFormat getMySQLDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	/**
	 * Parses a String like "A, B, C" by removing spaces around commas (", " or
	 * " ,"), removing commas following commas and removing leading and trailing
	 * commas.
	 * 
	 * @param list
	 *            The comma separated list as <code>String</code>.
	 * @return A <code>String[]</code> that does not contain empty
	 *         <code>String</code>s.
	 */
	public final static String[] parseCommaSeparatedList(String list) {
		if (list.length() > 0) {
			while (list.contains(", "))
				list = list.replace(", ", ",");
			while (list.contains(" ,"))
				list = list.replace(" ,", ",");
			while (list.contains(",,"))
				list = list.replace(",,", ",");
			if (list.endsWith(","))
				list = list.substring(0, list.length() - 2);
			if (list.startsWith(","))
				list = list.substring(1);
			return list.split(",");
		} else {
			return new String[0];
		}
	}

	/**
	 * Joins a <code>String[]</code> into a String. The array elements are
	 * separated by a comma followed by a space.
	 * 
	 * @param list
	 *            An array of <code>String</code>s to join separated by comma.
	 * @return A <code>String</code> that contains the received array elements
	 *         separated by comma.
	 */
	public final String joinCommaSeparatedList(String[] list) {
		String x = "";
		for (int i = 0; i < list.length; i++) {
			x += list[i];
			if (i + 1 < list.length)
				x += ", ";
		}
		return x;
	}
}
