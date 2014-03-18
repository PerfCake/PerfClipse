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

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.perfcake.model.Scenario.Generator;
import org.perfclipse.model.GeneratorModel;
import org.perfclipse.model.PropertyModel;
import org.perfclipse.model.RunModel;
import org.perfclipse.ui.gef.commands.EditGeneratorThreadsCommand;
import org.perfclipse.ui.gef.commands.EditRunTypeCommand;
import org.perfclipse.ui.gef.commands.EditRunValue;
import org.perfclipse.ui.gef.commands.RenameGeneratorCommand;

/**
 * @author Jakub Knetl
 *
 */
public class GeneratorEditWizard extends AbstractPerfCakeEditWizard {

	private GeneratorPage generatorPage;
	private CompoundCommand command;
	private GeneratorModel generator;
	private List<PropertyModel> properties;
	private RunModel run;

	public GeneratorEditWizard(GeneratorModel generator,
			List<PropertyModel> properties, RunModel run) {
		this.generator = generator;
		this.properties = properties;
		this.run = run;
	}

	@Override
	public boolean performFinish() {

		command = new CompoundCommand("Generator edit");
		
		Generator gen = generator.getGenerator();
		
		if (!(gen.getClazz().equals(generatorPage.getGeneratorName()))){
			command.add(new RenameGeneratorCommand(generator, generatorPage.getGeneratorName()));
		}
		if (!(gen.getRun().getType().equals(generatorPage.getRunType()))){
			command.add(new EditRunTypeCommand(run, generatorPage.getRunType()));
		}
		if (!(gen.getRun().getValue().equals(generatorPage.getRunValue()))){
			command.add(new EditRunValue(run, String.valueOf(generatorPage.getRunValue())));
		}
		
		if (!(gen.getThreads().equals(Integer.toString(generatorPage.getThreads())))){
			command.add(new EditGeneratorThreadsCommand(generator, Integer.toString(generatorPage.getThreads())));
		}
		
		//undo all direct edit changes and append them to global command which executes them again
		//it is needed in order to allow user undo whole edit command by one click.
		undoEditingSupportCommands();
		for (Command c : editingSupportCommands){
			command.add(c);
		}

		return true;
	}


	@Override
	public boolean performCancel() {
		
		undoEditingSupportCommands();
		return true;
	}

	@Override
	public void addPages() {
		generatorPage = new GeneratorPage(generator, properties);
		addPage(generatorPage);

		super.addPages();
	}
	
	public CompoundCommand getCommand(){
		return command;
	}
}
