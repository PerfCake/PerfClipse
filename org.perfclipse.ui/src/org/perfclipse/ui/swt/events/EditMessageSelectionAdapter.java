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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.perfclipse.model.MessageModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.wizards.MessageEditWizard;

/**
 * @author Jakub Knetl
 *
 */
public class EditMessageSelectionAdapter extends
		AbstractCommandSelectionAdapter {

	private TableViewer viewer;
	private MessageModel message;
	private MessageEditWizard wizard;
	
	
	/**
	 * 
	 * @param commands
	 * @param viewer
	 */
	public EditMessageSelectionAdapter(List<Command> commands, TableViewer viewer) {
		super(commands);
		this.viewer = viewer;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		if (! (viewer.getSelection() instanceof IStructuredSelection))
			return;
		
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (!(selection.getFirstElement() instanceof MessageModel))
			return;

		message = (MessageModel) selection.getFirstElement();
		wizard = new MessageEditWizard(message);
		if (Utils.showWizardDialog(wizard) != Window.OK)
			return;
		
		
		super.widgetSelected(e);
		viewer.refresh();
	}

	@Override
	protected Command getCommand() {
		if (wizard.getCommand().isEmpty())
			return null;
		return wizard.getCommand();
	}

}
