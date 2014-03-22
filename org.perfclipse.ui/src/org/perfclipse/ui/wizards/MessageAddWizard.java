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

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.PropertyModel;


/**
 * @author Jakub Knetl
 *
 */
/**
 * @author Jakub Knetl
 *
 */
public class MessageAddWizard extends AbstractPerfCakeAddWizard {

	private MessagePage messagePage;
	private Message message;

	@Override
	public boolean performFinish() {
		
		message = new ObjectFactory().createScenarioMessagesMessage();
		message.setMultiplicity(String.valueOf(messagePage.getMultiplicity()));
		message.setUri(messagePage.getUri());
		for (PropertyModel p : messagePage.getProperty()){
			message.getProperty().add(p.getProperty());
		}

		return true;
	}

	@Override
	public void addPages() {
		messagePage = new MessagePage();
		addPage(messagePage);
		super.addPages();
	}

	public Message getMessage() {
		return message;
	}
}
