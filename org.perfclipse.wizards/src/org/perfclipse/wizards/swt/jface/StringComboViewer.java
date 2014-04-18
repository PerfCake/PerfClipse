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

import java.util.Collection;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * ComboViewer with ArrayContentProvider and ClassLabelProvider, and 
 * sets collection as a input. 
 * @author Jakub Knetl
 *
 */
//TODO: Add type argument
public class StringComboViewer extends ComboViewer {

	private Collection<String> input;

	/**
	 * 
	 * @param parent
	 * @param input
	 */
	public StringComboViewer(Composite parent, Collection<String> input) {
		super(parent);
		initializeViewer(input);
	}

	/**
	 * 
	 * @param list
	 * @param input
	 */
	public StringComboViewer(Combo list, Collection<String> input) {
		super(list);
		initializeViewer(input);
	}
	
	/**
	 * 
	 * @param list
	 */
	public StringComboViewer(CCombo list, Collection<String> input) {
		super(list);
		initializeViewer(input);
	}

	/**
	 * 
	 * @param parent
	 * @param style
	 * @param input
	 */
	public StringComboViewer(Composite parent, int style, Collection<String> input) {
		super(parent, style);
		initializeViewer(input);
	}

	/**
	 * 
	 * @param input
	 */
	protected void initializeViewer(Collection<String> input) {
		
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider());
		this.input = input;
		setInput(input);
	}
	
	public Collection<String> getInputCollection(){
		return input;
	}
	

}
