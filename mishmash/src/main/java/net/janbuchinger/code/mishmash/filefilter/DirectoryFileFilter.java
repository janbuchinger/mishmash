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
 * <code>DirectoryFileFilter</code> is a <code>FileFilter</code> that accepts
 * all directories.
 * 
 * @author Jan Buchinger
 *
 * @see File#listFiles(FileFilter)
 */
public class DirectoryFileFilter implements FileFilter {
	private final boolean acceptHidden;

	/**
	 * Constructs a new <code>DirectoryFileFilter</code> that accepts all
	 * directories.
	 */
	public DirectoryFileFilter() {
		acceptHidden = true;
	}

	/**
	 * Constructs a new <code>DirectoryFileFilter</code> that accepts all
	 * directories with a name not beginning with &quot;.&quot;.
	 */
	public DirectoryFileFilter(boolean acceptHidden) {
		this.acceptHidden = acceptHidden;
	}

	/**
	 * Depending on the on construction specified boolean filters hidden directories
	 * (name beginning with &quot;.&quot;) or not.
	 */
	@Override
	public boolean accept(File pathname) {
		return acceptHidden ? pathname.isDirectory()
				: pathname.isDirectory() && !pathname.getName().startsWith(".");
	}
}
