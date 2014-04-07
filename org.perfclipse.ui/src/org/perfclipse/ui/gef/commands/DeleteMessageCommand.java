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

import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.ui.Utils;

public class DeleteMessageCommand extends MessageCommand {

	private MessagesModel messages;
	private MessageModel message;
	private int index;
	private byte[] resourceContents;
	

	public DeleteMessageCommand(MessagesModel messages,
			MessageModel message)
	{
		super("Delete message");
		this.messages = messages;
		this.message = message;
		syncResource = false;
		resourceContents = new byte[0];
	}

	@Override
	public void execute() {
		index = messages.getMessages().getMessage().indexOf(message.getMessage());
		if (syncResource){
			resourceContents = Utils.deleteMessage(message.getMessage().getUri(),
					project, shell);
			if (resourceContents == null)
				resourceContents = new byte[0];
		}
		messages.removeMessage(message.getMessage());
	}

	@Override
	public void undo() {
		if (syncResource)
			Utils.createMessage(message.getMessage().getUri(), resourceContents,
					project, shell);
		messages.addMessage(index, message.getMessage());
	}

	public MessageModel getMessage() {
		return message;
	}
	
	
}
