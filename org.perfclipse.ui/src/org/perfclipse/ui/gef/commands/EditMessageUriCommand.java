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
import org.perfclipse.ui.Utils;

public class EditMessageUriCommand extends MessageCommand {

	private String newUri;
	private String oldUri;
	private MessageModel messageModel;

	public EditMessageUriCommand(MessageModel message, String newUri) {
		super("rename message");
		this.messageModel = message;
		this.oldUri = message.getMessage().getUri();
		this.newUri = newUri;
		syncResource = false;
	}
	
	@Override
	public void execute() {
		if (syncResource)
			Utils.moveMessage(oldUri, newUri, project, shell);
		messageModel.setUri(newUri);
	}

	@Override
	public void undo() {
		if (syncResource)
			Utils.moveMessage(newUri, oldUri, project, shell);
		messageModel.setUri(oldUri);
	}

}
