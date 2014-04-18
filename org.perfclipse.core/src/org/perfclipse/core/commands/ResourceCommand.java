package org.perfclipse.core.commands;

import org.eclipse.core.resources.IProject;
import org.eclipse.gef.commands.Command;

/**
 * Abstract command with resource sync.
 * 
 * @author Jakub Knetl
 *
 */
public abstract class ResourceCommand extends Command {

	protected boolean syncResource;
	protected IProject project;

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
	public void setSyncResource(IProject project){
		if (project == null)
			throw new IllegalArgumentException("project cannot be null");

		syncResource = true;
		this.project = project;
	}
	
	/**
	 * Sets flag that command execution should not care about
	 * resource sync.
	 */
	public void setUnSyncResource(){
		syncResource = false;
		this.project = null;
	}
	
}