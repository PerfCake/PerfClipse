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

package org.perfclipse.wizards.swt.events;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * Selection Adapter which has support for GEF commands.
 * @author Jakub Knetl
 *
 */
public abstract class AbstractCommandSelectionAdapter extends SelectionAdapter {


	private List<Command> commands;
	private TableViewer viewer;

	/**
	 *
	 * @param commands Commands created as response to user actions. This commands
	 * are automatically executed in handleCommand() method.
	 * @param viewer Table viewer instance for updating view
	 */
	public AbstractCommandSelectionAdapter(List<Command> commands, TableViewer viewer) {
		if (viewer == null){
			throw new IllegalArgumentException("Viewer must not be null");
		}
		this.viewer = viewer;
		this.commands = commands;
	}
	
	/**
	 * @return Returns true if the instance has assigned command List. False otherwise.
	 */
	public boolean hasCommands(){
		if (commands != null){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Calls getCommand(). If null is returned from getCommand() then does nothing.
	 * Otherwise it executes returned command and calls addCommand()
	 */
	protected void handleCommand(){
		Command c = getCommand();
		if (c != null){
			c.execute();
			addCommand(c);
		}
	}

	/**
	 * if field commands is not null then add argument c to commands list
	 * Otherwise does nothing.
	 * @param c Command to be added
	 */
	protected void addCommand(Command c) {
		if (hasCommands()){
			commands.add(c);
		}
	}

	/**
	 * This method should decide if new command should be created according to user
	 * action.
	 * @return Command corresponding to user action. Or null if command is not needed.
	 */
	protected abstract Command getCommand();
	

	/**
	 * Calls handleCommand()
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		handleCommand();
	}

	/**
	 * Calls handleCommand()
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		handleCommand();
	}

	public TableViewer getViewer() {
		return viewer;
	}
}
