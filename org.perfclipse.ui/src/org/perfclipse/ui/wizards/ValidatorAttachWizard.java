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

package org.perfclipse.ui.wizards;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.TableItem;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.wizards.pages.AttachValidatorPage;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorAttachWizard extends AbstractPerfCakeEditWizard {

	/**
	 * newly attached validatorrefs by this wizard
	 */
	private List<ValidatorRef> validatorRefs = new ArrayList<>();
	private AttachValidatorPage page;
	private ValidationModel validation;
	
	//validators added by user, which are not in scenario.
	private List<ValidatorModel> validators;
	
	public ValidatorAttachWizard(ValidationModel validation) {
		super("Attach validator");
		setWindowTitle("Attach validator");
		this.validation = validation;
	}

	@Override
	public boolean performFinish() {
		
		// add created validators to the validators list if it is not null
		TableItem[] items = page.getValidatorViewer().getTable().getItems();
		if  (validators != null && items != null){
			for (TableItem i : items){
				ValidatorModel v = (ValidatorModel) i.getData();
				if (!validators.contains(v))
					validators.add(v);
			}
		}
		
		
		IStructuredSelection selection = (IStructuredSelection) page.getValidatorViewer().getSelection();
		
		Iterator<?> it = selection.iterator();
		while (it.hasNext()){
			ValidatorModel validator = (ValidatorModel) it.next();
			ValidatorRef ref = new ObjectFactory().createScenarioMessagesMessageValidatorRef();
			ref.setId(validator.getValidator().getId());
			validatorRefs.add(ref);
		}

		return super.performFinish();
	}

	@Override
	public void addPages() {
		if (validation != null)
			page = new AttachValidatorPage(validation);
		else
			page = new AttachValidatorPage();
		page.setValidators(validators);
		addPage(page);

		super.addPages();
	}

	public List<ValidatorRef> getValidatorRef() {
		return validatorRefs;
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
