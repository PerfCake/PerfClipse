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

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Reporting;
import org.perfcake.model.Scenario.Reporting.Reporter;

public class ReportingModel {

	public static final String PROPERTY_PROPERTIES = "reporting-property";
	public static final String PROPERTY_REPORTERS = "reporting-reporter";
	
	private PropertyChangeSupport listeners;
	private Reporting reporting;
	
	protected List<PropertyModel> propertyModel;
	protected List<ReporterModel> reporterModel;
	
	public ReportingModel(Reporting reporting){
		this.reporting = reporting;
		listeners = new PropertyChangeSupport(this);
		
		propertyModel = new ArrayList<>();
		reporterModel = new ArrayList<>();

		if (reporting != null){
			if (reporting.getProperty() != null){
				for (Property p : reporting.getProperty()){
					propertyModel.add(new PropertyModel(p));
				}
			}
			if (reporting.getReporter() != null){
				for (Reporter r : reporting.getReporter()){
					reporterModel.add(new ReporterModel(r));
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
	
	public void addReporterModel(ReporterModel reporterModel){
		this.reporterModel.add(reporterModel);
		this.addReporter(reporterModel.getReporter());
	}
	
	public void removeReporterModel(ReporterModel reporterModel){
		this.reporterModel.remove(reporterModel);
		this.removeReporter(reporterModel.getReporter());
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
	public List<ReporterModel> getReporterModel() {
		return reporterModel;
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
	
	protected void addReporter(Reporter reporter){
		getReporting().getReporter().add(reporter);
		listeners.firePropertyChange(PROPERTY_REPORTERS, null, reporter);
	}
	
	protected void removeReporter(Reporter reporter){
		if (getReporting().getReporter().remove(reporter)){
			listeners.firePropertyChange(PROPERTY_REPORTERS, reporter, null);
		}
	}
	
	protected void addProperty(Property property){
		if (reporting == null){
			reporting = createReporting();
		}
		getReporting().getProperty().add(property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	protected void removeProperty(Property property){
		if (getReporting().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	private Scenario.Reporting createReporting(){
		ObjectFactory f = new ObjectFactory();
		return f.createScenarioReporting();
	}
}
