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

import org.perfclipse.core.Activator;
import org.perfclipse.core.logging.Logger;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * Component scanners scans PerfCake for usable components in the scenario.
 *
 * @author Jakub Knetl
 */
public class ComponentScanner {

	final static Logger log = Activator.getDefault().getLogger();

	public <T> Set<Class<? extends T>> scanForComponent(String packageName, Class<T> componentType) throws PerfClipseScannerException {
		Set<Class<? extends T>> classes = new HashSet<>();
		String packagePath = packageName.replace(".", "/");

		try (JarFile perfcakeJar = ReflectUtils.getPerfcakeLibrary()) {

			Enumeration<JarEntry> entries = perfcakeJar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().startsWith(packagePath) && entry.getName().endsWith(".class")) {
					String className = pathToFullyQualifiedName(entry.getName());
					Class<? extends T> component = getClassOfType(className, componentType);
					if (component != null) {
						classes.add(component);
					}
				}
			}
		} catch (IOException e) {
			log.warn("Cannot inspect perfcake jar for components", e);
		}
		return classes;
	}

	/**
	 * Parses class name out of String with full path and suffix inside of jar. Example:
	 *
	 * <p>
	 *	/org/mypackage/MyObject.class will be parsed to org.mypackage.MyObject
	 * </p>
	 * @param name path inside of jar to the class
	 * @return fully qualified class name or null if the class cannot be parsed
	 */
	private String pathToFullyQualifiedName(String name) {
		//remove leading slash
		if (name.length() > 0 && '/' == name.charAt(0)) {
			name = name.substring(1);
		}
		//remove .class extenstion
		int firstDot = name.indexOf('.');
		if (firstDot > 0){
			name = name.substring(0, firstDot);
		}

		String className = name.replace("/", ".");

		return className;
	}

	private <T> Class<? extends T> getClassOfType(String path, Class<T> type) throws PerfClipseScannerException {
		Class<?> clazz = getClassFromPackage(path);
		//if class is subclass of componentType
		if (type.isAssignableFrom(clazz)) {
			// if class is not abstract
			Class<? extends T> component = clazz.asSubclass(type);
			if (!Modifier.isAbstract(clazz.getModifiers()))
				return component;
		}
		return null;

	}

	private Class<?> getClassFromPackage(String path) throws PerfClipseScannerException {
		try {
			Class<?> clazz = Class.forName(path);
			return clazz;
		} catch (ClassNotFoundException e) {
			log.error("Cannot obtain class file for given file", e);
			throw new PerfClipseScannerException("Cannot obtain class file for given file", e);
		}
	}

}
