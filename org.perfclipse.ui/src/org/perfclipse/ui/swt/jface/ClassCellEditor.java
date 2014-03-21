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

	
	/**
	 * 
	 * @param parent
	 * @param input
	 */
	public ClassCellEditor(Composite parent) {
		this(parent, SWT.NONE);
	}

	/**
	 * @param parent
	 * @param style
	 */
	public ClassCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected Control createControl(Composite parent) {
		Control returnValue = (CCombo) super.createControl(parent);
		getViewer().setContentProvider(ArrayContentProvider.getInstance());
		getViewer().setLabelProvider(new ClassLabelProvider());
		return returnValue;
	}
	
	

}
