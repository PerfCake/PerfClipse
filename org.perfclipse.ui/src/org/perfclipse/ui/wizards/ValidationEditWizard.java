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

import java.util.ArrayList;
import java.util.List;

import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ValidationModel;
import org.perfclipse.ui.wizards.pages.ValidationPage;

/**
 * @author Jakub Knetl
 *
 */
public class ValidationEditWizard extends AbstractPerfCakeEditWizard {

	private ValidationModel validation;
	private ValidationPage validationPage;

	/**
	 * @param validation
	 */
	public ValidationEditWizard(ValidationModel validation) {
		super("Edit Validation");
		this.validation = validation;
	}
	@Override
	public void addPages() {
		validationPage = new ValidationPage(validation);
		addPage(validationPage);
		validationPage.setMessages(parseMessages());
		super.addPages();
	}
	private List<MessageModel> parseMessages() {
		MessagesModel messagesModel = validation.getMapper().getMessagesModel();
		List<MessageModel> messages = new ArrayList<>();
		for (Message m : messagesModel.getMessages().getMessage()){
			messages.add((MessageModel) validation.getMapper().getModelContainer(m));
		}
		
		return messages;
	}
	
}
