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

package org.perfclipse.ui.swt.jface;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

/**
 * @author Jakub Knetl
 *
 */
public abstract class AbstractCommandEditingSupport extends EditingSupport {

	private CellEditor cellEditor;
	private List<Command> commands;

	public AbstractCommandEditingSupport(TableViewer viewer, List<Command> command) {
		super(viewer);
		this.cellEditor = new TextCellEditor(viewer.getTable()); 
		this.commands = command;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	/**
	 * Checks if the commands list is not null. If it is not, then its add given
	 * command to the commands list.
	 * 
	 * @param command
	 */
	protected void addCommand(Command command){
		if (commands != null){
			commands.add(command);
		}
	}
	
	
	/**
	 * Calls getCommand to obtain command then executes this command, adds the command
	 * to the command list and updates viewer.
	 * 
	 */
	@Override
	protected void setValue(Object element, Object value) {
		Command command = getCommand(element, value);
		if (command != null){
			command.execute();
			addCommand(command);
		
			getViewer().update(element, null);
		}
		
	}

	/**
	 * Returns command to be executed
	 */
	protected abstract Command getCommand(Object element, Object value);
	
}
