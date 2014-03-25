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

package org.perfclipse.ui.swt.events;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.perfclipse.model.IPropertyContainer;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.commands.DeletePropertyCommand;

/**
 * Selection Adapter which handles delete in PropertyViewer
 * @author Jakub Knetl
 *
 */
public class DeletePropertySelectionAdapter extends 
AbstractDeleteCommandSelectionAdapter {

	private IPropertyContainer propertyContainer;
	private PropertyModel property;

	public DeletePropertySelectionAdapter(List<Command> commands,
			TableViewer viewer, IPropertyContainer propertyContainer) {
		super(commands, viewer);
		this.propertyContainer = propertyContainer;
	}
	
	@Override
	protected Command getCommand() {
		if (propertyContainer != null){
			return new DeletePropertyCommand(propertyContainer, property);
		}
		return null;
	}

	@Override
	public void handleDeleteData(Object element) {
		property = (PropertyModel) element;
	}
}
