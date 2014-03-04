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

package org.perfclipse.ui.preferences;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.perfclipse.ui.Activator;

public class ColorPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	
	private ColorFieldEditor generatorColor;
	private ColorFieldEditor senderColor;
	private ColorFieldEditor messagesColor;
	private ColorFieldEditor validationColor;
	private ColorFieldEditor reportingColor;
	private ColorFieldEditor propertiesColor;

	public ColorPreferencePage(){
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Set PerfCake components colors");
		
	}
	@Override
	public void init(IWorkbench workbench) {
		
	}

	@Override
	protected void createFieldEditors() {
		generatorColor = new ColorFieldEditor(PreferencesConstants.GENERATOR_COLOR_FOREGROUND,
				"Generator: ", getFieldEditorParent());
		senderColor = new ColorFieldEditor(PreferencesConstants.SENDER_COLOR_FOREGROUND,
				"Sender: ", getFieldEditorParent());
		messagesColor = new ColorFieldEditor(PreferencesConstants.MESSAGES_COLOR_FOREGROUND,
				"Messages: ", getFieldEditorParent());
		validationColor = new ColorFieldEditor(PreferencesConstants.VALIDATION_COLOR_FOREGROUND,
				"Validation: ", getFieldEditorParent());
		reportingColor = new ColorFieldEditor(PreferencesConstants.REPORTING_COLOR_FOREGROUND,
				"Reporting: ", getFieldEditorParent());
		propertiesColor = new ColorFieldEditor(PreferencesConstants.PROPERTIES_COLOR_FOREGROUND,
				"Properties: ", getFieldEditorParent());

		addField(generatorColor);
		addField(senderColor);
		addField(messagesColor);
		addField(validationColor);
		addField(reportingColor);
		addField(propertiesColor);

	}
	
	



}
