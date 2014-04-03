package org.perfclipse.ui.gef.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.perfclipse.model.IPropertyContainer;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.Utils;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.PropertyEditPolicy;
import org.perfclipse.ui.gef.policies.directedit.RenamePropertyDirectEditPolicy;
import org.perfclipse.ui.preferences.PreferencesConstants;
import org.perfclipse.ui.wizards.PropertyEditWizard;

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
		Color fg = colorUtils.getColor(PreferencesConstants.PROPERTY_COLOR_FOREGROUND);
		Color bg = colorUtils.getColor(PreferencesConstants.PROPERTY_COLOR_BACKGROUND);
		LabeledRoundedRectangle figure = new LabeledRoundedRectangle(getText(), fg, bg);

		return figure;
	}
	
	@Override
	protected String getText(){
		return getPropertyModel().getProperty().getName() +
				" : " + getPropertyModel().getProperty().getValue();
	}

	@Override
	public void performRequest(Request request){
		if (request.getType() == RequestConstants.REQ_OPEN ||
				request.getType() == RequestConstants.REQ_DIRECT_EDIT)
		{
			PropertyEditWizard wizard = new PropertyEditWizard(getPropertyModel());
			if (Utils.showWizardDialog(wizard) == Window.OK){
				if (!wizard.getCommand().isEmpty())
					getViewer().getEditDomain().getCommandStack().execute(wizard.getCommand());
			}
			
		}
	}
	
	@Override
	protected void createEditPolicies() {
		if (getParent().getModel() instanceof IPropertyContainer){
			IPropertyContainer properties = (IPropertyContainer) getParent().getModel();
			installEditPolicy(EditPolicy.COMPONENT_ROLE, new PropertyEditPolicy(properties, getPropertyModel()));
			installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
					new RenamePropertyDirectEditPolicy(getPropertyModel(), (ILabeledFigure) getFigure()));
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertyModel.PROPERTY_NAME) ||
				evt.getPropertyName().equals(PropertyModel.PROPERTY_VALUE)){
			refreshVisuals();
		}
	}

}
