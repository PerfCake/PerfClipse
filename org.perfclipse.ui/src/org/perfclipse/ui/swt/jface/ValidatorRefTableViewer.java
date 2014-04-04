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
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.model.ValidatorRefModel;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorRefTableViewer extends AbstractCommandTableViewer {

	private TableViewerColumn refColumn;
	private TableViewerColumn clazzColumn;

	
	/**
	 * This field is used for constructing new scenario, where validators
	 * are not yet in the model so type of the validator in the viewer 
	 * cannot be resolved. Then this List is used as validator input and validator
	 * is searched in this list.
	 */
	private List<ValidatorModel> alternativeValidators;
	
	
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
	

	/**
	 * Crates two column. First column with ValidatorRef id.
	 * Second column with validator class type. If validator with given
	 * ID is not found. It searches for validator also in AlternativeValidators
	 * 
	 * @see ValidatorRefTableViewer#setAlternativeValidators(List)
	 * 
	 */
	@Override
	protected void initColumns() {

		refColumn = new TableViewerColumn(this, SWT.CENTER);
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


				// search for validator in alternative list
				if (alternativeValidators != null){
					for (ValidatorModel v : alternativeValidators){
						if (ref.getValidatorRef().getId().equals(v.getValidator().getId())){
							return v.getValidator().getClazz();
						}
					}
				}
				
				return "No such validator with given ID exists. Please add validator with this ID";
			}

		});
	}

	@Override
	protected void setColumnsSize() {

		refColumn.getColumn().pack();
		clazzColumn.getColumn().setWidth(140);
		super.setColumnsSize();
	}

	/**
	 * This method is used for adding alternative validators list.
	 */
	public void setAlternativeValidators(List<ValidatorModel> alternativeValidators) {
		this.alternativeValidators = alternativeValidators;
	}
}
