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

package org.perfclipse.core.commands;

import org.eclipse.core.runtime.CoreException;
import org.perfcake.model.Scenario;
import org.perfclipse.core.Activator;
import org.perfclipse.core.ResourceUtils;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.model.MessagesModel;

public class AddMessageCommand extends ResourceCommand {

	static Logger log = Activator.getDefault().getLogger();

	private Scenario.Messages.Message newMessage;
	private MessagesModel messages;
	private byte[] contents = new byte[0];
	public AddMessageCommand(Scenario.Messages.Message newMessage, MessagesModel messages) {
		super("Add message");
		this.newMessage = newMessage;
		this.messages = messages;
		syncResource = false;
	}

	@Override
	public void execute() {
		messages.addMessage(newMessage);
		if (syncResource)
			try {
				ResourceUtils.createMessage(newMessage.getUri(), contents, project);
			} catch (CoreException e) {
				log.error("Cannot create message file", e);
			}
	}

	@Override
	public void undo() {
		messages.removeMessage(newMessage);
	}
}
