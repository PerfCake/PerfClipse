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

package org.perfclipse.ui.editors;

import java.net.MalformedURLException;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;
import org.slf4j.LoggerFactory;

public class ScenarioDesignEditorInput extends FileEditorInput {

	final static org.slf4j.Logger log = LoggerFactory.getLogger(ScenarioDesignEditorInput.class);
	private ScenarioModel model;
	
	public ScenarioDesignEditorInput(IFile file) {
		super(file);
		createModel();
	}

	@Override
	public String getName() {
		return "Scenario Design editor";
	}

	public ScenarioModel getModel() {
		return model;
	}
	
	public void createModel(){
		ScenarioManager manager = new ScenarioManager();
		try {
			model = manager.createModel(getURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Show error dialog 
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openError(shell, "Wrong URL to scenario",
					"Scenario cannot be opened.");

			log.warn("Scenario cannot be opened.", e);
		} catch (ScenarioException e) {

			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			MessageDialog.openError(shell, "Scenario error",
					"Cannot parse scenario. Probabily there is error in scenario definition.");

			log.warn("Cannot parse scenario.", e);
		}
		
	}
}
