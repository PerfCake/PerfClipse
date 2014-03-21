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

import java.util.List;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario.Sender;

public class SenderModel extends PerfClipseModel implements IPropertyContainer {

	public static final String PROPERTY_CLASS = "sender-class";
	public static final String PROPERTY_PROPERTIES= "sender-property";
	
	private Sender sender;
	
	public SenderModel(Sender sender, ModelMapper mapper){
		super(mapper);
		if (sender == null){
			throw new IllegalArgumentException("Sender must not be null");
		}
		this.sender = sender;
	}
	
	/**
	 * This method should not be used for modifying Sender (in a way getSender().setClass()))
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Sender
	 */
	public Sender getSender() {
		return sender;
	}

	public void setClazz(String clazz){
		String oldClazz = getSender().getClazz();
		getSender().setClazz(clazz);
		getListeners().firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
	}
	
	public void addProperty(Property Property){
		addProperty(getSender().getProperty().size(), Property);
	}
	
	public void addProperty(int index, Property property){
		getSender().getProperty().add(index, property);
		getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getSender().getProperty().remove(property)){
			getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	
	public List<Property> getProperty(){
		return getSender().getProperty();
	}
}
