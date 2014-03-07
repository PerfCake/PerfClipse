package org.perfclipse.ui.gef.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.perfclipse.model.PropertiesModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.DeletePropertyEditPolicy;

public class PropertyEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener {

	public PropertyEditPart(PropertyModel modelProperty){
		setModel(modelProperty);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getPropertyModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getPropertyModel().removePropertyChangeListener(this);
		super.deactivate();
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
		PropertiesModel properties = (PropertiesModel) getParent().getModel();
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new DeletePropertyEditPolicy(properties, getPropertyModel()));

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

}
