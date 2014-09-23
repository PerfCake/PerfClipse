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

package org.perfclipse.core.scenario;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.FilenameUtils;
import org.perfcake.PerfCakeConst;
import org.perfcake.PerfCakeException;
import org.perfcake.scenario.Scenario;
import org.perfcake.scenario.ScenarioLoader;
import org.perfclipse.core.Activator;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.model.ScenarioModel;
import org.perfclipse.core.reflect.SchemaScanner;
import org.xml.sax.SAXException;

/**
 * ScenarioManger class provides methods for manipulating scenario.
 * @author Jakub Knetl
 *
 */
public class ScenarioManager {

	final static Logger log = Activator.getDefault().getLogger();
	
	//TODO : scenario is initialized in runScenario method and used for
	// stop in stopSceanario method which will be called from different threads
	// so this class should be revisited and maybe fixed for thread safe.
	private Scenario scenario;
	
	public void runScenario(URL scenarioURL) throws ScenarioException{

		if (scenarioURL == null){
			log.error("URL to scenario is null");
			throw new IllegalArgumentException("URL to scenario is null.");
		}

		try {
			String path = scenarioURL.getPath();
			String scenarioDir = FilenameUtils.getFullPath(path);
			String scenarioName = FilenameUtils.getName(FilenameUtils.removeExtension(path));

			//remove last slash in the path (PerfCake will append one slash)
			if (scenarioDir.lastIndexOf("/") == (scenarioDir.length() - 1))
				scenarioDir = scenarioDir.substring(0, scenarioDir.length() - 1);
			
			//Use system properties to specify folder with scenario
			System.setProperty(PerfCakeConst.SCENARIOS_DIR_PROPERTY, scenarioDir);
			scenario = new ScenarioLoader().load(scenarioName);
		} catch (PerfCakeException e) {
			log.error("Cannot load scenario", e);
			throw new ScenarioException("Cannot load scenario", e);
		} catch (Exception e) {
			log.error("Cannot build scenario.", e);
			throw new ScenarioException("Cannot build scenario.", e);
		}

		try {
			scenario.init();
			scenario.run();
		} catch (PerfCakeException e) {
			log.error("Error during scenario execution.", e);
			throw new ScenarioException("Error during scenario execution.", e);
		}
		
		try {
			scenario.close();
		} catch (PerfCakeException e) {
			log.error("Error during finishing scenario.", e);
			throw new ScenarioException("Error during finishing scenario.", e);
		}
	}
	
	public void stopScenario(){
		if (scenario != null){
			scenario.stop();
		} else{
			log.warn("Trying to stop null scenario");
		}
	}

	/**
	 * Creates {@link ScenarioModel} object representation of the scenario
	 * specified in XML file at scenarioURL parameter.
	 * @param scenarioURL - URL to scenario XML definition
	 * @return {@link ScenarioModel} of given XML definition
	 * @throws ScenarioException
	 */
	public ScenarioModel createModel(URL scenarioURL) throws ScenarioException {

		org.perfcake.model.Scenario model;

		if (scenarioURL == null){
			log.error("URL to scenario is null");
			throw new IllegalArgumentException("URL to scenario is null.");
		}

		try {

			SchemaFactory schemaFactory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			URL schemaUrl = SchemaScanner.getSchema();
			Schema schema = schemaFactory.newSchema(schemaUrl);

			JAXBContext context = JAXBContext .newInstance(org.perfcake.model.Scenario.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			model = (org.perfcake.model.Scenario) unmarshaller.unmarshal(scenarioURL);
			return new ScenarioModel(model);
		} catch(JAXBException e){
			throw new ScenarioException(e);
		} catch (IOException e) {
			throw new ScenarioException(e);
		} catch (SAXException e) {
			throw new ScenarioException(e);
		}
		
	}
	
	/**
	 * The createXML method converts scenario model into XML representation 
	 * according to PerfCake XML Schema and writes output to output stream out
	 * @param model model to be converted
	 * @param out OutputStream to which xml will be written
	 * @throws ScenarioException
	 */
	public void createXML(org.perfcake.model.Scenario model, OutputStream out) throws ScenarioException{
		try {
			JAXBContext context = JAXBContext.newInstance(org.perfcake.model.Scenario.class);
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			
			//obtain url to schema definition, which is located somewhere in PerfCakeBundle
			URL schemaUrl = SchemaScanner.getSchema();
			Schema schema = schemaFactory.newSchema(schemaUrl);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setSchema(schema);

			//add line breaks and indentation into output
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(model, out);
		} catch (JAXBException e) {
			log.error("JAXB error", e);
			throw new ScenarioException("JAXB error" ,e);
		} catch (MalformedURLException e) {
			log.error("Malformed url", e);
			throw new ScenarioException("Malformed url", e);
		} catch (SAXException e) {
			log.error("Cannot obtain schema definition", e);
			throw new ScenarioException("Cannot obtain schema definition", e);
		} catch (IOException e) {
			log.error("Cannot obtain XML schema file", e);
			throw new ScenarioException("Cannot obtain XML schema file", e);
		}
	}

}
