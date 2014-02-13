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

package org.perfclipse.scenario;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.perfcake.PerfCakeException;
import org.perfcake.Scenario;
import org.perfcake.ScenarioBuilder;
import org.perfcake.parser.ScenarioParser;
import org.perfclipse.model.ScenarioModel;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * ScenarioManger class provides methods for manipulating scenario.
 * @author Jakub Knetl
 *
 */
public class ScenarioManager {

	private Document scenarioDoc;
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(ScenarioManager.class);
	/**
	 * Load scenario from URL
	 * @param scenarioUrl URL to scenario
	 * @throws IOException 
	 * @throws ScenarioException - when resource at given url cannot be parsed as XML scenario.
	 */
	public void load(URL scenarioUrl) throws IOException, ScenarioException{
		if (scenarioUrl == null){
			log.error("Url to scenario is null.");
			throw new IllegalArgumentException("URL to scenario is null.");
		}
		SAXBuilder builder = new SAXBuilder();
		try {
			scenarioDoc = builder.build(scenarioUrl);
		} catch (JDOMException e) {
			log.error("Cannot parse given file as XML scenario." + scenarioUrl.toString(), e);
			throw new ScenarioException("Cannot parse given file as XML scenario." + scenarioUrl.toString(), e);
		}
		log.debug("Sceanrio loaded: " + scenarioUrl.toString());
	}
	
	/**
	 * Output scenario to Output stream out.
	 * @param out OutputStream used for output.
	 * @throws IOException
	 */
	public void save(OutputStream out) throws IOException{
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(scenarioDoc, out);
		log.debug("Scenario saved.");
	}
	
	public void runScenario(URL scenarioURL) throws ScenarioException{

		if (scenarioURL == null){
			log.error("URL to scenario is null");
			throw new IllegalArgumentException("URL to scenario is null.");
		}

		Scenario scenario;
		ScenarioModel model;

		model = createModel(scenarioURL);

		try {
			scenario = new ScenarioBuilder().load(model.getScenario()).build();
		} catch (PerfCakeException e) {
			log.error("Cannot load scenario", e);
			throw new ScenarioException("Cannot load scenario", e);
		} catch (Exception e) {
			log.error("Cannot build scenario.", e);
			throw new ScenarioException("Cannot build scenario.", e);
		}

		try {
			log.debug("Trying to execute scenario");
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

	public ScenarioModel createModel(URL scenarioURL) throws ScenarioException {

		org.perfcake.model.Scenario model;

		if (scenarioURL == null){
			log.error("URL to scenario is null");
			throw new IllegalArgumentException("URL to scenario is null.");
		}

		try {
			model = new ScenarioParser(scenarioURL).parse();
		} catch (PerfCakeException e) {
			log.error("Cannot load scenario", e);
			throw new ScenarioException("Cannot load scenario", e);
		}
		return new ScenarioModel(model);
	}
	
	public void createXML(org.perfcake.model.Scenario scenario, OutputStream out) throws ScenarioException{
		try {
			JAXBContext context;
			context = JAXBContext.newInstance(org.perfcake.model.Scenario.class);
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new URL("http://schema.perfcake.org/perfcake-scenario-" + Scenario.VERSION + ".xsd"));
			Marshaller marshaller = context.createMarshaller();
			marshaller.setSchema(schema);
			//add line breaks and indentation into output
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(true));
			marshaller.marshal(scenario, out);
		} catch (JAXBException e) {
			log.error("JAXB error", e);
			throw new ScenarioException(e.getMessage(),e.getCause());
		} catch (MalformedURLException e) {
			log.error("Malformed url", e);
			throw new ScenarioException(e.getMessage(), e.getCause());
		} catch (SAXException e) {
			log.error("Cannot obtain schema definition", e);
			throw new ScenarioException(e.getMessage(), e.getCause());
		}
	}

}
