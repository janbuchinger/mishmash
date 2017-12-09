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
package net.janbuchinger.code.mishmash.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * An extension of <code>JPanel</code> that displays an image.
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public class ImagePanel extends JPanel {
	private BufferedImage image;
	private int x, y, width, height;
	private double scale;

	/**
	 * Constructs a new <code>ImagePanel</code>.
	 */
	public ImagePanel() {
		image = null;
		x = y = width = height = 0;
		scale = 0.0;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, x, y, width, height, null);
		}
	}

	/**
	 * Sets the image to display. <code>setImage</code> should be called after
	 * the <code>ImagePanel</code> is made visible.
	 * <p>
	 * The image will be displayed scaled to the panel dimension and centered on
	 * the panel.
	 * 
	 * @param f
	 *            The image file to display.
	 */
	public final void setImage(File f) {
		try(InputStream is = new FileInputStream(f)){
			setImage(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the image to display. <code>setImage</code> should be called after
	 * the <code>ImagePanel</code> is made visible.
	 * <p>
	 * The image will be displayed scaled to the panel dimension and centered on
	 * the panel.
	 * 
	 * @param f
	 *            The image file to display as <code>InputStream</code>.
	 */
	public final void setImage(InputStream f) {
		image = null;
		try {
			image = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (image == null)
			return;

		width = height = x = y = 0;
		scale = 0.0;

		if (image.getWidth() > getWidth() || image.getHeight() > getHeight()) {
			double imgRatio = (double) image.getWidth() / (double) image.getHeight();
			double vpRatio = (double) getWidth() / (double) getHeight();
			if (imgRatio > vpRatio) {
				width = getWidth();
				height = (int) ((double) image.getHeight() * ((double) width / (double) image.getWidth()));
				x = 0;
				y = getHeight() / 2 - height / 2;
			} else {
				height = getHeight();
				width = (int) ((double) image.getWidth() * ((double) height / (double) image.getHeight()));
				x = getWidth() / 2 - width / 2;
				y = 0;
			}
			scale = (double) image.getWidth() / (double) width;
		} else {
			width = image.getWidth();
			height = image.getHeight();
			x = getWidth() / 2 - width / 2;
			y = getHeight() / 2 - height / 2;
			scale = 1;
		}

		repaint();
	}

	/**
	 * Gets the scale the current image is displayed in.
	 * 
	 * @return The scale the current image is displayed in.
	 */
	public final double getScale() {
		return scale;
	}
}
