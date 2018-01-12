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
public class ExtensionFileFilter implements FileFilter {
	private final String[] extensions;
	private final boolean acceptHidden;

	/**
	 * Constructs a new <code>ExtensionsFileFilter</code> that accepts the specified
	 * extensions.
	 * 
	 * @param extensions
	 *            The extensions to accept.
	 * @param acceptHidden
	 *            true to include hidden files.
	 */
	public ExtensionFileFilter(String[] extensions, boolean acceptHidden) {
		this.acceptHidden = acceptHidden;
		this.extensions = new String[extensions.length];
		for (int i = 0; i < extensions.length; i++) {
			this.extensions[i] = extensions[i].toLowerCase();
		}
	}

	/**
	 * Constructs a new <code>ExtensionsFileFilter</code> that accepts the specified
	 * extensions. Hidden files are included.
	 * 
	 * @param extensions
	 *            The extensions to accept.
	 */
	public ExtensionFileFilter(String[] extensions) {
		this(extensions, true);
	}

	/**
	 * Constructs a new <code>ExtensionsFileFilter</code> that accepts the specified
	 * extension.
	 * 
	 * @param extension
	 *            The extension to accept.
	 * @param acceptHidden
	 *            true to include hidden files.
	 */
	public ExtensionFileFilter(String extension, boolean acceptHidden) {
		this(new String[] { extension }, acceptHidden);
	}

	/**
	 * Constructs a new <code>ExtensionsFileFilter</code> that accepts the specified
	 * extension. Hidden files are included.
	 * 
	 * @param extension
	 *            The extension to accept.
	 */
	public ExtensionFileFilter(String extension) {
		this(extension, true);
	}

	/**
	 * Accepts all files that match (one of) the specified extension(s). Matching is
	 * case insensitive.
	 */
	@Override
	public boolean accept(File pathname) {
		if (!pathname.isFile()) {
			return false;
		}
		if (!acceptHidden && pathname.isHidden()) {
			return false;
		}
		String name = pathname.getName();
		for (String ext : extensions) {
			if (name.length() < ext.length()) {
				continue;
			}
			if (name.substring(name.length() - ext.length(), name.length()).toLowerCase().equals(ext)) {
				return true;
			}
		}
		return false;
	}
}
