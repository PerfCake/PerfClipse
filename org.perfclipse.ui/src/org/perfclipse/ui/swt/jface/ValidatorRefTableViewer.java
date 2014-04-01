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
import org.perfcake.model.Scenario.Validation;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ValidatorRefModel;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorRefTableViewer extends AbstractCommandTableViewer {


	
	private static final int ID_COLUMN_WIDTH = 100;
	private TableViewerColumn refColumn;
	private TableViewerColumn clazzColumn;

	/**
	 * @param parent
	 * @param style
	 * @param commands
	 */
	public ValidatorRefTableViewer(Composite parent, int style,
			List<Command> commands) {
		super(parent, style, commands);
	}

	/**
	 * @param parent
	 * @param commands
	 */
	public ValidatorRefTableViewer(Composite parent, List<Command> commands) {
		super(parent, commands);
	}
	

	@Override
	protected void initColumns() {

		refColumn = new TableViewerColumn(this, SWT.NONE);
		//TODO: Connect with validator using its name and enable to go to edit validator wizard.
		refColumn.getColumn().setText("Validator id");
		refColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				ValidatorRefModel ref = (ValidatorRefModel) element;
				return ref.getValidatorRef().getId();
			}
			
		});
		
		clazzColumn = new TableViewerColumn(this, SWT.NONE);
		clazzColumn.getColumn().setText("Type");
		clazzColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				ValidatorRefModel ref = (ValidatorRefModel) element;

				ModelMapper mapper = ref.getMapper();
				Validation validation = mapper.getScenario().getScenario().getValidation();
				if (validation != null){
					List<Validator> validators = validation.getValidator();
				
					for (Validator v : validators){
						if (ref.getValidatorRef().getId().equals(v.getId())){
							return v.getClazz();
						}
					}
				}
				
				return "No such validator with given ID exists";
			}

		});
	}

	@Override
	protected void setColumnsSize() {

		refColumn.getColumn().setWidth(ID_COLUMN_WIDTH);
		clazzColumn.getColumn().setWidth(140);
		super.setColumnsSize();
	}
	
	

}
