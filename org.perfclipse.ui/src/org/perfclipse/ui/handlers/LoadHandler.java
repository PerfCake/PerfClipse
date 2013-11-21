package org.perfclipse.ui.handlers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.perfclipse.scenario.*;


public class LoadHandler extends AbstractHandler {

	private final static Logger LOGGER = Logger.getLogger(LoadHandler.class .getName()); 
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		ISelection selected = HandlerUtil.getActiveMenuSelection(event);
		IStructuredSelection structuredSelection = (IStructuredSelection) selected;
		Object firstSelected = structuredSelection.getFirstElement();
		if (firstSelected instanceof IFile){
			//do my bussines
			IFile file = (IFile) firstSelected;
			writeFile(file, shell);
		}
		else{
			MessageDialog.openError(shell, "Cannot open scenario", "Scenario has to be xml file which is valid"
					+ " according to given xml schema");
		}
		return null;
	}

	private void writeFile(IFile file, Shell shell) {
		URL scenarioUrl = null;
		ScenarioManager scenarioManager = null;
		try {
			scenarioUrl = file.getLocationURI().toURL();
		} catch (MalformedURLException e1) {
			LOGGER.warning("Cannot open resource IFile: " + file.getFullPath());
			MessageDialog.openError(shell, "Error", "Cannot read selected file!");
			return;
		}
		try {
			scenarioManager = new ScenarioManager();
			scenarioManager.load(scenarioUrl);
			scenarioManager.save(System.out);
		} catch (ScenarioException | IOException e) {
			LOGGER.warning("Cannot parse sceanrio");
			MessageDialog.openError(shell, "Error", "Cannot parse selected file as scenario!");
		}
		
		
	}

}
