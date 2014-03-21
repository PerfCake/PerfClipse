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

package org.perfclipse.ui.gef.directedit;

import org.eclipse.draw2d.Label;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.perfclipse.ui.swt.jface.ClassLabelProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassDirectEditManager extends DirectEditManager {

	static final Logger log = LoggerFactory.getLogger(ClassDirectEditManager.class);

	private Label label;
	
	private Object comboInput;
	
	@SuppressWarnings("rawtypes")
	public ClassDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator, Object comboInput) {
		this(source, editorType, locator, null, comboInput);
	}

	@SuppressWarnings("rawtypes")
	public ClassDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator, Object feature, Object comboInput) {
		super(source, editorType, locator, feature);
		this.label = ((ComboViewerCellEditorLocator) locator).getLabel();
		this.comboInput = comboInput;
	}
	
	@Override
	protected void initCellEditor() {
			final ComboBoxViewerCellEditor editor = (ComboBoxViewerCellEditor) getCellEditor();
				editor.setContentProvider(new ArrayContentProvider());
				editor.setInput(comboInput);
				editor.setLabelProvider(new ClassLabelProvider());
				//TODO: select current validator class by default (or empty string with sufficient length)
				editor.setValue(label.getText());
				editor.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {
					
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						setDirty(true);
					}
				});
	}
}
