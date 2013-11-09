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

import java.math.BigInteger;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.XSType;
import com.sun.xml.xsom.parser.XSOMParser;

/**
 * The SchemaManager class introduces methods for retrieving information
 * about elements from XML schema.
 * 
 * @author Jakub Knetl
 */
public class SchemaManager {
	
	/**
	 * Represents infinity for values which can be from 0 to infinity.
	 * e. g. maxOccurs attribute in XML Schema.
	 */
	public static final BigInteger INFINITY = BigInteger.valueOf(-1);
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(SchemaManager.class);

	private XSSchemaSet schema;
	private Map<String, XSParticle> elementPathMap;

	private Map<String, XSElementDecl> elementDeclMap;
	
	/**
	 * Uses XSOM parser to parse XML schema given by URL.
	 * @param schemaFile location of XML schema
	 * @throws SAXException
	 */
	public SchemaManager(URL schemaFile) throws SAXException {
		if (schemaFile == null){
			throw new IllegalArgumentException("URL to XML Schema is null");
		}
		XSOMParser parser = new XSOMParser();
		try{
			parser.parse(schemaFile);
			schema = parser.getResult();
		} catch (SAXException e){
			log.error("Cannot parse schema file: " + schemaFile.getPath());
			throw e;
		}
		elementPathMap = new HashMap<String, XSParticle>();
		elementDeclMap = new HashMap<String, XSElementDecl>();
		findElements();
	}
	
	/**
	 * Returns all element names defined by XML schema. 
	 * 
	 * @return Set of all element names contained int parsed XML schema.
	 */
	public Set<String> getElementNames(){
		return elementDeclMap.keySet();
	}
	
	/**
	 * Returns all element paths defined by XML schema. 
	 * 
	 * @return Set of all element paths contained int parsed XML schema.
	 */
	public Set<String> getElementPaths(){
		return elementPathMap.keySet();
	}
	/*
	 * Returns minOccurs of element defined in XMLSchema.
	 * @param elementName name of the element
	 * @return minOccurs value from XMLSchema
	 * @throws XMLSchemaException if elementName not found in schema
	 */
	public BigInteger getMinOccurs(String elementPath) throws XMLSchemaException{
		if (elementPath == null){
			throw new IllegalArgumentException("Element name is null");
		}
		
		XSParticle particle = elementPathMap.get(elementPath); 

		if (particle == null)
			throw new XMLSchemaException("Element: " + elementPath + " not found in XML Schema.");
		
		
		return particle.getMinOccurs();
	}
	
	/**
	 * Returns minOccurs of element defined in XMLSchema.
	 * @param elementName name of the element
	 * @return minOccurs value from XMLSchema
	 * @throws XMLSchemaException if elementName not found in schema
	 */
	public BigInteger getMaxOccurs(String elementPath) throws XMLSchemaException{
		if (elementPath == null){
			throw new IllegalArgumentException("Element name is null");
		}
		
		XSParticle particle = elementPathMap.get(elementPath); 

		if (particle == null)
			throw new XMLSchemaException("Element: " + elementPath + " not found in XML Schema.");
		
		BigInteger maxOccurs = particle.getMaxOccurs();
		if(maxOccurs.compareTo(BigInteger.ZERO) == -1){
			return INFINITY;
		}
		else{
			return maxOccurs;
		}
	}
	
	
	/**
	 * Recursively goes through parsed xml schema and builds
	 * map<ElementName, ElementType>
	 * @param schemaSet
	 * @return Map of element names and type declaration.
	 */
	private void findElements(){
		Iterator<XSElementDecl> it = schema.iterateElementDecls();
		String path= "/";
		while(it.hasNext()){
			XSElementDecl elementDecl = (XSElementDecl) it.next();
			XSType type = elementDecl.getType();
			if (type.isComplexType()){
				elementDeclMap.put(elementDecl.getName(), elementDecl);
				XSContentType contentType = type.asComplexType().getContentType();
				XSParticle particle = contentType.asParticle();

				String tmpPath = path + elementDecl.getName();
				if (particle != null){
					//elementPathMap.put(tmpPath, particle);
					findElements(particle, tmpPath);
				}
			}
			
		}
	}

	private void findElements(XSParticle particle, String path) {

		XSTerm pTerm = particle.getTerm();
		if (pTerm.isElementDecl()){
			XSElementDecl elementDecl = pTerm.asElementDecl();
			XSType type = elementDecl.getType();
			if (type.isComplexType()){
				elementDeclMap.put(elementDecl.getName(), elementDecl);
				XSContentType contentType = type.asComplexType().getContentType();

				String tmpPath = path + "/" + elementDecl.getName();
				elementPathMap.put(tmpPath, particle);
				XSParticle particleChild = contentType.asParticle();
				if (particleChild != null){
					findElements(particleChild, tmpPath);
				}
			}
		} else if (pTerm.isModelGroup()){
			XSModelGroup modelGroup = pTerm.asModelGroup();
			XSParticle[] particleArray = modelGroup.getChildren();
			for(XSParticle p: particleArray){
				if (particle != null){
					findElements(p, path);
				}
			}
			
		}
	}
}
