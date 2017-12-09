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

/**
 * <code>PropFx</code> is an abbreviation for "properties functions".
 * <p>
 * It's a collection of shortcuts to get selected system properties.
 * 
 * @author Jan Buchinger
 * @see java.lang.System#getProperties()
 */
public final class PropFx {
	/**
	 * Prints all system properties to <code>System.out</code>.
	 * 
	 * @see java.lang.System#getProperties()
	 */
	public final static void printAllProperties() {
		System.getProperties().list(System.out);
	}

	/**
	 * Gets the value of the system property "os.name".
	 * <p>
	 * Values like "Linux" or "Windows 7".
	 * 
	 * @return The value of the system property "os.name".
	 */
	public static final String osName() {
		return System.getProperty("os.name");
	}

	/**
	 * Gets the value of the system property "file.separator".
	 * <p>
	 * Values like "\" or "/".
	 * 
	 * @return The value of the system property "file.separator".
	 */
	public static final String fileSeparator() {
		return System.getProperty("file.separator");
	}

	/**
	 * Gets the value of the system property "line.separator".
	 * 
	 * @return The value of the system property "line.separator".
	 */
	public static final String lineSeparator() {
		return System.getProperty("line.separator");
	}

	/**
	 * Gets the value of the system property "java.io.tmpdir".
	 * 
	 * @return The value of the system property "java.io.tmpdir".
	 */
	public static final String javaIoTmpdir() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * Gets the value of the system property "user.home".
	 * 
	 * @return The value of the system property "user.home".
	 */
	public static final String userHome() {
		return System.getProperty("user.home");
	}

	/**
	 * Gets the value of the system property "user.language".
	 * 
	 * @return The value of the system property "user.language".
	 */
	public static final String userLanguage() {
		return System.getProperty("user.language");
	}

	/**
	 * Gets the value of the system property "user.country".
	 * 
	 * @return The value of the system property "user.country".
	 */
	public static final String userCountry() {
		return System.getProperty("user.country");
	}

	/**
	 * Gets the value of the system property "user.name".
	 * 
	 * @return The value of the system property "user.name".
	 */
	public static final String userName() {
		return System.getProperty("user.name");
	}
}