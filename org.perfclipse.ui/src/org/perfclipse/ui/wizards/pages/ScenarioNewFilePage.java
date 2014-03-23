package org.perfclipse.ui.wizards.pages;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.perfclipse.ui.PerfClipseConstants;

public class ScenarioNewFilePage extends WizardNewFileCreationPage {

	private static final String ACCEPTED_EXTENSION = "xml";
	private static final String SCENARIO_DEFAULT_NAME = "scenario";
	private static final String WIZARD_DESCRIPTION = "Create new Scenario file.";
	private static final String WIZARD_TITLE = "PerfCake Scenario";
	private static final String SCENARIO_NAME_LABEL = "Scenario name";

	private IStructuredSelection selection;

	public ScenarioNewFilePage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
		this.selection = selection;
		setFileName(SCENARIO_DEFAULT_NAME);
		setFileExtension(ACCEPTED_EXTENSION);
		setTitle(WIZARD_TITLE);
		setDescription(WIZARD_DESCRIPTION);
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

	@Override
	protected String getNewFileLabel() {
		return SCENARIO_NAME_LABEL;
	}
	
	
}
