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

package org.perfclipse.wizards.pages;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.perfclipse.core.model.PeriodModel;
import org.perfclipse.wizards.WizardUtils;
import org.perfclipse.wizards.swt.ComboUtils;
import org.perfclipse.wizards.swt.jface.StringComboViewer;

/**
 * @author Jakub Knetl
 *
 */
public class PeriodPage extends AbstractPerfCakePage {

	
	public static final String PERIOD_PAGE_NAME = "Period page";

	private PeriodModel period;
	
	private Composite container;
	private Label typeLabel;
	private StringComboViewer typeCombo;
	
	private Label valueLabel;
	private Text valueText;
	
	public PeriodPage(){
		this(PERIOD_PAGE_NAME, false);
	}
	
	/**
	 * 
	 * @param period
	 */
	public PeriodPage(PeriodModel period){
		this(PERIOD_PAGE_NAME, true);
		this.period = period;
	}
	
	/**
	 * @param pageName
	 * @param edit
	 */
	private PeriodPage(String pageName, boolean edit) {
		super(pageName, edit);
	}

	@Override
	public void createControl(Composite parent) {
		setTitle("Period");
		setDescription("Fill in period type and value.");
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		
		GridData data;
		
		typeLabel = new Label(container, SWT.NONE);
		typeLabel.setText("Period type: ");
		typeCombo = new StringComboViewer(container, WizardUtils.getPeriodTypes());
		typeCombo.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.FILL;
		typeCombo.getControl().setLayoutData(data);
		
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
		if (getPeriodName() == null || "".equals(getPeriodName())){
			setDescription("Fill in period type.");
			setPageComplete(false);
			return;
		}
		if ("".equals(getPeriodValue())){
			setDescription("Fill in period value");
			setPageComplete(false);
			return;
		}
		setDescription("Complete!");
		setPageComplete(true);
		super.updateControls();
	}

	@Override
	protected void fillCurrentValues() {
		if (period.getPeriod().getType() != null)
			ComboUtils.select(typeCombo, period.getPeriod().getType());
		if (period.getPeriod().getValue() != null)
			valueText.setText(period.getPeriod().getValue());
		super.fillCurrentValues();
	}
	
	public String getPeriodName(){
		IStructuredSelection sel = (IStructuredSelection) typeCombo.getSelection();
		return (String) sel.getFirstElement();
	}

	public String getPeriodValue(){
		return valueText.getText();
	}
	
}
