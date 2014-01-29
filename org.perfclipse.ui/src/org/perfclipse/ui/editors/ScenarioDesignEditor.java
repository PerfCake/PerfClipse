package org.perfclipse.ui.editors;

import java.net.MalformedURLException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.perfcake.model.Scenario;
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

	@Override
	protected PaletteRoot getPaletteRoot() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Override
	protected void setInput(IEditorInput input){
		super.setInput(input);
		ScenarioDesignEditorInput editorInput = (ScenarioDesignEditorInput) input;
		//TODO move parsing into editorInput object!
		ScenarioManager manager = new ScenarioManager();
		try {
			model = manager.createModel(editorInput.getURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Show error dialog 
			MessageDialog.openError(getSite().getShell(), "Wrong URL to scenario",
					"Scenario cannot be opened.");

			log.warn("Scenario cannot be opened.", e);
		} catch (ScenarioException e) {

			// TODO Show error dialog 
			MessageDialog.openError(getSite().getShell(), "Scenario error",
					"Cannot parse scenario. Probabily there is error in scenario definition.");

			log.warn("Cannot parse scenario.", e);
		}
	}

	
	@Override
	protected void configureGraphicalViewer() {
	      super.configureGraphicalViewer();
	      GraphicalViewer viewer = getGraphicalViewer();
	      viewer.setEditPartFactory(new PerfCakeEditPartFactory());
	      viewer.setRootEditPart(new ScalableFreeformRootEditPart());
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
		// TODO Auto-generated method stub

	}
	
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type){
		if (type == Scenario.class)
			return model;
		return super.getAdapter(type);
	}

}
