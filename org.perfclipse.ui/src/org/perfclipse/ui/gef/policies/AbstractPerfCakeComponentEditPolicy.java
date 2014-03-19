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

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.perfclipse.ui.actions.EditDialogAction;

/**
 * Component edit policy which supports handling Properties Action request
 * in addition.
 * 
 * @author Jakub Knetl
 *
 */
public class AbstractPerfCakeComponentEditPolicy extends ComponentEditPolicy {

	@Override
	public Command getCommand(Request request) {
		if (request.getType().equals(EditDialogAction.REQ_SHOW_EDIT_DIALOG)){
			return getPropertiesCommand();
		}
		return super.getCommand(request);
	}

	protected Command getPropertiesCommand() {
		return createPropertiesCommand();
	}

	/**
	 * Subclass may override this method to create proper Command as a response
	 * to property action. 
	 * @return Command which will react to property action request
	 */
	protected Command createPropertiesCommand() {
		return null;
	}

	
}
