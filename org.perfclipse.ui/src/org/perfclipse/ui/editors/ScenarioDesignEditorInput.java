package org.perfclipse.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.part.FileEditorInput;
import org.perfclipse.model.ScenarioModel;

public class ScenarioDesignEditorInput extends FileEditorInput {

	private final ScenarioModel model;

	
	public ScenarioDesignEditorInput(IFile file) {
		super(file);
		// TODO parse file here !!!
		model = new ScenarioModel();
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Scenario Design editor";
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}

	public ScenarioModel getModel() {
		return model;
	}

}
