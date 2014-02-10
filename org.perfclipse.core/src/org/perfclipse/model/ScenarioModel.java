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

import org.perfcake.model.Header;
import org.perfcake.model.Property;
import org.perfcake.model.Scenario;

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
		if (model == null){
			throw new IllegalArgumentException("Scenario model must not be null.");
		}
		listeners = new PropertyChangeSupport(this);
		this.generator = new Generator(model.getGenerator());
		this.messages = new Messages(model.getMessages());
		this.properties = new Properties(model.getProperties());
		this.sender = new Sender(model.getSender());
		this.reporting = new Reporting(model.getReporting());
		this.validation = new Validation(model.getValidation());
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
			if (generator == null){
				throw new IllegalArgumentException("Generator must not be null.");
			}
			this.clazz = generator.getClazz();
			this.threads = generator.getThreads();
			this.property = generator.getProperty();
			if (generator.getProperty() != null){
				property = new ArrayList<>();
				for (Property p : generator.getProperty()){
					property.add(new PropertyModel(p));
				}
			}
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
				if (run == null){
					throw new IllegalArgumentException("Run must not be null.");
				}
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
		public  static final String PROPERTY_MESSAGE = "messages-message";
		
		public Messages(){
			super();
			listeners = new PropertyChangeSupport(this);
		}
		
		public Messages(Scenario.Messages messages){
			this();
			if (messages == null){
				throw new IllegalArgumentException("Messages must not be null.");
			}
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
			
			public static final String PROPERTY_HEADERS = "message-header";
			public static final String PROPERTY_PROPERTIES = "message-property";
			public static final String PROPERTY_VALIDATOR_REFS = "message-validator-ref";
			public static final String PROPERTY_URI = "message-uri";
			public static final String PROPERTY_MULTIPLICITY = "message-multiplicity";

			private PropertyChangeSupport listeners;
			
			public Message(){
				super();
				listeners = new PropertyChangeSupport(this);
				
			}
			public Message(Scenario.Messages.Message message){
				this();
				if (message == null){
					throw new IllegalArgumentException("Message must not be null");
				}
				if (message.getHeader() != null){
					this.header = new ArrayList<>();
					for (Header h : message.getHeader()){
						this.header.add(new HeaderModel(h));
					}
				}
				this.multiplicity = message.getMultiplicity();
				if (message.getProperty() != null){
					this.property = new ArrayList<>();
					for (Property p : message.getProperty()){
						this.property.add(new PropertyModel(p));
					}
				}
				this.uri = message.getUri();
				
				if (message.getValidatorRef() != null){
					this.validatorRef = new ArrayList<>();
					for (org.perfcake.model.Scenario.Messages.Message.ValidatorRef ref : message.getValidatorRef()){
						validatorRef.add(new ValidatorRef(ref));
					}
				}
			}
			
			public void addPropertyChangeListener(PropertyChangeListener listener){
				listeners.addPropertyChangeListener(listener);
			}
			
			public void removePropertyChangeListener(PropertyChangeListener listener){
				listeners.removePropertyChangeListener(listener);
			}
			
			public void addProperty(Property property){
				getProperty().add(property);
				listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
			}
			
			public void removeProperty(Property property){
				if (getProperty().remove(property)){
					listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
				}
			}
			
			public void addHeader(Header header){
				getHeader().add(header);
				listeners.firePropertyChange(PROPERTY_HEADERS, null, header);
			}
			
			public void removeHeader(Header header){
				if (getHeader().remove(header)){
					listeners.firePropertyChange(PROPERTY_HEADERS, header, null);
				}
			}
			
			public void addValidatorRef(ValidatorRef ref){
				getValidatorRef().add(ref);
				listeners.firePropertyChange(PROPERTY_VALIDATOR_REFS, null, ref);
			}
			
			public void removeValidatorRef(ValidatorRef ref){
				if(getValidatorRef().remove(ref)){
					listeners.firePropertyChange(PROPERTY_VALIDATOR_REFS, ref, null);
				}
			}
			
			@Override
			public void setUri(String uri){
				String oldUri = getUri();
				super.setUri(uri);
				listeners.firePropertyChange(PROPERTY_URI, oldUri, uri);
			}
			
			@Override
			public void setMultiplicity(String multiplicity){
				String oldMultiplicity = getMultiplicity();
				super.setMultiplicity(multiplicity);
				listeners.firePropertyChange(PROPERTY_MULTIPLICITY, oldMultiplicity, multiplicity);
			}
			
			public static class ValidatorRef extends Scenario.Messages.Message.ValidatorRef{
				
				public static final String PROPERTY_ID = "validatorRef-id";
				
				private PropertyChangeSupport listeners;
				
				public ValidatorRef(){
					super();
					listeners = new PropertyChangeSupport(this);
				}
				
				public ValidatorRef(org.perfcake.model.Scenario.Messages.Message.ValidatorRef validatorRef){
					if (validatorRef == null){
						throw new IllegalArgumentException("ValidatorRef must not be null");
					}
					this.id = validatorRef.getId();
				}
				
				public void addPropertyChangeListener(PropertyChangeListener listener){
					listeners.addPropertyChangeListener(listener);
				}
				
				public void removePropertyChangeListener(PropertyChangeListener listener){
					listeners.removePropertyChangeListener(listener);
				}

				@Override
				public void setId(String value) {
					String oldValue = getId();
					super.setId(value);
					listeners.firePropertyChange(PROPERTY_ID, oldValue, value);
				}
			}
		}
	}

	public static class Sender extends Scenario.Sender{

		public static final String PROPERTY_CLASS = "sender-class";
		public static final String PROPERTY_PROPERTIES= "sender-property";
		
		private PropertyChangeSupport listeners;
		
		public Sender(){
			super();
			listeners = new PropertyChangeSupport(this);
		}

		public Sender(Scenario.Sender sender){
			this();
			if (sender == null){
				throw new IllegalArgumentException("Sender must not be null.");
			}
			if (sender.getProperty() != null){
				property = new ArrayList<>();
				for (Property p : sender.getProperty()){
					property.add(new PropertyModel(p));
				}
			}
			clazz = sender.getClazz();
		}
		
		public void addPropertyChangeListener(PropertyChangeListener listener){
			listeners.addPropertyChangeListener(listener);
		}
		
		public void removePropertyChangeListener(PropertyChangeListener listener){
			listeners.removePropertyChangeListener(listener);
		}

		public void setClass(String clazz){
			String oldClazz = getClazz();
			super.setClazz(clazz);
			listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
		}
		
		public void addProperty(Property property){
			getProperty().add(property);
			listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
		}
		
		public void removeProperty(Property property){
			if (getProperty().remove(property)){
				listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
			}
		}
		
	}

	public static class Validation extends Scenario.Validation{

		public static final String PROPERTY_VALIDATORS = "validation-validators";
		
		private PropertyChangeSupport listeners;
		
		public Validation(){
			super();
			listeners = new PropertyChangeSupport(this);
		}

		public Validation(org.perfcake.model.Scenario.Validation validation){
			this();
			if (validation == null){
				throw new IllegalArgumentException("Validation must not be null.");
			}
			if (validation.getValidator() != null){
				validator = new ArrayList<>();
				for (org.perfcake.model.Scenario.Validation.Validator validator : validation.getValidator()){
					this.validator.add(new Validator(validator));
				}
			}
		}
		
		public void addPropertyChangeListener(PropertyChangeListener listener){
			listeners.addPropertyChangeListener(listener);
		}
		

		public void removePropertyChangeListener(PropertyChangeListener listener){
			listeners.removePropertyChangeListener(listener);
		}
		
		public void addValidator(Validator validator){
			getValidator().add(validator);
			listeners.firePropertyChange(PROPERTY_VALIDATORS, null, validator);
		}
		
		public void removeValidator(Validator validator){
			if (getValidator().remove(validator)){
				listeners.firePropertyChange(PROPERTY_VALIDATORS, validator, null);
			}
		}
		
		public static class Validator extends Scenario.Validation.Validator{
			
			public static final String PROPERTY_CLASS = "validator-class";
			public static final String PROPERTY_ID = "validator-id";
			public static final String PROPERTY_VALUE = "validator-value";
			
			private PropertyChangeSupport listeners;
			
			public Validator(){
				super();
				listeners = new PropertyChangeSupport(this);
			}
			public Validator(Scenario.Validation.Validator validator){
				this();
				
				if (validator == null){
					throw new IllegalArgumentException("Validator must not be null");
				}
				this.clazz = validator.getClazz();
				this.id = validator.getId();
				this.value = validator.getValue();
			}
			
			public void addPropertyChangeListener(PropertyChangeListener listener){
				listeners.addPropertyChangeListener(listener);
			}
			

			public void removePropertyChangeListener(PropertyChangeListener listener){
				listeners.removePropertyChangeListener(listener);
			}
			
			@Override
			public void setClazz(String clazz){
				String oldClazz = getClazz();
				super.setClazz(clazz);
				listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
			}
			
			@Override
			public void setId(String id){
				String oldId = getId();
				super.setId(id);
				listeners.firePropertyChange(PROPERTY_ID, oldId, id);
			}
			
			@Override
			public void setValue(String value){
				String oldValue = getValue();
				super.setValue(value);
				listeners.firePropertyChange(PROPERTY_VALUE, oldValue, value);
			}
		}
	}

	public static class Properties extends Scenario.Properties{


		public static final String PROPERTY_PROPERTIES = "properties-property";
		
		private PropertyChangeSupport listeners;
		
		public Properties(){
			super();
			listeners = new PropertyChangeSupport(this);
		}

		public Properties(Scenario.Properties properties) {
			this();
			if (properties == null){
				throw new IllegalArgumentException("Properties must not be null.");
			}
			if (properties.getProperty() != null){
				property = new ArrayList<>();
				
				for (Property p : properties.getProperty()){
					property.add(new PropertyModel(p));
				}
			}
		}

		public void addProperty(Property property){
			getProperty().add(property);
			listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
		}
		
		public void removeProperty(Property property){
			if (getProperty().remove(property)){
				listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
			}
		}
		
		public void addPropertyChangeListener(PropertyChangeListener listener){
			listeners.addPropertyChangeListener(listener);
		}
		
		public void removePropertyChangeListener(PropertyChangeListener listener){
			listeners.removePropertyChangeListener(listener);
		}
	}

	public static class Reporting extends Scenario.Reporting{
		public static final String PROPERTY_PROPERTIES = "reporting-property";
		public static final String PROPERTY_REPORTERS = "reporting-reporter";
		
		private PropertyChangeSupport listeners;
		
		public Reporting(){
			super();
			listeners = new PropertyChangeSupport(this);
		}
		
		public Reporting(Scenario.Reporting reporting){
			this();
			if (reporting == null){
				throw new IllegalArgumentException("Reporting must not be null.");
			}
			if (reporting.getReporter() != null){
				reporter = new ArrayList<>();
				for (Scenario.Reporting.Reporter r : reporting.getReporter()){
					reporter.add(new Reporter(r));
				}
			}
			if (reporting.getProperty() != null){
				property = new ArrayList<>();
				for (Property p : reporting.getProperty()){
					property.add(new PropertyModel(p));
				}
			}
		}
		
		public void addPropertyChangeListener(PropertyChangeListener listener){
			listeners.addPropertyChangeListener(listener);
		}
		
		public void removePropertyChangeListener(PropertyChangeListener listener){
			listeners.removePropertyChangeListener(listener);
		}
		
		public void addReporter(Reporter reporter){
			getReporter().add(reporter);
			listeners.firePropertyChange(PROPERTY_REPORTERS, null, reporter);
		}
		
		public void removeReporter(Reporter reporter){
			if (getReporter().remove(reporter)){
				listeners.firePropertyChange(PROPERTY_REPORTERS, reporter, null);
			}
		}
		
		public void addProperty(Property property){
			getProperty().add(property);
			listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
		}
		
		public void removeProperty(Property property){
			if (getProperty().remove(property)){
				listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
			}
		}
		
		public static class Reporter extends Scenario.Reporting.Reporter{
			public static final String PROPERTY_CLASS = "reporter-class";
			public static final String PROPERTY_DESTINATIONS = "reporter-destination";
			public static final String PROPERTY_PROPERTIES = "reporter-property";
			public static final String PROPERTY_ENABLED = "reporter-enabled";
			
			private PropertyChangeSupport listeners;

			public Reporter(){
				super();
				listeners = new PropertyChangeSupport(this);
			}
			public Reporter(Scenario.Reporting.Reporter reporter){
				this();
				
				if (reporter == null){
					throw new IllegalArgumentException("Reporter must not be null");
				}

				if (reporter.getDestination() != null){
					destination = new ArrayList<>();
					for (Scenario.Reporting.Reporter.Destination d : reporter.getDestination()){
						destination.add(new Destination(d));
					}
				}

				if (reporter.getProperty() != null){
					property = new ArrayList<>();
					for (Property p : reporter.getProperty()){
						property.add(new PropertyModel(p));
					}
				}
				
				this.clazz = reporter.getClazz();
				this.enabled = reporter.isEnabled();
			}
			
			public void addPropertyChangeListener(PropertyChangeListener listener){
				listeners.addPropertyChangeListener(listener);
			}
			
			public void removePropertyChangeListener(PropertyChangeListener listener){
				listeners.removePropertyChangeListener(listener);
			}
			
			public void setClass(String clazz){
				String oldClazz = getClazz();
				super.setClazz(clazz);
				listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
			}
			
			public void addDestination(Destination destination){
				getDestination().add(destination);
				listeners.firePropertyChange(PROPERTY_DESTINATIONS, null, destination);
			}
			
			public void removeDestionation(Destination destination){
				if (getDestination().remove(destination)){
					listeners.firePropertyChange(PROPERTY_DESTINATIONS, destination, null);
				}
			}
			
			public void addProperty(Property property){
				getProperty().add(property);
				listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
			}
			
			public void removeProperty(Property property){
				if (getProperty().remove(property)){
					listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
				}
			}
			
			public void setEnabled(boolean enabled){
				if (enabled != isEnabled()){
					super.setEnabled(enabled);
					listeners.firePropertyChange(PROPERTY_ENABLED, !enabled, enabled);
				}
			}
			
			public static class Destination extends Scenario.Reporting.Reporter.Destination{
				public static final String PROPERTY_PERIOD = "destination-period";
				public static final String PROPERTY_CLASS = "destination-class";
				public static final String PROPERTY_ENABLED = "destination-enabled";
				public static final String PROPERTY_PROPERTIES = "destination-property";
				
				private PropertyChangeSupport listeners;

				public Destination(){
					super();
					listeners = new PropertyChangeSupport(this);
				}
				public Destination(Scenario.Reporting.Reporter.Destination destination) {
					this();
					if (destination == null){
						throw new IllegalArgumentException("Destination must not be null");
					}
					
					if (destination.getProperty() != null){
						property = new ArrayList<>();
						for (Property p : destination.getProperty()){
							property.add(new PropertyModel(p));
						}
					}

					if (destination.getPeriod() != null){
						period = new ArrayList<>();
						for (Scenario.Reporting.Reporter.Destination.Period p : destination.getPeriod()){
							period.add(new Period(p));
						}
					}
					this.clazz = destination.getClazz();
					this.enabled = destination.isEnabled();

				}
				
				public void addPropertyChangeListener(PropertyChangeListener listener){
					listeners.addPropertyChangeListener(listener);
				}
				
				public void removePropertyChangeListener(PropertyChangeListener listener){
					listeners.removePropertyChangeListener(listener);
				}
				
				public void setClass(String clazz){
					String oldClazz = getClazz();
					super.setClazz(clazz);
					listeners.firePropertyChange(PROPERTY_CLASS, oldClazz, clazz);
				}
				
				public void addPeriod(Period period){
					getPeriod().add(period);
					listeners.firePropertyChange(PROPERTY_PERIOD, null, period);
				}
				
				public void removePeriod(Period period){
					if (getPeriod().remove(period)){
						listeners.firePropertyChange(PROPERTY_PERIOD, period, null);
					}
				}
				
				public void addProperty(Property property){
					getProperty().add(property);
					listeners.firePropertyChange(PROPERTY_PROPERTIES, null, property);
				}
				
				public void removeProperty(Property property){
					if (getProperty().remove(property)){
						listeners.firePropertyChange(PROPERTY_PROPERTIES, property, null);
					}
				}
				
				public void setEnabled(boolean enabled){
					if (enabled != isEnabled()){
						super.setEnabled(enabled);
						listeners.firePropertyChange(PROPERTY_ENABLED, !enabled, enabled);
					}
				}
				
				public static class Period extends Scenario.Reporting.Reporter.Destination.Period{
					public static final String PROPERTY_TYPE = "period-type";
					public static final String PROPERTY_VALUE= "period-value";

					private PropertyChangeSupport listeners;

					public Period(){
						super();
						listeners = new PropertyChangeSupport(this);
					}
					public Period(Scenario.Reporting.Reporter.Destination.Period period) {
						this();
						if (period == null){
							throw new IllegalArgumentException("Period must not be null.");
						}
						
						this.type = period.getType();
						this.value = period.getValue();
					}

					public void addPropertyChangeListener(PropertyChangeListener listener){
						listeners.addPropertyChangeListener(listener);
					}
					
					public void removePropertyChangeListener(PropertyChangeListener listener){
						listeners.removePropertyChangeListener(listener);
					}
					
					public void setType(String type){
						String oldType = getType();
						super.setType(type);
						listeners.firePropertyChange(PROPERTY_TYPE, oldType, type);
					}
					
					public void setValue(String value){
						String oldValue = getValue();
						super.setValue(value);
						listeners.firePropertyChange(PROPERTY_VALUE, oldValue, value);
					}
				}
			}
		}
	}
}
