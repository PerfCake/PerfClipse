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
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.reflect.PerfClipseScannerException;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.EditReporterEnabledCommand;
import org.perfclipse.ui.gef.commands.EditReporterTypeCommand;
import org.perfclipse.ui.gef.directedit.ClassLabelProvider;

/**
 * @author Jakub Knetl
 *
 */
public class ReporterTableViewer extends AbstractCommandTableViewer {

	private static final int CLASS_COLUMN_WIDTH = 220;
	private TableViewerColumn classColumn;
	private TableViewerColumn enabledColumn;

	public ReporterTableViewer(Composite parent, int style,
			List<Command> commands) {
		super(parent, style, commands);
	}
	
	public ReporterTableViewer(Composite parent, List<Command> commands) {
		super(parent, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION, commands);
	}
	
	

	@Override
	protected void initColumns() {
		enabledColumn = new TableViewerColumn(this, SWT.NONE);
		enabledColumn.getColumn().setText("Enabled");
		enabledColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public Image getImage(Object element) {
				// TODO: override to display image instead of text!
				return super.getImage(element);
			}

			@Override
			public String getText(Object element) {
				ReporterModel reporter = (ReporterModel) element;
				return String.valueOf(reporter.getReporter().isEnabled());
			}
			
			
		});
		CheckboxCellEditor checkboxEditor = new CheckboxCellEditor(getTable());
		enabledColumn.setEditingSupport(new AbstractCommandEditingSupport(this,
				getCommands(), checkboxEditor) {
			
			@Override
			protected Object getValue(Object element) {
				ReporterModel reporter = (ReporterModel) element;
				return reporter.getReporter().isEnabled();
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				ReporterModel reporter = (ReporterModel) element;
				return new EditReporterEnabledCommand(reporter, (Boolean) getCellEditor(element).getValue());
			}
		});
		
		classColumn = new TableViewerColumn(this, SWT.NONE);
		classColumn.getColumn().setText("Reporter type");
		classColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				ReporterModel reporter = (ReporterModel) element;

				return reporter.getReporter().getClazz();
			}
			
		});
		
		//TODO: extract own ClassComboViewerCellEditor and use it also in Direct Edit
		ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(getTable());
		editor.setContentProvider(ArrayContentProvider.getInstance());
		editor.setLabelProvider(new ClassLabelProvider());

		try {
			PerfCakeComponents components = PerfCakeComponents.getInstance();
			editor.setInput(components.getReporters().toArray());
		} catch (PerfClipseScannerException e) {
			//TODO: log error
		}
		
		classColumn.setEditingSupport(new AbstractCommandEditingSupport(this, getCommands(), editor) {
			
			@Override
			protected Object getValue(Object element) {

				//TODO: select current value
				return null;
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				ReporterModel reporter = (ReporterModel) element;
				Class<?> clazz = (Class<?>) value;
				return new EditReporterTypeCommand(reporter, Utils.clazzToString(clazz));
			}
		});
		
	}

	@Override
	protected void setColumnsSize() {
		enabledColumn.getColumn().setWidth(10);
		classColumn.getColumn().setWidth(CLASS_COLUMN_WIDTH);
	}
}
