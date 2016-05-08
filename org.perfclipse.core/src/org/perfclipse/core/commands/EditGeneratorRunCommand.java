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

package org.perfclipse.core.commands;

import org.eclipse.gef.commands.Command;
import org.perfcake.model.Scenario.Run;
import org.perfclipse.core.model.ScenarioModel;

/**
 * @author Jakub Knetl
 *
 */
public class EditGeneratorRunCommand extends Command {

	private ScenarioModel scenario;
	private Run run;
	private Run oldRun;

	public EditGeneratorRunCommand(ScenarioModel scenario, Run run) {
		super("Edit scenario run");
		this.scenario = scenario;
		this.run = run;
		this.oldRun = scenario.getScenario().getRun();
	}

	@Override
	public void execute() {
		scenario.setRun(run);
	}

	@Override
	public void undo() {
		scenario.setRun(oldRun);
	}
}
