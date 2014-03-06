package org.perfclipse.ui.gef.commands;

import org.eclipse.gef.commands.Command;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.model.ReportingModel;

public class AddReporterCommand extends Command {

	private ReportingModel reporting;
	private Reporter newReporter;

	public AddReporterCommand(Reporter reporter, ReportingModel reporting) {
		super("Add reporter");
		this.newReporter = reporter;
		this.reporting = reporting;
	}
	
	@Override
	public void execute() {
		reporting.addReporter(newReporter);
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		reporting.removeReporter(newReporter);
	}
}
