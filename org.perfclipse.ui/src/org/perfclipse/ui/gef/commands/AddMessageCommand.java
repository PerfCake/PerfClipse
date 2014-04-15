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

package org.perfclipse.ui.gef.commands;

import org.perfcake.model.Scenario;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.ui.Utils;

public class AddMessageCommand extends ResourceCommand {

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
			Utils.createMessage(newMessage.getUri(), contents, project, shell);
	}

	@Override
	public void undo() {
		messages.removeMessage(newMessage);
		if(syncResource){
			contents = Utils.deleteMessage(newMessage.getUri(), project, shell);
			if (contents == null)
				contents = new byte[0];
		}
	}
}
