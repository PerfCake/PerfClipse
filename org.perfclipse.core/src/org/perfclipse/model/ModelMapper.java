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

import java.util.HashMap;
import java.util.Map;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Generator.Run;
import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Header;
import org.perfcake.model.Scenario.Generator;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.model.Scenario.Properties;
import org.perfcake.model.Scenario.Reporting;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination.Period;
import org.perfcake.model.Scenario.Sender;
import org.perfcake.model.Scenario.Validation;
import org.perfcake.model.Scenario.Validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Class maps between PerfCake model object and PerfClipse model object container 
 * 
 * @author Jakub Knetl
 *
 */
public class ModelMapper {
	
	static final Logger log = LoggerFactory.getLogger(ModelMapper.class);

	private Map<Object, Object> map;
	private ScenarioModel scenario;

	
	public ModelMapper(ScenarioModel scenario){
		if (scenario == null){
			throw new IllegalArgumentException("Scenario must not be null");
		}
		map = new HashMap<>();
		this.scenario = scenario;
	};
	
	
	/**
	 * Returns PerfClipse model container for given perfcakeModel.
	 * It looks up for the perfcakeModel in map and return PerfClipse container.
	 * If it is not found then creates entry in the map and return this entry.
	 * 
	 * @param perfcakeModel PerfCake model
	 * @return PerfClipse model container of PerfCake model
	 */
	public Object getModelContainer(Object perfcakeModel){
		Object result = map.get(perfcakeModel) ;
		if (result == null){
			createEntry(perfcakeModel);
			result = map.get(perfcakeModel);
		}
		
		return result;
		
	}

	private void createEntry(Object perfcakeModel) {
		if (perfcakeModel instanceof Destination){
			map.put(perfcakeModel, new DestinationModel((Destination) perfcakeModel, this));
			return;
		}

		if (perfcakeModel instanceof Generator){
			map.put(perfcakeModel, new GeneratorModel((Generator) perfcakeModel, this));
			return;
		}
		
		if (perfcakeModel instanceof Header){
			map.put(perfcakeModel, new HeaderModel((Header) perfcakeModel, this));
			return;
		}
		
		if (perfcakeModel instanceof Message){
			map.put(perfcakeModel, new MessageModel((Message) perfcakeModel, this));
			return;
		}
		if (perfcakeModel instanceof Messages){
			map.put(perfcakeModel, new MessagesModel((Messages) perfcakeModel,
					scenario, this));
			return;
		}
		
		if (perfcakeModel instanceof Period){
			map.put(perfcakeModel, new PeriodModel((Period) perfcakeModel, this));
			return;
		}

		if (perfcakeModel instanceof Properties){
			map.put(perfcakeModel, new PropertiesModel((Properties) perfcakeModel,
					scenario, this));
			return;
		}

		if (perfcakeModel instanceof Property){
			map.put(perfcakeModel, new PropertyModel((Property) perfcakeModel, this));
			return;
		}
		
		if (perfcakeModel instanceof Reporter){
			map.put(perfcakeModel, new ReporterModel((Reporter) perfcakeModel, this));
			return;
		}
		
		if (perfcakeModel instanceof Reporting){
			map.put(perfcakeModel, new ReportingModel((Reporting) perfcakeModel,
					scenario, this));
			return;
		}
		
		if (perfcakeModel instanceof Run){
			map.put(perfcakeModel, new RunModel((Run) perfcakeModel, this));
			return;
		}
		
		if (perfcakeModel instanceof Scenario){
			map.put(perfcakeModel, scenario);
			return;
		}
		
		if (perfcakeModel instanceof Sender){
			map.put(perfcakeModel, new SenderModel((Sender) perfcakeModel, this));
			return;
		}
		
		if (perfcakeModel instanceof Validation){
			map.put(perfcakeModel, new ValidationModel((Validation) perfcakeModel,
					scenario, this));
			return;
		}
		
		if (perfcakeModel instanceof Validator){
			map.put(perfcakeModel, new ValidatorModel((Validator) perfcakeModel, this));
			return;
		}
		
		if (perfcakeModel instanceof ValidatorRef){
			map.put(perfcakeModel, new ValidatorRefModel((ValidatorRef) perfcakeModel, this));
			return;
		}
		
		log.warn("Unknown PerfCake model.", perfcakeModel);
		throw new IllegalArgumentException("Unknown PerfCake model.");
	}

}
