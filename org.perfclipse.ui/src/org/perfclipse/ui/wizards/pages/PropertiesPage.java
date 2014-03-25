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

package org.perfclipse.ui.wizards.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.perfcake.model.Property;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PropertiesModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.ui.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.ui.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;

/**
 * @author Jakub Knetl
 *
 */
public class PropertiesPage extends AbstractPerfCakePage {

	private static final String PROPERTIES_PAGE_NAME = "Properties page";
	private PropertiesModel properties;
	private List<PropertyModel> propertyList;

	private Composite container;
	private PropertyTableViewer propertyViewer;
	private TableViewerControl propertyControl;

	public PropertiesPage(){
		this(PROPERTIES_PAGE_NAME, false);
	}
	
	public PropertiesPage(PropertiesModel properties){
		this(PROPERTIES_PAGE_NAME, true);
		this.properties = properties;
		
		if (properties.getProperties() == null){
			setEditMode(false);
			return;
		}
		ModelMapper m = properties.getMapper();
		propertyList = new ArrayList<>(properties.getProperty().size());
		for (Property p : properties.getProperty()){
			propertyList.add((PropertyModel) m.getModelContainer(p));
		}
	}

	/**
	 * @param pageName
	 * @param edit
	 */
	private PropertiesPage(String pageName, boolean edit) {
		super(pageName, edit);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		GridData data;
		
		propertyViewer = new PropertyTableViewer(container, getEditingSupportCommands());
		data = Utils.getTableViewerGridData();
		propertyViewer.getTable().setLayoutData(data);
		
		propertyControl = new TableViewerControl(container, true, SWT.NONE);
		propertyControl.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getEditingSupportCommands(),
						propertyViewer, properties));
		propertyControl.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(getEditingSupportCommands(),
						propertyViewer, properties));
		propertyControl.getDeleteButton().addSelectionListener(
				new EditPropertySelectionAdapter(getEditingSupportCommands(), propertyViewer));
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void fillCurrentValues() {
		propertyViewer.setInput(propertyList);
		super.fillCurrentValues();
	}

	public PropertyTableViewer getPropertyViewer() {
		return propertyViewer;
	}
}
