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

package org.perfclipse.core.reflect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.perfclipse.core.Activator;
import org.perfclipse.core.logging.Logger;

/**
 * ComponentScanner class is able to parse  bundle installed in eclipse
 * and find implementation of components (e. g. for PerfCake bundle it finds
 * Senders, Generators, ...)
 * 
 * @author Jakub Knetl
 */
public class ComponentScanner {
	
	final static Logger log = Activator.getDefault().getLogger();
	private String bundle;
	
	
	public ComponentScanner(String bundle){
		this.bundle = bundle;
	}
	


	//TODO : remove duplicate information about type (both T and componentType)
	public <T> Set<Class<? extends T>> scanForComponent(String packageName, Class<T> componentType) throws PerfClipseScannerException{
		Set<Class<? extends T>> classes = new HashSet<>();
		String packagePath = "/" + packageName.replace(".", "/");
		
		Bundle bundle = Platform.getBundle(this.bundle);
		URL bundleUrl = bundle.getEntry(packagePath);
		URL pathUrl = null;
		try {
			pathUrl = FileLocator.toFileURL(bundleUrl);
		} catch (IOException e) {
			log.error("Cannot obtain URL to package", e);
			throw new PerfClipseScannerException("Cannot obtain URL to package", e);
		}
		
		File packageDir = new File(pathUrl.getFile());
		
		if (packageDir.exists()){
			String[] files = packageDir.list();
			for (String filename : files){
				// TODO: if directory then recursion ???
				Class<? extends T> component = getClassOfType(packageName + "." + filename, componentType);
				if (component != null){
					classes.add(component);
				}
			}
		}
		
		return classes;
	}
	
	private <T> Class<? extends T> getClassOfType(String path, Class<T> type) throws PerfClipseScannerException{
		if (path.endsWith(".class")){
			//trim .class extension
			path= path.substring(0, path.length() - 6);
			Class<?> clazz = getClassFromPackage(path);
			//if class is subclass of componentType
			if (type.isAssignableFrom(clazz)){
				// if class is not abstract
				Class<? extends T> component = clazz.asSubclass(type);
				if (!Modifier.isAbstract(clazz.getModifiers()))
					return component;
			}
		}
		return null;
		
	}
	
	private Class<?> getClassFromPackage(String path) throws PerfClipseScannerException{
		try {
			Class<?> clazz = Class.forName(path);
			return clazz;
		} catch (ClassNotFoundException e) {
			log.error("Cannot obtain class file for given file", e );
			throw new PerfClipseScannerException("Cannot obtain class file for given file", e);
		}
	}

}
