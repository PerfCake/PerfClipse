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
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;

public class DestinationModel {
	
	public static final String PROPERTY_PERIOD = "destination-period";
	public static final String PROPERTY_CLASS = "destination-class";
	public static final String PROPERTY_ENABLED = "destination-enabled";
	public static final String PROPERTY_PROPERTIES = "destination-property";
	
	private Destination destination;
	private PropertyChangeSupport listeners;

	public DestinationModel(Destination destination) {
		if (destination == null){
			throw new IllegalArgumentException("Destination must not be null");
		}
		this.destination = destination;
		listeners = new PropertyChangeSupport(this);
	}
	
	/**
	 * This method should not be used for modifying Destination (in a way getDestination().setClass()))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Destination
	 */
	public Destination getDestination() {
		return destination;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	public void setClass(String clazz){
		String oldClazz = getDestination().getClazz();
		getDestination().setClazz(clazz);
		listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
	}
	
	public void addPeriod(Period period){
		addPeriod(getDestination().getPeriod().size(), period);
	}

	public void addPeriod(int index, Period period){
		getDestination().getPeriod().add(index, period);
		listeners.firePropertyChange(PROPERTY_PERIOD, null, period);
	}
	
	public void removePeriod(Period period){
		if (getDestination().getPeriod().remove(period)){
			listeners.firePropertyChange(PROPERTY_PERIOD, period, null);
		}
	}
	
	public void addProperty(Property property){
		addProperty(getDestination().getProperty().size(), property);
	}
	
	public void addProperty(int index, Property property){
		getDestination().getProperty().add(index, property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getDestination().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public void setEnabled(boolean enabled){
		if (enabled != getDestination().isEnabled()){
			getDestination().setEnabled(enabled);
			listeners.firePropertyChange(PROPERTY_ENABLED, !enabled, enabled);
		}
	}
	
}
