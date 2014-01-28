package org.perfclipse.ui.gef.parts;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.perfcake.model.Scenario;

public class GeneratorEditPart extends AbstractPerfCakeNodeEditPart {

	private final Label label = new Label();
	
	private static final org.eclipse.draw2d.geometry.Insets CLIENT_AREA_INSETS = new org.eclipse.draw2d.geometry.Insets(10, 10, 21, 21);

	public GeneratorEditPart(Scenario.Generator generatorModel) {
		setModel(generatorModel);
		label.setText(generatorModel.getClazz());
	}
	
	public Scenario.Generator getGenerator(){
		return (Scenario.Generator) getModel();
	}

	@Override
	protected IFigure createFigure() {
		RoundedRectangle figure = new RoundedRectangle(){
			@Override
			public Rectangle getClientArea(Rectangle rect){
				Rectangle clientArea = super.getClientArea(rect);
				clientArea.shrink(CLIENT_AREA_INSETS);
				return clientArea;
			}
		};
		figure.setSize(200, 40);
		FlowLayout layout = new FlowLayout();
		layout.setMajorAlignment(FlowLayout.ALIGN_CENTER);
		layout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
		figure.setLayoutManager(layout);
		label.setTextAlignment(PositionConstants.LEFT);
		figure.add(label);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

}
