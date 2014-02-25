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

package org.perfclipse.ui.gef.directedit;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

public class LabelCellEditorLocator implements CellEditorLocator {

	protected Label label;

	public LabelCellEditorLocator(Label label) {
		this.label = label;
	}

	@Override
	public void relocate(CellEditor celleditor) {
		// TODO Auto-generated method stub
		Text text = (Text) celleditor.getControl();
		
		Rectangle bounds = label.getBounds().getCopy();
		label.translateToAbsolute(bounds);
		Point p = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		text.setBounds(bounds.x, bounds.y, p.x, p.y);
		
		
	}

	public final Label getLabel() {
		return label;
	}
	
}
