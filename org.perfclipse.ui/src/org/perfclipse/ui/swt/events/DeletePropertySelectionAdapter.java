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

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.perfclipse.model.IPropertyContainer;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.commands.DeletePropertyCommand;

/**
 * Selection Adapter which handles delete in PropertyViewer
 * @author Jakub Knetl
 *
 */
public class DeletePropertySelectionAdapter extends SelectionAdapter {

	private TableViewer viewer;
	private List<Command> commands;
	private IPropertyContainer propertyContainer;

	public DeletePropertySelectionAdapter(TableViewer viewer,
			List<Command> commands, IPropertyContainer propertyContainer) {
		this.viewer = viewer;
		this.commands = commands;
		this.propertyContainer = propertyContainer;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {

		ISelection selection = viewer.getSelection();
		if (selection instanceof IStructuredSelection){
			@SuppressWarnings("unchecked")
			Iterator<Object> it = ((IStructuredSelection) selection).iterator();
			while (it.hasNext()){
				PropertyModel property = (PropertyModel) it.next();
				Command c = new DeletePropertyCommand(propertyContainer, property);
				c.execute();
				commands.add(c);
				viewer.remove(property);
			}
			
		}
	}
}
