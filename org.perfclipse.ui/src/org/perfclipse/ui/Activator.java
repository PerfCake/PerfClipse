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

package org.perfclipse.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.reflect.PerfCakeComponents;
import org.perfclipse.core.reflect.PerfClipseScannerException;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	
	private Logger logger;

	// The plug-in ID
	public static final String PLUGIN_ID = "org.perfclipse.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
		// creates singleton of PerfCake components
		logger = new Logger(getLog(), PLUGIN_ID);
		plugin = this;
		try {
			PerfCakeComponents.getInstance();
		} catch (PerfClipseScannerException e) {
			logger.error("Cannot parse PerfCake components", e);
			MessageDialog.openError(getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Cannot parse PerfCake components", "Automatically loaded components from PerfCake will not be available");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		logger.info("PerfClipse UI bundle has been running");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		logger.info("Colors were successfully disposed.");
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
	
	/**
	 * 
	 * @return returns logger instance
	 */
	public Logger getLogger() {
		return logger;
	}

}
