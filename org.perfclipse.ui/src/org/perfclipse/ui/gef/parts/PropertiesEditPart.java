package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.perfcake.model.Property;
import org.perfclipse.model.PropertiesModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;

public class PropertiesEditPart extends AbstractPerfCakeSectionEditPart {

	public static final String PROPERTIES_SECTION_LABEL = "Scenario Properties";
	
	public PropertiesEditPart(PropertiesModel propertiesModel){
		setModel(propertiesModel);
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

	
}
