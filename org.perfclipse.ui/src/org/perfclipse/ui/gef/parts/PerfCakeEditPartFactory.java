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

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.perfclipse.core.model.DestinationModel;
import org.perfclipse.core.model.GeneratorModel;
import org.perfclipse.core.model.MessageModel;
import org.perfclipse.core.model.MessagesModel;
import org.perfclipse.core.model.PropertiesModel;
import org.perfclipse.core.model.PropertyModel;
import org.perfclipse.core.model.ReporterModel;
import org.perfclipse.core.model.ReportingModel;
import org.perfclipse.core.model.RunModel;
import org.perfclipse.core.model.ScenarioModel;
import org.perfclipse.core.model.SenderModel;
import org.perfclipse.core.model.ValidationModel;
import org.perfclipse.core.model.ValidatorModel;
import org.perfclipse.core.model.ValidatorRefModel;

public class PerfCakeEditPartFactory implements EditPartFactory {

	/**
	 * Instance of editor which uses this edit Part factory.
	 */
	private GraphicalEditor editor;
	
	
	/**
	 * @param editor
	 */
	public PerfCakeEditPartFactory(GraphicalEditor editor) {
		super();
		if (editor == null)
			throw new IllegalArgumentException("Editor cannot be null");
		this.editor = editor;
	}

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof ScenarioModel){
			return new ScenarioEditPart((ScenarioModel) model);
		}
		if (model instanceof GeneratorModel){
			return new GeneratorEditPart((GeneratorModel) model);
		}
		if (model instanceof SenderModel){
			return new SenderEditPart((SenderModel) model);
		}
		if (model instanceof ReportingModel){
			return new ReportingEditPart((ReportingModel) model);
		}
		if (model instanceof ValidationModel){
			return new ValidationEditPart((ValidationModel) model);
		}
		if (model instanceof ValidatorModel){
			return new ValidatorEditPart((ValidatorModel) model);
		}
		if (model instanceof MessagesModel){
			return new MessagesEditPart((MessagesModel) model);
		}
		if (model instanceof RunModel){
			return new RunEditPart((RunModel) model);
		}
		if (model instanceof MessageModel){
			return new MessageEditPart((MessageModel) model);
		}
		if (model instanceof String){
			return new StringEditPart((String) model);
		}
		if (model instanceof ReporterModel){
			return new ReporterEditPart((ReporterModel) model);
		}
		if (model instanceof DestinationModel){
			return new DestinationEditPart((DestinationModel) model);
		}
		if (model instanceof PropertiesModel){
			return new PropertiesEditPart((PropertiesModel) model);
		}
		if (model instanceof PropertyModel){
			return new PropertyEditPart((PropertyModel) model);
		}
		if (model instanceof ValidatorRefModel){
			return new ValidatorRefEditPart((ValidatorRefModel) model);
		}
		return null;
	}
	
	
	/**
	 * Returns IFile instance of the scenario
	 * @return
	 */
	public IFile getScenarioFile(){
		FileEditorInput input = (FileEditorInput) editor.getEditorInput();

		return input.getFile();
	}

}
