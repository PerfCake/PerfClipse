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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.perfclipse.core.model.PropertyModel;

/**
 * @author Jakub Knetl
 *
 */
public class PropertyPage extends AbstractPerfCakePage {

	public static final String PROPERTY_PAGE_NAME = "Property";
	private Composite container;
	private Label nameLabel;
	private Text nameText;

	private Label valueLabel;
	private Text valueText;
	
	private PropertyModel property;

	
	
	public PropertyPage(){
		this(PROPERTY_PAGE_NAME, false);
	}
	
	public PropertyPage(PropertyModel property){
		this(PROPERTY_PAGE_NAME, true);
		this.property = property;
	}
	

	/**
	 * @param pageName
	 * @param edit
	 */
	private PropertyPage(String pageName, boolean edit) {
		super(pageName, edit);
	}

	@Override
	public void createControl(Composite parent) {
		setTitle("Property wizard");
		setDescription("Fill in name and value.");
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		GridData data;
		layout.numColumns = 2;
		container.setLayout(layout);
		
		nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Name: ");
		
		nameText = new Text(container, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		nameText.setLayoutData(data);
		nameText.addModifyListener(new UpdateModifyListener(this));
		
		
		valueLabel = new Label(container, SWT.NONE);
		valueLabel.setText("Value: ");
		
		valueText = new Text(container, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		valueText.setLayoutData(data);
		valueText.addModifyListener(new UpdateModifyListener(this));
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void updateControls() {
		if ("".equals(nameText.getText())){
			setDescription("Fill in property name.");
			setPageComplete(false);
			return;
		}
		if ("".equals(valueText.getText())){
			setDescription("Fill in property value.");
			setPageComplete(false);
			return;
		}
		setDescription("Complete!");
		setPageComplete(true);
	}

	@Override
	protected void fillCurrentValues() {
		if (property.getProperty().getName() != null)
			nameText.setText(property.getProperty().getName());
		if (property.getProperty().getValue() != null)
			valueText.setText(property.getProperty().getValue());
		super.fillCurrentValues();
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public Text getNameText() {
		return nameText;
	}

	public Label getValueLabel() {
		return valueLabel;
	}

	public Text getValueText() {
		return valueText;
	}
	
	

}
