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

package org.perfclipse.gef;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.perfclipse.core.logging.Logger;

/**
 * @author Jakub Knetl
 *
 */
public class GEFActivator extends AbstractUIPlugin {

	private Logger logger;

	/**
	 * ColorUtils instance. Used for disposing colors.
	 */
//	private ColorUtils colorUtils;

	// The plug-in ID
	public static final String PLUGIN_ID = "org.perfclipse.gef"; //$NON-NLS-1$

	// The shared instance
	private static GEFActivator plugin;
	
	/**
	 * The constructor
	 */
	public GEFActivator() {
		// creates singleton of PerfCake components
		logger = new Logger(getLog(), PLUGIN_ID);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		logger.info("PerfClipse UI bundle has been running");
//		colorUtils = ColorUtils.getInstance();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
//		colorUtils.dispose();
		logger.info("Colors were successfully disposed.");
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static GEFActivator getDefault() {
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
