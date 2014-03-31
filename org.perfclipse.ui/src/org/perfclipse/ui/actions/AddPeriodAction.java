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
import org.eclipse.ui.IWorkbenchPart;
import org.perfclipse.model.DestinationModel;

/**
 * Add period action
 * @author Jakub Knetl
 *
 */
public class AddPeriodAction extends AbstractPerfCakeAddAction {

	public AddPeriodAction(IWorkbenchPart part) {
		super(part, PerfClipseActionConstants.REQ_ADD_PERIOD);
		setId(PerfClipseActionConstants.ADD_PERIOD);
		setText("Add period");
	}

	/**
	 * Calculates if action is enabled
	 * @return True if {@link AbstractPerfCakeAddAction#calculateEnabled()} returns true
	 * and model object of selected {@link EditPart} is instanceof {@link DestinationModel}
	 */
	@Override
	protected boolean calculateEnabled() {
		if (!super.calculateEnabled())
			return false;
		
		EditPart part = (EditPart) getSelectedObjects().get(0);
		
		if (!(part.getModel() instanceof DestinationModel))
			return false;
		
		return true;
	}
	
	

}
