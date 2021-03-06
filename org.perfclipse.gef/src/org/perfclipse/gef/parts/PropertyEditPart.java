package org.perfclipse.gef.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Color;
import org.perfclipse.core.model.IPropertyContainer;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.gef.PreferencesConstants;
import org.perfclipse.gef.figures.ILabeledFigure;
import org.perfclipse.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.gef.layout.colors.ColorUtils;
import org.perfclipse.gef.policies.PropertyEditPolicy;
import org.perfclipse.gef.policies.directedit.RenamePropertyDirectEditPolicy;
import org.perfclipse.wizards.PropertyEditWizard;
import org.perfclipse.wizards.WizardUtils;

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
		Color fg; 
		Color bg;
		if (getParent() instanceof SenderEditPart){
			fg = colorUtils.getColor(PreferencesConstants.SENDER_PROPERTY_COLOR_FOREGROUND);
			bg = colorUtils.getColor(PreferencesConstants.SENDER_PROPERTY_COLOR_BACKGROUND);
		}
		else{
			fg = colorUtils.getColor(PreferencesConstants.PROPERTY_COLOR_FOREGROUND);
			bg = colorUtils.getColor(PreferencesConstants.PROPERTY_COLOR_BACKGROUND);
		}
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
		if (request.getType() == RequestConstants.REQ_OPEN)
		{
			PropertyEditWizard wizard = new PropertyEditWizard(getPropertyModel());
			if (WizardUtils.showWizardDialog(wizard) == Window.OK){
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
