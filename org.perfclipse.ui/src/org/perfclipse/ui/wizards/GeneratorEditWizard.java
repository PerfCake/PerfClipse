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

package org.perfclipse.ui.wizards;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.wizard.Wizard;
import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Generator;
import org.perfcake.model.Scenario.Generator.Run;
import org.perfclipse.model.GeneratorModel;
import org.perfclipse.ui.gef.commands.EditGeneratorRunCommand;
import org.perfclipse.ui.gef.commands.EditGeneratorThreadsCommand;
import org.perfclipse.ui.gef.commands.RenameGeneratorCommand;

/**
 * @author Jakub Knetl
 *
 */
public class GeneratorEditWizard extends Wizard {

	private GeneratorPage generatorPage;
	private CompoundCommand command;
	private GeneratorModel generator;

	public GeneratorEditWizard(GeneratorModel generator) {
		this.generator = generator;
	}

	@Override
	public boolean performFinish() {

		command = new CompoundCommand("Generator edit");
		
		Generator gen = generator.getGenerator();
		
		if (!(gen.getClazz().equals(generatorPage.getGeneratorName()))){
			command.add(new RenameGeneratorCommand(generator, generatorPage.getGeneratorName()));
		}
		if (!(gen.getRun().getType().equals(generatorPage.getRunType())) 
				|| !(gen.getRun().getValue().equals(generatorPage.getRunValue()))){
			Run run = new ObjectFactory().createScenarioGeneratorRun();
			run.setType(generatorPage.getRunType());
			run.setValue(Integer.toString(generatorPage.getRunValue()));
			command.add(new EditGeneratorRunCommand(generator, run));
		}
		
		if (!(gen.getThreads().equals(Integer.toString(generatorPage.getThreads())))){
			command.add(new EditGeneratorThreadsCommand(generator, Integer.toString(generatorPage.getThreads())));
		}

		return true;
	}

	@Override
	public void addPages() {
		generatorPage = new GeneratorPage();
		addPage(generatorPage);

		super.addPages();
	}
	
	public CompoundCommand getCommand(){
		return command;
	}
}
