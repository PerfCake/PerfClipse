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

package org.perfclipse.ui.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class Utils {

	/**
	 * Return first selected File and returns it as IFile Instance.
	 * @param event
	 * @return IFile instance of selected file or null if the selected object is not IFile instance
	 *
	 */
	public static IFile getFirstSelectedFile(ExecutionEvent event){
		ISelection selected = HandlerUtil.getActiveMenuSelection(event);
		IStructuredSelection structuredSelection = (IStructuredSelection) selected;
		Object firstSelected = structuredSelection.getFirstElement();
		if (firstSelected instanceof IFile){
			IFile file = (IFile) firstSelected;
			return file;
		}
		
		return null;
	}
}
