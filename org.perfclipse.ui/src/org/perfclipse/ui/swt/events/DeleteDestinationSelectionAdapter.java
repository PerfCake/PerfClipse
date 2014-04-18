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

package org.perfclipse.ui.swt.events;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.perfclipse.core.commands.DeleteDestinationCommand;
import org.perfclipse.core.model.DestinationModel;
import org.perfclipse.core.model.ReporterModel;

/**
 * @author Jakub Knetl
 *
 */
public class DeleteDestinationSelectionAdapter extends
		AbstractDeleteCommandSelectionAdapter {

	private DestinationModel destination;
	private ReporterModel reporter;

	
	/**
	 * @param commands
	 * @param viewer
	 * @param reporter
	 */
	public DeleteDestinationSelectionAdapter(List<Command> commands,
			TableViewer viewer, ReporterModel reporter) {
		super(commands, viewer);
		this.reporter = reporter;
	}
	
	@Override
	protected Command getCommand() {
		if (destination != null && reporter != null)
			return new DeleteDestinationCommand(reporter, destination);
		return null;
	}

	@Override
	public void handleDeleteData(Object element) {
		destination = (DestinationModel) element;
	}
}
