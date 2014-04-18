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

package org.perfclipse.gef.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

public class StringEditPart extends AbstractPerfCakeNodeEditPart {

	public StringEditPart(String name){
		setModel(name);
	}
	
	@Override
	protected IFigure createFigure() {
		Label label = new Label();
		label.setText(getText());
		return label;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected String getText() {
		// TODO Auto-generated method stub
		return (String) getModel();
	}

	@Override
	protected void refreshVisuals(){
		super.refreshVisuals();
	}
}
