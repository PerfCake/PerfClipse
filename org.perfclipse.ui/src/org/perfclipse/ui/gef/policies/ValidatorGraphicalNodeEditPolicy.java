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
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.gef.commands.AddValidatorRefCommand;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	
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
		
		//check if connection is unique
		MessageModel message = c.getMessage();
		if (message.getMessage().getValidatorRef() != null){
			for (ValidatorRef currentRef : message.getMessage().getValidatorRef()){
				if (ref.getId().equals(currentRef.getId()))
					return null;
			}
		}

		return c;

	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
