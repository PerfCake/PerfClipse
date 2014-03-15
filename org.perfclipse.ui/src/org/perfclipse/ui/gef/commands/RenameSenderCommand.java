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
import org.perfclipse.model.SenderModel;

/**
 * @author Jakub Knetl
 *
 */
public class RenameSenderCommand extends Command {
	
	private SenderModel sender;
	private String oldClazz;
	private String newClazz;
	/**
	 * @param sender
	 * @param newClazz
	 */
	public RenameSenderCommand(SenderModel sender, String newClazz) {
		super("Rename sender");
		this.sender = sender;
		this.newClazz = newClazz;
		this.oldClazz = sender.getSender().getClazz();
	}

	@Override
	public void execute() {
		sender.setClazz(newClazz);
	}

	@Override
	public void undo() {
		sender.setClazz(oldClazz);
	}
	
}
