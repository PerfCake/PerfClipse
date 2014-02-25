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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.reflect.PerfClipseScannerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComboViewerDirectEditManager extends DirectEditManager {

	static final Logger log = LoggerFactory.getLogger(ComboViewerDirectEditManager.class);

	private Label label;

	@SuppressWarnings("rawtypes")
	public ComboViewerDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator) {
		super(source, editorType, locator);
		this.label = ((ComboViewerCellEditorLocator) locator).getLabel();
	}

	@SuppressWarnings("rawtypes")
	public ComboViewerDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator, Object feature) {
		super(source, editorType, locator, feature);
		this.label = ((ComboViewerCellEditorLocator) locator).getLabel();
	}
	
	@Override
	protected void initCellEditor() {
			final ComboBoxViewerCellEditor editor = (ComboBoxViewerCellEditor) getCellEditor();
			PerfCakeComponents components;
			try {
				components = PerfCakeComponents.getInstance();
				editor.setContentProvider(new ArrayContentProvider());
				editor.setInput(components.getValidators());
				editor.setLabelProvider(new LabelProvider(){
					@Override 
					public String getText(Object element){
						if (element instanceof Class){
							Class<?> clazz = (Class<?>) element;
							return clazz.getSimpleName();
						}
						return null;
					}
				});
				//TODO: select current validator class by default (or empty string with sufficient length)
				editor.setValue(label.getText());
				editor.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {
					
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						setDirty(true);
					}
				});
			} catch (PerfClipseScannerException e) {
				log.error("Cannot parse PerfCake components", e);
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"PerfCake componets cannot be parsed", "No PerfCake component was found due to error in parsing.");
			}
	}
}
