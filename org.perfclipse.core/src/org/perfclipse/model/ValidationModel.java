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

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Validation;
import org.perfcake.model.Scenario.Validation.Validator;

public class ValidationModel {

	public static final String PROPERTY_VALIDATORS = "validation-validators";
	
	private Validation validation;
	private PropertyChangeSupport listeners;
	
	public ValidationModel(Validation validation){
//		if (validation == null){
//			throw new IllegalArgumentException("Validation must not be null");
//		}
		this.validation = validation;
		listeners = new PropertyChangeSupport(this);
	}

	/**
	 * This method should not be used for modifying Validation (in a way getValidation().getValidator().add()))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Validation
	 */
	public Validation getValidation() {
		return validation;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	

	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	public void createValidation(){
		//no need to fire property change since it will not change the view
		if (validation == null){
			ObjectFactory f = new ObjectFactory();
			validation = f.createScenarioValidation();
		}
	}
	
	public void addValidator(Validator validator){
		getValidation().getValidator().add(validator);
		listeners.firePropertyChange(PROPERTY_VALIDATORS, null, validator);
	}
	
	public void removeValidator(Validator validator){
		if (getValidation().getValidator().remove(validator)){
			listeners.firePropertyChange(PROPERTY_VALIDATORS, validator, null);
		}
	}

}
