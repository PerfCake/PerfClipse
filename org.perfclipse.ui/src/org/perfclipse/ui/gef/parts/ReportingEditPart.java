package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfcake.model.Scenario;
import org.perfclipse.ui.gef.figures.PerfCakeRoundedRectangle;

public class ReportingEditPart extends AbstractPerfCakeNodeEditPart {

	
	public ReportingEditPart(Scenario.Reporting reportingModel){
		setModel(reportingModel);
	}
	
	public Scenario.Reporting getReporting(){
		return (Scenario.Reporting) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		PerfCakeRoundedRectangle figure = new PerfCakeRoundedRectangle("Reporting Section");
		figure.setPreferredSize(300,200);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		modelChildren.add(0, "Reporting section");
		modelChildren.addAll(getReporting().getReporter());
		return modelChildren;
	}

}
