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
import org.perfclipse.model.PeriodModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.EditPeriodType;
import org.perfclipse.ui.gef.commands.EditPeriodValue;

/**
 * @author Jakub Knetl
 *
 */
public class PeriodTableViewer extends AbstractCommandTableViewer {

	private static final int TYPE_COLUMN_WIDTH = 100;
	private static final int VALUE_COLUMN_WIDTH = 200;

	private TableViewerColumn typeColumn;
	private TableViewerColumn valueColumn;
	/**
	 * @param parent
	 * @param style
	 * @param commands
	 */
	public PeriodTableViewer(Composite parent, int style, List<Command> commands) {
		super(parent, style, commands);
	}

	/**
	 * @param parent
	 * @param commands
	 */
	public PeriodTableViewer(Composite parent, List<Command> commands) {
		super(parent, commands);
	}

	@Override
	protected void initColumns() {
		 typeColumn = new TableViewerColumn(this, SWT.NONE);
		 typeColumn.getColumn().setText("Period type: ");
		 typeColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				PeriodModel period = (PeriodModel) element;

				return period.getPeriod().getType();
			}
			 
		 });
		 
		 StringComboCellEditor comboEditor = 
				 new StringComboCellEditor(getTable(), Utils.getPeriodTypes());
		 typeColumn.setEditingSupport(
				 new AbstractCommandEditingSupport(this, getCommands(), comboEditor) {
			
			@Override
			protected Object getValue(Object element) {
				PeriodModel period = (PeriodModel) element;
				//TODO : check if correct value is selected
				return period.getPeriod().getType();
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				PeriodModel period = (PeriodModel) element;

				return new EditPeriodType(period, String.valueOf(value));
			}
		});
		 
		 valueColumn = new TableViewerColumn(this, SWT.NONE);
		 valueColumn.getColumn().setText("Period value");
		 valueColumn.getColumn().setAlignment(SWT.CENTER);
		 valueColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				PeriodModel period = (PeriodModel) element;
				return period.getPeriod().getValue();
			}
			 
		 });
		 
		 valueColumn.setEditingSupport(
				 new AbstractCommandEditingSupport(this, getCommands()){
			
			@Override
			protected Object getValue(Object element) {
				PeriodModel period = (PeriodModel) element;
				return period.getPeriod().getValue();
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				PeriodModel period = (PeriodModel) element;
				try{
					Integer.parseInt(String.valueOf(value));
				} catch (NumberFormatException e){
					return null;
				}
				
				return new EditPeriodValue(period, String.valueOf(value));
			}
		});
	}

	@Override
	protected void setColumnsSize() {
		typeColumn.getColumn().setWidth(TYPE_COLUMN_WIDTH);
		valueColumn.getColumn().setWidth(VALUE_COLUMN_WIDTH);
		super.setColumnsSize();
	}

}
