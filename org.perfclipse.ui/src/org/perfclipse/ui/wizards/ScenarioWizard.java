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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.FileEditorInput;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;
import org.perfclipse.ui.wizards.pages.GeneratorPage;
import org.perfclipse.ui.wizards.pages.MessagesPage;
import org.perfclipse.ui.wizards.pages.PropertiesPage;
import org.perfclipse.ui.wizards.pages.ReportingPage;
import org.perfclipse.ui.wizards.pages.ScenarioNewFilePage;
import org.perfclipse.ui.wizards.pages.SenderPage;
import org.perfclipse.ui.wizards.pages.ValidationPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScenarioWizard extends Wizard implements INewWizard {

	private WizardNewFileCreationPage fileCreationPage;
	private GeneratorPage generatorPage;
	private SenderPage senderPage;
	private MessagesPage messagesPage;
	private ValidationPage validationPage;
	private ReportingPage reportingPage; 
	private PropertiesPage propertiesPage;
	
	IStructuredSelection selection;
	
	static final Logger log = LoggerFactory.getLogger(ScenarioWizard.class);

	public ScenarioWizard() {
		super();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		

	}

	@Override
	public boolean performFinish() {
		ObjectFactory factory = new ObjectFactory();
		Scenario scenario = factory.createScenario();

		//Generator section
		Scenario.Generator generator = factory.createScenarioGenerator();
		Scenario.Generator.Run run = factory.createScenarioGeneratorRun();
		run.setType(generatorPage.getRunType());
		run.setValue(String.valueOf(generatorPage.getRunValue()));
		generator.setClazz(generatorPage.getGeneratorName());
		generator.setRun(run);
		generator.setThreads(String.valueOf(generatorPage.getThreads()));
		for (TableItem i : generatorPage.getPropertiesViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				generator.getProperty().add(p.getProperty());
			}
		}
		scenario.setGenerator(generator);

		//Sender section
		Scenario.Sender sender = factory.createScenarioSender();
		sender.setClazz(senderPage.getSenderName());
		for (TableItem i : senderPage.getPropertyViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				sender.getProperty().add(p.getProperty());
			}
		}
		scenario.setSender(sender);

		//Messages section
		Scenario.Messages messages = factory.createScenarioMessages();
		for (TableItem i : messagesPage.getMessagesViewer().getTable().getItems()){
			if (i.getData() instanceof MessageModel){
				MessageModel m = (MessageModel) i.getData();
				messages.getMessage().add(m.getMessage());
			}
		}
		if (!messages.getMessage().isEmpty()){
			scenario.setMessages(messages);
		}

		//Validation section
		Scenario.Validation validation = factory.createScenarioValidation();
		for (TableItem i : validationPage.getValidatorViewer().getTable().getItems()){
			if (i.getData() instanceof ValidatorModel){
				ValidatorModel v = (ValidatorModel) i.getData();
				validation.getValidator().add(v.getValidator());
			}
		}
		if (!validation.getValidator().isEmpty())
			scenario.setValidation(validation);

		//Reporting section
		Scenario.Reporting reporting = factory.createScenarioReporting();
		for (TableItem i : reportingPage.getReporterViewer().getTable().getItems()){
			if (i.getData() instanceof ReporterModel){
				ReporterModel r = (ReporterModel) i.getData();
				reporting.getReporter().add(r.getReporter());
			}
		}
		
		for (TableItem i : reportingPage.getPropertyViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				reporting.getProperty().add(p.getProperty());
			}
		}
		if (!reporting.getReporter().isEmpty()){
			scenario.setReporting(reporting);
		}
		

		//Properties section
		Scenario.Properties properties = factory.createScenarioProperties();
		for (TableItem i : propertiesPage.getPropertyViewer().getTable().getItems()){
			if (i.getData() instanceof PropertyModel){
				PropertyModel p = (PropertyModel) i.getData();
				properties.getProperty().add(p.getProperty());
			}
		}
		if (!properties.getProperty().isEmpty()){
			scenario.setProperties(properties);
		}

		
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
			page.openEditor(new FileEditorInput(scenarioFile), scenarioEditorID, true);
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
		generatorPage = new GeneratorPage();
		senderPage = new SenderPage();
		messagesPage = new MessagesPage();
		validationPage = new ValidationPage();
		reportingPage = new ReportingPage();
		propertiesPage = new PropertiesPage();

		addPage(fileCreationPage);
		addPage(generatorPage);
		addPage(senderPage);
		addPage(messagesPage);
		addPage(validationPage);
		addPage(reportingPage);
		addPage(propertiesPage);
	}

//	@Override
//	public boolean canFinish() {
//		return super.canFinish();
//	}
	
	

}
