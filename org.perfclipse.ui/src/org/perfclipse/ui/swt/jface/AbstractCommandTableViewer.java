package org.perfclipse.ui.swt.jface;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public abstract class AbstractCommandTableViewer extends TableViewer {

	private List<Command> commands;

	public AbstractCommandTableViewer(Composite parent, List<Command> commands) {
		this(parent,SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.FULL_SELECTION , commands);
	}

	public AbstractCommandTableViewer(Composite parent, int style,
			List<Command> commands) {
		super(parent, style);
		this.commands = commands;
		
		initializeViewer();
	}

	/**
	 * Sets array content provider, calls initColumns() method and sets table header
	 *  and lines visible
	 */
	protected void initializeViewer() {
		setContentProvider(ArrayContentProvider.getInstance());

		initColumns();
		setColumnsSize();

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
	
	/**
	 * Method called in initializeViewer.
	 * Default implementation does nothing. 
	 */
	protected void setColumnsSize(){
		
	}
}