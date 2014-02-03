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

import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;

public class ScenarioPalleteFactory {

	 public static PaletteRoot createPalette() {
	      PaletteRoot palette = new PaletteRoot();
	      palette.add(createToolsGroup(palette));
	      return palette;
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
