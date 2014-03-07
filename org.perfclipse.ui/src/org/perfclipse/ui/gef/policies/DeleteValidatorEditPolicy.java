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

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.commands.DeleteValidatorCommand;

/**
 * @author Jakub Knetl
 *
 */
public class DeleteValidatorEditPolicy extends ComponentEditPolicy implements
		EditPolicy {

	private ValidationModel validation;
	private ValidatorModel validator;

	public DeleteValidatorEditPolicy(ValidationModel validation,
			ValidatorModel validator) {
		super();
		this.validation = validation;
		this.validator = validator;
	}

	@Override
	protected Command getDeleteCommand(GroupRequest request) {
		return new DeleteValidatorCommand(validation, validator);
	}
	
}
