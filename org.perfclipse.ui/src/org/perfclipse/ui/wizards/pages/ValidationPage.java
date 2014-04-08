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
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.swt.events.AddValidatorSelectionAdapater;
import org.perfclipse.ui.swt.events.DeleteValidatorSelectionAdapter;
import org.perfclipse.ui.swt.events.EditValidatorSelectionAdapter;
import org.perfclipse.ui.swt.jface.ValidatorTableViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class ValidationPage extends AbstractPerfCakePage {

	public static final String VALIDATION_PAGE_NAME = "Validation page";
	private Composite container;
	private ValidatorTableViewer validatorViewer;
	private TableViewerControl validatorControl;
	
	private ValidationModel validation;
	private List<ValidatorModel> validators;
	
	private List<MessageModel> messages;
	private DeleteValidatorSelectionAdapter deleteValidatorAdapter;
	private EditValidatorSelectionAdapter editValidatorAdapter;
	
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
		
		validatorViewer = new ValidatorTableViewer(container, getEditingSupportCommands());
		data = Utils.getTableViewerGridData();
		validatorViewer.getTable().setLayoutData(data);
		
		validatorControl = new TableViewerControl(container, true, SWT.NONE);
		validatorControl.getAddButton().addSelectionListener(
				new AddValidatorSelectionAdapater(getEditingSupportCommands(), validatorViewer, validation));
		editValidatorAdapter = new EditValidatorSelectionAdapter(getEditingSupportCommands(), validatorViewer);
		validatorControl.getEditButton().addSelectionListener(editValidatorAdapter);

		deleteValidatorAdapter = new DeleteValidatorSelectionAdapter(getEditingSupportCommands(), validatorViewer, validation);
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

	public List<MessageModel> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageModel> messages) {
		if (deleteValidatorAdapter != null)
			deleteValidatorAdapter.setMessages(messages);
		if (editValidatorAdapter != null)
			editValidatorAdapter.setMessages(messages);
		this.messages = messages;
	}
	
	
}
