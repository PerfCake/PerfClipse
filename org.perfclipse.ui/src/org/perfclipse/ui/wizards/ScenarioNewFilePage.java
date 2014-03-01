package org.perfclipse.ui.wizards;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.perfclipse.ui.PerfClipseConstants;

public class ScenarioNewFilePage extends WizardNewFileCreationPage {

	private IStructuredSelection selection;
	public ScenarioNewFilePage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		this.selection = selection;
		setFileName("scenario");
		setFileExtension("xml");
	}

	@Override
	protected void initialPopulateContainerNameField() {
		if (selection != null &&
				selection.getFirstElement() instanceof IProject)
		{
			IProject project = (IProject) selection.getFirstElement();
			IFolder scenarioDir = project.getFolder(PerfClipseConstants.SCENARIO_DIR_NAME);
			if (scenarioDir.exists()){
				setContainerFullPath(scenarioDir.getFullPath());
				return;
			}
		}

		super.initialPopulateContainerNameField();
	}
}
