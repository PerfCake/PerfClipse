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

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionEvent;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfclipse.model.MessagesModel;

/**
 * @author Jakub Knetl
 *
 */
public class AddMessageSelectionAdapter extends AbstractCommandSelectionAdapter {

	private TableViewer viewer;
	private MessagesModel messages;
	private Message message;
	
	
	
	/**
	 * @param commands
	 * @param viewer
	 * @param messages
	 */
	public AddMessageSelectionAdapter(List<Command> commands,
			TableViewer viewer, MessagesModel messages) {
		super(commands);
		this.viewer = viewer;
		this.messages = messages;
	}

	

	@Override
	public void widgetSelected(SelectionEvent e) {
		super.widgetSelected(e);
	}



	@Override
	protected Command getCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
