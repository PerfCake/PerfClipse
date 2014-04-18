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

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.core.model.ValidationModel;
import org.perfclipse.core.model.ValidatorModel;

/**
 * @author Jakub Knetl
 *
 */
public class AttachValidatorPage extends ValidationPage {

	//validators added by user, which are not in scenario.
	private List<ValidatorModel> validators;
	
	/**
	 * 
	 */
	public AttachValidatorPage() {
	}

	/**
	 * @param validation
	 */
	public AttachValidatorPage(ValidationModel validation) {
		super(validation);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		setTitle("Attach validator");
		setDescription("Select validator to attach");
		setPageComplete(false);
		getValidatorViewer().addSelectionChangedListener(new UpdateSelectionChangeListener(this));
	}
	

	@Override
	protected void fillValues() {
		List<ValidatorModel> scenarioValidators = getValidators();
		
		if (scenarioValidators != null && validators != null){
			scenarioValidators.removeAll(validators);
			scenarioValidators.addAll(validators);;
			getValidatorViewer().setInput(scenarioValidators);
		}
		
		if (scenarioValidators != null && validators == null){
			getValidatorViewer().setInput(scenarioValidators);
		}
		if (scenarioValidators == null && validators != null){
			getValidatorViewer().setInput(validators);
		}
	}

	@Override
	protected void updateControls() {
		IStructuredSelection selection;
		if (! (getValidatorViewer().getSelection() instanceof IStructuredSelection)){
			setDescription("Select validator to attach.");
			setPageComplete(false);
			return;
		}

		selection = (IStructuredSelection) getValidatorViewer().getSelection();
			
		Iterator<?> it = selection.iterator();
		while (it.hasNext()){
			Object selected = it.next();
			if (!(selected instanceof ValidatorModel)){
				setPageComplete(false);
				setDescription("Select validator to attach.");
				return;
			}
		}

		setPageComplete(true);
	}

	/**
	 * Initializes list of validators to which new validators (created by wizard) will be added.
	 * These validator are not currently in scenario, so it will be stored in this list.
	 * @param validators non null list of validators (may be empty)
	 */
	public void setValidators(List<ValidatorModel> validators) {
		this.validators = validators;
	}
	
}
