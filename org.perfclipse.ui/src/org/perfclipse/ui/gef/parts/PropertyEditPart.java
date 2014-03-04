package org.perfclipse.ui.gef.parts;

import org.eclipse.draw2d.IFigure;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;

public class PropertyEditPart extends AbstractPerfCakeNodeEditPart {

	public PropertyEditPart(PropertyModel modelProperty){
		setModel(modelProperty);
	}
	
	public PropertyModel getPropertyModel(){
		return (PropertyModel) getModel();
	}
	
	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		LabeledRoundedRectangle figure = new LabeledRoundedRectangle(getText(),
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));

		return figure;
	}
	
	@Override
	protected String getText(){
		return getPropertyModel().getProperty().getName() +
				"=" + getPropertyModel().getProperty().getValue();
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

}
