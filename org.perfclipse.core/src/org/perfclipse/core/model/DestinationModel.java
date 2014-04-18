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

package org.perfclipse.core.model;

import java.util.List;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;

public class DestinationModel extends PerfClipseModel implements IPropertyContainer, IEnableable {
	
	public static final String PROPERTY_PERIOD = "destination-period";
	public static final String PROPERTY_CLASS = "destination-class";
	public static final String PROPERTY_ENABLED = "destination-enabled";
	public static final String PROPERTY_PROPERTIES = "destination-property";
	
	private Destination destination;

	public DestinationModel(Destination destination, ModelMapper mapper) {
		super(mapper);
		if (destination == null){
			throw new IllegalArgumentException("Destination must not be null");
		}
		this.destination = destination;
	}
	
	/**
	 * This method should not be used for modifying Destination (in a way getDestination().setClass()))
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Destination
	 */
	public Destination getDestination() {
		return destination;
	}
	
	public void setClazz(String clazz){
		String oldClazz = getDestination().getClazz();
		getDestination().setClazz(clazz);
		getListeners().firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
	}
	
	public void addPeriod(Period period){
		addPeriod(getDestination().getPeriod().size(), period);
	}

	public void addPeriod(int index, Period period){
		getDestination().getPeriod().add(index, period);
		getListeners().firePropertyChange(PROPERTY_PERIOD, null, period);
	}
	
	public void removePeriod(Period period){
		if (getDestination().getPeriod().remove(period)){
			getListeners().firePropertyChange(PROPERTY_PERIOD, period, null);
		}
	}
	
	public void addProperty(Property property){
		addProperty(getDestination().getProperty().size(), property);
	}
	
	public void addProperty(int index, Property property){
		getDestination().getProperty().add(index, property);
		getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getDestination().getProperty().remove(property)){
			getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public List<Property> getProperty(){
		return getDestination().getProperty();
	}
	
	public void setEnabled(boolean enabled){
		if (enabled != getDestination().isEnabled()){
			getDestination().setEnabled(enabled);
			getListeners().firePropertyChange(PROPERTY_ENABLED, !enabled, enabled);
		}
	}

	@Override
	public boolean isEnabled() {
		return getDestination().isEnabled();
	}
	
}
