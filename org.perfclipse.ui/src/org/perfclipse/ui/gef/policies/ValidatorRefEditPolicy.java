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
import org.eclipse.gef.requests.GroupRequest;
import org.perfclipse.core.commands.DeleteValidatorRefCommand;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.ValidatorRefModel;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorRefEditPolicy extends AbstractPerfCakeComponentEditPolicy {
	
	private MessageModel message;
	private ValidatorRefModel validatorRef;
	/**
	 * @param message
	 * @param validatorRef
	 */
	public ValidatorRefEditPolicy(MessageModel message,
			ValidatorRefModel validatorRef) {
		super();
		this.message = message;
		this.validatorRef = validatorRef;
	}
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteValidatorRefCommand(message, validatorRef.getValidatorRef());
	}

}
