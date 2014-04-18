/*
 * PerfClispe
 * 
 *
 * Copyright (c) 2014 Jakub Knetl
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

package org.perfclipse.gef.directedit;

import org.eclipse.draw2d.Label;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;

public class LabelDirectEditManager extends DirectEditManager {

	protected Label label;

	@SuppressWarnings("rawtypes")
	public LabelDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator) {
		super(source, editorType, locator);
		this.label = ((LabelCellEditorLocator) locator).getLabel();
	}

	@SuppressWarnings("rawtypes")
	public LabelDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator, Object feature) {
		super(source, editorType, locator, feature);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initCellEditor() {
		getCellEditor().setValue(label.getText());
	}
	

}
