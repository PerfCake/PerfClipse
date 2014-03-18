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

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;


public abstract class AbstractPerfCakeNodeEditPart extends  AbstractPerfCakeEditPart{

	public AbstractPerfCakeNodeEditPart(ModelMapper mapper) {
		super(mapper);
	}

	private Dimension figureDefaultSize;
	
	public int getFigureRequiredHeight(){
		int h = 0;

		//sum childrens required height
		AbstractPerfCakeNodeEditPart previous = null;
		for (Object o : getChildren()){
			if (o instanceof AbstractPerfCakeNodeEditPart){
				AbstractPerfCakeNodeEditPart part = (AbstractPerfCakeNodeEditPart) o;
				//if they are on the same row then do not add next height
				if (previous == null || previous.getFigure().getBounds().getTopLeft().y != part.getFigure().getBounds().getTopLeft().y){
					h += part.getFigureRequiredHeight();
					
					//TODO: extend this computation for other layouts
					if (part.getFigure().getLayoutManager() instanceof FlowLayout){
						h += ((FlowLayout) part.getFigure().getLayoutManager()).getMajorSpacing();
					}
				}
				previous = part;
			}
		}
		
		//add default figure structure size
		h += getFigureDefaultSize().height;
		
		return h;
	}

	@Override
	public IFigure getContentPane() {
		if (getFigure() instanceof TwoPartRectangle){
			return ((TwoPartRectangle) getFigure()).getContentLayer();
		}
		return super.getContentPane();
	}

	public Dimension getFigureDefaultSize() {
		if (figureDefaultSize == null){
			figureDefaultSize = getFigure().getPreferredSize().getCopy(); 
		}
		return figureDefaultSize;
	}

	protected void setFigureDefaultSize(Dimension figureDefaultSize) {
		this.figureDefaultSize = figureDefaultSize;
	}
	
	
	
	@Override
	protected void refreshVisuals() {
		// TODO Auto-generated method stub
		super.refreshVisuals();

		if (getFigure() instanceof ILabeledFigure){
			((ILabeledFigure) getFigure()).getLabel().setText(getText());
		}
	}

	/**
	 * 
	 * @return text displayed on the figure
	 */
	protected String getText(){
		return "";
	}
	
//	@Override
//	public void refreshVisuals() {
//		getFigure().validate();
//		
//		int requiredHeight = getFigureRequiredHeight();
//		
//		Dimension preferredSize = getFigure().getSize().getCopy();
//		preferredSize.height = requiredHeight;
//		getFigure().setPreferredSize(preferredSize);
//		if (getFigure() instanceof PerfCakeTwoPartRectangle){
//			Dimension d = getContentPane().getPreferredSize().getCopy();
//			d.height = requiredHeight;
//			getContentPane().setPreferredSize(d);
//		}
//		getFigure().validate();
//		
//	}
}
