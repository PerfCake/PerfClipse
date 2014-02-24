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

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Sender;

public class SenderModel {

	public static final String PROPERTY_CLASS = "sender-class";
	public static final String PROPERTY_PROPERTIES= "sender-property";
	
	private Sender sender;
	private PropertyChangeSupport listeners;
	
	public SenderModel(Sender sender){
		if (sender == null){
			throw new IllegalArgumentException("Sender must not be null");
		}
		this.sender = sender;
		listeners = new PropertyChangeSupport(this);
	}
	
	/**
	 * This method should not be used for modifying Sender (in a way getSender().setClass()))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Sender
	 */
	public Sender getSender() {
		return sender;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}

	public void setClass(String clazz){
		String oldClazz = getSender().getClazz();
		getSender().setClazz(clazz);
		listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
	}
	
	public void addProperty(Property property){
		getSender().getProperty().add(property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getSender().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
}
