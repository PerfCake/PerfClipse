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

import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Generator;
import org.perfcake.model.Scenario.Generator.Run;
import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Messages.Message;
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
			result = create(perfcakeModel);
		}
		
		return result;
		
	}

	/**
	 * Creates record of pair (PerfCake model, PerfClipse model) if it is
	 * not exists in the map.
	 * @param perfcakeModel
	 */
	public void createEntry(Object perfcakeModel) {
		if (map.get(perfcakeModel) != null)
			return;
		
		create(perfcakeModel);
	}
	
	/**
	 * Adds entry (PerfCake model, PerfClipse model) to map.
	 * 
	 * @param perfcakeMode
	 * @param perfclipseModel
	 */
	public void addEntry(Object perfcakeModel, Object perfclipseModel){
		map.put(perfcakeModel, perfclipseModel);
	}
	
	/**
	 * Remove entry from map.
	 * @param perfcakeModel key of entry
	 */
	public void removeEntry(Object perfcakeModel){
		map.remove(perfcakeModel);
	}

	private Object create(Object perfcakeModel){
		Object result;
		if (perfcakeModel instanceof Destination){
			result = new DestinationModel((Destination) perfcakeModel, this);
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Generator){
			result = new GeneratorModel((Generator) perfcakeModel, this);
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Header){
			result =  new HeaderModel((Header) perfcakeModel, this);
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Message){
			result =  new MessageModel((Message) perfcakeModel, this);	
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Messages){
			result = new MessagesModel((Messages) perfcakeModel, scenario, this);
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Period){
			result =  new PeriodModel((Period) perfcakeModel, this);	
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Properties){
			result = new PropertiesModel((Properties) perfcakeModel, scenario, this);
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Property){
			result =  new PropertyModel((Property) perfcakeModel, this);	
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Reporter){
			result =  new ReporterModel((Reporter) perfcakeModel, this);	
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Reporting){
			result =  new ReportingModel((Reporting) perfcakeModel, scenario, this);
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Run){
			result =  new RunModel((Run) perfcakeModel, this);	
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Scenario){
			map.put(perfcakeModel, scenario);
			return scenario;
		}

		if (perfcakeModel instanceof Sender){
			result =  new SenderModel((Sender) perfcakeModel, this);	
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Validation){
			result = new ValidationModel((Validation) perfcakeModel, scenario, this);
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof Validator){
			result =  new ValidatorModel((Validator) perfcakeModel, this);	
			map.put(perfcakeModel, result);
			return result;
		}

		if (perfcakeModel instanceof ValidatorRef){
			result =  new ValidatorRefModel((ValidatorRef) perfcakeModel, this);	
			map.put(perfcakeModel, result);
			return result;
		}
		
		log.warn("Unknown PerfCake model.", perfcakeModel);
		throw new IllegalArgumentException("Unknown PerfCake model.");
	}

}
