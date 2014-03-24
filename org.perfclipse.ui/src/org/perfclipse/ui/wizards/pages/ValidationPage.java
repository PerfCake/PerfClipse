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

package org.perfclipse.ui.wizards.pages;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.swt.jface.ValidatorTableViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class ValidationPage extends AbstractPerfCakePage {

	private static final String VALIDATION_PAGE_NAME = "Validation page";
	private Composite container;
	private ValidatorTableViewer validatorViewer;
	private TableViewerControl validatorControl;
	
	private ValidationModel validation;
	private List<ValidatorModel> validators;
	
	public ValidationPage(){
		this(VALIDATION_PAGE_NAME, false);
	}
	
	/**
	 * 
	 * @param validation
	 */
	public ValidationPage(ValidationModel validation){
		this(VALIDATION_PAGE_NAME, true);
		this.validation = validation;
		
		ModelMapper mapper = validation.getMapper();
		validators = new ArrayList<>(validation.getValidation().getValidator().size());
		for (Validator v : validation.getValidation().getValidator()){
			validators.add((ValidatorModel) mapper.getModelContainer(v));
		}
	}
	
	
	/**
	 * @param pageName
	 * @param edit
	 */
	private ValidationPage(String pageName, boolean edit) {
		super(pageName, edit);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		GridData data;
		
		validatorViewer = new ValidatorTableViewer(container, getEditingSupportCommands());
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		validatorViewer.getTable().setLayoutData(data);
		
		validatorControl = new TableViewerControl(container, false, SWT.NONE);

		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void fillCurrentValues() {
		validatorViewer.setInput(validators);
		super.fillCurrentValues();
	}

	public ValidatorTableViewer getValidatorViewer() {
		return validatorViewer;
	}

}