package org.perfclipse.ui.gef.commands;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Shell;

/**
 * Abstract command with resource sync.
 * 
 * @author Jakub Knetl
 *
 */
public abstract class ResourceCommand extends Command {

	protected boolean syncResource;
	protected IProject project;
	protected Shell shell;

	public ResourceCommand(String label) {
		super(label);
		syncResource = false;
	}

	/**
	 * Sets flags that resource should be kept in sync 
	 * in the given project
	 * @param project
	 * @param shell
	 */
	public void setSyncResource(IProject project, Shell shell){
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
	 * resource sync.
	 */
	public void setUnSyncResource(){
		syncResource = false;
		this.project = null;
		this.shell = null;
	}
	
}