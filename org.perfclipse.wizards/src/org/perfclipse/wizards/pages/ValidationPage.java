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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.ValidationModel;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.wizards.WizardUtils;
import org.perfclipse.wizards.swt.events.AddValidatorSelectionAdapater;
import org.perfclipse.wizards.swt.events.DelKeyPressedSelectionAdapter;
import org.perfclipse.wizards.swt.events.DeleteValidatorSelectionAdapter;
import org.perfclipse.wizards.swt.events.DoubleClickSelectionAdapter;
import org.perfclipse.wizards.swt.events.EditValidatorSelectionAdapter;
import org.perfclipse.wizards.swt.jface.ValidatorTableViewer;
import org.perfclipse.wizards.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class ValidationPage extends AbstractPerfCakePage {

	public static final String VALIDATION_PAGE_NAME = "Validation page";
	private Composite container;
	private ValidatorTableViewer validatorViewer;
	private TableViewerControl validatorControl;
	
	private Label enabledLabel;
	private Button enabledButton;
	
	private Label fastForwardLabel;
	private Button fastForwardButton;
	
	private ValidationModel validation;
	private List<ValidatorModel> validators;
	
	private List<MessageModel> messages;
	private DeleteValidatorSelectionAdapter deleteValidatorAdapter;
	private EditValidatorSelectionAdapter editValidatorAdapter;
	protected DoubleClickSelectionAdapter doubleClickValidatorAdapter;
	
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
		
		if (validation.getValidation() == null){
			setEditMode(false);
			return;
		}
		
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
		setTitle("Validation section");
		setDescription("Manage validators.");
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		GridData data;
		
		enabledLabel = new  Label(container, SWT.NONE);
		enabledLabel.setText("Enabled: ");
		enabledButton = new Button(container, SWT.CHECK);
		data = new GridData();
		data.horizontalAlignment = SWT.LEFT;
		enabledButton.setLayoutData(data);
		
		fastForwardLabel = new  Label(container, SWT.NONE);
		fastForwardLabel.setText("Fast forward: ");
		fastForwardButton = new Button(container, SWT.CHECK);
		data = new GridData();
		data.horizontalAlignment = SWT.LEFT;
		fastForwardButton.setLayoutData(data);
		
		validatorViewer = new ValidatorTableViewer(container, getNestedCommands());
		data = WizardUtils.getTableViewerGridData();
		validatorViewer.getTable().setLayoutData(data);
		
		validatorControl = new TableViewerControl(container, true, SWT.NONE);
		validatorControl.getAddButton().addSelectionListener(
				new AddValidatorSelectionAdapater(getNestedCommands(), validatorViewer, validation));
		editValidatorAdapter = new EditValidatorSelectionAdapter(getNestedCommands(), validatorViewer);
		doubleClickValidatorAdapter = new DoubleClickSelectionAdapter(editValidatorAdapter);
		validatorViewer.getTable().addMouseListener(doubleClickValidatorAdapter);
		validatorControl.getEditButton().addSelectionListener(editValidatorAdapter);

		deleteValidatorAdapter = new DeleteValidatorSelectionAdapter(getNestedCommands(), validatorViewer, validation);
		validatorViewer.getTable().addKeyListener(new DelKeyPressedSelectionAdapter(deleteValidatorAdapter));
		validatorControl.getDeleteButton().addSelectionListener(deleteValidatorAdapter);

		//set messages in adapters
		if (messages != null){
			deleteValidatorAdapter.setMessages(messages);
			editValidatorAdapter.setMessages(messages);
		}

		setControl(container);
		setPageComplete(true);
		super.createControl(parent);
	}

	
	@Override
	protected void fillCurrentValues() {
		if (validators != null)
			validatorViewer.setInput(validators);
		
		enabledButton.setSelection(validation.getValidation().isEnabled());
		fastForwardButton.setSelection(validation.getValidation().isFastForward());

		super.fillCurrentValues();
	}

	public ValidatorTableViewer getValidatorViewer() {
		return validatorViewer;
	}

	/**
	 * @return list of validators in scenario.
	 */
	public List<ValidatorModel> getValidators() {
		return validators;
	}
	
	public void setValidators(List<ValidatorModel> validators) {
		if (this.validators == null){
			this.validators = new ArrayList<>();
		}
		if (validators != null){
			this.validators.addAll(validators);
			validatorViewer.setInput(validators);
		}
	}

	public List<MessageModel> getMessages() {
		return messages;
	}
	
	public boolean isEnabled(){
		return enabledButton.getSelection();
	}
	
	public boolean isFastForward(){
		return fastForwardButton.getSelection();
	}

	public void setMessages(List<MessageModel> messages) {
		if (deleteValidatorAdapter != null)
			deleteValidatorAdapter.setMessages(messages);
		if (editValidatorAdapter != null)
			editValidatorAdapter.setMessages(messages);
		this.messages = messages;
	}
	
	
}
