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

package org.perfclipse.ui.editors;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;
import org.perfclipse.core.model.DestinationModel;
import org.perfclipse.core.model.IEnableable;
import org.perfclipse.core.model.IPropertyContainer;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.MessagesModel;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ReportingModel;
import org.perfclipse.core.model.ValidationModel;
import org.perfclipse.ui.actions.PerfClipseActionConstants;

/**
 * @author Jakub Knetl
 *
 */
public class DesignEditorContextMenuProvider extends ContextMenuProvider {
	
	private ActionRegistry actionRegistry;

	public DesignEditorContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		this.actionRegistry = registry;
		
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		GEFActionConstants.addStandardActionGroups(menu);
		
		IAction action;
		

		action = actionRegistry.getAction(ActionFactory.UNDO.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

		action = actionRegistry.getAction(ActionFactory.REDO.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

		action = actionRegistry.getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = actionRegistry.getAction(PerfClipseActionConstants.SHOW_EDIT_DIALOG);
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		

		if (getViewer().getSelectedEditParts().isEmpty())
			return;

		EditPart lastSelected = (EditPart) getViewer().getSelectedEditParts().
				get(getViewer().getSelectedEditParts().size() - 1);


		action = actionRegistry.getAction(PerfClipseActionConstants.ADD_PROPERTY);
		if (lastSelected.getModel() instanceof IPropertyContainer)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(PerfClipseActionConstants.ADD_DESTINATION);

		if (lastSelected.getModel() instanceof ReporterModel)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(PerfClipseActionConstants.ADD_HEADER);
		if (lastSelected.getModel() instanceof MessageModel)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(PerfClipseActionConstants.ADD_MESSAGE);
		if (lastSelected.getModel() instanceof MessagesModel)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(PerfClipseActionConstants.ADD_PERIOD);
		if (lastSelected.getModel() instanceof DestinationModel)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(PerfClipseActionConstants.ADD_REPORTER);
		if (lastSelected.getModel() instanceof ReportingModel)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(PerfClipseActionConstants.ADD_VALIDATOR);
		if (lastSelected.getModel() instanceof ValidationModel)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(PerfClipseActionConstants.ATTACH_VALIDATOR);
		if (lastSelected.getModel() instanceof MessageModel)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = actionRegistry.getAction(PerfClipseActionConstants.SWITCH);
		if (lastSelected.getModel() instanceof IEnableable)
			menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
	}
}
