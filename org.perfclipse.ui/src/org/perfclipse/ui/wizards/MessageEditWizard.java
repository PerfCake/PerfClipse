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

import org.perfclipse.model.HeaderModel;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ValidatorRefModel;
import org.perfclipse.ui.gef.commands.EditMessageMultiplicityCommand;
import org.perfclipse.ui.gef.commands.EditMessageUriCommand;

/**
 * @author Jakub Knetl
 *
 */
public class MessageEditWizard extends AbstractPerfCakeEditWizard {

	private MessageModel message;
	private List<PropertyModel> properties;
	private List<HeaderModel> headers;
	private List<ValidatorRefModel> refs;
	
	private MessagePage messagePage;
	/**
	 * @param commandLabel
	 * @param message
	 * @param properties
	 * @param headers
	 * @param refs
	 */
	public MessageEditWizard(MessageModel message,
			List<PropertyModel> properties, List<HeaderModel> headers,
			List<ValidatorRefModel> refs) {
		super("Edit Message");
		this.message = message;
		this.properties = properties;
		this.headers = headers;
		this.refs = refs;
	}
	@Override
	public boolean performFinish() {
		if (message.getMessage().getUri() == null
				|| !message.getMessage().getUri().equals(messagePage.getUri())){
			command.add(new EditMessageUriCommand(message, messagePage.getUri()));
		}
		
		if (message.getMessage().getMultiplicity() == null ||
				! message.getMessage().getMultiplicity().equals(messagePage.getMultiplicity())){
			String multiplicity = String.valueOf(messagePage.getMultiplicity());
			command.add(new EditMessageMultiplicityCommand(message, multiplicity));
		}
		return super.performFinish();
	}
	@Override
	public void addPages() {
		messagePage = new MessagePage(message, headers, properties, refs);
		addPage(messagePage);
		super.addPages();
	}
}
