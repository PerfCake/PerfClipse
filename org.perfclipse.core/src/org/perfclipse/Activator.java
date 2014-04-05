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

package org.perfclipse;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.reflect.PerfClipseScannerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jakub Knetl
 *
 */
public class Activator extends Plugin {
	
	final static Logger log = LoggerFactory.getLogger(Activator.class);

	// The plug-in ID
	public static final String PLUGIN_ID = "org.perfclipse.core"; 

	// The shared instance
	private static Activator plugin;
	/**
	 * 
	 */
	public Activator() {
		super();
		try {
			PerfCakeComponents.getInstance();
		} catch (PerfClipseScannerException e) {
			log.error("Cannot parse PerfCake components", e);
		}
	}
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		log.debug("Core bundle has been running");
		plugin = this;
	}


	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	

}
