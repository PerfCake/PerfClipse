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

package org.perfclipse.wizards.swt.events;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;

/**
 * Class which maps mouse double click MouseListener event to 
 * {@link SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)}
 * @author Jakub Knetl
 *
 */
public class DoubleClickSelectionAdapter extends MouseAdapter {

	private SelectionAdapter adapter;
	/**
	 * 
	 */
	public DoubleClickSelectionAdapter(SelectionAdapter adapter) {
		if (adapter == null){
			throw new IllegalArgumentException("adapter is null");
		}
		this.adapter = adapter;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		SelectionEvent selEvent = new SelectionEvent(mouseEventCopy(e));

		/*
		 * Current implementation of widgetSelected method does not use
		 * the argument so it could basically be null, but it should be 
		 * fixed.
		 */
		//TODO: copy data from MouseEvent to selection event
		adapter.widgetSelected(selEvent);
	}
	
	private Event mouseEventCopy(MouseEvent mouseEvent){
		Event event = new Event();
		
		return event;
	}
	

}
