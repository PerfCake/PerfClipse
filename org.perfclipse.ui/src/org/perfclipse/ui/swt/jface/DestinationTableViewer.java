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

import java.util.Collection;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.ui.gef.commands.EditDestinationEnabledCommand;
import org.perfclipse.ui.gef.commands.EditDestinationTypeCommand;

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
		enabledColumn = new TableViewerColumn(this, SWT.NONE);
		enabledColumn.getColumn().setText("On");
		enabledColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public Image getImage(Object element) {
				// TODO: override to display image instead of text!
				return super.getImage(element);
			}

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
		
		
		final StringComboCellEditor editor = 
				new StringComboCellEditor(getTable(),
						getPerfCakeComponents().getDestinationNames());

		
		clazzColumn.setEditingSupport(new AbstractCommandEditingSupport(this, getCommands(), editor) {
			
			@Override
			protected Object getValue(Object element) {
				DestinationModel destination = (DestinationModel) element;
				Collection<String> input =   editor.getViewerInputCollection();
				for (String c : input){
					if (destination.getDestination().getClazz().equals(c)){
						return c;
					}
				}
				
				return element;
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				DestinationModel destination = (DestinationModel) element;
				String clazz = (String) value;
				return new EditDestinationTypeCommand(destination, clazz);
			}
		});
	}
	
	@Override
	protected void setColumnsSize() {
		enabledColumn.getColumn().setWidth(30);
		clazzColumn.getColumn().setWidth(CLAZZ_COLUMN_WIDTH);
	}
	

}
