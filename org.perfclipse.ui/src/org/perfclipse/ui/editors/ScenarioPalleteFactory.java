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

import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.SimpleFactory;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.model.ScenarioModel.Messages.Message;

public class ScenarioPalleteFactory {

	 public static PaletteRoot createPalette() {
	      PaletteRoot palette = new PaletteRoot();
	      palette.add(createToolsGroup(palette));
	      palette.add(createElementsDrawer());
	      return palette;
	   }

	   private static PaletteEntry createElementsDrawer() {
		   PaletteDrawer componentDrawer = new PaletteDrawer("Elemnts drawer");

		   SimpleFactory factory = new SimpleFactory(ScenarioModel.Messages.Message.class){
			   public Object getNewObject(){
				   ScenarioModel.Messages.Message m = new Message();
				   m.setUri("Added_by_GEF");
				   return m;
			   }
		   };
		   
		   CombinedTemplateCreationEntry messageComponent = new CombinedTemplateCreationEntry("Create message", "Add new message", factory, null, null);
		   componentDrawer.add(messageComponent);
		   
		   return componentDrawer;
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
