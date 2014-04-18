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

package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfclipse.core.model.HeaderModel;

/**
 * @author Jakub Knetl
 *
 */
public class EditHeaderNameCommand extends Command {
	private HeaderModel header;
	private String newName;	
	private String oldName;
	/**
	 * @param header
	 * @param newName
	 */
	public EditHeaderNameCommand(HeaderModel header, String newName) {
		super("Edit header name");
		this.header = header;
		this.newName = newName;
		oldName = header.getHeader().getName();
	}
	@Override
	public void execute() {
		header.setName(newName);
	}
	@Override
	public void undo() {
		header.setName(oldName);
	}
}
