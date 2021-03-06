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

package org.perfclipse.wizards.swt;

import java.util.Collection;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Combo;
import org.perfclipse.wizards.swt.jface.StringComboViewer;

/**
 * @author Jakub Knetl
 *
 */
public class ComboUtils {

	/**
	 * Select given value in the combo, if such value exists in the combo
	 * @param combo Combo where value should be selected
	 * @param value Value to be selected
	 */
	public static void select(Combo combo, Object value){
		for (int i = 0; i <combo.getItems().length; i++) {
			if (value.equals(combo.getItem(i))){
				combo.select(i);
				break;
			}
		}
	}
	
	/**
	 * Select given value in the combo viewer, if such value exists in the viewer
	 * @param viewer ComboViewer where value should be selected
	 * @param value Value to be selected
	 */
	public static void select(StringComboViewer viewer, Object value){
		
		Collection<String> input =   viewer.getInputCollection();
		int i = 0;
		for (String name : input){
			if (value.equals(name)){
				viewer.setSelection(new StructuredSelection(viewer.getElementAt(i)));
				return;
			}
			i++;
		}
	}
}
