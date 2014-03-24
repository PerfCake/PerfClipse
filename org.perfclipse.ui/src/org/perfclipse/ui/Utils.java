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

import java.util.ArrayList;
import java.util.List;

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

public class Utils {

	public static final String PERFCLIPSE_STDOUT_CONSOLE = "Perfclipse standard output:";

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
		MessageConsole newConsole = new MessageConsole(PERFCLIPSE_STDOUT_CONSOLE, null);
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
}
