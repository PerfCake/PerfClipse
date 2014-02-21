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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.perfclipse.ui.PerfClipseConstants;
import org.slf4j.LoggerFactory;

public class PerfcakeProjectWizard extends Wizard implements INewWizard {
	
	private static final String[] defaultFolders = {PerfClipseConstants.SCENARIO_DIR_NAME, PerfClipseConstants.MESSAGE_DIR_NAME};
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(PerfcakeProjectWizard.class);

	private PerfcakeProjectFirstPage firstPage;
	private IWorkbench workbench;

	public PerfcakeProjectWizard() {
		super();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
	}

	@Override
	public boolean performFinish() {
		String projectName = firstPage.getProjectName();
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = workspaceRoot.getProject(projectName);
	
		try{
			project.create(null); //TODO: progress monitor
			project.open(null); //TODO: progress monitor
			for (String s : defaultFolders){
				IFolder folder = project.getFolder(s);
				if (!folder.exists()){
					folder.create(false, true, null); //TODO: progress monitor
				}
			}
		} catch (CoreException e){
			Shell shell = workbench.getDisplay().getActiveShell();
			if (project.exists()){
				try {
					project.delete(true, true, null); //TODO: progress monitor
				} catch (CoreException e1) {
					log.warn("Cannot delete resources: " + project.getProjectRelativePath());
					MessageDialog.openError(shell, "Cannot delete resources", project.getProjectRelativePath().toString());
				}
					
				MessageDialog.openError(shell, "Error", "Project cannot be created.");
				log.warn("Cannot create project due to CoreException: " + e.toString());}
				return false;
			}

		return true;
	}
	
	@Override
	public void addPages(){
		firstPage = new PerfcakeProjectFirstPage();
		addPage(firstPage);
	}

}
