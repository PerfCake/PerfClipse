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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.perfclipse.core.logging.Logger;
import org.perfclipse.core.model.ScenarioModel;
import org.perfclipse.core.scenario.ScenarioException;
import org.perfclipse.core.scenario.ScenarioManager;
import org.perfclipse.ui.Activator;
import org.perfclipse.ui.actions.AddDestinationAction;
import org.perfclipse.ui.actions.AddHeaderAction;
import org.perfclipse.ui.actions.AddMessageAction;
import org.perfclipse.ui.actions.AddPeriodAction;
import org.perfclipse.ui.actions.AddPropertyAction;
import org.perfclipse.ui.actions.AddReporterAction;
import org.perfclipse.ui.actions.AddValidatorAction;
import org.perfclipse.ui.actions.AttachValidatorAction;
import org.perfclipse.ui.actions.EditDialogAction;
import org.perfclipse.ui.actions.PerfClipseDeleteAction;
import org.perfclipse.ui.actions.SwitchAction;
import org.perfclipse.ui.gef.parts.PerfCakeEditPartFactory;
import org.perfclipse.ui.gef.parts.ScenarioEditPart;

public class ScenarioDesignEditor extends GraphicalEditorWithPalette {
	
	final static Logger log = Activator.getDefault().getLogger();

	private ScenarioModel model;
	private ScenarioMultiPageEditor parent;
	
	private final IPropertyChangeListener preferencesChangeListener = new IPropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent event) {
			getGraphicalViewer().setContents(model);
		}
	};


	public ScenarioDesignEditor(ScenarioMultiPageEditor parent) {
		super();
		setEditDomain(new DefaultEditDomain(this));
		this.parent = parent;
		Activator.getDefault().getPreferenceStore().addPropertyChangeListener(preferencesChangeListener);
	}
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		super.selectionChanged(part, selection);

		if (getSite().getWorkbenchWindow().getActivePage().getActiveEditor() == parent &&
				parent.isNestedEditorActive(this)){
			updateActions(getSelectionActions());
		}
	}

	/*
	 * Notify editor that command stack change to update dirty flag
	 */
	@Override
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {
		return ScenarioPalleteFactory.createPalette();
	}	
	
	
	@Override
	protected void setInput(IEditorInput input){
		super.setInput(input);
		model = ((ScenarioDesignEditorInput) input).getModel();
	}

	
	@Override
	protected void configureGraphicalViewer() {
	      super.configureGraphicalViewer();
	      GraphicalViewer viewer = getGraphicalViewer();
	      viewer.setEditPartFactory(new PerfCakeEditPartFactory(this));
	      viewer.setRootEditPart(new ScalableRootEditPart());
	      viewer.setContextMenu(new DesignEditorContextMenuProvider(getGraphicalViewer(), getActionRegistry()));

	      // These two lines add support for drag and drop between palette and editor
	      getGraphicalViewer().addDropTargetListener(
	    		  new TemplateTransferDropTargetListener(getGraphicalViewer()));
	      getEditDomain().getPaletteViewer().addDragSourceListener(
	    		  new TemplateTransferDragSourceListener(getEditDomain().getPaletteViewer()));
	      
	      
	   }

	   /**
	    * Initialize the graphic viewer and the connection layer so that connections
	    * are routed around existing figures.
	    * 
	    * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#initializeGraphicalViewer()
	    */
	@Override
	protected void initializeGraphicalViewer() {

		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setContents(model);

		//TODO error handling for empty model - IndexOutOfBoundsException is thrown now
		ScalableRootEditPart rootEditPart =
				 (ScalableRootEditPart) viewer.getRootEditPart();
		ScenarioEditPart scenarioPart =
				(ScenarioEditPart) rootEditPart.getChildren().get(0);
		ConnectionLayer connectionLayer =
				(ConnectionLayer) rootEditPart.getLayer(LayerConstants.CONNECTION_LAYER);
		connectionLayer.setConnectionRouter(new ShortestPathConnectionRouter(
				scenarioPart.getFigure()));
	}


	@Override
	protected void createActions() {
		
		addSelectionAction(new EditDialogAction(this));
		addSelectionAction(new AddPropertyAction(this));
		addSelectionAction(new AddMessageAction(this));
		addSelectionAction(new AddHeaderAction(this));
		addSelectionAction(new AttachValidatorAction(this));
		addSelectionAction(new AddValidatorAction(this));
		addSelectionAction(new AddReporterAction(this));
		addSelectionAction(new AddDestinationAction(this));
		addSelectionAction(new AddPeriodAction(this));
		addSelectionAction(new SwitchAction(this));
		
		super.createActions();

		//remove standard delete actin and replace it with PerfclipseDeleteAction
		IAction deleteAction = getActionRegistry().getAction(ActionFactory.DELETE.getId());
		if (deleteAction != null){
			getActionRegistry().removeAction(deleteAction);
		}
		addSelectionAction(new PerfClipseDeleteAction(this));

	}
	
	/**
	 * registers selection action 
	 * @param action Action to be added
	 */
	@SuppressWarnings("unchecked")
	private void addSelectionAction(SelectionAction action){
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		ScenarioManager manager = new ScenarioManager();

		IFile file = ((FileEditorInput) getEditorInput()).getFile();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			manager.createXML(model.getScenario(), out);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			out.close();

			try {
				file.setContents(in, false, true, monitor);
				getEditDomain().getCommandStack().markSaveLocation();
			} catch (CoreException e) {
				MessageDialog.openError(getSite().getShell(), "Write error", "Cannot write data into file.");
				log.error("Write error: cannot write data into file", e);
			} finally {
				try {
					if (in != null){
						in.close();
					}
				} catch (IOException e) {
					log.error("Cannot close piped input stream for scenario xml", e);
				}
			}
		} catch (ScenarioException e) {
			MessageDialog.openError(getSite().getShell(), "Error", "Cannot create xml representation of the model.");
			log.error("Cannot create xml representation of the model.", e);
		} catch (IOException e) {
			MessageDialog.openError(getSite().getShell(), "IO Exception", "Cannot transfer data to file.");
			log.error("Cannot handle piped stream connections.", e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				log.error("Cannot close piped output stream for scenario xml output", e);
			}
		}

		
	}

	@Override
	public void dispose() {
		Activator.getDefault().getPreferenceStore().removePropertyChangeListener(preferencesChangeListener);
		super.dispose();
	}
	
	
}
