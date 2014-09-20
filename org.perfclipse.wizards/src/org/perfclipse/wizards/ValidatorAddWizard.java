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

package org.perfclipse.wizards;

import java.util.List;

import org.eclipse.swt.widgets.TableItem;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.wizards.pages.ValidatorPage;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorAddWizard extends AbstractPerfCakeAddWizard {

	private ValidatorPage page;
	private Validator validator;
	private List<ValidatorModel> validators;
	
	
	public ValidatorAddWizard(List<ValidatorModel> validators) {
		super();
		this.validators = validators;
		setWindowTitle("Add validator");
	}

	@Override
	public boolean performFinish() {
		validator = new ObjectFactory().createScenarioValidationValidator();
		validator.setClazz(page.getValidatorName());
		validator.setId(page.getValidatorId());
		
		for (TableItem i : page.getPropertyViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				validator.getProperty().add(p.getProperty());
			}
		}

		return true;
	}

	@Override
	public void addPages() {
		page = new ValidatorPage(validators);
		addPage(page);
		super.addPages();
	}

	public Validator getValidator() {
		return validator;
	}
}
