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

package org.perfclipse.ui.wizards;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.EditMessageMultiplicityCommand;
import org.perfclipse.ui.gef.commands.EditMessageUriCommand;
import org.perfclipse.ui.wizards.pages.MessagePage;

/**
 * @author Jakub Knetl
 *
 */
public class MessageEditWizard extends AbstractPerfCakeEditWizard {

	private MessageModel message;
	
	private MessagePage messagePage;
	
	//List of validators which will be created invoked by Add message wizard.
	private List<ValidatorModel> validators;
	
	//Project of the current scenario.
	private IFile scenarioFile;
	
	/**
	 * @param commandLabel
	 * @param message
	 * @param properties
	 * @param headers
	 * @param refs
	 */
	public MessageEditWizard(MessageModel message, IFile scenarioFile) {
		super("Edit Message");
		this.message = message;
		this.scenarioFile = scenarioFile;
	}
	@Override
	public boolean performFinish() {
		boolean uriChanged = false;
		if (message.getMessage().getUri() == null
				|| !message.getMessage().getUri().equals(messagePage.getUri())){
			uriChanged = true;
			getCommand().add(new EditMessageUriCommand(message, messagePage.getUri()));
		}
		
		if (message.getMessage().getMultiplicity() == null ||
				! message.getMessage().getMultiplicity().equals(messagePage.getMultiplicity())){
			String multiplicity = String.valueOf(messagePage.getMultiplicity());
			getCommand().add(new EditMessageMultiplicityCommand(message, multiplicity));
		}
		
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IProject project = scenarioFile.getProject();
		if (uriChanged && project != null)
			Utils.handleCreateMessage(message.getMessage().getUri(), project, shell);
		return super.performFinish();
	}
	@Override
	public void addPages() {
		messagePage = new MessagePage(message);
		messagePage.setValidators(validators);
		addPage(messagePage);
		super.addPages();
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
