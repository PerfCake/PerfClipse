/*
 * Perfclispe
 * 
 * 
 * Copyright (c) 2013 Jakub Knetl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.perfclipse.ui.gef.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.perfclipse.core.model.MessagesModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.PropertiesModel;
import org.perfclipse.core.model.ReportingModel;
import org.perfclipse.core.model.ScenarioModel;
import org.perfclipse.core.model.ValidationModel;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.preferences.PreferencesConstants;

public class ScenarioEditPart extends AbstractPerfCakeEditPart {

//	private static final int BORDER_PADDING = 1;

	private GridLayout layout;
	
	public ScenarioEditPart(ScenarioModel scenarioModel){
		setModel(scenarioModel);
	}

	@Override
	protected IFigure createFigure() {
		Figure figure = new Figure();
		ColorUtils utils = ColorUtils.getInstance();
		Color backgroundColor = utils.getColor(PreferencesConstants.SCENARIO_COLOR_BACKGROUND);
		if (backgroundColor != null){
			getViewer().getControl().setBackground(backgroundColor);
		}
		layout = new GridLayout();
		layout.numColumns = 2;
		layout.horizontalSpacing = 10;
		layout.verticalSpacing = 10;
		figure.setLayoutManager(layout);

		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected List<Object> getModelChildren(){
		ModelMapper mapper = getScenarioModel().getMapper();
		List<Object> modelChildren = new ArrayList<>();
		modelChildren.add(mapper.getModelContainer(getScenarioModel().getScenario().getGenerator()));
		modelChildren.add(mapper.getModelContainer(getScenarioModel().getScenario().getSender()));
		
		if (getScenarioModel().getScenario().getMessages() == null){
			MessagesModel messages = new MessagesModel(getScenarioModel().getScenario().getMessages(), mapper.getScenario(), mapper);
			mapper.setMessagesModel(messages);
			modelChildren.add(messages);
		}else{
			modelChildren.add(mapper.getModelContainer(getScenarioModel().getScenario().getMessages()));
		}
		if (getScenarioModel().getScenario().getReporting() == null){
			modelChildren.add(new ReportingModel(getScenarioModel().getScenario().getReporting(), getScenarioModel(), mapper));
		} else {
			modelChildren.add(mapper.getModelContainer(getScenarioModel().getScenario().getReporting()));
		}
		if (getScenarioModel().getScenario().getValidation() == null){
			ValidationModel validation = new ValidationModel(getScenarioModel().getScenario().getValidation(), getScenarioModel(), mapper);
			mapper.setValidationModel(validation);
			modelChildren.add(validation);
		} else {
			modelChildren.add(mapper.getModelContainer(getScenarioModel().getScenario().getValidation()));
		}
		
		if (getScenarioModel().getScenario().getProperties() == null){
			modelChildren.add(new PropertiesModel(getScenarioModel().getScenario().getProperties(),getScenarioModel(), mapper));
		} else {
			modelChildren.add(mapper.getModelContainer(getScenarioModel().getScenario().getProperties()));
		}
		return modelChildren;
	}
	
	/**
	 * Resizes Width of layout
	 */
	public void resize(){
		layout.layout(figure);
	}
	public ScenarioModel getScenarioModel(){
		return (ScenarioModel) getModel();
	}
	
	@Override
	protected void addChild(EditPart child, int index){
		if (child instanceof AbstractGraphicalEditPart){
			GridData layoutData = new GridData();
			layoutData.horizontalAlignment = SWT.FILL;
			layoutData.widthHint = 300;
			layoutData.verticalAlignment = SWT.FILL;
			layoutData.grabExcessHorizontalSpace = true;
			IFigure figure = ((AbstractGraphicalEditPart) child).getFigure();
			if (child instanceof AbstractPerfCakeSectionEditPart){
				
			}
			if (child instanceof GeneratorEditPart || 
					child instanceof SenderEditPart ||
					child instanceof PropertiesEditPart){
				layoutData.horizontalSpan = 2;

			}
			
			if (child instanceof ReportingEditPart){
				layoutData.verticalSpan = 2;
			}
			layout.setConstraint(figure, layoutData);
		}
		super.addChild(child, index);
		
	}
}
