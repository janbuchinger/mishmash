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
	/**
	 * Accepts all directories.
	 */
	@Override
	public boolean accept(File pathname) {
		return pathname.isDirectory();
	}
}
