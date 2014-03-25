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

package org.perfclipse.ui.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.wizard.Wizard;

/**
 * Implementation of Wizard which has API for managing Eclipse GEF commands 
 * 
 * @author Jakub Knetl
 */
public abstract class AbstractPerfCakeEditWizard extends Wizard {

	/**
	 * List of commands which wizard pages creates
	 */
	private List<Command> editingSupportCommands;
	private CompoundCommand command;

	/**
	 * 
	 * @param title Title is used as window title and also for command label.
	 */
	public AbstractPerfCakeEditWizard(String title) {
		super();
		setWindowTitle(title);
		editingSupportCommands = new ArrayList<>();
		command = new CompoundCommand(title);
	}
	
	/**
	 * Undo executed editing support commands before cancel
	 */
	@Override
	public boolean performCancel() {
		undoEditingSupportCommands();
		return super.performCancel();
	}
	
	

	/**
	 * Undo all direct editing support commands and adds these command to
	 * global command. Then return true.
	 */
	@Override
	public boolean performFinish() {
		undoEditingSupportCommands();

		for (Command c : editingSupportCommands){
			command.add(c);
		}

		return true;
	}

	/**
	 * Add commond to list of editing support commands.
	 * @param command
	 */
	public void addEditingSupportCommand(Command command){
		editingSupportCommands.add(command);
	}
	
	/**
	 * Iterates through command list in reverse order and undo every command.
	 */
	protected void undoEditingSupportCommands(){
		ListIterator<Command> it = 
				editingSupportCommands.listIterator(editingSupportCommands.size());
		while (it.hasPrevious()){
			it.previous().undo();
		}
	}
	
	public CompoundCommand getCommand(){
		return command;
	}

	public List<Command> getEditingSupportCommands() {
		return editingSupportCommands;
	}
}
