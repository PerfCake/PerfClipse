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

import java.util.Collection;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author Jakub Knetl
 *
 */
public class ClassCellEditor extends ComboBoxViewerCellEditor {

	Collection<String> input;
	
	/**
	 * 
	 * @param parent
	 * @param input
	 */
	public ClassCellEditor(Composite parent, Collection<String> input) {
		this(parent, SWT.NONE, input);
	}

	/**
	 * @param parent
	 * @param style
	 */
	public ClassCellEditor(Composite parent, int style, Collection<String> input) {
		super(parent, style);
		this.input = input;
		getViewer().setInput(input);

	}

	@Override
	protected Control createControl(Composite parent) {
		Control returnValue = (CCombo) super.createControl(parent);
		getViewer().setContentProvider(ArrayContentProvider.getInstance());
		getViewer().setLabelProvider(new ClassLabelProvider());
		return returnValue;
	}
	
	public Collection<String> getViewerInputCollection(){
		return input;
	}
	
	

}
