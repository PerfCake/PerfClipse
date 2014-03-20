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
	private TableViewerColumn keyColumn;
	private TableViewerColumn valueColumn;

	public PropertyTableViewer(Composite parent, int style, List<Command> commands) {
		super(parent, style, commands);
	}

	public PropertyTableViewer(Composite parent, List<Command> commands) {
		super(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION, commands);
	}

	@Override
	protected void initColumns() {
		keyColumn = new TableViewerColumn(this, SWT.NONE);
		keyColumn.getColumn().setText("Property name");
		keyColumn.setEditingSupport(new PropertyNameEditingSupport(this, getCommands()));
		keyColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				PropertyModel property = (PropertyModel) element;
				return property.getProperty().getName();
			}
			
		});
		valueColumn = new TableViewerColumn(this, SWT.NONE);
		valueColumn.getColumn().setText("Property value");
		valueColumn.setEditingSupport(new PropertyValueEditingSupport(this, getCommands()));
		valueColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				PropertyModel property = (PropertyModel) element;
				return property.getProperty().getValue();
			}
			
		});
		
	}

	@Override
	protected void setColumnsSize() {
		keyColumn.getColumn().setWidth(COLUMN_WIDTH);
		valueColumn.getColumn().setWidth(COLUMN_WIDTH);
	}
	
}
