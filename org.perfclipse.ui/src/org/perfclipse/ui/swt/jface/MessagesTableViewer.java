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

package org.perfclipse.ui.swt.jface;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.model.MessageModel;
import org.perfclipse.ui.gef.commands.EditMessageMultiplicityCommand;
import org.perfclipse.ui.gef.commands.EditMessageUriCommand;

/**
 * @author Jakub Knetl
 *
 */
public class MessagesTableViewer extends AbstractCommandTableViewer {
	
	private static final int VALIDATOR_REF_COLUMN_WIDTH = 50;
	private static final int MULTIPLICITY_COLUMN_WIDTH = 50;
	static final int URI_COLUMN_WIDTH = 220;
	private TableViewerColumn multiplicity;
	private TableViewerColumn validatorRef;
	private TableViewerColumn uri;

	/**
	 * @param parent
	 * @param style
	 * @param commands
	 */
	public MessagesTableViewer(Composite parent, int style,
			List<Command> commands) {
		super(parent, style, commands);
	}

	@Override
	protected void initializeViewer() {
		setContentProvider(ArrayContentProvider.getInstance());
		super.initializeViewer();
	}


	@Override
	protected void initColumns() {

		uri = new TableViewerColumn(this, SWT.NONE);
		uri.getColumn().setText("Message URI");
		uri.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {

				MessageModel message = (MessageModel) element;
				return message.getMessage().getUri();
			}
			
		});

		uri.setEditingSupport(new AbstractCommandEditingSupport(this, getCommands()) {
			
			@Override
			protected Object getValue(Object element) {
				MessageModel message = (MessageModel) element;

				return message.getMessage().getUri();
			}

			@Override
			protected Command getCommand(Object element, Object value) {
				MessageModel message = (MessageModel) element;
				return new EditMessageUriCommand(message, String.valueOf(value));
			}
		});
		
		multiplicity = new TableViewerColumn(this, SWT.NONE);
		multiplicity.getColumn().setText("Mulitplicity");
		multiplicity.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				MessageModel message = (MessageModel) element;
				return message.getMessage().getMultiplicity();
			}
			
		});
		
		multiplicity.setEditingSupport(new AbstractCommandEditingSupport(this, getCommands()) {
			
			@Override
			protected Object getValue(Object element) {
				MessageModel message = (MessageModel) element;
				return message.getMessage().getMultiplicity();
			}
			
			@Override
			protected Command getCommand(Object element, Object value) {
				MessageModel message = (MessageModel) element;
				try {
					int newValue = Integer.valueOf(String.valueOf(value));
					return new EditMessageMultiplicityCommand(message, newValue);
				} catch (NumberFormatException e){
					//TODO: show user warning
					return null;
				}
			}
		});
		
		validatorRef = new TableViewerColumn(this, SWT.NONE);
		validatorRef.getColumn().setText("Validators");
		validatorRef.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				MessageModel message = (MessageModel) element;
				return String.valueOf(message.getMessage().getValidatorRef().size());
			}
			
		});
		
	}

	@Override
	protected void setColumnsSize() {
		uri.getColumn().setWidth(URI_COLUMN_WIDTH);
		validatorRef.getColumn().setWidth(VALIDATOR_REF_COLUMN_WIDTH);
		multiplicity.getColumn().setWidth(MULTIPLICITY_COLUMN_WIDTH);
	}
}
