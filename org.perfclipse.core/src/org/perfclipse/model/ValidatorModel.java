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

import org.perfcake.model.Scenario.Validation.Validator;

public class ValidatorModel {

	public static final String PROPERTY_CLASS = "validator-class";
	public static final String PROPERTY_ID = "validator-id";
	public static final String PROPERTY_VALUE = "validator-value";
	
	private Validator validator;
	private PropertyChangeSupport listeners;
	
	public ValidatorModel(Validator validator){
		if (validator == null){
			throw new IllegalArgumentException("Validator must not be null.");
		}
		
		this.validator = validator;
		listeners = new PropertyChangeSupport(this);
	}
	
	/**
	 * This method should not be used for modifying validator (in a way getValidator().setClass())
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Validator
	 */
	public Validator getValidator() {
		return validator;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	

	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	public void setClass(String clazz){
		String oldClazz = getValidator().getClazz();
		getValidator().setClazz(clazz);
		listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
	}
	
	public void setId(String id){
		String oldId = getValidator().getId();
		getValidator().setId(id);
		listeners.firePropertyChange(PROPERTY_ID, oldId, id);
	}
	
	public void setValue(String value){
		String oldValue = getValidator().getValue();
		getValidator().setValue(value);
		listeners.firePropertyChange(PROPERTY_VALUE, oldValue, value);
	}


}
