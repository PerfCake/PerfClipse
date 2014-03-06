package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;
import org.perfclipse.model.ReporterModel;

public class AddDestinationCommand extends Command {

	private Destination destination;
	private ReporterModel reporter;

	public AddDestinationCommand(Destination destination, ReporterModel reporter) {
		super("Add destination");
		
		this.destination = destination;
		this.reporter = reporter;
	}

	@Override
	public void execute() {
		reporter.addDestination(destination);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		reporter.removeDestionation(destination);
	}
	
	

}
