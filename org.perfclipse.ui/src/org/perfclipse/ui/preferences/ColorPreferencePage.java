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


import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.perfclipse.ui.Activator;

public class ColorPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	

	private List<FieldEditor> fieldEditors = new ArrayList<>();
	
	
	public ColorPreferencePage(){
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Set PerfCake components colors");
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.BORDER);

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.FILL;
		parent.setLayoutData(data);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		ColorFieldEditor scenarioBg;
		

		
		scenarioBg = new ColorFieldEditor(PreferencesConstants.SCENARIO_COLOR_BACKGROUND,
				"Editor background: ", container);

		Group leftPane = new Group(container, SWT.NONE);
		leftPane.setText("Components foreground: ");
		data = new GridData(SWT.BEGINNING, SWT.FILL, false, true);
		leftPane.setLayoutData(data);
		Group rightPane = new Group(container, SWT.NONE);
		data = new GridData(SWT.BEGINNING, SWT.FILL, false, true);
		rightPane.setText("Components background: ");
		rightPane.setLayoutData(data);

		ColorFieldEditor generatorFg;
		ColorFieldEditor senderFg;
		ColorFieldEditor messagesFg;
		ColorFieldEditor messageFg;
		ColorFieldEditor validationFg;
		ColorFieldEditor validatorFg;
		ColorFieldEditor reportingFg;
		ColorFieldEditor reporterFg;
		ColorFieldEditor destinationFg;
		ColorFieldEditor propertiesFg;
		ColorFieldEditor propertyFg;

		ColorFieldEditor generatorBg;
		ColorFieldEditor senderBg;
		ColorFieldEditor messagesBg;
		ColorFieldEditor messageBg;
		ColorFieldEditor validationBg;
		ColorFieldEditor validatorBg;
		ColorFieldEditor reportingBg;
		ColorFieldEditor reporterBg;
		ColorFieldEditor destinationBg;
		ColorFieldEditor propertiesBg;
		ColorFieldEditor propertyBg;

		generatorFg = new ColorFieldEditor(PreferencesConstants.GENERATOR_COLOR_FOREGROUND,
				"Generator: ", leftPane);
		generatorBg = new ColorFieldEditor(PreferencesConstants.GENERATOR_COLOR_BACKGROUND,
				"Generator", rightPane);

		senderFg = new ColorFieldEditor(PreferencesConstants.SENDER_COLOR_FOREGROUND,
				"Sender: ", leftPane);
		senderBg = new ColorFieldEditor(PreferencesConstants.SENDER_COLOR_BACKGROUND, "Sender background: ",
				rightPane);

		messagesFg = new ColorFieldEditor(PreferencesConstants.MESSAGES_COLOR_FOREGROUND,
				"Messages: ", leftPane);
		messagesBg = new ColorFieldEditor(PreferencesConstants.MESSAGES_COLOR_BACKGROUND,
				"Messages: ", rightPane);
		messageFg = new ColorFieldEditor(PreferencesConstants.MESSAGE_COLOR_FOREGROUND,
				"Message: ", leftPane);
		messageBg = new ColorFieldEditor(PreferencesConstants.MESSAGE_COLOR_BACKGROUND,
				"Message: ", rightPane);

		validationFg = new ColorFieldEditor(PreferencesConstants.VALIDATION_COLOR_FOREGROUND,
				"Validation: ", leftPane);
		validationBg = new ColorFieldEditor(PreferencesConstants.VALIDATION_COLOR_BACKGROUND,
				"Validation: ", rightPane);
		validatorFg = new ColorFieldEditor(PreferencesConstants.VALIDATOR_COLOR_FOREGROUND,
				"Validator: ", leftPane);
		validatorBg = new ColorFieldEditor(PreferencesConstants.VALIDATOR_COLOR_BACKGROUND,
				"Validator: ", rightPane);

		reportingFg = new ColorFieldEditor(PreferencesConstants.REPORTING_COLOR_FOREGROUND,
				"Reporting: ", leftPane);
		reportingBg = new ColorFieldEditor(PreferencesConstants.REPORTING_COLOR_BACKGROUND,
				"Reporting: ", rightPane);
		reporterFg = new ColorFieldEditor(PreferencesConstants.REPORTER_COLOR_FOREGROUND,
				"Reporter: ", leftPane);
		reporterBg = new ColorFieldEditor(PreferencesConstants.REPORTER_COLOR_BACKGROUND,
				"Reporter: ", rightPane);
		destinationFg = new ColorFieldEditor(PreferencesConstants.DESTINATION_COLOR_FOREGROUND,
				"Destination: ", leftPane);
		destinationBg = new ColorFieldEditor(PreferencesConstants.DESTINATION_COLOR_BACKGROUND,
				"Destination: ", rightPane);

		propertiesFg = new ColorFieldEditor(PreferencesConstants.PROPERTIES_COLOR_FOREGROUND,
				"Properties: ", leftPane);
		propertiesBg = new ColorFieldEditor(PreferencesConstants.PROPERTIES_COLOR_BACKGROUND,
				"Properties: ", rightPane);
		propertyFg = new ColorFieldEditor(PreferencesConstants.PROPERTY_COLOR_FOREGROUND,
				"Property: ", leftPane);
		propertyBg = new ColorFieldEditor(PreferencesConstants.PROPERTY_COLOR_BACKGROUND,
				"Property: ", rightPane);

		addFieldEditor(scenarioBg);
		addFieldEditor(generatorFg);
		addFieldEditor(senderFg);
		addFieldEditor(messagesFg);
		addFieldEditor(messageFg);
		addFieldEditor(validationFg);
		addFieldEditor(validatorFg);
		addFieldEditor(reportingFg);
		addFieldEditor(reporterFg);
		addFieldEditor(destinationFg);
		addFieldEditor(propertiesFg);
		addFieldEditor(propertyFg);
		addFieldEditor(generatorBg);
		addFieldEditor(senderBg);
		addFieldEditor(messagesBg);
		addFieldEditor(messageBg);
		addFieldEditor(validationBg);
		addFieldEditor(validatorBg);
		addFieldEditor(reportingBg);
		addFieldEditor(reporterBg);
		addFieldEditor(destinationBg);
		addFieldEditor(propertiesBg);
		addFieldEditor(propertyBg);
		
		for (FieldEditor e : fieldEditors){
			initFieldEditor(e);
		}

		return container;
	}
	@Override
	public boolean performCancel() {
		return super.performCancel();
	}
	@Override
	public boolean performOk() {
		for (FieldEditor e : fieldEditors) {
			e.store();
		}
		return super.performOk();
	}
	@Override
	protected void performDefaults() {
		for (FieldEditor e : fieldEditors){
			e.loadDefault();
		}
		super.performDefaults();
	}
	
	
	private void addFieldEditor(FieldEditor editor){
		fieldEditors.add(editor);
	}
	
	private void initFieldEditor(FieldEditor editor){
		editor.setPage(this);
		editor.setPreferenceStore(getPreferenceStore());
		editor.load();
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
