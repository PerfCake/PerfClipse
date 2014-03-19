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
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;
import org.perfclipse.ui.actions.EditDialogAction;

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
	        action = actionRegistry.getAction(EditDialogAction.SHOW_EDIT_DIALOG);
	        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
	}
}
