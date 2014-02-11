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

package org.perfclipse.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.perfcake.model.Property;

public class PropertyModel extends Property {
	
	private PropertyChangeSupport listeners;
	public static final String PROPERTY_NAME = "property-name";
	public static final String PROPERTY_VALUE = "property-value";

	public PropertyModel() {
		super();
		listeners = new PropertyChangeSupport(this);
	}
	
	public PropertyModel(Property property){
		this();
		if (property != null){
			this.name = property.getName();
			this.value = property.getValue();
		}
	}

	@Override
	public void setName(String value) {
		String oldValue = getName();
		super.setName(value);
		listeners.firePropertyChange(PROPERTY_NAME, oldValue, value);
	}

	@Override
	public void setValue(String value) {
		String oldValue = getValue();
		super.setValue(value);
		listeners.firePropertyChange(PROPERTY_VALUE, oldValue, value);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}

	
	

}
