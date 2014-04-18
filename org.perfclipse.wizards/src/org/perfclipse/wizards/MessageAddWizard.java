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

package org.perfclipse.wizards;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.core.ResourceUtils;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.model.HeaderModel;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.core.model.ValidatorRefModel;
import org.perfclipse.wizards.pages.MessagePage;


/**
 * @author Jakub Knetl
 *
 */
/**
 * @author Jakub Knetl
 *
 */
public class MessageAddWizard extends AbstractPerfCakeAddWizard {
	
	static Logger log = Activator.getDefault().getLogger();

	private MessagePage messagePage;
	private Message message;

	//list of validators which will be possibly created and which are not contained in model.
	private List<ValidatorModel> validators;
	
	//Project of the current scenario.
	private IFile scenarioFile;

	/**
	 * 
	 */
	public MessageAddWizard(IFile scenarioFile) {
		super();
		setWindowTitle("Add message");
		this.scenarioFile = scenarioFile;
	}

	@Override
	public boolean performFinish() {
		
		message = new ObjectFactory().createScenarioMessagesMessage();
		message.setMultiplicity(String.valueOf(messagePage.getMultiplicity()));
		message.setUri(messagePage.getUri());
		for (TableItem i : messagePage.getPropertyViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				message.getProperty().add(p.getProperty());
			}
		}
		
		for (TableItem i : messagePage.getHeaderViewer().getTable().getItems()){
			if (i.getData() instanceof HeaderModel){
				HeaderModel h = (HeaderModel) i.getData();
				message.getHeader().add(h.getHeader());
			}
		}

		for (TableItem i : messagePage.getRefViewer().getTable().getItems()){
			if (i.getData() instanceof ValidatorRefModel){
				ValidatorRefModel v = (ValidatorRefModel) i.getData();
				message.getValidatorRef().add(v.getValidatorRef());
			}
		}
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IProject project = scenarioFile.getProject();
		if (project != null){
			if (WizardUtils.calculateSyncAddMessage(message.getUri(), project, shell))
				try {
					ResourceUtils.createMessage(message.getUri(), new byte[0], project);
				} catch (CoreException e) {
					log.warn("Cannot create file representing message", e);
				}
		}
		return true;
	}

	@Override
	public void addPages() {
		messagePage = new MessagePage();
		messagePage.setValidators(validators);
		addPage(messagePage);
		super.addPages();
	}

	public Message getMessage() {
		return message;
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
