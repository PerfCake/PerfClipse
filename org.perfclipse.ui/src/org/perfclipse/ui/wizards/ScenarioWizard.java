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

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;
import org.perfclipse.ui.editors.ScenarioDesignEditorInput;

public class ScenarioWizard extends Wizard implements INewWizard {

	private ScenarioFirstPage firstPage;
	IStructuredSelection selection;
	

	public ScenarioWizard() {
		super();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		

	}

	@Override
	public boolean performFinish() {
		ObjectFactory scenarioFactory = new ObjectFactory();
		Scenario scenario = scenarioFactory.createScenario();

		//Generator section
		Scenario.Generator generator = scenarioFactory.createScenarioGenerator();
		Scenario.Generator.Run run = scenarioFactory.createScenarioGeneratorRun();
		run.setType("time");
		run.setValue("5000");
		generator.setClazz(firstPage.getGeneratorName());
		generator.setRun(run);
		generator.setThreads("1");
		scenario.setGenerator(generator);

		//Sender section
		Scenario.Sender sender = scenarioFactory.createScenarioSender();
		sender.setClazz(firstPage.getSenderName());
		scenario.setSender(sender);
		
		ScenarioModel model = new ScenarioModel(scenario);
		
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		try {
			URI uri = new URI("file://" + firstPage.getScenarioDirectory());
			IContainer[] container = workspaceRoot.findContainersForLocationURI(uri);
			if (container[0] instanceof IFolder){
				IFolder folder = (IFolder) container[0];
				IFile scenarioFile = folder.getFile(firstPage.getScenarioName() + ".xml");
				
				ScenarioManager manager = new ScenarioManager();
				PipedOutputStream out = new PipedOutputStream();
				PipedInputStream in = new PipedInputStream(out);
				manager.createXML(model.getScenario(), out);
				out.close();
				scenarioFile.create(in, false, null);
				
				//open scenario editor
				String scenarioEditorID = "org.perfclipse.ui.editors.scenario";
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				//TODO : editor cannot be initialized after Eclipse reload
				page.openEditor(new ScenarioDesignEditorInput(scenarioFile), scenarioEditorID);
				return true;
			}
		} catch (URISyntaxException e) {
			MessageDialog.openError(getShell(), "URI syntax error", "Cannot locate selected folder for scenario.");
		} catch (CoreException e) {
			MessageDialog.openError(getShell(), "Core exception", "Cannot create or open file with scenario." );
		} catch (IOException e) {
			MessageDialog.openError(getShell(), "IO Exception", "Cannot create or read resource");
		} catch (ScenarioException e) {
			MessageDialog.openError(getShell(), "XML conversion error", "Cannot convert given data into XML.");
		}
		
		return false;
	}
	
	@Override
	public void addPages(){
		firstPage = new ScenarioFirstPage(selection);
		addPage(firstPage);
	}

}
