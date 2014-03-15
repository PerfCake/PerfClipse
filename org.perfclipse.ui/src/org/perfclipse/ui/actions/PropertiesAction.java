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
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Jakub Knetl
 *
 */
public class PropertiesAction extends SelectionAction {

	public static final String SHOW_PROPERTIES = "org.perfclipse.ui.actions.PropertiesAction";
	public static final String REQ_SHOW_PROPERTIES = "org.perfclipse.ui.actions.PropertiesAction";
	
	private Request request;

	public PropertiesAction(IWorkbenchPart part) {
		super(part);
		setId(SHOW_PROPERTIES);
		setText("Properties");
		request = new Request(REQ_SHOW_PROPERTIES);
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
		if (getSelectedObjects().isEmpty())
			return false;
		for (Object selected : getSelectedObjects()){
			if (!(selected instanceof EditPart))
				return false;
		}
		
		return true;
	}

}
