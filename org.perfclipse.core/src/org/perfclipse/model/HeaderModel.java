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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.perfcake.model.Header;

public class HeaderModel {

	public static final String PROPERTY_NAME = "header-name";
	public static final String PROPERTY_VALUE = "header-value";
	
	private Header header;
	private PropertyChangeSupport listeners;

	public HeaderModel(Header header) {
		if (header == null){
			throw new IllegalArgumentException("Header must not be null");
		}
		this.header = header;
		listeners = new PropertyChangeSupport(this);
	}

	
	/**
	 * This method should not be used for modifying header (in a way getHeader().setName()))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Header 
	 */
	public Header getHeader() {
		return header;
	}
	
	public void setName(String name){
		String oldName = getHeader().getName();
		getHeader().setName(name);
		listeners.firePropertyChange(PROPERTY_NAME, oldName, name);
	}
	

	public void setValue(String value){
		String oldValue = getHeader().getValue();
		getHeader().setValue(value);
		listeners.firePropertyChange(PROPERTY_VALUE, oldValue, value);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
}
