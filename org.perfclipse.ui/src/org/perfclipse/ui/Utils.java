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
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		
		IFile message = getMessageFileByName(name, project);
		if (message == null)
			return false;
		return message.exists();
	}
	
	/**
	 * Finds message file resource for given message specified by name.
	 * @param name
	 * @param project
	 * @return {@link IFolder#getFile(String)}.
	 */
	private static IFile getMessageFileByName(String name, IProject project){
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		
		IFolder messagesFolder = project.getFolder(PerfClipseConstants.MESSAGE_DIR_NAME);
		if (messagesFolder == null || !messagesFolder.exists())
			return null;
			
		IFile message = messagesFolder.getFile(name);

		return message;
	}
	
	/**
	 * Creates message with file name in messages directory of the project
	 * @param name name of the message file
	 * @param project project in which message should be created
	 * @param shell
	 * @return True if the message was successfuly created.
	 */
	public static boolean createMessage(String name, IProject project, Shell shell){
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		
		IFile message = getMessageFileByName(name, project);
		String content = "";
		try {
			//TODO: progressmonitor
			message.create(new ByteArrayInputStream(content.getBytes()), false, null);
		} catch (CoreException e) {
			log.error("Cannot create empty message file", e);
			MessageDialog.openError(shell, "Cannot create message", "Cannot create empty file in messages directory.");
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Removes message with given file name in the project
	 * 
	 * @param message File with the message 
	 * @param shell
	 * 
	 * @return true if the message was successfully deleted
	 */
	public static boolean deleteMessage(IFile message, Shell shell){
		if (message == null){
			throw new IllegalArgumentException("Name cannot be null");
		}

		try {
			//TODO: progressmonitor
			message.delete(true, null);
		} catch (CoreException e) {
			log.error("Cannot delete message file", e);
			MessageDialog.openError(shell, "Cannot delete file", "File containing message cannot be deleted.");
			return false;
		}
		
		
		return true;
		
	}
	
	/**
	 * Change message resource name.
	 * @param oldName
	 * @param newName
	 * @param project
	 * @param shell
	 * @return true if message was moved (renamed).
	 */
	public static boolean moveMessage(String oldName, String newName, IProject project, Shell shell){
		if (oldName == null || newName == null){
			throw new IllegalArgumentException("Name cannot be null.");
		}
	
		IFile file = getMessageFileByName(oldName, project);
		if (file == null)
			return false;
		
		IPath newPath = new Path(newName);
		
		try {
			//TODO: progressmonitor
			file.move(newPath, false, null);
		} catch (CoreException e) {
			log.error("Cannot move message resource", e);
			MessageDialog.openError(shell, "Cannot move resource",
					"Cannot move message resource");
		}
		
		return true;
	}
	
	/**
	 * Checks if the message is local and if it does not exits. THen asks user if
	 * he wants to create message resource and creates resource.
	 * @param name name of the message
	 * @param project project in which message should be created.
	 * @param shell
	 * @return True if message resource exists (It was created or it existed before calling this method). 
	 * Return false if message is not present and it was not created.
	 */
	public static boolean handleCreateMessage(String name, IProject project, Shell shell){
		if (!Utils.isMessageLocal(name))
			return false;

		if (Utils.messageExists(name, project))
			return true;
		
		boolean create = MessageDialog.openQuestion(shell, "Message does not exits.",
				"Message has local path and does not exists in the messages directory. "
				+ "Do you want to create empty file " + name + " in Messages directory?");
		if (create){
			Utils.createMessage(name, project, shell);
			return true;
		}

		return false;
	}
	
	/**
	 * Conditionally moves message resource to different file.
	 * 
	 * @param oldName Name of the resource
	 * @param project  Project in which resource is located
	 * @param shell
	 * @return True if new resource exists and old one is not.
	 */
	public static boolean handleMoveMessage(String oldName, String newName, IProject project, Shell shell){
		if (!Utils.isMessageLocal(oldName) || !Utils.isMessageLocal(newName))
			return false;
		
		if (!Utils.messageExists(oldName, project))
			return false;
		
		boolean move = MessageDialog.openQuestion(shell, "Move message file",
				"Message has associated file resource in messages directory. Do"
				+ "you want to rename the file to be in sync with message object?");
		
		if (move){
			Utils.moveMessage(oldName, newName, project, shell);
			return true;
		}
		return false;
	}
	
	/**
	 * Check if local resource exists in the project for the message. If it does it
	 * conditionally (shows question dialog) deletes resource.
	 * @param name Name of the message resource
	 * @param project project in which resource would be placed
	 * @param shell 
	 * @return True if the resource is not present after this method (it means it was deleted
	 * by this method or ti was not exists before this method was called). False otherwise
	 */
	public static boolean handleDeleteMessage(String name, IProject project, Shell shell){
		if (!Utils.isMessageLocal(name))
			return true;

		if (!Utils.messageExists(name, project))
			return true;
		
		boolean delete = MessageDialog.openQuestion(shell, "Message has resource file.",
				"There is file in messages folder with name " + name + " which is"
						+ "same as currently deleted message. Do you want to delete"
						+ "this file also?");
		if (delete){
			IFile message = getMessageFileByName(name, project);
			Utils.deleteMessage(message, shell);
			return true;
		}

		return false;
	}
}
