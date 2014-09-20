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
import org.perfcake.model.Scenario.Validation.Validator;

public class ValidatorModel extends PerfClipseModel implements IPropertyContainer{

	public static final String PROPERTY_CLASS = "validator-class";
	public static final String PROPERTY_ID = "validator-id";
	public static final String PROPERTY_VALUE = "validator-value";
	public static final String PROPERTY_PROPERTIES = "validator-properties";
	
	private Validator validator;
	
	public ValidatorModel(Validator validator, ModelMapper mapper){
		super(mapper);
		if (validator == null){
			throw new IllegalArgumentException("Validator must not be null.");
		}
		
		this.validator = validator;
	}
	
	/**
	 * This method should not be used for modifying validator (in a way getValidator().setClass())
	 * since these changes would not fire PropertyChange getListeners() which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Validator
	 */
	public Validator getValidator() {
		return validator;
	}
	
	public void setClazz(String clazz){
		String oldClazz = getValidator().getClazz();
		getValidator().setClazz(clazz);
		getListeners().firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
	}
	
	public void setId(String id){
		String oldId = getValidator().getId();
		getValidator().setId(id);
		getListeners().firePropertyChange(PROPERTY_ID, oldId, id);
	}
	
	public void addProperty(Property Property){
		addProperty(getValidator().getProperty().size(), Property);
	}
	
	public void addProperty(int index, Property property){
		getValidator().getProperty().add(index, property);
		getListeners().firePropertyChange(PROPERTY_PROPERTIES, null, property);
	}
	
	public void removeProperty(Property property){
		if (getValidator().getProperty().remove(property)){
			getListeners().firePropertyChange(PROPERTY_PROPERTIES, property, null);
		}
	}
	
	public List<Property> getProperty(){
		return getValidator().getProperty();
	}


}
