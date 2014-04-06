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

package org.perfclipse.ui;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.perfcake.common.PeriodType;
import org.perfclipse.logging.Logger;

public class Utils {

	
	static final Logger log = Activator.getDefault().getLogger();

	/**
	 * Find eclipse console with given name. If not found then create new
	 * console with given name and append it to console view.
	 * @param name name of console
	 * @return console with given name
	 */
	public static MessageConsole findConsole(String name){
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager consoleManager = plugin.getConsoleManager();
		IConsole[] existing = consoleManager.getConsoles();
		for (IConsole console: existing){
			if (name.equals(console.getName())){
				return (MessageConsole) console;
			}
		}
		
		//Console was not found. Create new one.
		MessageConsole newConsole = new MessageConsole(PerfClipseConstants.PERFCLIPSE_STDOUT_CONSOLE, null);
		consoleManager.addConsoles(new IConsole[]{newConsole});
		return newConsole;
		
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
	 * Return new instance of GridData used for TableViewer
	 * @return
	 */
	public static GridData getTableViewerGridData(){
		GridData data =  new GridData(SWT.FILL, SWT.FILL, true, true);
		data.minimumWidth = 120;
		return data;
	}
	
	/**
	 * Check if message is local with no path. It means it contains only filename
	 * and thus it should be placed in the messages directory
	 * @param url url to be checked
	 * @return
	 */
	public static boolean isMessageLocal(String url){
		if (url == null){
			throw new IllegalArgumentException("url cannot be null");
		}
		// if there is no slash than url is just name
		if (url.indexOf("/") < 0)
			return true;
		
//		//check if url is not name like ./name.msg
//		if (url.length() > 2){
//				//check if message starts with ./
//				if (url.charAt(0) == '.' && url.charAt(1) == '/'){
//					//check if there is no other slash than on the second position 
//					if (url.indexOf("/", 2) < 0)
//						return true;
//			}
//		}
		
		return false;
	}
	
	/**
	 * Checks if message with given name exits in the project's messages directory.
	 * @param name name of the message
	 * @param project name of the project
	 * @return true if message exists in messages directory of the project. Else otherwise
	 */
	public static boolean messageExists(String name, IProject project){
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		IFolder messagesFolder = project.getFolder(PerfClipseConstants.MESSAGE_DIR_NAME);
		
		IFile message = messagesFolder.getFile(name);
		return message.exists();
	}
	
	/**
	 * Creates message with file name in messages directory of the project
	 * @param name name of the message file
	 * @param project project in which message should be created
	 */
	public static void createMessage(String name, IProject project){
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		IFolder messagesFolder = project.getFolder(PerfClipseConstants.MESSAGE_DIR_NAME);
		
		if (!messagesFolder.exists()){
			throw new IllegalArgumentException("Project does not contain messages folder.");
		}
		
		IFile message = messagesFolder.getFile(name);
		String content = "";
		try {
			message.create(new ByteArrayInputStream(content.getBytes()), false, null);
		} catch (CoreException e) {
			log.error("Cannot create empty message file", e);
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openError(shell, "Cannot create message", "Cannot create empty file in messages directory.");
		}
	}
}
