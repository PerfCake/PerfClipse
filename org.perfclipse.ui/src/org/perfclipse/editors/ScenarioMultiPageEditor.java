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

package org.perfclipse.editors;

import java.util.logging.Logger;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.wst.sse.ui.StructuredTextEditor;

public class ScenarioMultiPageEditor extends MultiPageEditorPart implements IResourceChangeListener{
	
	
	private final static Logger LOGGER = Logger.getLogger(ScenarioMultiPageEditor.class .getName()); 

	private StructuredTextEditor textEditor;

	private int textEditorIndex;

	public ScenarioMultiPageEditor(){
		super();

	}

	@Override
	protected void createPages() {
		createTextEditorPage();
		setPartName(getEditorInput().getName());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		getEditor(textEditorIndex).doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		IEditorPart editor = getEditor(textEditorIndex);
		editor.doSaveAs();
		setPageText(textEditorIndex, editor.getTitle());
		setPartName(getEditorInput().getName());
		setInput(editor.getEditorInput());

	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	private void createTextEditorPage() {
		try{
		textEditor = new StructuredTextEditor();
		textEditorIndex = addPage(textEditor, getEditorInput());
		setPageText(textEditorIndex, "XML editor");
		} catch (PartInitException e){
			LOGGER.warning("Cannot create Scenario text editor: " + e.toString());
			ErrorDialog.openError(getSite().getShell(), "Error",
					"Error creating scenario text editor.", e.getStatus());
			
		}
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for (int i = 0; i<pages.length; i++){
						if(((FileEditorInput) textEditor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							IEditorPart editorPart = pages[i].findEditor(textEditor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		}
	}
	

}
