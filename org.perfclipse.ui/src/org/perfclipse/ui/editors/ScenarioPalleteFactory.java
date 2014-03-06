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

package org.perfclipse.ui.editors;

import javax.print.attribute.standard.Destination;

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.reflect.PerfClipseScannerException;
import org.perfclipse.ui.editors.palettefactories.DestinationFactory;
import org.perfclipse.ui.editors.palettefactories.MessageFactory;
import org.perfclipse.ui.editors.palettefactories.ReporterFactory;
import org.perfclipse.ui.editors.palettefactories.ValidatorFactory;

public class ScenarioPalleteFactory {

	private static PerfCakeComponents components;

	public static PaletteRoot createPalette() {
		try {
			components = PerfCakeComponents.getInstance();
		} catch (PerfClipseScannerException e) {
			// TODO handle exception 
			e.printStackTrace();
		}
		PaletteRoot palette = new PaletteRoot();
		palette.add(createToolsGroup(palette));
		palette.add(createMessageDrawer());
		palette.add(createValidatorDrawer());
		palette.add(createReporterDrawer());
		palette.add(createDestinationDrawer());
		return palette;
	}

	private static PaletteEntry createMessageDrawer() {
		PaletteDrawer messageDrawer = new PaletteDrawer("Messages");

		MessageFactory factory = new MessageFactory(Message.class);

		CombinedTemplateCreationEntry messageComponent = new CombinedTemplateCreationEntry("Create message", "Add new message", factory, null, null);
		messageDrawer.add(messageComponent);

		return messageDrawer;
	}
	
	private static PaletteEntry createValidatorDrawer(){
		PaletteDrawer validatorDrawer = new PaletteDrawer("Validators");
		
		for(Class<?> clazz : components.getValidators()){
			String name = clazz.getSimpleName();
			ValidatorFactory factory = new ValidatorFactory(Validator.class, name);
			CombinedTemplateCreationEntry validatorComponent = new
					CombinedTemplateCreationEntry(name, "Adds " + name + " to the scenario", factory, null, null);
			validatorDrawer.add(validatorComponent);
		}
		return validatorDrawer;
	}
	
	private static PaletteEntry createReporterDrawer(){
		PaletteDrawer reporterDrawer = new PaletteDrawer("Reporters");
		
		for (Class<?> clazz: components.getReporters()){
			String name = clazz.getSimpleName();
			ReporterFactory factory = new ReporterFactory(Reporter.class, name);
			CombinedTemplateCreationEntry reporterComponent = new 
					CombinedTemplateCreationEntry(name, "Adds " + name + " to the scenario", factory, null, null);
			reporterDrawer.add(reporterComponent);
		}
		
		return reporterDrawer;

	}
	
	private static PaletteEntry createDestinationDrawer(){
		PaletteDrawer destinationDrawer = new PaletteDrawer("Destinations");
		
		for (Class<?> clazz: components.getDestinations()){
			String name = clazz.getSimpleName();
			DestinationFactory factory = new DestinationFactory(Destination.class, name);
			CombinedTemplateCreationEntry destinationComponent = new
					CombinedTemplateCreationEntry(name, "Adds " + name + " to the scenario", factory, null, null);

			destinationDrawer.add(destinationComponent);
		}
		
		return destinationDrawer;
	}
	

	private static PaletteContainer createToolsGroup(PaletteRoot palette) {
		PaletteGroup toolGroup = new PaletteGroup("Tools");

		ToolEntry tool = new PanningSelectionToolEntry();
		toolGroup.add(tool);
		palette.setDefaultEntry(tool);

		toolGroup.add(new MarqueeToolEntry());

		return toolGroup;
	}
	   
}
