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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.annotation.Generated;

import org.perfcake.model.Property;
import org.perfcake.model.Scenario;
import org.perfcake.model.Scenario.Generator;
import org.perfcake.model.Scenario.Generator.Run;
import org.perfcake.model.Scenario.Messages;
import org.perfcake.model.Scenario.Properties;
import org.perfcake.model.Scenario.Reporting;
import org.perfcake.model.Scenario.Sender;
import org.perfcake.model.Scenario.Validation;

// TODO rewrite initialization (copy constructor) using reflection!!!
public class ScenarioModel extends Scenario {
	
	private PropertyChangeSupport listeners;
	public static final String PROPERTY_GENERATOR = "scenario-generator";
	public static final String PROPERTY_SENDER = "scenario-sender";
	public static final String PROPERTY_MESSAGES = "scenario-messages";
	public static final String PROPERTY_PROPERTIES = "scenario-properties";
	public static final String PROPERTY_REPORTING = "scenario-reporting";
	public static final String PROPERTY_VALIDATION = "scenario-validation";
	
	public ScenarioModel() {
		super();
		listeners = new PropertyChangeSupport(this);
	}
	
	public ScenarioModel(Scenario model){
		super();
		listeners = new PropertyChangeSupport(this);
		this.generator = new ScenarioModel.Generator(model.getGenerator());
		this.messages = new ScenarioModel.Messages(model.getMessages());
		this.properties = model.getProperties();
		this.sender = model.getSender();
		this.reporting = model.getReporting();
		this.validation = model.getValidation();
	}
	



	@Override
	public void setGenerator(Scenario.Generator value) {
		Scenario.Generator oldValue = getGenerator();
		super.setGenerator(value);
		listeners.firePropertyChange(PROPERTY_GENERATOR, oldValue, value);
	}

	@Override
	public void setMessages(Scenario.Messages value) {
		Scenario.Messages oldValue = getMessages();
		super.setMessages(value);
		listeners.firePropertyChange(PROPERTY_MESSAGES, oldValue, value);
	}

	@Override
	public void setProperties(Scenario.Properties value) {
		Scenario.Properties oldValue = getProperties();
		super.setProperties(value);
		listeners.firePropertyChange(PROPERTY_PROPERTIES, oldValue, value);
	}

	@Override
	public void setReporting(Scenario.Reporting value) {
		Scenario.Reporting oldValue = getReporting();
		super.setReporting(value);
		listeners.firePropertyChange(PROPERTY_REPORTING, oldValue, value);
	}

	@Override
	public void setSender(Scenario.Sender value) {
		Scenario.Sender oldValue = getSender();
		super.setSender(value);
		listeners.firePropertyChange(PROPERTY_SENDER, oldValue, value);
	}

	@Override
	public void setValidation(Scenario.Validation value) {
		Scenario.Validation oldValue = getValidation();
		super.setValidation(value);
		listeners.firePropertyChange(PROPERTY_VALIDATION, oldValue, value);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener){
		listeners.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		listeners.removePropertyChangeListener(listener);
	}
	

	public static class Generator extends Scenario.Generator{
		
		public final static String PROPERTY_CLASS = "generator-class";
		public final static String PROPERTY_THREADS = "generator-threads";
		public final static String PROPERTY_RUN = "generator-run";
		public final static String PROPERTY_PROPERTY = "generator-property";
		
		private PropertyChangeSupport listeners;
		
		public Generator(){
			super();
			listeners = new PropertyChangeSupport(this);
		}
		
		public Generator(Scenario.Generator generator){
			this();
			this.clazz = generator.getClazz();
			this.threads = generator.getThreads();
			this.property = generator.getProperty();
			this.run = new Run(generator.getRun());
		}

		public void addPropertyChangeListener(PropertyChangeListener listener){
			listeners.addPropertyChangeListener(listener);
		}
		
		public void removePropertyChangeListener(PropertyChangeListener listener){
			listeners.removePropertyChangeListener(listener);
		}

		@Override
		public void setThreads(String value) {
			String oldValue = getThreads();
			super.setThreads(value);
			listeners.firePropertyChange(PROPERTY_THREADS, oldValue, value);
		}

		@Override
		public void setClazz(String value) {
			String oldValue = getClazz();
			super.setClazz(value);
			listeners.firePropertyChange(PROPERTY_CLASS, oldValue, value);
		}

		@Override
		public void setRun(Scenario.Generator.Run value) {
			Scenario.Generator.Run oldValue = getRun();
			super.setRun(value);
			listeners.firePropertyChange(PROPERTY_RUN, oldValue, value);
		}
		
		public void addProperty(Property newProperty){
			getProperty().add(newProperty);
			listeners.firePropertyChange(PROPERTY_PROPERTY, null, newProperty);
		}
		
		public void removeProperty(Property property){
			if (getProperty().remove(property)){
				listeners.firePropertyChange(PROPERTY_PROPERTY, property, null);
			}
			
		}
		
		public static class Run extends Scenario.Generator.Run{
			
			public static final String PROPERTY_TYPE = "run-type";
			public static final String PROPERTY_VALUE = "run-value";
			private PropertyChangeSupport listeners;
			
			public Run(){
				super();
				listeners = new PropertyChangeSupport(this);
			}
			
			public Run(Scenario.Generator.Run run){
				this();
				this.type = run.getType();
				this.value = run.getValue();
			}

			public void addPropertyChangeListener(PropertyChangeListener listener){
				listeners.addPropertyChangeListener(listener);
			}

			public void removePropertyChangeListener(PropertyChangeListener listener){
				listeners.removePropertyChangeListener(listener);
			}

			@Override
			public void setType(String value) {
				String oldValue = getType();
				super.setType(value);
				listeners.firePropertyChange(PROPERTY_TYPE, oldValue, value);
			}

			@Override
			public void setValue(String value) {
				String oldValue = getValue();
				super.setValue(value);
				listeners.firePropertyChange(PROPERTY_VALUE, oldValue, value);
			}
			
		}
		
		
	}
	
	public static class Messages extends Scenario.Messages{
		private PropertyChangeSupport listeners;
		public  static final String PROPERTY_MESSAGE = "message";
		
		public Messages(){
			super();
			listeners = new PropertyChangeSupport(this);
		}
		
		public Messages(Scenario.Messages messages){
			this();
			if (messages.getMessage() != null){
				this.message = new ArrayList<>();
				for (Scenario.Messages.Message m : messages.getMessage()){
					this.message.add(new Message(m));
				}
			}
		}
		
		public void addMessage(Message m){
			getMessage().add(m);
			listeners.firePropertyChange(PROPERTY_MESSAGE, null, m);
		}
		
		public void removeMessage(Message m){
			if (getMessage().remove(m)){
				listeners.firePropertyChange(PROPERTY_MESSAGE, m, null);
			}
		}
		
		public void addPropertyChangeListener(PropertyChangeListener listener){
			listeners.addPropertyChangeListener(listener);
		}
		
		public void removePropertyChangeListener(PropertyChangeListener listener){
			listeners.removePropertyChangeListener(listener);
		}
		
		public static class Message extends Scenario.Messages.Message{
			public Message(){
				
			}
			public Message(Scenario.Messages.Message message){
				super();
				this.header = message.getHeader();
				this.multiplicity = message.getMultiplicity();
				this.property = message.getProperty();
				this.uri = message.getUri();
				this.validatorRef = message.getValidatorRef();
			}
			
		}
	}

}
