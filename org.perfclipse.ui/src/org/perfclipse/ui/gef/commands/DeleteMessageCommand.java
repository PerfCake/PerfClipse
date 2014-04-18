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

import org.eclipse.gef.commands.Command;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.MessagesModel;

public class DeleteMessageCommand extends Command {

	private MessagesModel messages;
	private MessageModel message;
	private int index;
	

	public DeleteMessageCommand(MessagesModel messages,
			MessageModel message)
	{
		super("Delete message");
		this.messages = messages;
		this.message = message;
	}

	@Override
	public void execute() {
		index = messages.getMessages().getMessage().indexOf(message.getMessage());
		messages.removeMessage(message.getMessage());
	}

	@Override
	public void undo() {
		messages.addMessage(index, message.getMessage());
	}

	public MessageModel getMessage() {
		return message;
	}
	
	
}
