/*
 * Copyright 2018 Jan Buchinger
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
package net.janbuchinger.code.mishmash.filefilter;

import java.io.File;
import java.io.FileFilter;

/**
 * <code>ExtensionFileFilter</code> is a <code>FileFilter</code> that accepts
 * all files matching the specified extension. Matching is case insensitive.
 * 
 * @author Jan Buchinger
 *
 * @see File#listFiles(FileFilter)
 */
public class ExtensionFileFilter implements FileFilter {
	private final String extension;

	/**
	 * Constructs a new <code>ExtensionFileFilter</code> that accepts the specified
	 * extension. Matching is case insensitive.
	 * 
	 * @param extension
	 *            The extension to accept.
	 */
	public ExtensionFileFilter(String extension) {
		this.extension = extension.toLowerCase();
	}

	/**
	 * Accepts all files that match the specified extension. Matching is case
	 * insensitive.
	 */
	@Override
	public boolean accept(File pathname) {
		if (!pathname.isFile())
			return false;
		return pathname.getName().toLowerCase().endsWith(extension);
	}
}
