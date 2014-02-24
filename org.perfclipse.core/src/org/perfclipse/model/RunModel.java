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

import org.perfcake.model.Scenario.Generator.Run;

public class RunModel {
	public static final String PROPERTY_TYPE = "run-type";
	public static final String PROPERTY_VALUE = "run-value";
	private PropertyChangeSupport listeners;
	private Run run;
	
	public RunModel(Run run){
		if (run == null){
			throw new IllegalArgumentException("Run must not be null");
		}
		this.run = run;
		listeners = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	
	/**
	 * This method should not be used for modifying run (in a way getRun().setType(s))
	 * since these changes would not fire PropertyChange listeners which implies that
	 * the GEF View will not be updated according to these changes. Use set methods of this class instead.
	 * 
	 * @return PerfCake model of Run
	 */
	public Run getRun(){
		return run;
	}

	public void setType(String value) {
		String oldValue = getRun().getType();
		getRun().setType(value);
		listeners.firePropertyChange(PROPERTY_TYPE, oldValue, value);
	}

	public void setValue(String value) {
		String oldValue = getRun().getValue();
		getRun().setValue(value);
		listeners.firePropertyChange(PROPERTY_VALUE, oldValue, value);
	}



}
