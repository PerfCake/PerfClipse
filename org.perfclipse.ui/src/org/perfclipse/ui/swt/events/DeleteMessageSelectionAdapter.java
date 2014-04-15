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

package org.perfclipse.ui.swt.events;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.commands.DeleteMessageCommand;

/**
 * @author Jakub Knetl
 *
 */
public class DeleteMessageSelectionAdapter extends
		AbstractDeleteCommandSelectionAdapter {

	private MessagesModel messages;
	private MessageModel message;
	
	
	//scenario file
	private IFile scenarioFile;
	
	/**
	 * @param commands
	 * @param viewer
	 * @param messages
	 * @param scenarioFile
	 */
	public DeleteMessageSelectionAdapter(List<Command> commands,
			TableViewer viewer, MessagesModel messages, IFile scenarioFile) {
		super(commands, viewer);
		this.messages = messages;
		this.scenarioFile = scenarioFile;
	}

	@Override
	protected Command getCommand() {
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		boolean deleteResource = Utils.calculateDeleteMessage(
				message.getMessage().getUri(), scenarioFile.getProject(), shell);
		if (message != null && messages != null){
			DeleteMessageCommand c = new DeleteMessageCommand(messages, message);

			return c;
		}
		// if command was not executed since the wizard does not use commands then
		// we need to sync messages resource
		if (deleteResource)
			Utils.deleteMessage(message.getMessage().getUri(), scenarioFile.getProject(), shell);
		
		return null;
	}

	@Override
	public void handleDeleteData(Object element) {
		message = (MessageModel) element;
	}

	public IFile getScenarioFile() {
		return scenarioFile;
	}

	public void setScenarioFile(IFile scenarioFile) {
		this.scenarioFile = scenarioFile;
	}

	
}
