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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * Abstract PerfClipse model representation of PerfCake model.
 * @author Jakub Knetl
 *
 */
public class PerfClipseModel implements IMapperSupport{

	private PropertyChangeSupport listeners;
	private ModelMapper mapper;

	public PerfClipseModel(ModelMapper mapper) {
		if (mapper == null && !(this instanceof ScenarioModel)){
			throw new IllegalArgumentException("ModelMapper is null");
		}
		this.mapper = mapper;
		listeners = new PropertyChangeSupport(this);
	}


	/**
	 * 
	 * @return ModelMapper object.
	 */
	public ModelMapper getMapper() {
		return mapper;
	}

	/**
	 * 
	 * @return listeners 
	 */
	public PropertyChangeSupport getListeners() {
		return listeners;
	}
	
	/**
	 * Adds listener
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	/**
	 * Remove listener
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
}
