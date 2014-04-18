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

/**
 * Command for changing message position in the list
 * @author Jakub Knetl
 *
 */
public class MoveMessageCommand extends Command {
	
	private MessagesModel messages;
	private int newIndex;
	private int oldIndex;
	private MessageModel message;

	/**
	 * @param messages
	 * @param newIndex
	 */
	public MoveMessageCommand(MessagesModel messages, MessageModel message,
			int newIndex) {
		super("Move message");
		this.messages = messages;
		this.newIndex = newIndex;
		this.message = message;
		oldIndex = messages.getMessages().getMessage().indexOf(message);
	}

	@Override
	public void execute() {
		messages.removeMessage(message.getMessage());
		messages.addMessage(newIndex, message.getMessage());
	}

	@Override
	public void undo() {
		messages.removeMessage(message.getMessage());
		messages.addMessage(oldIndex, message.getMessage());
	}
}
