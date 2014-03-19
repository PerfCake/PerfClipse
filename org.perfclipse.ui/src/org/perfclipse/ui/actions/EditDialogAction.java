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

package org.perfclipse.ui.actions;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.perfclipse.ui.gef.policies.AbstractPerfCakeComponentEditPolicy;

/**
 * @author Jakub Knetl
 *
 */
public class EditDialogAction extends SelectionAction {

	public static final String SHOW_EDIT_DIALOG = "org.perfclipse.ui.actions.EditAction";
	public static final String REQ_SHOW_EDIT_DIALOG = "org.perfclipse.ui.actions.EditAction";
	
	private Request request;

	public EditDialogAction(IWorkbenchPart part) {
		super(part);
		setId(SHOW_EDIT_DIALOG);
		setText("Edit");
		request = new Request(REQ_SHOW_EDIT_DIALOG);
	}
	
	

	@Override
	public void run() {
		@SuppressWarnings("unchecked")
		List<EditPart> selectedParts = getSelectedObjects();
		CompoundCommand command = new CompoundCommand();
		for (EditPart selected : selectedParts){
			command.add(selected.getCommand(request));
		}
		
		execute(command);
	}

	@Override
	protected boolean calculateEnabled() {
		//check if something is selected
		if (getSelectedObjects().isEmpty()){
			return false;
		}

		//check if all selected objects are instance of editPart
		for (Object selected : getSelectedObjects()){
			if (!(selected instanceof EditPart)){
				return false;
			}
		}

		//Check if all selected eidtParts has installed edit policy with edit properties
		for (Object selected : getSelectedObjects()){
			EditPart part = (EditPart) selected;
			EditPolicy policy = part.getEditPolicy(EditPolicy.COMPONENT_ROLE);
			if (!(policy instanceof AbstractPerfCakeComponentEditPolicy)){
				return false;
			}
		}
		
		return true;
	}


}
