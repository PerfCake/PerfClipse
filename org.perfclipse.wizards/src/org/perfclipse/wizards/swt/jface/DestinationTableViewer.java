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

package org.perfclipse.wizards.swt.jface;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.core.commands.EditDestinationEnabledCommand;
import org.perfclipse.core.model.DestinationModel;

/**
 * @author Jakub Knetl
 *
 */
public class DestinationTableViewer extends AbstractCommandTableViewer {

	private static final int CLAZZ_COLUMN_WIDTH = 220;
	private TableViewerColumn clazzColumn;
	private TableViewerColumn enabledColumn;
	
	/**
	 * @param parent
	 * @param commands
	 */
	public DestinationTableViewer(Composite parent, List<Command> commands) {
		super(parent, commands);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param parent
	 * @param style
	 * @param commands
	 */
	public DestinationTableViewer(Composite parent, int style,
			List<Command> commands) {
		super(parent, style, commands);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initColumns() {
		enabledColumn = new TableViewerColumn(this, SWT.CENTER);
		enabledColumn.getColumn().setText("Enabled");
		enabledColumn.setLabelProvider(new ColumnLabelProvider(){
			
			@Override
			public String getText(Object element) {
				DestinationModel destination =  (DestinationModel) element;
				if (destination.getDestination().isEnabled())
					return "*";
				else
					return "";
			}
			
			
		});
		CheckboxCellEditor checkboxEditor = new CheckboxCellEditor(getTable());
		enabledColumn.setEditingSupport(new AbstractCommandEditingSupport(this,
				getCommands(), checkboxEditor) {
			
			@Override
			protected Object getValue(Object element) {
				DestinationModel destination =  (DestinationModel) element;
				return destination.getDestination().isEnabled();
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				DestinationModel destination = (DestinationModel) element;
				return new EditDestinationEnabledCommand(destination, (Boolean) getCellEditor(element).getValue());
			}
		});
		
		clazzColumn = new TableViewerColumn(this, SWT.NONE);
		clazzColumn.getColumn().setText("Destination type");
		clazzColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				DestinationModel destination = (DestinationModel) element;

				return destination.getDestination().getClazz();
			}
			
		});
		
	}
	
	@Override
	protected void setColumnsSize() {
		enabledColumn.getColumn().pack();
		clazzColumn.getColumn().setWidth(CLAZZ_COLUMN_WIDTH);
	}
	

}
