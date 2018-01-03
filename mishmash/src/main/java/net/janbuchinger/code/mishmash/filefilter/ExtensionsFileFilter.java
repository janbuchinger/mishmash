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
 * <code>ExtensionsFileFilter</code> is a <code>FileFilter</code> that accepts
 * all files matching the specified extensions. Matching is case insensitive.
 * 
 * @author Jan Buchinger
 * 
 * @see File#listFiles(FileFilter)
 */
public class ExtensionsFileFilter implements FileFilter {
	private final String[] extensions;

	/**
	 * Constructs a new <code>ExtensionsFileFilter</code> that accepts the specified
	 * extensions. Matching is case insensitive.
	 * 
	 * @param extensions
	 *            The extensions to accept.
	 */
	public ExtensionsFileFilter(String[] extensions) {
		this.extensions = new String[extensions.length];
		for (int i = 0; i < extensions.length; i++) {
			this.extensions[i] = extensions[i].toLowerCase();
		}
	}

	/**
	 * Accepts all files that match one of the specified extensions. Matching is
	 * case insensitive.
	 */
	@Override
	public boolean accept(File pathname) {
		boolean a = false;
		if (!pathname.isFile())
			return a;
		for (String ext : extensions) {
			if (pathname.getName().toLowerCase().endsWith(ext)) {
				a = true;
				break;
			}
		}
		return a;
	}
}
