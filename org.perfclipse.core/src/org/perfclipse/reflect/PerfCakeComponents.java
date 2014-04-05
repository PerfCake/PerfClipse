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

package org.perfclipse.reflect;

import java.util.HashSet;
import java.util.Set;

import org.perfcake.message.generator.AbstractMessageGenerator;
import org.perfcake.message.sender.AbstractSender;
import org.perfcake.reporting.destinations.Destination;
import org.perfcake.reporting.reporters.Reporter;
import org.perfcake.validation.MessageValidator;
import org.perfclipse.Activator;
import org.perfclipse.PerfClipseConstants;
import org.perfclipse.logging.Logger;

public class PerfCakeComponents {
	
	final static Logger log = Activator.getDefault().getLogger();

	private static PerfCakeComponents instance;
	
	private Set<Class<? extends AbstractSender>> senders;
	private Set<Class<? extends AbstractMessageGenerator>> generators;
	private Set<Class<? extends Reporter>> reporters;
	private Set<Class<? extends Destination>> destinations;
	private Set<Class<? extends MessageValidator>> validators;
	
	private Set<String> senderNames;
	private Set<String> generatorNames;
	private Set<String> reporterNames;
	private Set<String> destinationNames;
	private Set<String> validatorNames;
	
	
	private PerfCakeComponents() throws PerfClipseScannerException {
		ComponentScanner scanner = new ComponentScanner(PerfClipseConstants.PERFCAKE_BUNDLE);
		senders = scanner.scanForComponent(PerfClipseConstants.PERFCAKE_SENDER_PACKAGE, AbstractSender.class);
		generators = scanner.scanForComponent(PerfClipseConstants.PERFCAKE_GENERATOR_PACKAGE, AbstractMessageGenerator.class);
		reporters = scanner.scanForComponent(PerfClipseConstants.PERFCAKE_REPORTER_PACKAGE, Reporter.class);
		destinations = scanner.scanForComponent(PerfClipseConstants.PERFCAKE_DESTINATION_PACKAGE, Destination.class);
		validators = scanner.scanForComponent(PerfClipseConstants.PERFCAKE_VALIDATOR_PACKAGE, MessageValidator.class);
		
		senderNames = getStringReperesentation(senders); 
		generatorNames = getStringReperesentation(generators); 
		reporterNames = getStringReperesentation(reporters);
		destinationNames = getStringReperesentation(destinations);
		validatorNames = getStringReperesentation(validators); 

		log.info("Components included in PerfCake has been loaded.");
	}
	
	private <T> Set<String> getStringReperesentation(Set<Class<? extends T>> components) {
		HashSet<String> result = new HashSet<>(components.size());
		for (Class<?> c : components){
			result.add(clazzToString(c));
		}
		return result;
	}

	public static PerfCakeComponents getInstance() throws PerfClipseScannerException{
		if (instance == null){
			instance = new PerfCakeComponents();
		}
		
		return instance;
	}
	
	public Set<Class<? extends AbstractSender>> getSenders() {
		return senders;
	}

	public Set<Class<? extends AbstractMessageGenerator>> getGenerators() {
		return generators;
	}

	public Set<Class<? extends Reporter>> getReporters() {
		return reporters;
	}

	public Set<Class<? extends Destination>> getDestinations() {
		return destinations;
	}

	public Set<Class<? extends MessageValidator>> getValidators() {
		return validators;
	}
	
	
	public Set<String> getSenderNames() {
		return senderNames;
	}

	public Set<String> getGeneratorNames() {
		return generatorNames;
	}

	public Set<String> getReporterNames() {
		return reporterNames;
	}

	public Set<String> getDestinationNames() {
		return destinationNames;
	}

	public Set<String> getValidatorNames() {
		return validatorNames;
	}

	/**
	 * Return string representation of class type of PerfCake component
	 * For Components which are directly in PerfCake it returns simple name.
	 * @return name of PerfCake Component
	 */
	public static String clazzToString(Class<?> clazz){
		//TODO: Check if component is in PerfCake bundle
		return clazz.getSimpleName();
	}

}
