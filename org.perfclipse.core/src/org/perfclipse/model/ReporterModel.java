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
import java.util.List;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;

public class ReporterModel implements IPropertyContainer {

	public static final String PROPERTY_CLASS = "reporter-class";
	public static final String PROPERTY_DESTINATIONS = "reporter-destination";
	public static final String PROPERTY_PROPERTIES = "reporter-property";
	public static final String PROPERTY_ENABLED = "reporter-enabled";
	
	private PropertyChangeSupport listeners;
	private Reporter reporter;

	public ReporterModel(Reporter reporter){
		if (reporter == null){
			throw new IllegalArgumentException("Reporter must not be null");
		}
		this.reporter = reporter;
		listeners = new PropertyChangeSupport(this);
	}
	
	/**
	 * This method should not be used for modifying Reporter(in a way getReporter().setClass()))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Reporter
	 */
	public Reporter getReporter() {
		return reporter;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	public void setClazz(String clazz){
		String oldClazz = getReporter().getClazz();
		getReporter().setClazz(clazz);
		listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
	}
	
	public void addDestination(Destination destination){
		addDestination(getReporter().getDestination().size(), destination);
	}
	public void addDestination(int index, Destination destination){
		getReporter().getDestination().add(index, destination);
		listeners.firePropertyChange(PROPERTY_DESTINATIONS, null, destination);
	}
	
	public void removeDestionation(Destination destination){
		if (getReporter().getDestination().remove(destination)){
			listeners.firePropertyChange(PROPERTY_DESTINATIONS, destination, null);
		}
	}
	
	public void addProperty(Property Property){
		addProperty(getReporter().getProperty().size(), Property);
	}
	
	public void addProperty(int index, Property property){
		getReporter().getProperty().add(index, property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getReporter().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public List<Property> getProperty(){
		return getReporter().getProperty();
	}
	
	public void setEnabled(boolean enabled){
		if (enabled != getReporter().isEnabled()){
			getReporter().setEnabled(enabled);
			listeners.firePropertyChange(PROPERTY_ENABLED, !enabled, enabled);
		}
	}
}
