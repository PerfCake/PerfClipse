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

package org.perfclipse.ui.gef.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.AddValidatorCommand;
import org.perfclipse.ui.wizards.ValidationEditWizard;
import org.perfclipse.ui.wizards.ValidatorAddWizard;

/**
 * @author Jakub Knetl
 *
 */
public class ValidationEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private ValidationModel validation;

	/**
	 * @param validation
	 */
	public ValidationEditPolicy(ValidationModel validation) {
		super();
		this.validation = validation;
	}

	@Override
	protected Command createPropertiesCommand(Request request) {
		ValidationEditWizard wizard = new ValidationEditWizard(validation);
		if (Utils.showWizardDialog(wizard) == Window.OK){
			if (!wizard.getCommand().isEmpty())
				return wizard.getCommand();
		}
		return null;
	}

	@Override
	protected Command createAddValidatorCommand(Request request) {
		List<ValidatorModel> validators = new ArrayList<>();
		for (Validator v : validation.getValidation().getValidator()){
			validators.add((ValidatorModel) validation.getMapper().getModelContainer(v));
		}
		ValidatorAddWizard wizard = new ValidatorAddWizard(validators);
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return null;
		
		return new AddValidatorCommand(wizard.getValidator(), validation);
			
	}

}
