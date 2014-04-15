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


import org.eclipse.core.resources.IProject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.AddMessageCommand;
import org.perfclipse.ui.gef.commands.MoveMessageCommand;
import org.perfclipse.ui.gef.parts.PerfCakeEditPartFactory;

public class MessagesListEditPolicy extends AbstractListEditPolicy {

	private MessagesModel model;

	public MessagesListEditPolicy(MessagesModel model) {
		this.model = model;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object type = request.getNewObjectType();
		if (type == Message.class){
			String pathToMessage = getMessagePath();
			if (pathToMessage == null){
				return null;
			}
			Message message = (Scenario.Messages.Message) request.getNewObject();
			message.setUri(pathToMessage);
			
			PerfCakeEditPartFactory factory = (PerfCakeEditPartFactory) getHost().getViewer().getEditPartFactory();
			IProject project = factory.getScenarioFile().getProject();
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

			AddMessageCommand c = new AddMessageCommand(message, model);
			if (project != null && Utils.calculateSyncAddMessage(pathToMessage, project, shell)){
				c.setSyncResource(project, shell);
			}

			return c;
		}
		return null;
	}
	
	@Override
	protected Command createMoveChildCommand(EditPart child, EditPart after) {
		int newIndex = calculateNewIndex(child, after);
		if (newIndex < 0)
			return null;

		return new MoveMessageCommand(model, (MessageModel) child.getModel(), newIndex);
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
