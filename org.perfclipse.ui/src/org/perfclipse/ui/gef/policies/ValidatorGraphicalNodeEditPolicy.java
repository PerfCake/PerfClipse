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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.core.commands.AddValidatorRefCommand;
import org.perfclipse.core.commands.EditValidatorRefCommand;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.core.model.ValidatorRefModel;
import org.perfclipse.ui.gef.parts.ValidatorRefEditPart;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorGraphicalNodeEditPolicy extends ValidatorRefGraphicalNodeEditPolicy {

	
	private ValidatorModel validator;
	
	/**
	 * @param validator
	 */
	public ValidatorGraphicalNodeEditPolicy(ValidatorModel validator) {
		super();
		this.validator = validator;
	}

	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {


		if (request.getStartCommand() == null ||
				!(request.getStartCommand() instanceof AddValidatorRefCommand))
			return null;
		
		if (request.getNewObjectType() != ValidatorRef.class)
			return null;

		ValidatorRef ref = (ValidatorRef) request.getNewObject();
		ref.setId(validator.getValidator().getId());
		
		AddValidatorRefCommand c = (AddValidatorRefCommand) request.getStartCommand();
		c.setValidatorRef(ref);
		
		if (isReferenceUnique(c.getMessage().getMessage(), ref.getId()))
			return c;
		else
			return null;

	}
	
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		if (request.getConnectionEditPart() instanceof ValidatorRefEditPart){
			ValidatorRefEditPart connectionPart = (ValidatorRefEditPart) request.getConnectionEditPart();
			ValidatorRefModel ref =  connectionPart.getValidatorRefModel();
			
			MessageModel message = connectionPart.findParentMessage(ref);

			Command c = new EditValidatorRefCommand(ref, validator.getValidator().getId());
			
			if (message == null)
				return c;
			
			if (isReferenceUnique(message.getMessage(), validator.getValidator().getId()))
				return c;
		}
		return null;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
