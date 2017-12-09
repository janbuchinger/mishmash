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
package net.janbuchinger.code.mishmash.ui.dialog.dirChooser;

import java.io.File;
import java.io.FileFilter;

/**
 * <code>DirFileFilter</code> is used by <code>DirChooserDialog</code> to filter
 * directories and / or hidden directories. 
 * 
 * @author Jan Buchinger
 * 
 */
public final class DirFileFilter implements FileFilter {
	private final boolean showHiddenFolders;

	public DirFileFilter(boolean showHiddenFolders) {
		this.showHiddenFolders = showHiddenFolders;
	}

	@Override
	public final boolean accept(File pathname) {
		if (showHiddenFolders)
			return pathname.isDirectory();
		else
			return pathname.isDirectory() && !pathname.getName().startsWith(".");
	}

}
