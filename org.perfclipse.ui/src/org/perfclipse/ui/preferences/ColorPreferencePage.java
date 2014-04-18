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

import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.perfclipse.ui.Activator;
import org.perfclipse.ui.swt.KeyColorSelector;

/**
 * Preference page for setting color of PerfCake components.
 * 
 * @author Jakub Knetl
 *
 */
public class ColorPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	
	private static final int COMPONENT_COLUMN_WIDTH = 150;
	private static final int FOREGROUND_COLUMN_WIDTH = 80;
	private static final int BACKGROUND_COLUMN_WIDTH = 80;

	private Table table;
	
	private List<KeyColorSelector> colorSelectors;
	
	public ColorPreferencePage(){
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Set PerfCake components colors");
		colorSelectors = new ArrayList<>();
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		//sets background of widgets transparent
		container.setBackgroundMode(SWT.INHERIT_FORCE);

		GridData data;
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		
		Label sceanrioLabel = new Label(container, SWT.NONE);
		sceanrioLabel.setText("Editor background: ");
		KeyColorSelector scenarioBg = new KeyColorSelector(container, PreferencesConstants.SCENARIO_COLOR_BACKGROUND);
		colorSelectors.add(scenarioBg);
		
		table = new Table(container, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		table.setLayoutData(data);
		
		TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setText("Component");
		nameColumn.setWidth(COMPONENT_COLUMN_WIDTH);
		TableColumn foregroundColumn = new TableColumn(table, SWT.NONE);
		foregroundColumn.setText("foreground");
		foregroundColumn.setWidth(FOREGROUND_COLUMN_WIDTH);
		TableColumn backgroundColumn = new TableColumn(table, SWT.NONE);
		backgroundColumn.setText("background");
		backgroundColumn.setWidth(BACKGROUND_COLUMN_WIDTH);
		


		
		
		initRow("Generator", PreferencesConstants.GENERATOR_COLOR_FOREGROUND,
				PreferencesConstants.GENERATOR_COLOR_BACKGROUND, table);
		initRow("Sender", PreferencesConstants.SENDER_COLOR_FOREGROUND,
				PreferencesConstants.SENDER_COLOR_BACKGROUND, table);
		initRow("Sender properties", PreferencesConstants.SENDER_PROPERTY_COLOR_FOREGROUND,
				PreferencesConstants.SENDER_PROPERTY_COLOR_BACKGROUND, table);
		initRow("Messages section", PreferencesConstants.MESSAGES_COLOR_FOREGROUND,
				PreferencesConstants.MESSAGES_COLOR_BACKGROUND, table);
		initRow("Message", PreferencesConstants.MESSAGE_COLOR_FOREGROUND,
				PreferencesConstants.MESSAGE_COLOR_BACKGROUND, table);
		initRow("Validation section", PreferencesConstants.VALIDATION_COLOR_FOREGROUND,
				PreferencesConstants.VALIDATION_COLOR_BACKGROUND, table);
		initRow("Validator", PreferencesConstants.VALIDATOR_COLOR_FOREGROUND,
				PreferencesConstants.VALIDATOR_COLOR_BACKGROUND, table);
		initRow("Reporting section", PreferencesConstants.REPORTING_COLOR_FOREGROUND,
				PreferencesConstants.REPORTING_COLOR_BACKGROUND, table);
		initRow("Reporter", PreferencesConstants.REPORTER_COLOR_FOREGROUND,
				PreferencesConstants.REPORTER_COLOR_BACKGROUND, table);
		initRow("Destination", PreferencesConstants.DESTINATION_COLOR_FOREGROUND,
				PreferencesConstants.DESTINATION_COLOR_BACKGROUND, table);
		initRow("Properties section", PreferencesConstants.PROPERTIES_COLOR_FOREGROUND,
				PreferencesConstants.PROPERTIES_COLOR_BACKGROUND, table);
		initRow("Property", PreferencesConstants.PROPERTY_COLOR_FOREGROUND,
				PreferencesConstants.PROPERTY_COLOR_BACKGROUND, table);


		for (KeyColorSelector selector : colorSelectors){
			loadFromPreferences(selector);
		}

		scenarioBg.setColorValue(PreferenceConverter.getColor(
				getPreferenceStore(), scenarioBg.getKey()));

		return container;
	}
	
	/**
	 * 
	 * Intializes one line of table with component name and two color
	 * selectors with given Key.
	 * KeyColorSelectors are added to the list of colorSelctors.
	 * In current layout this method lays out one row of components.
	 * 
	 * @param name Name displayed in first column
	 * @param selectorId1 Key for first selector
	 * @param selectorId2 Key for second selector
	 * @param parent Composite to which new components will be added.
	 */
	private void initRow(String name, String selectorId1, String selectorId2, Table parent){

		TableEditor editor;
		TableItem item = new TableItem(parent, SWT.NONE);
		item.setText(0, name);
		
		KeyColorSelector selector1 = new KeyColorSelector(parent, selectorId1);
		selector1.getButton().pack();
		editor = new TableEditor (parent);
		editor.minimumWidth = selector1.getButton().getSize().x;
		editor.setEditor(selector1.getButton(), item, 1);

		KeyColorSelector selector2 = new KeyColorSelector(parent, selectorId2);
		selector2.getButton().pack();
		editor = new TableEditor(parent);
		editor.minimumWidth = selector2.getButton().getSize().x;
		editor.setEditor(selector2.getButton(), item, 2);
		
		//Adds selector to the list of selectors.
		colorSelectors.add(selector1);
		colorSelectors.add(selector2);
		
	}

	@Override
	public boolean performOk() {
		for (KeyColorSelector selector : colorSelectors){
			PreferenceConverter.setValue(getPreferenceStore(), selector.getKey(), selector.getColorValue());
		}
		return super.performOk();
	}
	
	@Override
	protected void performDefaults() {
		for (KeyColorSelector selector : colorSelectors){
			selector.setColorValue(PreferenceConverter.getDefaultColor(getPreferenceStore(), selector.getKey()));
		}
		super.performDefaults();
	}
	
	private void loadFromPreferences(KeyColorSelector selector){
		selector.setColorValue(PreferenceConverter.getColor(getPreferenceStore(), selector.getKey()));
	}

	@Override
	public void init(IWorkbench workbench) {
	}
}
