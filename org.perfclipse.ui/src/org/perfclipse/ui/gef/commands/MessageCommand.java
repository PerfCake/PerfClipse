package org.perfclipse.ui.gef.commands;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract message command.
 * 
 * @author Jakub Knetl
 *
 */
public abstract class MessageCommand extends Command {

	protected boolean syncResource;
	protected IProject project;
	protected Shell shell;

	public MessageCommand(String label) {
		super(label);
		syncResource = false;
	}

	/**
	 * Sets flags that message should be kept in sync with Messages directory
	 * in the given project
	 * @param project
	 * @param shell
	 */
	public void setsyncMessage(IProject project, Shell shell){
		if (project == null)
			throw new IllegalArgumentException("project cannot be null");
		if (shell == null)
			throw new IllegalArgumentException("shell cannot be null.");

		syncResource = true;
		this.project = project;
		this.shell = shell;
	}
	
	/**
	 * Sets flag that command execution should not care about
	 * resource syncing.
	 */
	public void setUnSyncMessage(){
		syncResource = false;
		this.project = null;
		this.shell = null;
	}
	
}