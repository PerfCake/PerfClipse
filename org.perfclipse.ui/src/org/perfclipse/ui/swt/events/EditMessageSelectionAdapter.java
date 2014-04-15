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
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.ui.wizards.MessageEditWizard;

/**
 * @author Jakub Knetl
 *
 */
public class EditMessageSelectionAdapter extends
		AbstractEditCommandSelectionAdapter {

	
	//List of validators which will be created invoked by Add message wizard.
	private List<ValidatorModel> validators;
	
	
	/**
	 * 
	 * @param commands
	 * @param viewer
	 */
	public EditMessageSelectionAdapter(List<Command> commands, TableViewer viewer) {
		super(commands, viewer);
	}


	@Override
	protected AbstractPerfCakeEditWizard createWizard(IStructuredSelection selection) {
		MessageEditWizard wizard = new MessageEditWizard((MessageModel) selection.getFirstElement());
		wizard.setValidators(validators);
		return wizard;
	}

	/**
	 * Initializes list of validators to which new validators (created by wizard) will be added.
	 * These validator are not currently in scenario, so it will be stored in this list.
	 * @param validators non null list of validators (may be empty)
	 */
	public void setValidators(List<ValidatorModel> validators) {
		this.validators = validators;
	}
}
