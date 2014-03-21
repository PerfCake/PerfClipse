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

package org.perfclipse.model;

import org.perfcake.model.Property;

public class PropertyModel extends PerfClipseModel {

	public static final String PROPERTY_NAME = "property-name";
	public static final String PROPERTY_VALUE = "property-value";
	
	private Property property;

	public PropertyModel(Property property, ModelMapper mapper) {
		super(mapper);
		if (property == null){
			throw new IllegalArgumentException("Property must not be null");
		}
		this.property = property;
	}

	
	/**
	 * This method should not be used for modifying property (in a way getProperty().setName()))
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Property
	 */
	public Property getProperty() {
		return property;
	}
	
	public void setName(String name){
		String oldName = getProperty().getName();
		getProperty().setName(name);
		getListeners().firePropertyChange(PROPERTY_NAME, oldName, name);
	}
	

	public void setValue(String value){
		String oldValue = getProperty().getValue();
		getProperty().setValue(value);
		getListeners().firePropertyChange(PROPERTY_VALUE, oldValue, value);
	}
}
