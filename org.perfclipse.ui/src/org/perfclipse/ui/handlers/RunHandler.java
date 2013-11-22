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

import java.net.MalformedURLException;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;


public class RunHandler extends AbstractHandler {
	
	private final static Logger LOGGER = Logger.getLogger(LoadHandler.class .getName()); 

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
		ScenarioManager scenarioManager = new ScenarioManager();
		
		try {
			scenarioManager.runScenario(file.getLocationURI().toURL());
		} catch (ScenarioException e) {
			LOGGER.warning("Cannot run scenario");
			MessageDialog.openError(shell, "Scenario error", e.getMessage());
		} catch (MalformedURLException e) {
			LOGGER.warning("Wrong url to scenario.");
			MessageDialog.openError(shell, "Scenario URL error", e.getMessage());
		}
	}

}
