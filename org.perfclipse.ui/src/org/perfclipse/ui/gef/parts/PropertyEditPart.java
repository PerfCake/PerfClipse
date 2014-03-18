package org.perfclipse.ui.gef.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.viewers.TextCellEditor;
import org.perfclipse.model.IPropertyContainer;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.directedit.LabelCellEditorLocator;
import org.perfclipse.ui.gef.directedit.LabelDirectEditManager;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.PropertyEditPolicy;
import org.perfclipse.ui.gef.policies.directedit.RenamePropertyDirectEditPolicy;

public class PropertyEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener {


	private LabelDirectEditManager manager;

	public PropertyEditPart(PropertyModel modelProperty,
			ModelMapper mapper){
		super(mapper);
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
				" : " + getPropertyModel().getProperty().getValue();
	}

	@Override
	public void performRequest(Request request){
		if (request.getType() == RequestConstants.REQ_OPEN ||
				request.getType() == RequestConstants.REQ_DIRECT_EDIT)
		{
			if (manager == null){
				manager = new LabelDirectEditManager(this,
						TextCellEditor.class,
						new LabelCellEditorLocator(((LabeledRoundedRectangle) getFigure()).getLabel()));
			}
			manager.show();
			
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
