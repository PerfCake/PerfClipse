/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
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


package org.perfclipse.ui.gef.policies;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.commands.AddMessageCommand;

public class MessagesListEditPolicy extends AbstractListEditPolicy {

	private MessagesModel model;
	private ScenarioModel parent;

	public MessagesListEditPolicy(MessagesModel model, ScenarioModel parent) {
		this.model = model;
		this.parent = parent;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		String pathToMessage = getMessagePath();
		Object type = request.getNewObjectType();
		if (type == Message.class && pathToMessage != null){
			Message message = (Scenario.Messages.Message) request.getNewObject();
			if (model.getMessages() == null){
				model.createMessages();
				parent.setMessages(model.getMessages());
			}
			message.setUri(pathToMessage);
			return new AddMessageCommand(message, model);
		}
		return null;
	}
	
	@Override
	protected EditPart getInsertionReference(Request request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected Command createAddCommand(EditPart child, EditPart after) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getMessagePath() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		//TODO: validator
		InputDialog dialog = new InputDialog(shell, "Add Message", "Enter message URL: ", "", null);
		dialog.open();
		if (dialog.getReturnCode() == InputDialog.OK)
			return dialog.getValue();
		
		return null;
	}
}
