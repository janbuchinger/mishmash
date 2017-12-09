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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * FSFx stands as an abbreviation for file system functions.
 * 
 * @author Jan Buchinger
 * 
 */
public final class FSFx {

	/**
	 * Formats a file length according its binary magnitude. The magnitudes are
	 * <ul>
	 * <li>bytes
	 * <li>KiB: kibibyte (1024 bytes)
	 * <li>MiB: mebibyte (1024<sup>2</sup> bytes)
	 * <li>GiB: gibibyte (1024<sup>3</sup> bytes)
	 * <li>TiB: tebibyte (1024<sup>4</sup> bytes)
	 * <li>PiB: pebibyte (1024<sup>5</sup> bytes)
	 * <li>EiB: exbibyte (1024<sup>6</sup> bytes)
	 * <li>ZiB: zebibyte (1024<sup>7</sup> bytes)
	 * <li>YiB: yobibyte (1024<sup>8</sup> bytes)
	 * </ul>
	 * 
	 * @param length
	 *            The data length to format.
	 * @return A file length formatted according to its magnitude with two
	 *         fraction digits followed by a multiplier descriptor like
	 *         "2,95 MiB".
	 */
	public static final String formatFileLength(double length) {
		String s = "";
		double x;

		boolean inv = length < 0;

		if (inv)
			length = length * -1;

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(true);

		if (length > (x = Math.pow(1024, 8))) {
			s = nf.format(length / x) + " YiB";
		} else if (length > (x = Math.pow(1024, 7))) {
			s = nf.format(length / x) + " ZiB";
		} else if (length > (x = Math.pow(1024, 6))) {
			s = nf.format(length / x) + " EiB";
		} else if (length > (x = Math.pow(1024, 5))) {
			s = nf.format(length / x) + " PiB";
		} else if (length > (x = Math.pow(1024, 4))) {
			s = nf.format(length / x) + " TiB";
		} else if (length > (x = Math.pow(1024, 3))) {
			s = nf.format(length / x) + " GiB";
		} else if (length > (x = Math.pow(1024, 2))) {
			s = nf.format(length / x) + " MiB";
		} else if (length > (x = Math.pow(1024, 1))) {
			s = nf.format(length / x) + " KiB";
		} else {
			s = (int) length + " bytes";
		}

		return (inv ? "-" : "") + s;
	}

	/**
	 * Packs the provided files into a zip file into the root directory,
	 * modification date is lost.
	 * 
	 * @param files
	 *            The Vector of Files to be zipped.
	 * @param target
	 *            The zip file
	 * @return A boolean[] indicating zipping success of the corresponding
	 *         files.
	 */
	public static boolean[] createFlatZip(Vector<File> files, File target) {

		boolean[] bx = new boolean[files.size()];
		for (int i = 0; i < bx.length; i++) {
			bx[i] = false;
		}

		// input file
		FileInputStream in = null;

		// out put file
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(target));
		} catch (FileNotFoundException e3) {
			e3.printStackTrace();
			return bx;
		}

		Iterator<File> iFiles = files.iterator();
		File f;
		byte[] b;
		int count;
		int counter = 0;
		while (iFiles.hasNext()) {
			f = iFiles.next();
			try {
				in = new FileInputStream(f);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
				continue;
			}
			// name the file inside the zip file
			try {
				out.putNextEntry(new ZipEntry(f.getName()));
			} catch (IOException e1) {
				e1.printStackTrace();
				continue;
			}

			// buffer size
			b = new byte[1024];

			try {
				while ((count = in.read(b)) > 0) {
					out.write(b, 0, count);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bx[counter++] = true;
		}

		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bx;
	}

	/**
	 * Makes a file hidden on Windows. This requires Java 7.
	 * 
	 * @param f
	 *            The file to be hidden.
	 */
	public final static void hideWindowsFile(File f) {
		Path p = Paths.get(f.getAbsolutePath());
		try {
			Files.setAttribute(p, "dos:hidden", true);
		} catch (IOException e1) {
			// e1.printStackTrace();
		}
	}

	/**
	 * Checks whether a directory has any entries.
	 * 
	 * @param directory
	 *            The directory to check.
	 * @return <code>true</code> if the specified directory has any entries.
	 * @throws IOException
	 *             when there is a problem with the specified directory or
	 *             closing the stream.
	 */
	public final static boolean hasDirEntries(Path directory) throws IOException {
		DirectoryStream<Path> ds = Files.newDirectoryStream(directory);
		boolean b = true;
		try {
			b = ds.iterator().hasNext();
		} finally {
			if (ds != null)
				ds.close();
		}
		return b;
	}

	/**
	 * Copies a resource that is bundled with the program to an external file.
	 * 
	 * @param from
	 *            The class to locate the resource from.
	 * @param fromName
	 *            The resource name relative to the specified class.
	 * @param to
	 *            The new File to copy to.
	 * @return true if copying was successful.
	 */
	public final static boolean copyResourceFile(@SuppressWarnings("rawtypes") Class from, String fromName,
			File to) {
		if (!to.getParentFile().exists()) {
			to.getParentFile().mkdirs();
		}
		InputStream stream = null;
		OutputStream resStreamOut = null;
		try {
			stream = from.getResourceAsStream(fromName);
			if (stream == null) {
				throw new Exception("Cannot get resource \"" + fromName + "\" from Jar file.");
			}

			int readBytes;
			byte[] buffer = new byte[5120];
			resStreamOut = new FileOutputStream(to);
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (resStreamOut != null) {
				try {
					resStreamOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
