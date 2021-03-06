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

package org.perfclipse.core.scenario;

/**
 * Exception class which represent error in XML file
 * which contains scenario according to given XML
 * schema definiton.
 * 
 * @author Jakub Knetl
 *
 */
public class ScenarioException extends Exception {

	private static final long serialVersionUID = -1845858271949880851L;

	public ScenarioException() {
	}

	public ScenarioException(String message) {
		super(message);
	}

	public ScenarioException(Throwable cause) {
		super(cause);
	}

	public ScenarioException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScenarioException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
