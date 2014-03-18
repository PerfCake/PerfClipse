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

package org.perfclipse.ui.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Jakub Knetl
 *
 */
public class TableViewerControl extends Composite {


	private Button addElementButton;
	private Button editElementButton;
	private Button deleteElementButton;

	/**
	 * @param parent
	 * @param style
	 */
	public TableViewerControl(Composite parent, boolean hasEditButton, int style) {
		super(parent, style);
		
		setLayout(new RowLayout(SWT.VERTICAL));
		
		addElementButton = new Button(this, SWT.PUSH);
		addElementButton.setText("Add");
		
		if (hasEditButton){
			editElementButton = new Button(this, SWT.PUSH);
			editElementButton.setText("Edit");
		}
		
		deleteElementButton = new Button(this, SWT.PUSH);
		deleteElementButton.setText("Delete");
	}

	public Button getAddElementButton() {
		return addElementButton;
	}
	public Button getEditElementButton() {
		return editElementButton;
	}
	public Button getDeleteElementButton() {
		return deleteElementButton;
	}
}
