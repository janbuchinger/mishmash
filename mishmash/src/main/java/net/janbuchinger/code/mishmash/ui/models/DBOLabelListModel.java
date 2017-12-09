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

import net.janbuchinger.code.mishmash.dbo.DBOLabel;


/**
 * <code>DBOLabelListModel</code> is a <code>ListModel</code> for
 * <code>JList&lt;DBOLabel&gt;</code>
 * 
 * @author Jan Buchinger
 * 
 * @see net.janbuchinger.code.mishmash.dbo.DBOLabel
 */
@SuppressWarnings("serial")
public class DBOLabelListModel extends AbstractListModel<DBOLabel> {

	private Vector<DBOLabel> data;

	/**
	 * Constructs a <code>DBOLabelListModel</code> with an empty data
	 * <code>List</code>.
	 */
	public DBOLabelListModel() {
		data = new Vector<DBOLabel>();
	}

	/**
	 * Constructs a <code>DBOLabelListModel</code> with the supplied data.
	 * 
	 * @param data
	 *            The <code>List</code> to initiate this
	 *            <code>DBOLabelListModel</code> with.
	 */
	public DBOLabelListModel(Vector<DBOLabel> data) {
		if (data != null)
			this.data = data;
		else
			this.data = new Vector<DBOLabel>();

	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public DBOLabel getElementAt(int index) {
		return data.get(index);
	}

	/**
	 * Gets the current data <code>List</code>.
	 * 
	 * @return The current data <code>List</code>.
	 */
	public final Vector<DBOLabel> getData() {
		return data;
	}

	/**
	 * Sets the current data <code>List</code>.
	 * 
	 * @param data
	 *            The current data <code>List</code> to set.
	 */
	public final void setData(Vector<DBOLabel> data) {
		this.data = data;
		fireContentsChanged(this, 0, getSize() - 1);
	}

	/**
	 * Removes the specified index from the model.
	 * 
	 * @param index
	 *            The requested index.
	 */
	public void removeElementAt(int index) {
		data.remove(index);
		fireContentsChanged(this, 0, getSize() - 1);
	}
}
