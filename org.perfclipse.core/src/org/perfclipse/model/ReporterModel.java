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
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;

public class ReporterModel {

	public static final String PROPERTY_CLASS = "reporter-class";
	public static final String PROPERTY_DESTINATIONS = "reporter-destination";
	public static final String PROPERTY_PROPERTIES = "reporter-property";
	public static final String PROPERTY_ENABLED = "reporter-enabled";
	
	private PropertyChangeSupport listeners;
	private Reporter reporter;
	
	protected List<PropertyModel> propertyModel;
	protected List<DestinationModel> destinationModel;

	public ReporterModel(Reporter reporter){
		this.reporter = reporter;
		listeners = new PropertyChangeSupport(this);
		
		if (reporter != null){
			if (reporter.getProperty() != null){
				propertyModel = new ArrayList<>();
				for (Property p : reporter.getProperty()){
					propertyModel.add(new PropertyModel(p));
				}
			}
			if (reporter.getDestination() != null){
				destinationModel = new ArrayList<>();
				for (Destination d: reporter.getDestination()){
					destinationModel.add(new DestinationModel(d));
				}
			}
		}
	}
	
	public void addPropertyModel(PropertyModel propertyModel){
		this.propertyModel.add(propertyModel);
		this.addProperty(propertyModel.getProperty());
	}
	
	public void removePropertyModel(PropertyModel propertyModel){
		this.propertyModel.remove(propertyModel);
		this.removeProperty(propertyModel.getProperty());
	}
	
	public void addDestinationModel(DestinationModel destinationModel){
		this.destinationModel.add(destinationModel);
		this.addDestination(destinationModel.getDestination());
	}
	
	public void removeDestinationModel(DestinationModel destinationModel){
		this.destinationModel.remove(destinationModel);
		this.removeDestination(destinationModel.getDestination());
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
	 * Do not modify list using getList().add() since it will not fire
	 * propertyChangeEvent and the view of the model won't be refreshed.
	 * Use add* and remove* methods instead.
	 * @return List of PerfClipse model
	 */
	public List<DestinationModel> getDestinationModel() {
		return destinationModel;
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
	
	public String getClazz(){
		return getReporter().getClazz();
	}
	
	protected void addDestination(Destination destination){
		getReporter().getDestination().add(destination);
		listeners.firePropertyChange(PROPERTY_DESTINATIONS, null, destination);
	}
	
	protected void removeDestination(Destination destination){
		if (getReporter().getDestination().remove(destination)){
			listeners.firePropertyChange(PROPERTY_DESTINATIONS, destination, null);
		}
	}
	
	protected void addProperty(Property property){
		getReporter().getProperty().add(property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	protected void removeProperty(Property property){
		if (getReporter().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	protected void setEnabled(boolean enabled){
		if (enabled != getReporter().isEnabled()){
			getReporter().setEnabled(enabled);
			listeners.firePropertyChange(PROPERTY_ENABLED, !enabled, enabled);
		}
	}
	
	public boolean isEnabled(){
		return getReporter().isEnabled();
	}
}
