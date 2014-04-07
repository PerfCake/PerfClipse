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
import java.io.IOException;
import java.io.InputStream;
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
	 * @param contents Contents of the new created file.
	 * @param project project in which message should be created
	 * @param shell
	 * @return True if the message was successfuly created.
	 */
	public static boolean createMessage(String name, String contents,
			IProject project, Shell shell){
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		if (project == null){
			throw new IllegalArgumentException("Project cannot be null");
		}
		
		IFile message = getMessageFileByName(name, project);
		ByteArrayInputStream in = null;
		try {
			in = new ByteArrayInputStream(contents.getBytes());
			message.create(in, false, null);
		} catch (CoreException e) {
			log.error("Cannot create empty message file", e);
			MessageDialog.openError(shell, "Cannot create message", "Cannot create empty file in messages directory.");
			return false;
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					log.error("Cannot close input stream for newly created message", e);
				}
			}
		}
		
		
		return true;
	}
	
	
	/**
	 * Removes message with given file name in the project
	 * 
	 * @param name message name 
	 * @param shell
	 * 
	 * @return Contents of the deleted file of null if the file was not deleted.
	 */
	public static String deleteMessage(String name, IProject project, Shell shell){
		if (name == null){
			throw new IllegalArgumentException("Name cannot be null");
		}
		
		String contents = null;

		IFile messageFile = getMessageFileByName(name, project);

		if (messageFile == null || !messageFile.exists())
			return null;

		InputStream in = null;
		try{
			in = messageFile.getContents();
			contents = in.toString();
		} catch (CoreException e) {
			log.error("Cannot get contents of the file", e);
			MessageDialog.openError(shell, "Cannot delete file", "Contents of the deleted file cannot be stored.");
			return null;
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					log.error("Cannot close message contents stream.", e);
				}
			}
		}

		try{
			messageFile.delete(true, null);
		} catch (CoreException e) {
			log.error("Cannot delete message file", e);
			MessageDialog.openError(shell, "Cannot delete file", "File containing message cannot be deleted.");
			return null;
		}
		
		return contents;
		
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
	 * Calculate if message should be kept in sync with resource in Messages directory
	 * 
	 * @param name name of the message
	 * @param project project which message should belong to
	 * 
	 * @return True if message should be in sync with messages directory.
	 */
	public static boolean calculateSyncAddMessage(String name, IProject project, Shell shell){
		if (!Utils.isMessageLocal(name))
			return false;

		if (Utils.messageExists(name, project))
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
		if (!Utils.isMessageLocal(oldName) || !Utils.isMessageLocal(newName))
			return false;
		
		if (!Utils.messageExists(oldName, project))
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
		if (!Utils.isMessageLocal(name))
			return false;

		if (!Utils.messageExists(name, project))
			return false;
		
		boolean delete = MessageDialog.openQuestion(shell, "Message has resource file.",
				"There is file in messages folder with name " + name + " which is"
						+ "same as currently deleted message. Do you want to delete"
						+ "this file also?");
		
		return delete;
	}
}
