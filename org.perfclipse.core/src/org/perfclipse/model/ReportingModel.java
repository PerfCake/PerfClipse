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

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Reporting;
import org.perfcake.model.Scenario.Reporting.Reporter;

public class ReportingModel implements IPropertyContainer {

	public static final String PROPERTY_PROPERTIES = "reporting-property";
	public static final String PROPERTY_REPORTERS = "reporting-reporter";
	
	private PropertyChangeSupport listeners;
	private Reporting reporting;
	
	public ReportingModel(Reporting reporting){
//		if (reporting == null){
//			throw new IllegalArgumentException("Reporting must not be null.");
//		}
	this.reporting = reporting;
	listeners = new PropertyChangeSupport(this);
	}
	
	/**
	 * This method should not be used for modifying Reporting(in a way getReporting().addReporter()))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Reporting
	 */
	public Reporting getReporting() {
		return reporting;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	public void addReporter(Reporter reporter){
		addReporter(getReporting().getReporter().size(), reporter);
	}
	public void addReporter(int index, Reporter reporter){
		getReporting().getReporter().add(index, reporter);
		listeners.firePropertyChange(PROPERTY_REPORTERS, null, reporter);
	}
	
	public void removeReporter(Reporter reporter){
		if (getReporting().getReporter().remove(reporter)){
			listeners.firePropertyChange(PROPERTY_REPORTERS, reporter, null);
		}
	}
	
	public void createReporting(){
		//no need to fire property change since it will not change the view
		if (reporting == null){
			ObjectFactory f = new ObjectFactory();
			reporting = f.createScenarioReporting();
		}
	}
	
	public void addProperty(Property Property){
		addProperty(getReporting().getProperty().size(), Property);
	}
	
	public void addProperty(int index, Property property){
		getReporting().getProperty().add(index, property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getReporting().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public List<Property> getProperty(){
		return getReporting().getProperty();
	}
}
