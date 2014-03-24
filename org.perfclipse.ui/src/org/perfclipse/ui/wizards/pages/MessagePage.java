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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.HeaderModel;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ValidatorRefModel;
import org.perfclipse.ui.swt.events.AbstractEditCommandSelectionAdapter;
import org.perfclipse.ui.swt.events.AddHeaderSelectionAdapter;
import org.perfclipse.ui.swt.events.AddPropertySelectionAdapter;
import org.perfclipse.ui.swt.events.DeleteHeaderSelectionAdapter;
import org.perfclipse.ui.swt.events.DeletePropertySelectionAdapter;
import org.perfclipse.ui.swt.events.EditPropertySelectionAdapter;
import org.perfclipse.ui.swt.jface.HeaderTableViewer;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.jface.ValidatorRefTableViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;
import org.perfclipse.ui.wizards.AbstractPerfCakeEditWizard;
import org.perfclipse.ui.wizards.HeaderEditWizard;

/**
 * @author Jakub Knetl
 *
 */
public class MessagePage extends AbstractPerfCakePage {



	private static final String MESSAGE_PAGE_NAME = "Message";

	private MessageModel message;
	private List<HeaderModel> headers;
	private List<PropertyModel> properties;
	private List<ValidatorRefModel> refs;

	private Label uriLabel;
	private Text uriText;
	private Label multiplicityLabel;
	private Spinner multiplicitySpinner;

	private Composite container;
	private HeaderTableViewer headerViewer;
	private PropertyTableViewer propertyViewer;
	private ValidatorRefTableViewer refViewer;
	
	private TableViewerControl headerControl;
	private TableViewerControl propertyControl;
	private TableViewerControl refControl;

	public MessagePage(){
		this(MESSAGE_PAGE_NAME, false);
	}

	public MessagePage(MessageModel message){
		this(MESSAGE_PAGE_NAME, true);
		this.message = message;
		
		ModelMapper mapper = message.getMapper();
		
		properties = new ArrayList<>(message.getProperty().size());
		headers = new ArrayList<>(message.getMessage().getHeader().size());
		refs = new ArrayList<>(message.getMessage().getValidatorRef().size());
		
		for (Property p : message.getProperty()){
			properties.add((PropertyModel) mapper.getModelContainer(p));
		}
		
		for (Header h : message.getMessage().getHeader()){
			headers.add((HeaderModel) mapper.getModelContainer(h));
		}
		
		for (ValidatorRef r : message.getMessage().getValidatorRef()){
			refs.add((ValidatorRefModel) mapper.getModelContainer(r));
		}
	}

	private MessagePage(String pageName, boolean edit) {
		super(pageName, edit);
		setTitle("Message");
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		
		GridData data;
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		container.setLayout(layout);
		
		
		uriLabel = new Label(container, SWT.NONE);
		uriLabel.setText("URI: ");
		uriText = new Text(container, SWT.NONE);
		data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = SWT.FILL;
		uriText.setLayoutData(data);

		multiplicityLabel = new Label(container, SWT.NONE);
		multiplicityLabel.setText("Multiplicity");
		
		multiplicitySpinner = new Spinner(container, SWT.NONE);
		multiplicitySpinner.setMinimum(0);
		multiplicitySpinner.setMaximum(Integer.MAX_VALUE);
		data = new GridData(SWT.FILL, SWT.BEGINNING, false, false);
		data.horizontalSpan = 2;
		multiplicitySpinner.setLayoutData(data);

		headerViewer = new HeaderTableViewer(container, getEditingSupportCommands());
		headerViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		headerViewer.getTable().setLayoutData(data);
		
		headerControl = new TableViewerControl(container, true, SWT.NONE);
		headerControl.getAddButton().addSelectionListener(
				new AddHeaderSelectionAdapter(getEditingSupportCommands(),
						headerViewer, message));
		headerControl.getDeleteButton().addSelectionListener(
				new DeleteHeaderSelectionAdapter(getEditingSupportCommands(),
						headerViewer, message));
		headerControl.getEditButton().addSelectionListener(new AbstractEditCommandSelectionAdapter(getEditingSupportCommands(), headerViewer) {
			
			@Override
			protected AbstractPerfCakeEditWizard createWizard(
					IStructuredSelection selection) {
				return new HeaderEditWizard((HeaderModel) selection.getFirstElement());
			}
		});
		
		propertyViewer = new PropertyTableViewer(container, getEditingSupportCommands());
		propertyViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		propertyViewer.getTable().setLayoutData(data);
		
		propertyControl = new TableViewerControl(container, true, SWT.NONE);
		propertyControl.getAddButton().addSelectionListener(
				new AddPropertySelectionAdapter(getEditingSupportCommands(),
						propertyViewer, message));
		propertyControl.getDeleteButton().addSelectionListener(
				new DeletePropertySelectionAdapter(getEditingSupportCommands(),
						propertyViewer, message));
		propertyControl.getDeleteButton().addSelectionListener(
				new EditPropertySelectionAdapter(getEditingSupportCommands(), propertyViewer));
		
		refViewer = new ValidatorRefTableViewer(container, getEditingSupportCommands());
		refViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.horizontalSpan = 2;
		refViewer.getTable().setLayoutData(data);
		
		//TODO: figure out ValidatorRef control
		refControl = new TableViewerControl(container, true, SWT.NONE);
		
		setControl(container);
		super.createControl(parent);
	}

	@Override
	protected void updateControls() {
		if ("".equals(uriText.getText())){
			setDescription("Fill in message URI.");
			setPageComplete(false);
		}
		setPageComplete(true);
		super.updateControls();
	}


	@Override
	protected void fillCurrentValues() {
		if (message.getMessage().getUri() != null){
			uriText.setText(message.getMessage().getUri());
		}
		if (message.getMessage().getMultiplicity() != null){
			multiplicitySpinner.setSelection(
					Integer.valueOf(message.getMessage().getMultiplicity()));
		} else{
			multiplicitySpinner.setSelection(1);
		}
		propertyViewer.setInput(properties);
		headerViewer.setInput(headers);
		refViewer.setInput(refs);
		super.fillCurrentValues();
	}

	@Override
	protected void fillDefaultValues() {
		multiplicitySpinner.setSelection(1);
		super.fillDefaultValues();
	}
	
	public String getUri(){
		return uriText.getText();
	}
	
	public int getMultiplicity(){
		return multiplicitySpinner.getSelection();
	}

	public PropertyTableViewer getPropertyViewer() {
		return propertyViewer;
	}

	public HeaderTableViewer getHeaderViewer() {
		return headerViewer;
	}

	public ValidatorRefTableViewer getRefViewer() {
		return refViewer;
	}
	
}
