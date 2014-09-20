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

package org.perfclipse.wizards.pages;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.perfcake.model.Property;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.wizards.WizardUtils;
import org.perfclipse.wizards.swt.ComboUtils;
import org.perfclipse.wizards.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.DelKeyPressedSelectionAdapter;
import org.perfclipse.wizards.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.wizards.swt.events.DoubleClickSelectionAdapter;
import org.perfclipse.wizards.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.wizards.swt.jface.PropertyTableViewer;
import org.perfclipse.wizards.swt.jface.StringComboViewer;
import org.perfclipse.wizards.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorPage extends AbstractPerfCakePage {

	public static final String VALIDATOR_PAGE_NAME = "Validator page";

	private ValidatorModel validator;
	private List<ValidatorModel> otherValidators;
	private List<PropertyModel> properties;
	
	private Composite container;
	private Label typeLabel;
	private StringComboViewer typeCombo; 
	private Label idLabel;
	private Text idText;
	private PropertyTableViewer propertyViewer;
	private TableViewerControl propertyControl;	

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
		
		ModelMapper m = validator.getMapper();
		properties = new ArrayList<>(validator.getValidator().getProperty().size());
		for (Property p : validator.getValidator().getProperty()){
			properties.add((PropertyModel) m.getModelContainer(p));
		}
		
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
		
		
		propertyViewer = new PropertyTableViewer(container, getNestedCommands());
		data = WizardUtils.getTableViewerGridData();
		data.horizontalSpan = 2;
		propertyViewer.getTable().setLayoutData(data);
		
		EditPropertySelectionAdapter editPropertyAdapter = new EditPropertySelectionAdapter(getNestedCommands(), propertyViewer);
		propertyViewer.getTable().addMouseListener(new DoubleClickSelectionAdapter(editPropertyAdapter));
		propertyControl = new TableViewerControl(container, true, SWT.NONE);
		propertyControl.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getNestedCommands(),
						propertyViewer, validator));
		DeletePropertySelectionAdapter deletePropertyAdapter = 
				new DeletePropertySelectionAdapter(getNestedCommands(), propertyViewer, validator);
		propertyViewer.getTable().addKeyListener(new DelKeyPressedSelectionAdapter(deletePropertyAdapter));
		propertyControl.getDeleteButton().addSelectionListener(deletePropertyAdapter);
		propertyControl.getEditButton().addSelectionListener(editPropertyAdapter);
		
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void fillCurrentValues() {
		if (validator.getValidator().getClazz() != null)
			ComboUtils.select(typeCombo, validator.getValidator().getClazz());
		if (validator.getValidator().getId() != null)
			idText.setText(validator.getValidator().getId());
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
	
	public PropertyTableViewer getPropertyViewer(){
		return propertyViewer;
	}
	
	public String getValidatorId(){
		return idText.getText();
	}
	
}
