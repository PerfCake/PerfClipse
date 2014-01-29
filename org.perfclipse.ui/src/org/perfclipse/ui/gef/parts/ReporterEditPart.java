package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class ReporterEditPart extends AbstractPerfCakeNodeEditPart {

	
	public ReporterEditPart(ScenarioModel.Reporting.Reporter reporterModel){
		setModel(reporterModel);
	}
	
	public ScenarioModel.Reporting.Reporter getReporter(){
		return (ScenarioModel.Reporting.Reporter) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle(getReporter().getClazz());
		figure.setPreferredSize(260, 80);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		modelChildren.add(getReporter().getClazz());
		modelChildren.addAll(getReporter().getDestination());
		return modelChildren;
	}

}
