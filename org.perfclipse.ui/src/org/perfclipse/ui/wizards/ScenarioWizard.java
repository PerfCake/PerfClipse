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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;
import org.perfclipse.ui.editors.ScenarioDesignEditorInput;
import org.perfclipse.ui.wizards.pages.GeneratorPage;
import org.perfclipse.ui.wizards.pages.ScenarioNewFilePage;
import org.perfclipse.ui.wizards.pages.SenderPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScenarioWizard extends Wizard implements INewWizard {

	private GeneratorPage generatorAndSenderPage;
	private WizardNewFileCreationPage fileCreationPage;
	IStructuredSelection selection;
	
	static final Logger log = LoggerFactory.getLogger(ScenarioWizard.class);
	private SenderPage senderPage;

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
		generator.setClazz(generatorAndSenderPage.getGeneratorName());
		generator.setRun(run);
		generator.setThreads("1");
		scenario.setGenerator(generator);

		//Sender section
		Scenario.Sender sender = scenarioFactory.createScenarioSender();
		sender.setClazz(senderPage.getSenderName());
		scenario.setSender(sender);
		
		
		try {
			IFile scenarioFile = fileCreationPage.createNewFile();;
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			ScenarioManager manager = new ScenarioManager();
			manager.createXML(scenario, out);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			out.close();
			scenarioFile.setContents(in, false, true, null);

			//open scenario editor
			String scenarioEditorID = "org.perfclipse.ui.editors.scenario";
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			//TODO : editor cannot be initialized after Eclipse reload
			page.openEditor(new ScenarioDesignEditorInput(scenarioFile), scenarioEditorID, true);
			return true;
		} catch (CoreException e) {
			MessageDialog.openError(getShell(), "Core exception", "Cannot create or open file with scenario." );
			log.error("Cannot create or open file with scenario.", e);
		} catch (IOException e) {
			MessageDialog.openError(getShell(), "IO Exception", "Cannot create or read resource");
			log.error("Cannot create or read resource", e);
		} catch (ScenarioException e) {
			MessageDialog.openError(getShell(), "XML conversion error", "Cannot convert given data into XML.");
			log.error("Cannot convert given data into XML.", e);
		}
		
		return false;
	}
	
	@Override
	public void addPages(){
		fileCreationPage = new ScenarioNewFilePage("New Scenario file", selection);
		generatorAndSenderPage = new GeneratorPage();
		senderPage = new SenderPage();

		addPage(fileCreationPage);
		addPage(generatorAndSenderPage);
		addPage(senderPage);
	}

	@Override
	public boolean canFinish() {
		return super.canFinish();
	}
	
	

}
