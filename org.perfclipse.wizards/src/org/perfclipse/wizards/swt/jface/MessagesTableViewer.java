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

package org.perfclipse.wizards.swt.jface;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.core.model.MessageModel;

/**
 * @author Jakub Knetl
 *
 */
public class MessagesTableViewer extends AbstractCommandTableViewer {
	
	static final int URI_COLUMN_WIDTH = 350;
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

	/**
	 * @param parent
	 * @param commands
	 */
	public MessagesTableViewer(Composite parent, List<Command> commands) {
		super(parent, commands);
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
		
		multiplicity = new TableViewerColumn(this, SWT.NONE);
		multiplicity.getColumn().setText("Mulitplicity");
		multiplicity.getColumn().setAlignment(SWT.CENTER);
		multiplicity.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				MessageModel message = (MessageModel) element;
				return message.getMessage().getMultiplicity();
			}
			
		});
		
		
		validatorRef = new TableViewerColumn(this, SWT.NONE);
		validatorRef.getColumn().setText("Attached validators");
		validatorRef.getColumn().setAlignment(SWT.CENTER);
		validatorRef.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				MessageModel message = (MessageModel) element;
				StringBuilder builder = new StringBuilder();
				for (int i = 0 ; i < message.getMessage().getValidatorRef().size(); i++){
					ValidatorRef ref = message.getMessage().getValidatorRef().get(i); 
					if (i != 0){
						builder.append(", ");
					}
					builder.append(ref.getId());
				}

				return builder.toString();
			}
			
		});
		
	}

	@Override
	protected void setColumnsSize() {
		uri.getColumn().setWidth(URI_COLUMN_WIDTH);
		validatorRef.getColumn().pack();
		multiplicity.getColumn().pack();
	}
}
