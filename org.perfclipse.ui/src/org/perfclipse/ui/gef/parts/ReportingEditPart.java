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

import org.eclipse.draw2d.IFigure;
import org.perfcake.model.Scenario.Reporting.Reporter;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.model.ReportingModel;
import org.perfclipse.ui.gef.figures.TwoPartRectangle;

public class ReportingEditPart extends AbstractPerfCakeSectionEditPart {

	public ReportingEditPart(ReportingModel reportingModel){
		setModel(reportingModel);
	}
	
	public ReportingModel getReportingModel(){
		return (ReportingModel) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		TwoPartRectangle figure = new TwoPartRectangle("Reporting section", getDefaultSize());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		if (getReportingModel().getReporting() != null ){
			if(getReportingModel().getReporting().getReporter() != null)
			{
				for (Reporter r : getReportingModel().getReporting().getReporter()){
					modelChildren.add(new ReporterModel(r));
				}
			}
		}
		return modelChildren;
	}

}
