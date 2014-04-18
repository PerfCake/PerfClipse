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
import org.perfclipse.core.model.IPropertyContainer;

/**
 * Add property to selected EditPart which can have properties.
 * 
 * @author Jakub Knetl
 *
 */
public class AddPropertyAction extends AbstractPerfCakeAddAction {

	public AddPropertyAction(IWorkbenchPart part) {
		super(part, PerfClipseActionConstants.REQ_ADD_PROPERTY);
		setText("Add property");
		setId(PerfClipseActionConstants.ADD_PROPERTY);
	}

	/**
	 * Calculates if action is enabled.
	 * 
	 * @return True if {@link AbstractPerfCakeAddAction#calculateEnabled()} 
	 * is true and EditPart has as a model object IPropertyContainer instance.
	 * 
	 */
	@Override
	protected boolean calculateEnabled() {

		if (!super.calculateEnabled())
			return false;

		EditPart part = (EditPart) getSelectedObjects().get(0);

		// Has model object properties?
		if (!(part.getModel() instanceof IPropertyContainer))
			return false;
		
		return true;
	}

}
