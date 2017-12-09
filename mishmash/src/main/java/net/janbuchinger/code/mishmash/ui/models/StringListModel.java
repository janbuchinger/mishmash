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
package net.janbuchinger.code.mishmash.ui.models;

import java.util.Vector;

import javax.swing.DefaultListModel;

/**
 * <code>StringListModel</code> is a list model for <code>JList</code> that
 * manages a string list.
 * 
 * 
 * @author Jan Buchinger
 * 
 */
@SuppressWarnings("serial")
public class StringListModel extends DefaultListModel<String> {
	private Vector<String> data;

	/**
	 * Instantiates <code>StringListModel</code> and initiates a new list of
	 * <code>String</code>s.
	 */
	public StringListModel() {
		data = new Vector<String>();
	}

	/**
	 * Instantiates <code>StringListModel</code> with the specified list.
	 * 
	 * @param data
	 *            The list of <code>String</code>s to use.
	 */
	public StringListModel(Vector<String> data) {
		this.data = data;
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public String get(int index) {
		return data.get(index);
	}

	@Override
	public String elementAt(int index) {
		return get(index);
	}

	@Override
	public String getElementAt(int index) {
		return get(index);
	}

	@Override
	public void addElement(String element) {
		data.add(element);
		fireContentsChanged(this, 0, getSize() - 1);
	}

	@Override
	public String remove(int index) {
		String x = data.remove(index);
		fireContentsChanged(this, 0, getSize() - 1);
		return x;
	}

	/**
	 * Gets the <code>String</code> list.
	 * 
	 * @return The <code>String</code> list.
	 */
	public Vector<String> getStrings() {
		return data;
	}

	/**
	 * Sets the <code>String</code> list and fires contents changed.
	 * 
	 * @param data The new list.
	 */
	public void setStrings(Vector<String> data) {
		this.data = data;
		fireContentsChanged(this, 0, getSize() - 1);
	}
}
