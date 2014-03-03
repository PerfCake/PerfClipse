/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
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

package org.perfclipse.ui.launching;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.part.FileEditorInput;
import org.perfcake.PerfCakeConst;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;
import org.perfclipse.ui.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfCakeLaunchDeleagate implements ILaunchConfigurationDelegate, ILaunchShortcut {

	final static Logger log = LoggerFactory.getLogger(PerfCakeLaunchDeleagate.class);

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {

		IFile scenario = getScenarioResource(configuration);

		runScenario(scenario);
		
	}

	@Override
	public void launch(ISelection selection, String mode) {
		IFile scenario = getScenarioResourceForSelection(selection);
		if (scenario != null){
			try {
				launch(scenario, mode);
			} catch (CoreException e) {
				log.error("Cannot launch current launch configuration.", e);
				// TODO: display info to user?
			}
		}
	}
	
	@Override
	public void launch(IEditorPart editor, String mode) {
		IFile scenario = getScenarioResourceForEditor(editor);
		if (scenario != null){
			try {
				launch(scenario, mode);
			} catch (CoreException e) {
				log.error("Cannot launch current launch configuration.", e);
				// TODO: display info to user?
			}
		}
	}

	private void launch(IFile scenario, String mode) throws CoreException{
		List<ILaunchConfiguration> configurations = getLaunchConfigurationsForScenario(scenario);
		
		if (configurations.isEmpty()){
			//Create new configuration type
			ILaunchConfiguration configuration = createLaunchConfiguration(scenario);
			configurations.add(configuration);
		}
		
		//TODO: check monitor
		configurations.get(0).launch(mode, null);
	}
	
	private ILaunchConfiguration createLaunchConfiguration(IFile scenario) throws CoreException {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(PerfCakeLaunchConstants.LAUNCH_CONFIG_TYPE_ID);

		ILaunchConfigurationWorkingCopy configuration = type.newInstance(null, scenario.getName());
		configuration.setAttribute(PerfCakeLaunchConstants.PROJECT, scenario.getProject().getName());
		configuration.setAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, scenario.getProjectRelativePath().toPortableString());
		configuration.doSave();

		return configuration;
	}

	private List<ILaunchConfiguration> getLaunchConfigurationsForScenario(IFile file) throws CoreException{
		
		List<ILaunchConfiguration> configurationsForFile = new ArrayList<>();
		
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(PerfCakeLaunchConstants.LAUNCH_CONFIG_TYPE_ID);
		
		try {
			ILaunchConfiguration[] configurations = manager.getLaunchConfigurations(type);
			for (ILaunchConfiguration configuration : configurations){
				IFile scenario = getScenarioResource(configuration); 
				if (file.equals(scenario)){
					configurationsForFile.add(configuration);
				}
			}
		} catch (CoreException e) {
			log.warn("Cannot obtain launch configurations.", e);
			throw e;
		}
		
		return configurationsForFile;
	}

	private IFile getScenarioResourceForSelection(ISelection selection) {
		 if (selection instanceof IStructuredSelection){
			 IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			 if (structuredSelection.getFirstElement() instanceof IFile){
				 return (IFile) structuredSelection.getFirstElement();
			 }
		 }
		 
		 return null;
	}
	
	private IFile getScenarioResourceForEditor(IEditorPart editor) {
		if (editor.getEditorInput() instanceof FileEditorInput){
			// find out if this file is scenario file and the return IFile instance
		}
		return null;
	}
	
	private IFile getScenarioResource(ILaunchConfiguration configuration) throws CoreException{
		String projectName = configuration.getAttribute(PerfCakeLaunchConstants.PROJECT, "");
		String filePath = configuration.getAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, "");
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return project.getFile(filePath);
	}
	
	private void runScenario(IFile file) {
		//redirect System.out to Eclipse console
		MessageConsole perfclipseConsole = Utils.findConsole(Utils.PERFCLIPSE_STDOUT_CONSOLE);
		
		//show console view
		//TODO: check if shell is always initialized
		IWorkbench wb = PlatformUI.getWorkbench();
		
		//show console view in UI thread:
		wb.getDisplay().syncExec(new Runnable(){

			@Override
			public void run() {
				try{
					IWorkbenchWindow win = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage page = win.getActivePage();
					MessageConsole perfclipseConsole = Utils.findConsole(Utils.PERFCLIPSE_STDOUT_CONSOLE);
					String id = IConsoleConstants.ID_CONSOLE_VIEW;
					try {
						IConsoleView view = (IConsoleView) page.showView(id);
						view.display(perfclipseConsole);
					} catch (PartInitException e1) {
						log.warn("Cannot show console view." + e1);
					}
				} catch (NullPointerException e){
					log.warn("Cannot show console view since "
							+ "getActiveWorkbenchWindow() is null." + e);
				}
			}
			
		});

		
		PerfCakeRunJob job = new PerfCakeRunJob("PerfCake run job", file, perfclipseConsole);
		job.schedule();
	}
	
	final class PerfCakeRunJob extends Job{
		
		private IFile file;
		private MessageConsole console;

		public PerfCakeRunJob(String name, IFile file, MessageConsole console) {
			super(name);
			if (file == null){
				log.warn("File with scenario is null.");
				throw new IllegalArgumentException("File with scenario is null.");
			}
			if (console == null){
				log.warn("Console for scenario run  output is null.");
				throw new IllegalArgumentException("Console for scenario run output is null."); 
				} 
			this.file = file;
			this.console = console;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {

			//redirect System.out to eclipse console
			OutputStream out = console.newOutputStream();
			PrintStream standardOut = System.out;
			System.setOut(new PrintStream(out));
			
			//Add appender for Log4j to eclipse console
			org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();

			Appender appender = rootLogger.getAppender("CONSOLE");
			if (appender instanceof ConsoleAppender){
				ConsoleAppender consoleAppender = (ConsoleAppender) rootLogger.getAppender("CONSOLE");
				consoleAppender.activateOptions();
			} else{
				log.warn("Cannot obtain PerfCake console logger. Output will not be redirected to Eclipse console");
			}
			


			//set message and scenario paths for PerfCake 
			IFolder messageDir = file.getProject().getFolder("messages");
			IFolder scenarioDir = file.getProject().getFolder("scenarios");
			System.setProperty(PerfCakeConst.MESSAGES_DIR_PROPERTY, messageDir.getRawLocation().toString());
			System.setProperty(PerfCakeConst.SCENARIOS_DIR_PROPERTY, scenarioDir.getRawLocation().toString());
			ScenarioManager scenarioManager = new ScenarioManager();

			try {
				monitor.beginTask("PerfCake task", 100);
				scenarioManager.runScenario(file.getLocationURI().toURL());
				monitor.done();
			} catch (ScenarioException e) {
				log.warn("Cannot run scenario" + e);
				Display.getDefault().asyncExec(new ErrorDialog("Scenario error", e.getMessage()));
			} catch (MalformedURLException e) {
				log.warn("Wrong url to scenario." + e);
				Display.getDefault().asyncExec(new ErrorDialog("Scenario URL error", e.getMessage()));
			} finally {
				System.setOut(standardOut); //set System.out to standard output
				try {
					out.close();
				} catch (IOException e) {
					log.warn("Cannot close stream to eclipse consolse!" + e);
				}
			}
			return Status.OK_STATUS;
		}
	}

	final class ErrorDialog implements Runnable{
		
		private String title;
		private String message;

		public ErrorDialog(String title, String message){
			this.title = title;
			this.message = message;
		}

		@Override
		public void run() {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), title, message);
			
		}
	}

}