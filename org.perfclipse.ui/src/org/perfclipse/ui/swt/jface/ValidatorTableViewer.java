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
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.commands.EditValidatorIdCommand;
import org.perfclipse.ui.gef.commands.EditValidatorTypeCommand;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorTableViewer extends AbstractCommandTableViewer {


	private static final int CLAZZ_COLUMN_WIDTH = 220;
	private static final int ID_COLUMN_WIDTH = 70;
	private TableViewerColumn clazzColumn;
	private TableViewerColumn idColumn;

	/**
	 * @param parent
	 * @param commands
	 */
	public ValidatorTableViewer(Composite parent, List<Command> commands) {
		super(parent, commands);
	}

	/**
	 * @param parent
	 * @param style
	 * @param commands
	 */
	public ValidatorTableViewer(Composite parent, int style,
			List<Command> commands) {
		super(parent, style, commands);
	}

	@Override
	protected void initColumns() {
		idColumn = new TableViewerColumn(this, SWT.NONE);
		idColumn.getColumn().setText("Validator Id");
		idColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				ValidatorModel validator = (ValidatorModel) element;
				return validator.getValidator().getId();
			}
			
		});
		
		idColumn.setEditingSupport(new AbstractCommandEditingSupport(this, getCommands()) {
			
			@Override
			protected Object getValue(Object element) {
				ValidatorModel validator = (ValidatorModel) element;
				
				return validator.getValidator().getId();
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				ValidatorModel validator = (ValidatorModel) element;
				return new EditValidatorIdCommand(validator, String.valueOf(value));
			}
		});
		
		clazzColumn = new TableViewerColumn(this, SWT.NONE);
		clazzColumn.getColumn().setText("Type");
		clazzColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				ValidatorModel validator = (ValidatorModel) element;
				return validator.getValidator().getClazz();
			}

		});
		
		StringComboCellEditor comboEditor = 
				new StringComboCellEditor(getTable(), getPerfCakeComponents().getValidatorNames());
		
		clazzColumn.setEditingSupport(
				new AbstractCommandEditingSupport(this, getCommands(), comboEditor) {
			
			@Override
			protected Object getValue(Object element) {
				//TODO: check if correct value is set
				ValidatorModel validator = (ValidatorModel) element;
				return validator.getValidator().getClazz();
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				ValidatorModel validator = (ValidatorModel) element;
				return new EditValidatorTypeCommand(validator, String.valueOf(value));
			}

		});
	}

	@Override
	protected void setColumnsSize() {
		idColumn.getColumn().setWidth(ID_COLUMN_WIDTH);
		clazzColumn.getColumn().setWidth(CLAZZ_COLUMN_WIDTH);
	}
	

}
