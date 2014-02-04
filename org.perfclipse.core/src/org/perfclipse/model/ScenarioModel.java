/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2013 Jakub Knetl
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

import java.util.ArrayList;
import java.util.List;

import org.perfcake.model.Scenario;

public class ScenarioModel extends Scenario {
	
	private List<ScenarioChangeListener> listeners = new ArrayList<>();
	
	public ScenarioModel() {
		super();
	}
	
	public ScenarioModel(Scenario model){
		super();
		// TODO rewrite initialization using reflection!!!
		this.generator = model.getGenerator();
		this.messages = model.getMessages();
		this.properties = model.getProperties();
		this.sender = model.getSender();
		this.reporting = model.getReporting();
		this.validation = model.getValidation();
	}

	public void addScenarioListener(ScenarioChangeListener l){
		if (! listeners.contains(l)){
			listeners.add(l);
		}
	}
	
	public void removeScenarioListener(ScenarioChangeListener l){
		listeners.remove(l);
	}
	
	public void fireScenarioChanged(){
		for (ScenarioChangeListener l : listeners){
			l.ScenarioChanged();
		}
	}
}
