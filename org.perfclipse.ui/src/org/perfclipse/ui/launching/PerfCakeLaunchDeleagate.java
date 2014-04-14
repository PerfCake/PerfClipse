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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.part.FileEditorInput;
import org.perfclipse.logging.Logger;
import org.perfclipse.ui.Activator;
import org.perfclipse.ui.PerfClipseConstants;
import org.perfclipse.ui.Utils;

public class PerfCakeLaunchDeleagate implements ILaunchConfigurationDelegate, ILaunchShortcut {

	static final Logger log = Activator.getDefault().getLogger();

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
			FileEditorInput input = (FileEditorInput) editor.getEditorInput();
			return input.getFile();
		}
		return null;
	}
	
	private IFile getScenarioResource(ILaunchConfiguration configuration) throws CoreException{
		String projectName = configuration.getAttribute(PerfCakeLaunchConstants.PROJECT, "");
		String filePath = configuration.getAttribute(PerfCakeLaunchConstants.SCENARIO_FILE, "");
		
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return project.getFile(filePath);
	}
	
	private void runScenario(final IFile file) {
		//redirect System.out to Eclipse console
		MessageConsole perfclipseConsole = Utils.findConsole(PerfClipseConstants.PERFCLIPSE_STDOUT_CONSOLE);
		
		
		// Ask save in UI thread
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				IEditorReference[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();

				for (IEditorReference editor : editors){

					try {
						IEditorInput input = editor.getEditorInput();
						if (input instanceof FileEditorInput){
							IFileEditorInput fileInput = (IFileEditorInput) input;
							if (file.equals(fileInput.getFile())){
								IEditorPart editorPart = editor.getEditor(true);
								if (editorPart != null && editorPart.isDirty()){
									//TODO: Progress monitor
									boolean save = MessageDialog.openConfirm(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Save editor",
											"Scenario was modified in the editor but not saved. Do you want to save changes before launching scenario?");
									if (save)
										editorPart.doSave(null);
								}
							}
						}
					} catch (PartInitException e) {
						log.error("Cannot obtain editor input since it cannod be restored", e);
					}
				}

			}
		});

		//show console view in UI thread:
		IWorkbench wb = PlatformUI.getWorkbench();
		wb.getDisplay().syncExec(new Runnable(){

			@Override
			public void run() {
				try{
					IWorkbenchWindow win = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
					IWorkbenchPage page = win.getActivePage();
					MessageConsole perfclipseConsole = Utils.findConsole(PerfClipseConstants.PERFCLIPSE_STDOUT_CONSOLE);
					String id = IConsoleConstants.ID_CONSOLE_VIEW;
					try {
						IConsoleView view = (IConsoleView) page.showView(id);
						view.display(perfclipseConsole);
					} catch (PartInitException e1) {
						log.warn("Cannot show console view.", e1);
					}
				} catch (NullPointerException e){
					log.warn("Cannot show console view since "
							+ "getActiveWorkbenchWindow() is null.", e);
				}
			}
			
		});

		
		PerfCakeRunJob job = new PerfCakeRunJob("PerfCake run job", file, perfclipseConsole);
		job.schedule();
	}
	


}