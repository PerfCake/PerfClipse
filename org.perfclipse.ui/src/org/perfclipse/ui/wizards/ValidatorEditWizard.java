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

import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.commands.EditValidatorIdCommand;
import org.perfclipse.ui.gef.commands.EditValidatorTypeCommand;
import org.perfclipse.ui.gef.commands.EditValidatorValueCommand;
import org.perfclipse.ui.wizards.pages.ValidatorPage;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorEditWizard extends AbstractPerfCakeEditWizard {

	private ValidatorModel validator;
	private ValidatorPage validatorPage;
	/**
	 * @param validator
	 */
	public ValidatorEditWizard(ValidatorModel validator) {
		super("Edit validator");
		this.validator = validator;
	}
	@Override
	public boolean performFinish() {
		Validator v = validator.getValidator();
		if (!v.getClazz().equals(validatorPage.getValidatorName()))
			command.add(new EditValidatorTypeCommand(validator, validatorPage.getValidatorName()));
		if (!v.getId().equals(validatorPage.getValidatorId()))
			command.add(new EditValidatorIdCommand(validator, validatorPage.getValidatorId()));
		if (!v.getValue().equals(validatorPage.getValidatorValue()))
			command.add(new EditValidatorValueCommand(validator, validatorPage.getValidatorValue()));
		return super.performFinish();
	}
	@Override
	public void addPages() {
		validatorPage = new ValidatorPage(validator);
		addPage(validatorPage);
		super.addPages();
	}
	
	
	
	
}
