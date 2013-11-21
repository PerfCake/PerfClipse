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
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.slf4j.LoggerFactory;

/**
 * ScenarioManger class provides methods for manipulating scenario.
 * @author Jakub Knetl
 *
 */
public class ScenarioManager {

	private Document scenario;
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(ScenarioManager.class);
	/**
	 * Load scenario from URL
	 * @param scenarioUrl URL to scenario
	 * @throws IOException 
	 * @throws ScenarioException - when resource at given url cannot be parsed as XML scenario.
	 */
	public void load(URL scenarioUrl) throws IOException, ScenarioException{
		SAXBuilder builder = new SAXBuilder();
		try {
			scenario = builder.build(scenarioUrl);
		} catch (JDOMException e) {
			log.error("Cannot parse given file as XML scenario." + scenarioUrl.toString());
			throw new ScenarioException("Cannot parse given file as XML scenario." + scenarioUrl.toString());
		}
	}
	
	/**
	 * Output scenario to Output stream out.
	 * @param out OutputStream used for output.
	 * @throws IOException
	 */
	public void save(OutputStream out) throws IOException{
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(scenario, out);
	}
}
