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

package org.perfclipse.ui.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.handlers.HandlerUtil;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;


public class RunHandler extends AbstractHandler {
	
	private final static Logger LOGGER = Logger.getLogger(RunHandler.class .getName()); 

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Shell shell = HandlerUtil.getActiveShell(event);
		IFile file = Utils.getFirstSelectedFile(event);
		if (file != null){
			runScenario(file, shell);
		}
		else{
			MessageDialog.openError(shell, "Cannot open scenario", "Scenario has to be xml file which is valid"
					+ " according to given xml schema");
		}
		return null;
		
	}

	private void runScenario(IFile file, Shell shell) {
		//redirect System.out to Eclipse console
		MessageConsole perfclipseConsole = Utils.findConsole(Utils.PERFCLIPSE_STDOUT_CONSOLE);
		
		//show console view
		try{
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	    IWorkbenchPage page = win.getActivePage();
	    String id = IConsoleConstants.ID_CONSOLE_VIEW;
		    try {
				IConsoleView view = (IConsoleView) page.showView(id);
				view.display(perfclipseConsole);
			} catch (PartInitException e1) {
				LOGGER.warning("Cannot show console view.");
			}
		} catch (NullPointerException e){
			LOGGER.warning("Cannot show console view since "
					+ "getActiveWorkbenchWindow() is null.");
		}
		
		PerfCakeRunJob job = new PerfCakeRunJob("PerfCake run job", file, perfclipseConsole, shell);
		job.schedule();
	    
	 
	}
	
	final class PerfCakeRunJob extends Job{
		
		private IFile file;
		private Shell shell;
		private MessageConsole console;

		public PerfCakeRunJob(String name, IFile file, MessageConsole console, Shell shell) {
			super(name);
			if (file == null){
				LOGGER.warning("File with scenario is null.");
				throw new IllegalArgumentException("File with scenario is null.");
			}
			if (console == null){
				LOGGER.warning("Console for scenario run  output is null.");
				throw new IllegalArgumentException("Console for scenario run output is null."); } if (shell == null){
				LOGGER.warning("Parent shell is null.");
				throw new IllegalArgumentException("Parent shell is null.");
			}
			this.file = file;
			this.console = console;
			this.shell = shell;
			
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
		OutputStream out = console.newOutputStream();
		PrintStream standardOut = System.out;
		System.setOut(new PrintStream(out));
				ScenarioManager scenarioManager = new ScenarioManager();
				
				try {
					scenarioManager.runScenario(file.getLocationURI().toURL());
				} catch (ScenarioException e) {
					LOGGER.warning("Cannot run scenario");
					Display.getDefault().asyncExec(new ErrorDialog(shell, "Scenario error", e.getMessage()));
				} catch (MalformedURLException e) {
					LOGGER.warning("Wrong url to scenario.");
					Display.getDefault().asyncExec(new ErrorDialog(shell, "Scenario URL error", e.getMessage()));
				} finally {
					try {
						System.setOut(standardOut); //set System.out to standard output
						out.close();
					} catch (IOException e) {
						LOGGER.warning("Cannot close stream to eclipse consolse!");
					}
				}
			return Status.OK_STATUS;
		}
		
	}
	
	
	final class ErrorDialog implements Runnable{
		
		private Shell parent;
		private String title;
		private String message;

		public ErrorDialog(Shell parent, String title, String message){
			this.parent = parent;
			this.title = title;
			this.message = message;
		}

		@Override
		public void run() {
			MessageDialog.openError(parent, title, message);
			
		}
		
	}

}
