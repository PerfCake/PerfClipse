/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2013 Jakub Knetl
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

package org.perfclipse.ui.swt.jface;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.model.PropertyModel;

/**
 * @author Jakub Knetl
 *
 */
public class PropertyTableViewer extends AbstractCommandTableViewer {

	static final int COLUMN_WIDTH = 220;

	public PropertyTableViewer(Composite parent, int style, List<Command> commands) {
		super(parent, style, commands);
		this.commands = commands;
		
		initializeViewer();
	}

	public PropertyTableViewer(Composite parent, List<Command> commands) {
		super(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION, commands);
	}
	
	protected void initializeViewer() {
		setContentProvider(ArrayContentProvider.getInstance());
		super.initializeViewer();
	}

	@Override
	protected void initColumns() {
		TableViewerColumn keyColumn = new TableViewerColumn(this, SWT.NONE);
		keyColumn.getColumn().setWidth(COLUMN_WIDTH);
		keyColumn.getColumn().setText("Property name");
		keyColumn.setEditingSupport(new PropertyNameEditingSupport(this, commands));
		keyColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				PropertyModel property = (PropertyModel) element;
				return property.getProperty().getName();
			}
			
		});
		TableViewerColumn valueColumn = new TableViewerColumn(this, SWT.NONE);
		valueColumn.getColumn().setText("Property value");
		valueColumn.getColumn().setWidth(COLUMN_WIDTH);
		valueColumn.setEditingSupport(new PropertyValueEditingSupport(this, commands));
		valueColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				PropertyModel property = (PropertyModel) element;
				return property.getProperty().getValue();
			}
			
		});
		
	}
}
