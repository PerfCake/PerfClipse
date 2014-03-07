package org.perfclipse.ui.gef.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.perfcake.model.Property;
import org.perfclipse.model.PropertiesModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.PropertyListEditPolicy;

public class PropertiesEditPart extends AbstractPerfCakeSectionEditPart implements PropertyChangeListener {

	public static final String PROPERTIES_SECTION_LABEL = "Scenario Properties";
	
	public PropertiesEditPart(PropertiesModel propertiesModel){
		setModel(propertiesModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getPropertiesModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getPropertiesModel().removePropertyChangeListener(this);
		super.deactivate();
	}
	
	public PropertiesModel getPropertiesModel(){
		return (PropertiesModel) getModel();
	}

	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		TwoPartRectangle figure = new TwoPartRectangle(getText(), getDefaultSize(),
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
		return figure;
	}
	
	@Override
	protected void createEditPolicies() {
		ScenarioModel scenarioModel = ((ScenarioEditPart) getParent()).getScenarioModel();
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new PropertyListEditPolicy(getPropertiesModel(), scenarioModel));
	}

	@Override
	protected String getText() {
		return PROPERTIES_SECTION_LABEL;
	}

	@Override
	protected List<Object> getModelChildren() {
		List<Object> children = new ArrayList<>();
		if (getPropertiesModel().getProperties() != null){
			if (getPropertiesModel().getProperties().getProperty() != null){
				for (Property p : getPropertiesModel().getProperties().getProperty()){
					children.add(new PropertyModel(p));
				}
			}
		}
		return children;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(PropertiesModel.PROPERTY_PROPERTIES)){
			if (evt.getOldValue() == null && evt.getNewValue() instanceof Property){
				PropertyModel propertyModel = new PropertyModel((Property) evt.getNewValue());
				addChild(new PropertyEditPart(propertyModel), getChildren().size());
			}
		}
		
	}

	
}
