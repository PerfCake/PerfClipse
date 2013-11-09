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
import java.math.BigInteger;
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
		expectedPaths.add("/scenario/properties");
		expectedPaths.add("/scenario/properties/property");
		expectedPaths.add("/scenario/generator");
		expectedPaths.add("/scenario/generator/run");
		expectedPaths.add("/scenario/generator/property");
		expectedPaths.add("/scenario/sender");
		expectedPaths.add("/scenario/sender/property");

		
		assertEquals(expectedPaths, elementPaths);
	}
	
	@Test
	public void getMinOccurs() throws XMLSchemaException{
		BigInteger minOccurs;
		BigInteger expected;
		
		expected = BigInteger.valueOf(1);
		minOccurs = manager.getMinOccurs("/scenario/sender");
		assertEquals(minOccurs, minOccurs);

		
		expected = BigInteger.valueOf(2);
		minOccurs = manager.getMinOccurs("/scenario/generator/run");
		assertEquals(minOccurs, minOccurs);
		
		expected = BigInteger.ZERO;
		minOccurs = manager.getMinOccurs("/scenario/properties/property");
		assertEquals(expected, minOccurs);
	}
	
	
	@Test
	public void getMaxOccurs() throws XMLSchemaException{
		BigInteger minOccurs;
		BigInteger expected;
		
		expected = BigInteger.valueOf(1);
		minOccurs = manager.getMaxOccurs("/scenario/sender");
		assertEquals(minOccurs, minOccurs);

		
		expected = BigInteger.valueOf(5);
		minOccurs = manager.getMaxOccurs("/scenario/generator/run");
		assertEquals(minOccurs, minOccurs);
		
		expected = SchemaManager.INFINITY;
		minOccurs = manager.getMaxOccurs("/scenario/generator/property");
		assertEquals(expected, minOccurs);
	}


}
