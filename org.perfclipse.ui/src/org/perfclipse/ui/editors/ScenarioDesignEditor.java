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


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
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
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.perfclipse.model.ScenarioModel;
import org.perfclipse.scenario.ScenarioException;
import org.perfclipse.scenario.ScenarioManager;
import org.perfclipse.ui.gef.parts.PerfCakeEditPartFactory;
import org.perfclipse.ui.gef.parts.ScenarioEditPart;
import org.slf4j.LoggerFactory;

public class ScenarioDesignEditor extends GraphicalEditorWithPalette {
	
	final static org.slf4j.Logger log = LoggerFactory.getLogger(ScenarioDesignEditor.class);

	private ScenarioModel model;

	public ScenarioDesignEditor() {
		setEditDomain(new DefaultEditDomain(this));
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
	      viewer.setEditPartFactory(new PerfCakeEditPartFactory());
	      viewer.setRootEditPart(new ScalableFreeformRootEditPart());
	      
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
		ScalableFreeformRootEditPart rootEditPart =
				(ScalableFreeformRootEditPart) viewer.getRootEditPart();
		ScenarioEditPart scenarioPart =
				(ScenarioEditPart) rootEditPart.getChildren().get(0);
		ConnectionLayer connectionLayer =
				(ConnectionLayer) rootEditPart.getLayer(LayerConstants.CONNECTION_LAYER);
		connectionLayer.setConnectionRouter(new ShortestPathConnectionRouter(
				scenarioPart.getFigure()));
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		ScenarioManager manager = new ScenarioManager();

		IFile file = ((FileEditorInput) getEditorInput()).getFile();

		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in = null;
		try {
			in = new PipedInputStream(out);
			manager.createXML(model.getScenario(), out);
			out.close();
			try {
				file.setContents(in, false, true, monitor);
				getEditDomain().getCommandStack().markSaveLocation();
			} catch (CoreException e) {
				MessageDialog.openError(getSite().getShell(), "Write error", "Cannot write data into file.");
				log.error("Write error", "Cannot write data into file", e);
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
}
