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

package org.perfclipse.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.perfcake.common.PeriodType;
import org.perfclipse.core.ResourceUtils;

public class WizardUtils {

	/**
	 * Return new instance of GridData used for TableViewer
	 * @return
	 */
	public static GridData getTableViewerGridData(){
		GridData data =  new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = 120;
		return data;
	}
	
	/**
	 * @return List of String reperesentation of period Type in PerfCake
	 * 
	 */
	public static List<String> getPeriodTypes(){
		List<String> list = new ArrayList<>();
		for (PeriodType p : PeriodType.values()){
			list.add(p.toString().toLowerCase());
		}
		
		return list;
	}
	
	
	/**
	 * Shows wizard wrapped as WizardDialog.
	 * @return Return value of dialog.open()
	 */
	
	public static int showWizardDialog(IWizard wizard){
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		
		return dialog.open();
	}
	
	/**
	 * Calculate if message should be kept in sync with resource in Messages directory
	 * 
	 * @param name name of the message
	 * @param project project which message should belong to
	 * 
	 * @return True if message should be in sync with messages directory.
	 */
	public static boolean calculateSyncAddMessage(String name, IProject project, Shell shell){
		if (!ResourceUtils.isMessageLocal(name))
			return false;

		if (ResourceUtils.messageExists(name, project))
			return false;
		
		boolean create = MessageDialog.openQuestion(shell, "Message does not exits.",
				"Message has local path and does not exists in the messages directory. "
				+ "Do you want to create empty file " + name + " in Messages directory?");
		
		return create;
	}
	
	/**
	 * Calculates if message location should be kept in sync with Messages directory
	 * 
	 * @param oldName Name of the resource
	 * @param project  Project in which resource is located
	 * @param shell
	 * @return True if message should be moved.
	 */
	public static boolean calculateMoveMessage(String oldName, String newName, IProject project, Shell shell){
		if (!ResourceUtils.isMessageLocal(oldName) || !ResourceUtils.isMessageLocal(newName))
			return false;
		
		if (!ResourceUtils.messageExists(oldName, project))
			return false;
		
		boolean move = MessageDialog.openQuestion(shell, "Move message file",
				"Message has associated file resource in messages directory. Do"
				+ "you want to rename the file to be in sync with message object?");
		
		return  move;
	}
	
	/**
	 * Calculates if message resource should be deleted.
	 * 
	 * @param name Name of the message resource
	 * @param project project in which resource would be placed
	 * @param shell 
	 * 
	 * @return True if message should be deleted. Else if it does not.
	 */
	public static boolean calculateDeleteMessage(String name, IProject project, Shell shell){
		if (!ResourceUtils.isMessageLocal(name))
			return false;

		if (!ResourceUtils.messageExists(name, project))
			return false;
		
		boolean delete = MessageDialog.openQuestion(shell, "Message has resource file.",
				"There is file in messages folder with name " + name + " which is"
						+ "same as currently deleted message. Do you want to delete"
						+ "this file also?");
		
		return delete;
	}
}
