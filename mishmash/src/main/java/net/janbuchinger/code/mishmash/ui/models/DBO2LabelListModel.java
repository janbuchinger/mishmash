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

import javax.swing.AbstractListModel;

import net.janbuchinger.code.mishmash.dbo.DBO2Label;


/**
 * <code>DBO2LabelListModel</code> is a <code>ListModel</code> for
 * <code>JList&lt;DBO2Label&gt;</code>
 * 
 * @author Jan Buchinger
 * 
 * @see DBO2Label
 */
@SuppressWarnings("serial")
public class DBO2LabelListModel extends AbstractListModel<DBO2Label> {

	private Vector<DBO2Label> data;

	/**
	 * Constructs a <code>DBO2LabelListModel</code> with an empty data
	 * <code>List</code>.
	 */
	public DBO2LabelListModel() {
		data = new Vector<DBO2Label>();
	}

	/**
	 * 
	 * Constructs a <code>DBO2LabelListModel</code> with the supplied data.
	 * 
	 * @param data
	 *            The <code>List</code> to initiate this
	 *            <code>DBO2LabelListModel</code> with.
	 */
	public DBO2LabelListModel(Vector<DBO2Label> data) {
		if (data != null)
			this.data = data;
		else
			this.data = new Vector<DBO2Label>();

	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public DBO2Label getElementAt(int index) {
		return data.get(index);
	}

	/**
	 * Gets the current data <code>List</code>.
	 * 
	 * @return The current data <code>List</code>.
	 * @see #refresh()
	 */
	public final Vector<DBO2Label> getData() {
		return data;
	}

	/**
	 * Sets the current data <code>List</code>.
	 * 
	 * @param data
	 *            The current data <code>List</code> to set.
	 */
	public final void setData(Vector<DBO2Label> data) {
		this.data = data;
		refresh();
	}

	/**
	 * Removes the element at the specified index.
	 * 
	 * @param index
	 *            The requested index.
	 */
	public void removeElementAt(int index) {
		data.remove(index);
		refresh();
	}
	
	/**
	 * should be called after altering the data object externally.
	 * 
	 * @see #getData()
	 */
	public final void refresh() {
		fireContentsChanged(this, 0, getSize() - 1);
	}
}
