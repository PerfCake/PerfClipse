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

import java.util.Set;

import org.perfcake.message.generator.AbstractMessageGenerator;
import org.perfcake.message.sender.AbstractSender;
import org.perfcake.reporting.destinations.Destination;
import org.perfcake.reporting.reporters.Reporter;
import org.perfcake.validation.MessageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfCakeComponents {
	
	final static Logger log = LoggerFactory.getLogger(PerfCakeComponents.class);

	private static final String PERFCAKE_BUNDLE = "org.perfcake";
	private static final String PERFCAKE_SENDER_PACKAGE = "org.perfcake.message.sender";
	private static final String PERFCAKE_GENERATOR_PACKAGE = "org.perfcake.message.generator";
	private static final String PERFCAKE_REPORTER_PACKAGE = "org.perfcake.reporting.reporters";
	private static final String PERFCAKE_DESTINATION_PACKAGE = "org.perfcake.reporting.destinations";
	private static final String PERFCAKE_VALIDATOR_PACKAGE = "org.perfcake.validation";


	private static PerfCakeComponents instance;
	
	private Set<Class<? extends AbstractSender>> senders;
	private Set<Class<? extends AbstractMessageGenerator>> generators;
	private Set<Class<? extends Reporter>> reporters;
	private Set<Class<? extends Destination>> destinations;
	private Set<Class<? extends MessageValidator>> validators;
	
	
	private PerfCakeComponents() throws PerfClipseScannerException {
		ComponentScanner scanner = new ComponentScanner(PERFCAKE_BUNDLE);
		senders = scanner.scanForComponent(PERFCAKE_SENDER_PACKAGE, AbstractSender.class);
		generators = scanner.scanForComponent(PERFCAKE_GENERATOR_PACKAGE, AbstractMessageGenerator.class);
		reporters = scanner.scanForComponent(PERFCAKE_REPORTER_PACKAGE, Reporter.class);
		destinations = scanner.scanForComponent(PERFCAKE_DESTINATION_PACKAGE, Destination.class);
		validators = scanner.scanForComponent(PERFCAKE_VALIDATOR_PACKAGE, MessageValidator.class);
		log.debug("Components included in PerfCake has been loaded.");
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

}
