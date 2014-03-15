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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.ui.PlatformUI;
import org.perfclipse.model.DestinationModel;
import org.perfclipse.model.ReporterModel;
import org.perfclipse.reflect.PerfCakeComponents;
import org.perfclipse.reflect.PerfClipseScannerException;
import org.perfclipse.ui.gef.directedit.ClassDirectEditManager;
import org.perfclipse.ui.gef.directedit.ComboViewerCellEditorLocator;
import org.perfclipse.ui.gef.figures.ILabeledFigure;
import org.perfclipse.ui.gef.figures.LabeledRoundedRectangle;
import org.perfclipse.ui.gef.layout.colors.ColorUtils;
import org.perfclipse.ui.gef.policies.DestionationEditPolicy;
import org.perfclipse.ui.gef.policies.directedit.DestinationDirectEditPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestinationEditPart extends AbstractPerfCakeNodeEditPart implements PropertyChangeListener {

	static final Logger log = LoggerFactory.getLogger(DestinationEditPart.class);

	private DirectEditManager manager;

	public DestinationEditPart(DestinationModel destinationModel){
		setModel(destinationModel);
	}
	
	@Override
	public void activate() {
		if (!isActive()){
			getDestinationModel().addPropertyChangeListener(this);
		}
		super.activate();
	}

	@Override
	public void deactivate() {
		getDestinationModel().removePropertyChangeListener(this);
		super.deactivate();
	}

	public DestinationModel getDestinationModel(){
		return (DestinationModel) getModel(); 
	}
	@Override
	protected IFigure createFigure() {
		ColorUtils colorUtils = ColorUtils.getInstance();
		LabeledRoundedRectangle figure = new LabeledRoundedRectangle(getText(),
				colorUtils.getForegroundColor(this), colorUtils.getBackgroundColor(this));
//		figure.setPreferredSize(EMPTY_WIDHT, EMPTY_HEIGHT);
		return figure;
	}

	
	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_OPEN ||
				req.getType() == RequestConstants.REQ_DIRECT_EDIT){
			PerfCakeComponents components;
			try {
				components = PerfCakeComponents.getInstance();
				if (manager == null){
					manager = new ClassDirectEditManager(this, ComboBoxViewerCellEditor.class,
							new ComboViewerCellEditorLocator(((ILabeledFigure) getFigure()).getLabel()),
									components.getDestinations());
				}
				manager.show();
			} catch (PerfClipseScannerException e) {
				log.error("Cannot parse PerfCake components.", e);
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
						"Cannot parse PerfCake components", "Edit is not possible");
			}
		}
	}

	@Override
	protected void createEditPolicies() {
		ReporterModel reporter = (ReporterModel) getParent().getModel();
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new DestionationEditPolicy(reporter, getDestinationModel()));
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new DestinationDirectEditPolicy(getDestinationModel(), (ILabeledFigure) getFigure()));

	}

	@Override
	protected String getText() {
		return getDestinationModel().getDestination().getClazz();
	}

	@Override
	protected List<Object> getModelChildren(){
		List<Object> modelChildren = new ArrayList<Object>();
		return modelChildren;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(DestinationModel.PROPERTY_CLASS)){
			refreshVisuals();
		}
	
	}

}
