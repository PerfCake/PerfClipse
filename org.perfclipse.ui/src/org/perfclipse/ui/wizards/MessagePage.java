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

package org.perfclipse.ui.wizards;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.model.HeaderModel;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ValidatorRefModel;
import org.perfclipse.ui.swt.events.AbstractCommandSelectionAdapter;
import org.perfclipse.ui.swt.jface.PropertyTableViewer;
import org.perfclipse.ui.swt.jface.ValidatorRefTableViewer;
import org.perfclipse.ui.swt.widgets.TableViewerControl;

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

	
	private Composite container;
	private PropertyTableViewer headerViewer;
	private PropertyTableViewer propertyViewer;
	private ValidatorRefTableViewer refViewer;
	
	private TableViewerControl headerControl;
	private TableViewerControl propertyControl;
	private TableViewerControl refControl;

	public MessagePage(){
		this(MESSAGE_PAGE_NAME, false);
	}

	public MessagePage(MessageModel message, List<HeaderModel> headers, 
			List<PropertyModel> properties, List<ValidatorRefModel> refs){
		this(MESSAGE_PAGE_NAME, true);
		this.message = message;
		this.headers = headers;
		this.properties = properties;
		this.refs = refs;
	}

	private MessagePage(String pageName, boolean edit) {
		super(pageName, edit);
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		
		GridData data;
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		container.setLayout(layout);
		
		
		headerViewer = new PropertyTableViewer(container, getEditingSupportCommands());
		headerViewer.addSelectionChangedListener(new UpdateSelectionChangeListener(this));
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		headerViewer.getTable().setLayoutData(data);
		
		headerControl = new TableViewerControl(container, false, SWT.NONE);
//		headerControl.getAddButton().addSelectionListener();
		
		
		super.createControl(parent);
	}
	
	


}
