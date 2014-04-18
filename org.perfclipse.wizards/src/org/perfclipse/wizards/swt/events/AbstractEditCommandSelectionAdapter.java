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

package org.perfclipse.wizards.swt.events;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.perfclipse.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.wizards.WizardUtils;

/**
 * @author Jakub Knetl
 *
 */
public abstract class AbstractEditCommandSelectionAdapter extends
		AbstractCommandSelectionAdapter {

	private AbstractPerfCakeEditWizard wizard;
	
	/**
	 * @param commands
	 * @param viewer
	 */
	public AbstractEditCommandSelectionAdapter(List<Command> commands,
			TableViewer viewer) {
		super(commands, viewer);
	}


	
	/**
	 * this method checks if some element is selected if it is, then it calls
	 * createWizard(element) and shows this wizard. If wizard is closed properly
	 * then it creates command and call refresh on viewer
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (! (getViewer().getSelection() instanceof IStructuredSelection))
			return;
		
		IStructuredSelection selection = (IStructuredSelection) getViewer().getSelection();

		if (selection.getFirstElement() == null)
			return;

		wizard = createWizard(selection);
		if (WizardUtils.showWizardDialog(wizard) != Window.OK)
			return;
		
		
		super.widgetSelected(e);
		getViewer().refresh(selection.getFirstElement());;
	}

	/**
	 * Returns wizard to show for editing.
	 * @param selection
	 * @return Returns wizard to show for editing.
	 */
	protected abstract AbstractPerfCakeEditWizard createWizard(IStructuredSelection selection);



	@Override
	protected Command getCommand() {
		if (wizard.getCommand().isEmpty())
			return null;
		return wizard.getCommand();
	}

}
