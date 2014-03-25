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
import org.eclipse.swt.events.SelectionEvent;

/**
 * Abstract selection adpater for handling delete command in TableViewer.
 * @author Jakub Knetl
 *
 */
public abstract class AbstractDeleteCommandSelectionAdapter extends
		AbstractCommandSelectionAdapter {

	public AbstractDeleteCommandSelectionAdapter(List<Command> commands,
			TableViewer viewer) {
		super(commands, viewer);
	}

	/**
	 * Iterates through selected elements in the viewer and for each of them it calls
	 * handleDeleteData() and handleCommand() in this order.
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		ISelection selection = getViewer().getSelection();
		if (selection instanceof IStructuredSelection){
			Iterator<?> it = ((IStructuredSelection) selection).iterator();
			while (it.hasNext()){
				Object data = it.next();
				handleDeleteData(data);
				handleCommand();
				getViewer().remove(data);
			}
			
		}
	}
	
	/**
	 * This method is called for every element which is selected for delete.
	 * It is called before handleCommand.
	 * 
	 * @param element One of the selected elements in the viewer.
	 */
	public abstract void handleDeleteData(Object element);

}
