package org.perfclipse.ui.swt.jface;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public abstract class AbstractCommandTableViewer extends TableViewer {

	private List<Command> commands;


	public AbstractCommandTableViewer(Composite parent, int style,
			List<Command> commands) {
		super(parent, style);
		this.commands = commands;
		
		initializeViewer();
	}

	/**
	 * Calls initColumns() method and sets table header and lines visible
	 */
	protected void initializeViewer() {

		initColumns();

		final Table propertiesTable = getTable();
		propertiesTable.setHeaderVisible(true);
		propertiesTable.setLinesVisible(true);
	}
	
	/**
	 * This method is intended to create columns and adds them to tableViewer.
	 */
	protected abstract void initColumns();

	public List<Command> getCommands() {
		return commands;
	}
}