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

package org.perfclipse.gef.actions;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.perfclipse.gef.policies.AbstractPerfCakeComponentEditPolicy;

/**
 * Abstract action for adding components 
 *
 * @author Jakub Knetl
 *
 */
public abstract class AbstractPerfCakeAddAction extends SelectionAction {

	
	public AbstractPerfCakeAddAction(IWorkbenchPart part, Object requestId) {
		super(part);
		initRequest(requestId);
	}

	/**
	 * Request which will be used to invoke this action.
	 */
	protected Request request;

	/**
	 * This method iterates through all selected editParts and calls getCommand(Request) 
	 * for {@link request} of this action. Then it executes all returned command.
	 */
	@Override
	public void run() {
		@SuppressWarnings("unchecked")
		List<EditPart> selectedParts = getSelectedObjects();
		CompoundCommand commands = new CompoundCommand();
		for (EditPart selected : selectedParts){
			Command currentCommand = selected.getCommand(request);
			if (currentCommand != null)
				commands.add(currentCommand);
		}
		
		execute(commands);
	}

	/**
	 * Calculates if action is enabled.
	 * 
	 * @return True if there is selected just one edit part which
	 * has {@link AbstractPerfCakeComponentEditPolicy} installed. Otherwise
	 * it returns false.
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelectedObjects().size() != 1){
			return false;
		}
		
		Object selected = getSelectedObjects().get(0);
		
		if (!(selected instanceof EditPart))
			return false;
		
		EditPart part = (EditPart) selected;
		
		if (!(part.getEditPolicy(EditPolicy.COMPONENT_ROLE) 
				instanceof AbstractPerfCakeComponentEditPolicy))
			return false;
		
		return true;
	}
	
	/**
	 * Initializes request variable which correspond with this action type.
	 * @param requestId Id of the request.
	 */
	protected void initRequest(Object requestId){
		request = new Request(requestId);
	}
	
}
