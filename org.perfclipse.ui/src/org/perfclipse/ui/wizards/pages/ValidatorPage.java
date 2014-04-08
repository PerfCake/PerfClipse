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


import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.swt.ComboUtils;
import org.perfclipse.ui.swt.jface.StringComboViewer;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorPage extends AbstractPerfCakePage {

	public static final String VALIDATOR_PAGE_NAME = "Validator page";

	private ValidatorModel validator;
	private List<ValidatorModel> otherValidators;
	
	private Composite container;
	private Label typeLabel;
	private StringComboViewer typeCombo; 
	private Label idLabel;
	private Text idText;
	private Label valueLabel;
	private Text valueText;
	
	public ValidatorPage(List<ValidatorModel> otherValidators){
		this(VALIDATOR_PAGE_NAME, false, otherValidators);
	}
	
	/**
	 * 
	 * @param validator
	 * @param otherValidators
	 */
	public ValidatorPage(ValidatorModel validator, List<ValidatorModel> otherValidators) {
		this(VALIDATOR_PAGE_NAME, true, otherValidators);
		this.validator = validator;
	}


	/**
	 * @param pageName
	 * @param edit
	 */
	private ValidatorPage(String pageName, boolean edit, List<ValidatorModel> otherValidators) {
		super(pageName, edit);
		this.otherValidators = otherValidators;
	}

	@Override
	public void createControl(Composite parent) {
		setTitle("Validator");
		setDescription("Fill in validator type and its id.");
		
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		GridData data;
		
		typeLabel = new Label(container, SWT.NONE);
		typeLabel.setText("Validator type: ");
		typeCombo = new StringComboViewer(container, getPerfCakeComponents().getValidatorNames());
		typeCombo.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		typeCombo.getControl().setLayoutData(data);

		idLabel = new Label(container, SWT.NONE);
		idLabel.setText("Validator id: ");
		idText = new Text(container, SWT.NONE);
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		idText.setLayoutData(data);
		idText.addModifyListener(new UpdateModifyListener(this));
		
		valueLabel = new Label(container, SWT.NONE);
		valueLabel.setText("Value: ");
		valueText = new  Text(container, SWT.MULTI);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		valueText.setLayoutData(data);
		valueText.addModifyListener(new UpdateModifyListener(this));
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void fillCurrentValues() {
		if (validator.getValidator().getClazz() != null)
			ComboUtils.select(typeCombo, validator.getValidator().getClazz());
		if (validator.getValidator().getId() != null)
			idText.setText(validator.getValidator().getId());
		if (validator.getValidator().getValue() != null)
			valueText.setText(validator.getValidator().getValue());
		super.fillCurrentValues();
	}

	@Override
	protected void updateControls() {
		if ( getValidatorName() == null || "".equals(getValidatorName())){
			setDescription("Select validator type.");
			setPageComplete(false);
			return;
		}
		if ("".equals(getValidatorId())){
			setDescription("Fill in validator id.");
			setPageComplete(false);
			return;
		}
		if (!isValidatorIdUnique(getValidatorId())){
			setDescription("Validator id is not unique. Please enter another ID.");
			setPageComplete(false);
			return;
		}
//		if ("".equals(getValidatorValue())){
//			setDescription("Fill in validator value.");
//			setPageComplete(false);
//			return;
//		}
		setPageComplete(true);
		setDescription("Complete!");
		super.updateControls();
	}
	
	/**
	 * Checks if id is unique
	 * @param validatorId
	 * 
	 * @return True if id is unique. False otherwise
	 * 
	 */
	private boolean isValidatorIdUnique(String validatorId) {
		if (otherValidators == null)
			return true;
		for (ValidatorModel v : otherValidators){
			//if edited validator is not null and it is same instance as 
			// validator v then continue
			if (validator != null && validator == v)
				continue;
			if (v.getValidator().getId().equals(validatorId)){
				return false;
			}
		}
		return true;
	}

	public String getValidatorName(){
		IStructuredSelection sel = (IStructuredSelection) typeCombo.getSelection();
		return (String) sel.getFirstElement();
	}
	
	public String getValidatorId(){
		return idText.getText();
	}
	
	public String getValidatorValue(){
		return valueText.getText();
	}
	
}
