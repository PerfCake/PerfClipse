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
import org.perfcake.model.Scenario.Properties;

public class PropertiesModel {


	public static final String PROPERTY_PROPERTIES = "properties-property";
	
	private Properties properties;
	private PropertyChangeSupport listeners;
	
	protected List<PropertyModel> propertyModel;

	public PropertiesModel(Properties properties) {
		this.properties = properties;
		listeners = new PropertyChangeSupport(this);

		propertyModel = new ArrayList<>();

		if (properties != null){
			if (properties.getProperty() != null){
				for (Property p : properties.getProperty()){
					propertyModel.add(new PropertyModel(p));
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
	 * This method should not be used for modifying properties (in a way getProperties().add()))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Properties 
	 */
	public Properties getProperties() {
		return properties;
	}
	
	protected void addProperty(Property property){
		if (property == null){
			properties = createProperties();
		}
		getProperties().getProperty().add(property);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	protected void removeProperty(Property property){
		if (getProperties().getProperty().remove(property)){
			listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	private Scenario.Properties createProperties(){
		ObjectFactory f = new ObjectFactory();
		return f.createScenarioProperties();
	}
}
