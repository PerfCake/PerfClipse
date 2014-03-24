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

import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.ui.gef.commands.EditDestinationEnabledCommand;
import org.perfclipse.ui.gef.commands.EditDestinationTypeCommand;
import org.perfclipse.ui.wizards.pages.DestinationPage;

/**
 * @author Jakub Knetl
 *
 */
public class DestinationEditWizard extends AbstractPerfCakeEditWizard {

	private DestinationModel destination;
	private DestinationPage destinationPage;
	
	/**
	 * 
	 * @param destination
	 */
	public DestinationEditWizard(DestinationModel destination) {
		super("Edit destination");
		this.destination = destination;
	}
	@Override
	public boolean performFinish() {
		Destination d = destination.getDestination();
		if (!(d.getClazz().equals(destinationPage.getDestinationType()))){
			getCommand().add(new EditDestinationTypeCommand(destination, destinationPage.getDestinationType()));
		}
		if (d.isEnabled() != destinationPage.getEnabled()){
			getCommand().add(new EditDestinationEnabledCommand(destination, destinationPage.getEnabled()));
		}
		
		return super.performFinish();
	}
	@Override
	public void addPages() {
		destinationPage = new DestinationPage(destination);
		addPage(destinationPage);
		super.addPages();
	}
	
	
	

}
