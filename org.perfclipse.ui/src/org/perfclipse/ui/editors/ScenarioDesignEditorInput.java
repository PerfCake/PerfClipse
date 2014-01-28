package org.perfclipse.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.part.FileEditorInput;
import org.perfcake.model.Scenario;

public class ScenarioDesignEditorInput extends FileEditorInput {

	private final Scenario model;

	
	public ScenarioDesignEditorInput(IFile file) {
		super(file);
		//parse file here !!!
		Scenario model = new Scenario();
		this.model = model;
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

	public Scenario getModel() {
		return model;
	}

}
