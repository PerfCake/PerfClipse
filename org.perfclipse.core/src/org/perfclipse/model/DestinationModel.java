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
import java.util.ArrayList;
import java.util.List;

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
	
	protected List<PeriodModel> periodModel;
	protected List<PropertyModel> propertyModel;

	public DestinationModel(Destination destination) {
		this.destination = destination;
		listeners = new PropertyChangeSupport(this);
		
		periodModel = new ArrayList<>();
		propertyModel = new ArrayList<>();

		if (destination != null){
			if (destination.getPeriod() != null){
				for (Period p : destination.getPeriod()){
					periodModel.add(new PeriodModel(p));
				}
			}
			if (destination.getProperty() != null){
				for (Property p : destination.getProperty()){
					propertyModel.add(new PropertyModel(p));
				}
			}
		}
	}
	
	public void addPeriodModel(PeriodModel periodModel){
		this.periodModel.add(periodModel);
		this.addPeriod(periodModel.getPeriod());
	}
	
	public void removePeriodModel(PeriodModel periodModel){
		this.periodModel.remove(periodModel);
		this.removePeriod(periodModel.getPeriod());
	}
	
	public void addPropertyModel(PropertyModel propertyModel){
		this.propertyModel.add(propertyModel);
		this.addProperty(propertyModel.getProperty());
	}
	
	public void removePropertyModel(PropertyModel propertyModel){
		this.propertyModel.remove(propertyModel);
		this.removeProperty(propertyModel.getProperty());
	}
	
	/**
	 * Do not modify list using getList().add() since it will not fire
	 * propertyChangeEvent and the view of the model won't be refreshed.
	 * Use add* and remove* methods instead.
	 * @return List of PerfClipse model
	 */
	public List<PeriodModel> getPeriodModel() {
		return periodModel;
	}

	/**
	 * Do not modify list using getList().add() since it will not fire
	 * propertyChangeEvent and the view of the model won't be refreshed.
	 * Use add* and remove* methods instead.
	 * @return List of PerfClipse model
	 */
	public List<PropertyModel> getPropertyModel() {
		return propertyModel;
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
	
	public void setClazz(String clazz){
		String oldClazz = getDestination().getClazz();
		getDestination().setClazz(clazz);
		listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
	}
	
	public String getClazz(){
		return getDestination().getClazz();
	}
	
	protected void addPeriod(Period period){
		getDestination().getPeriod().add(period);
		listeners.firePropertyChange(PROPERTY_PERIOD, null, period);
	}
	
	protected void removePeriod(Period period){
		if (getDestination().getPeriod().remove(period)){
			listeners.firePropertyChange(PROPERTY_PERIOD, period, null);
		}
	}
	
	protected void addProperty(Property property){
		getDestination().getProperty().add(property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	protected void removeProperty(Property property){
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
	
	public boolean isEnabled(){
		return getDestination().isEnabled();
	}
	
}
