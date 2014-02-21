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

package org.perfclipse.ui.launching;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

public class XMLFileContentProvider extends BaseWorkbenchContentProvider implements ITreeContentProvider {

    @Override
    public Object[] getChildren(final Object element) {
    	//get all children of element
        List<Object> children = Arrays.asList(super.getChildren(element));

        for (Object child : children) {
            if ((child instanceof IFile) && (!isAccepted((IFile) child)))
                children.remove(child);
        }

        return children.toArray();
    }

    private boolean isAccepted(final IFile child) {
    	if ("xml".equalsIgnoreCase(child.getFileExtension()))
    		return true;

        return false;
    }
}