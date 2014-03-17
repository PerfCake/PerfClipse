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

package org.perfclipse.ui.wizards;

import org.eclipse.jface.viewers.TableViewer;
import org.perfclipse.model.PropertyModel;

/**
 * @author Jakub Knetl
 *
 */
public class PropertyNameEditingSupport extends PropertyEditingSupport {

	public PropertyNameEditingSupport(TableViewer viewer) {
		super(viewer);
	}

	@Override
	protected Object getValue(Object element) {
		PropertyModel property = (PropertyModel) element;
		return property.getProperty().getName();
	}

	@Override
	protected void setValue(Object element, Object value) {
		PropertyModel property = (PropertyModel) element;
		property.setName(String.valueOf(value));
		getViewer().update(element, null);
	}
}
