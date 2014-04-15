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
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.perfclipse.model.HeaderModel;

/**
 * @author Jakub Knetl
 *
 */
public class HeaderTableViewer extends AbstractCommandTableViewer {
	
	static final int COLUMN_WIDTH = 220;
	private TableViewerColumn keyColumn;
	private TableViewerColumn valueColumn;
	
	/**
	 * @param parent
	 * @param commands
	 */
	public HeaderTableViewer(Composite parent, List<Command> commands) {
		super(parent, commands);
	}

	/**
	 * @param parent
	 * @param style
	 * @param commands
	 */
	public HeaderTableViewer(Composite parent, int style, List<Command> commands) {
		super(parent, style, commands);
	}

	@Override
	protected void initColumns() {
		keyColumn = new TableViewerColumn(this, SWT.NONE);
		keyColumn.getColumn().setText("Header name");
		keyColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				HeaderModel header = (HeaderModel) element;
				return header.getHeader().getName();
			}
			
		});
		valueColumn = new TableViewerColumn(this, SWT.NONE);
		valueColumn.getColumn().setText("Header value");
		valueColumn.setLabelProvider(new ColumnLabelProvider(){

			@Override
			public String getText(Object element) {
				HeaderModel header = (HeaderModel) element;
				return header.getHeader().getValue();
			}
			
		});
		
	}

	@Override
	protected void setColumnsSize() {
		keyColumn.getColumn().setWidth(COLUMN_WIDTH);
		valueColumn.getColumn().setWidth(COLUMN_WIDTH);
	}

}
