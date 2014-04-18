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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.perfclipse.core.model.IEnableable;
import org.perfclipse.ui.gef.policies.AbstractPerfCakeComponentEditPolicy;

/**
 * Action which switch selected parts on/off
 * @author Jakub Knetl
 *
 */
public class SwitchAction extends SelectionAction {

	

	private Request request;
	
	/**
	 * @param part
	 */
	public SwitchAction(IWorkbenchPart part) {
		super(part);
		setId(PerfClipseActionConstants.SWITCH);
		request = new Request(PerfClipseActionConstants.REQ_SWITCH);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		//check if something is selected
		if (getSelectedObjects().isEmpty()){
			return false;
		}

		//check if all last object is instance of editPart
		Object selected = getSelectedObjects().get(getSelectedObjects().size() - 1);
		if (!(selected instanceof EditPart)){
			return false;
		}

		//Check if last selected eidtParts has installed edit policy
		EditPart part = (EditPart) selected;
		EditPolicy policy = part.getEditPolicy(EditPolicy.COMPONENT_ROLE);
		if (!(policy instanceof AbstractPerfCakeComponentEditPolicy)){
			return false;
		}
		if (!(part.getModel() instanceof IEnableable))
			return false;
			
		setText(calculateTitle(((IEnableable) part.getModel()).isEnabled()));
		return true;

	}

	@Override
	public void run() {

		if (getSelectedObjects() == null)
			return;

		Object selected = getSelectedObjects().get(getSelectedObjects().size() - 1);
		if (selected instanceof EditPart){
			EditPart part = (EditPart) selected;
			if (part.getModel() instanceof IEnableable){
				Command c = part.getCommand(request);
				execute(c);
				IEnableable model = (IEnableable) part.getModel();
				setText(calculateTitle(model.isEnabled()));
			}

		}
	}
	
	protected String calculateTitle(boolean enabled){
		return (enabled) ? "Disable" : "Enable";
	}

}
