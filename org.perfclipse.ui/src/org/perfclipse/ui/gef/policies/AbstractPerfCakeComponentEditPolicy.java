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
import org.perfclipse.ui.actions.PropertiesAction;

/**
 * @author Jakub Knetl
 *
 */
public class AbstractPerfCakeComponentEditPolicy extends ComponentEditPolicy {

	@Override
	public Command getCommand(Request request) {
		if (request.getType().equals(PropertiesAction.REQ_SHOW_PROPERTIES)){
			return getPropertiesCommand();
		}
		return super.getCommand(request);
	}

	protected Command getPropertiesCommand() {
		return createPropertiesCommand();
	}

	protected Command createPropertiesCommand() {
		//show dialog
		System.err.println("Show dialog and get input");
		return null;
	}

	
}
