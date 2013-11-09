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
package org.perfclipse.schema;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;


/**
 * Class SchemaManagerTest provides unit test for class SchemaManager
 * 
 * @author Jakub Knetl
 *
 */
public class SchemaManagerTest {
	private static final String XSD_PATH = "/simpleSchema.xsd";
	SchemaManager manager;
	
	@Before
	public void setUp() throws IOException, SAXException{
		URL xsd = this.getClass().getResource(XSD_PATH);
		manager = new SchemaManager(xsd);
	}
	
	@Test
	public void findElements(){
		Set<String> elementNames = manager.getElementNames();

		Set<String> expectedNames = new HashSet<String>();
		expectedNames.add("property");
		expectedNames.add("header");
		expectedNames.add("scenario");
		expectedNames.add("properties");
		expectedNames.add("generator");
		expectedNames.add("run");
		expectedNames.add("sender");
		
		assertEquals(elementNames, expectedNames);
		
		Set<String> elementPaths = manager.getElementPaths();
		
		Set<String> expectedPaths = new HashSet<String>();
		expectedPaths.add("/header");
		expectedPaths.add("/property");
		expectedPaths.add("/scenario");
		expectedPaths.add("/scenario/properties");
		expectedPaths.add("/scenario/properties/property");
		expectedPaths.add("/scenario/generator");
		expectedPaths.add("/scenario/generator/run");
		expectedPaths.add("/scenario/generator/property");
		expectedPaths.add("/scenario/sender");
		expectedPaths.add("/scenario/sender/property");

		
		assertEquals(expectedPaths, elementPaths);
	}


}
