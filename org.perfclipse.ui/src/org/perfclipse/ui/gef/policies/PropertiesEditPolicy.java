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

package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.perfclipse.core.model.PropertiesModel;
import org.perfclipse.wizards.PropertiesEditWizard;
import org.perfclipse.wizards.WizardUtils;

/**
 * @author Jakub Knetl
 *
 */
public class PropertiesEditPolicy extends AbstractPerfCakeComponentEditPolicy {

	private PropertiesModel properties;

	/**
	 * @param properties
	 */
	public PropertiesEditPolicy(PropertiesModel properties) {
		super();
		this.properties = properties;
	}

	@Override
	protected Command createPropertiesCommand(Request request) {
		PropertiesEditWizard wizard = new PropertiesEditWizard(properties);
		if (WizardUtils.showWizardDialog(wizard) == Window.OK){
			if (!wizard.getCommand().isEmpty()){
				return wizard.getCommand();
			}
		}
		return null;
	}
	
	
}

