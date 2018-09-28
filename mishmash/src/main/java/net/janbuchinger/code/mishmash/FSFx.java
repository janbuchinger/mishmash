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
	private static NumberFormat nf;

	private static void initNumberFormat() {
		if (nf == null) {
			nf = NumberFormat.getInstance();
			nf.setMinimumFractionDigits(2);
			nf.setMaximumFractionDigits(2);
			nf.setGroupingUsed(true);
		}
	}

	/**
	 * Formats a file length according its binary magnitude.
	 * <p>
	 * The magnitudes are:
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
	 * @return A file length formatted according to its magnitude with two fraction
	 *         digits followed by a multiplier descriptor like "2,95 MiB".
	 */
	public static final String formatFileLength(double length) {
		String s = "";
		double x;

		boolean inv = length < 0;

		if (inv) {
			length = length * -1;
		}

		initNumberFormat();

		if (length > (x = Math.pow(1024, 8))) {
			s = nf.format((double) length / x).concat(" YiB");
		} else if (length >= (x = Math.pow(1024, 7))) {
			s = nf.format((double) length / x).concat(" ZiB");
		} else if (length >= (x = Math.pow(1024, 6))) {
			s = nf.format((double) length / x).concat(" EiB");
		} else if (length >= (x = Math.pow(1024, 5))) {
			s = nf.format((double) length / x).concat(" PiB");
		} else if (length >= (x = Math.pow(1024, 4))) {
			s = nf.format((double) length / x).concat(" TiB");
		} else if (length >= (x = Math.pow(1024, 3))) {
			s = nf.format((double) length / x).concat(" GiB");
		} else if (length >= (x = Math.pow(1024, 2))) {
			s = nf.format((double) length / x).concat(" MiB");
		} else if (length >= (x = Math.pow(1024, 1))) {
			s = nf.format((double) length / x).concat(" KiB");
		} else {
			s = Integer.toString((int) length).concat(" bytes");
		}

		return (inv ? "-" : "").concat(s);
	}

	/**
	 * Formats transferred file length per second. This method uses
	 * <code>System.currentTimeMillis()</code>, so it should be called immediately
	 * after the transfer succeeded.
	 * 
	 * @param tStart
	 *            The file transfer start time in milliseconds
	 * @param bytesCopied
	 *            The bytes copied since tStart
	 * @return the file length per second like
	 *         <code>formatFileLength(bytesCopied / seconds).concat("/s")</code>
	 * @see FSFx#formatFileLength(double)
	 */
	public final static String formatTransferSpeed(long tStart, long bytesCopied) {
		try {
			return FSFx.formatFileLength(bytesCopied / ((System.currentTimeMillis() - tStart) / 1000.0))
					.concat("/s");
		} catch (Exception e) {
			return "0 bytes/s";
		}
	}

	/**
	 * Formats a file length according its decimal magnitude.
	 * <p>
	 * The magnitudes are:
	 * <ul>
	 * <li>bytes
	 * <li>kB: kilobyte (1000 bytes)
	 * <li>MB: megabyte (1000<sup>2</sup> bytes)
	 * <li>GB: gigabyte (1000<sup>3</sup> bytes)
	 * <li>TB: terabyte (1000<sup>4</sup> bytes)
	 * <li>PB: petabyte (1000<sup>5</sup> bytes)
	 * <li>EB: exabyte (1000<sup>6</sup> bytes)
	 * <li>ZB: zettabyte (1000<sup>7</sup> bytes)
	 * <li>YB: yottabyte (1000<sup>8</sup> bytes)
	 * </ul>
	 * 
	 * @param length
	 *            The data length to format.
	 * @return A file length formatted according to its magnitude with two fraction
	 *         digits followed by a multiplier descriptor like "2,95 MB".
	 */
	public static final String formatFileLengthDec(double length) {
		String s = "";
		double x;

		boolean inv = length < 0;

		if (inv) {
			length = length * -1;
		}

		initNumberFormat();

		if (length > (x = Math.pow(1000, 8))) {
			s = nf.format((double) length / x).concat(" YB");
		} else if (length >= (x = Math.pow(1000, 7))) {
			s = nf.format((double) length / x).concat(" ZB");
		} else if (length >= (x = Math.pow(1000, 6))) {
			s = nf.format((double) length / x).concat(" EB");
		} else if (length >= (x = Math.pow(1000, 5))) {
			s = nf.format((double) length / x).concat(" PB");
		} else if (length >= (x = Math.pow(1000, 4))) {
			s = nf.format((double) length / x).concat(" TB");
		} else if (length >= (x = Math.pow(1000, 3))) {
			s = nf.format((double) length / x).concat(" GB");
		} else if (length >= (x = Math.pow(1000, 2))) {
			s = nf.format((double) length / x).concat(" MB");
		} else if (length >= (x = Math.pow(1000, 1))) {
			s = nf.format((double) length / x).concat(" kB");
		} else {
			s = Integer.toString((int) length).concat(" bytes");
		}

		return (inv ? "-" : "").concat(s);

	}

	/**
	 * Formats transferred file length per second. This method uses
	 * <code>System.currentTimeMillis()</code>, so it should be called immediately
	 * after the transfer succeeded.
	 * 
	 * @param tStart
	 *            The file transfer start time in milliseconds
	 * @param bytesCopied
	 *            The bytes copied since tStart
	 * @return the file length per second like
	 *         <code>formatFileLengthDec(bytesCopied / seconds).concat("/s")</code>
	 * @see FSFx#formatFileLengthDec(double)
	 */
	public final static String formatTransferSpeedDec(long tStart, long bytesCopied) {
		try {
			return FSFx.formatFileLengthDec(bytesCopied / ((System.currentTimeMillis() - tStart) / 1000.0))
					.concat("/s");
		} catch (Exception e) {
			return "0 bytes/s";
		}
	}

	/**
	 * Packs the provided files into a zip file into the root directory,
	 * modification date is lost.
	 * 
	 * @param files
	 *            The Vector of Files to be zipped.
	 * @param target
	 *            The zip file
	 * @return A boolean[] indicating zipping success of the corresponding files.
	 */
	public static boolean[] createFlatZip(Vector<File> files, File target) {

		boolean[] bx = new boolean[files.size()];
		for (int i = 0; i < bx.length; i++) {
			bx[i] = false;
		}

		// input file
		FileInputStream in = null;

		// output file
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
		Path p = Paths.get(f.getPath());
		try {
			if (PropFx.osName().toLowerCase().startsWith("win")) {
				Files.setAttribute(p, "dos:hidden", true);
			}
		} catch (IOException e1) {}
	}

	/**
	 * Checks whether a directory has any entries.
	 * 
	 * @param directory
	 *            The directory to check.
	 * @return <code>true</code> only if the specified file exists, is a directory
	 *         and has entries.
	 */
	public final static boolean hasDirEntries(File directory) {
		return hasDirEntries(directory.toPath());
	}

	/**
	 * Checks whether a directory has any entries.
	 * 
	 * @param directory
	 *            The directory to check.
	 * @return <code>true</code> only if the specified path exists, is a directory
	 *         and has entries.
	 */
	public final static boolean hasDirEntries(Path directory) {
		DirectoryStream<Path> ds = null;
		boolean b = false;
		try {
			ds = Files.newDirectoryStream(directory);
			b = ds.iterator().hasNext();
		} catch (Exception e) {} finally {
			if (ds != null) {
				try {
					ds.close();
				} catch (IOException e) {}
			}
		}
		return b;
	}

	/**
	 * Copies a resource that is bundled with the program to an external file. The
	 * parent file path is being created if it does not exist yet.
	 * 
	 * @param from
	 *            The class to locate the resource from.
	 * @param fromName
	 *            The resource name relative to the specified class.
	 * @param to
	 *            The new File to copy to.
	 * @return true only if the copying was successful. false if the parent
	 *         directory could not be created or an Exception is thrown during
	 *         copying the file.
	 */
	public final static boolean copyResourceFile(@SuppressWarnings("rawtypes") Class from, String fromName,
			File to) {
		if (!to.getParentFile().exists()) {
			if (!to.getParentFile().mkdirs()) {
				return false;
			}
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
