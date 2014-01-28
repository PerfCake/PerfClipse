package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.perfcake.model.Scenario;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class GeneratorEditPart extends AbstractPerfCakeNodeEditPart {

	public GeneratorEditPart(Scenario.Generator generatorModel) {
		setModel(generatorModel);
	}
	
	public Scenario.Generator getGenerator(){
		return (Scenario.Generator) getModel();
	}

	@Override
	protected void addChild(EditPart child, int index){
		super.addChild(child, index);
	}
	@Override
	protected IFigure createFigure() {
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle(getGenerator().getClazz());
		figure.setPreferredSize(300, 50);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<>();
		modelChildren.add(getGenerator().getClazz());
		if (getGenerator().getThreads() != null)
			modelChildren.add("(" + getGenerator().getThreads() + ")");
		if (getGenerator().getRun() != null)
			modelChildren.add(getGenerator().getRun());

		return modelChildren;
	}

}
