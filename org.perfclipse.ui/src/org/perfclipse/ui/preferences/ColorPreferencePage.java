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
	
	private ColorFieldEditor scenarioBg;
	
	private ColorFieldEditor generatorFg;
	private ColorFieldEditor senderFg;
	private ColorFieldEditor messagesFg;
	private ColorFieldEditor messageFg;
	private ColorFieldEditor validationFg;
	private ColorFieldEditor validatorFg;
	private ColorFieldEditor reportingFg;
	private ColorFieldEditor reporterFg;
	private ColorFieldEditor destinationFg;
	private ColorFieldEditor propertiesFg;
	private ColorFieldEditor propertyFg;

	
	private ColorFieldEditor generatorBg;
	private ColorFieldEditor senderBg;
	private ColorFieldEditor messagesBg;
	private ColorFieldEditor messageBg;
	private ColorFieldEditor validationBg;
	private ColorFieldEditor validatorBg;
	private ColorFieldEditor reportingBg;
	private ColorFieldEditor reporterBg;
	private ColorFieldEditor destinationBg;
	private ColorFieldEditor propertiesBg;
	private ColorFieldEditor propertyBg;
	
	
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
		scenarioBg = new ColorFieldEditor(PreferencesConstants.SCENARIO_COLOR_BACKGROUND,
				"Scenario background: ", getFieldEditorParent());
		generatorFg = new ColorFieldEditor(PreferencesConstants.GENERATOR_COLOR_FOREGROUND,
				"Generator foreground: ", getFieldEditorParent());
		generatorBg = new ColorFieldEditor(PreferencesConstants.GENERATOR_COLOR_BACKGROUND,
				"Generator background", getFieldEditorParent());

		senderFg = new ColorFieldEditor(PreferencesConstants.SENDER_COLOR_FOREGROUND,
				"Sender foreground: ", getFieldEditorParent());
		senderBg = new ColorFieldEditor(PreferencesConstants.SENDER_COLOR_BACKGROUND, "Sender background: ",
				getFieldEditorParent());

		messagesFg = new ColorFieldEditor(PreferencesConstants.MESSAGES_COLOR_FOREGROUND,
				"Messages foreground: ", getFieldEditorParent());
		messagesBg = new ColorFieldEditor(PreferencesConstants.MESSAGES_COLOR_BACKGROUND,
				"Messages background: ", getFieldEditorParent());
		messageFg = new ColorFieldEditor(PreferencesConstants.MESSAGE_COLOR_FOREGROUND,
				"Message foreground: ", getFieldEditorParent());
		messageBg = new ColorFieldEditor(PreferencesConstants.MESSAGE_COLOR_BACKGROUND,
				"Message background: ", getFieldEditorParent());

		validationFg = new ColorFieldEditor(PreferencesConstants.VALIDATION_COLOR_FOREGROUND,
				"Validation foreground: ", getFieldEditorParent());
		validationBg = new ColorFieldEditor(PreferencesConstants.VALIDATION_COLOR_BACKGROUND,
				"Validation background: ", getFieldEditorParent());
		validatorFg = new ColorFieldEditor(PreferencesConstants.VALIDATOR_COLOR_FOREGROUND,
				"Validator foreground: ", getFieldEditorParent());
		validatorBg = new ColorFieldEditor(PreferencesConstants.VALIDATOR_COLOR_BACKGROUND,
				"Validator background: ", getFieldEditorParent());

		reportingFg = new ColorFieldEditor(PreferencesConstants.REPORTING_COLOR_FOREGROUND,
				"Reporting foreground: ", getFieldEditorParent());
		reportingBg = new ColorFieldEditor(PreferencesConstants.REPORTING_COLOR_BACKGROUND,
				"Reporting background: ", getFieldEditorParent());
		reporterFg = new ColorFieldEditor(PreferencesConstants.REPORTER_COLOR_FOREGROUND,
				"Reporter foreground: ", getFieldEditorParent());
		reporterBg = new ColorFieldEditor(PreferencesConstants.REPORTER_COLOR_BACKGROUND,
				"Reporter background: ", getFieldEditorParent());
		destinationFg = new ColorFieldEditor(PreferencesConstants.DESTINATION_COLOR_FOREGROUND,
				"Destination foreground: ", getFieldEditorParent());
		destinationBg = new ColorFieldEditor(PreferencesConstants.DESTINATION_COLOR_BACKGROUND,
				"Destination background: ", getFieldEditorParent());

		propertiesFg = new ColorFieldEditor(PreferencesConstants.PROPERTIES_COLOR_FOREGROUND,
				"Properties foreground: ", getFieldEditorParent());
		propertiesBg = new ColorFieldEditor(PreferencesConstants.PROPERTIES_COLOR_BACKGROUND,
				"Properties background: ", getFieldEditorParent());
		propertyFg = new ColorFieldEditor(PreferencesConstants.PROPERTY_COLOR_FOREGROUND,
				"Property foreground: ", getFieldEditorParent());
		propertyBg = new ColorFieldEditor(PreferencesConstants.PROPERTY_COLOR_BACKGROUND,
				"Property background: ", getFieldEditorParent());

		addField(scenarioBg);

		addField(generatorFg);
		addField(generatorBg);

		addField(senderFg);
		addField(senderBg);

		addField(messagesFg);
		addField(messagesBg);
		addField(messageFg);
		addField(messageBg);

		addField(validationFg);
		addField(validationBg);
		addField(validatorFg);
		addField(validatorBg);
		addField(destinationFg);
		addField(destinationBg);
		
		addField(reportingFg);
		addField(reportingBg);
		addField(reporterFg);
		addField(reporterBg);
		addField(destinationFg);
		addField(destinationBg);

		addField(propertiesFg);
		addField(propertiesBg);
		addField(propertyFg);
		addField(propertyBg);

	}
	
	



}
