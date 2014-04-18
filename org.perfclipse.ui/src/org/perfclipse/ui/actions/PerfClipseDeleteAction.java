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

package org.perfclipse.ui.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.core.commands.DeleteValidatorCommand;

/**
 * Delete action which understands PerfClipse commands and show confirmation dialog
 * if some component will be edited automatically in order to handle delete command.
 * 
 * @author Jakub Knetl
 *
 */
public class PerfClipseDeleteAction extends DeleteAction {

	/**
	 * @param part
	 */
	public PerfClipseDeleteAction(IWorkbenchPart part) {
		super(part);
	}

	/**
	 * Checks whether the action need to be confirmed.
	 * If action is not confirmed then it does nothing.
	 */
	@Override
	public void run() {
		Command c = (CompoundCommand) createDeleteCommand(getSelectedObjects());
		if (calculateShowDialog()){
			if (showDialog()){
				execute(c);
			}
		} else {
			execute(c);
		}

	}

	/**
	 * Shows dialog and returns true if user confirm dialog.
	 * 
	 * @return True if command should be executed. Else otherwise 
	 */
	protected boolean confirmed() {

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		return MessageDialog.openConfirm(shell, "Delete", "Are you sure you want to delete selected components?");
	}
	
	/**
	 * Calculates if confirm dialog will be shown.
	 * @return true if command causes edit of some components which is not selected for delete.
	 */
	protected boolean calculateShowDialog(){
		CompoundCommand command = (CompoundCommand) createDeleteCommand(getSelectedObjects());
		
		if (command != null){
			for (Object c : command.getCommands()){
				if (c instanceof CompoundCommand){
					// delete command for one selected component
					CompoundCommand deleteCommand = (CompoundCommand) c;
					if (!deleteCommand.isEmpty()){
						// if current command is deletValidaotr command and modifies messages
						if (deleteCommand.getCommands().get(0) instanceof DeleteValidatorCommand &&
								deleteCommand.getCommands().size() > 1){
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Show confirm dialog.
	 * @return {@link MessageDialog#openConfirm(Shell, String, String)}
	 */
	protected boolean showDialog(){
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		return MessageDialog.openConfirm(shell , "Warning - some message will be modified",
				"There are messages which references valdiator selected to delete."
				+ " Deletion of selected validator will cause removing reference"
				+ " to this validator of the messages. Do you wish to continue?");
	}
	
}
