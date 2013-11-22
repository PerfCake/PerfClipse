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

/**
 * Class XMLSchemaException represent error in XML Schema.
 * @author Jakub Knetl
 *
 */
public class XMLSchemaException extends Exception {

	private static final long serialVersionUID = 8929989321839987530L;

	/**
	 * Create XMLSchemaException without any information
	 */
	public XMLSchemaException() {
	}

	/**
	 * Create XMLSchemaException with detail message
	 * @param message information about Exception
	 */
	public XMLSchemaException(String message) {
		super(message);
	}

	/**
	 * Create XMLSchemaException with previous throwable cause
	 * @param cause
	 */
	public XMLSchemaException(Throwable cause) {
		super(cause);
	}

	/**
	 * Crates XMLSchemaException with detail message and throwable cause
	 * @param message information about exception
	 * @param cause 
	 */
	public XMLSchemaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Crates XMLSchemaException with detail message and throwable cause.
	 * @param message information about exception
	 * @param cause 
	 * @param enableSuprression
	 * @param cause
	 */
	public XMLSchemaException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
