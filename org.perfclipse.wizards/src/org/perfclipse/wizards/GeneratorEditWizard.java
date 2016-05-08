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

package org.perfclipse.wizards;

import org.perfcake.model.Scenario.Generator;
import org.perfclipse.core.commands.EditGeneratorThreadsCommand;
import org.perfclipse.core.commands.EditGeneratorTypeCommand;
import org.perfclipse.core.commands.EditRunTypeCommand;
import org.perfclipse.core.commands.EditRunValue;
import org.perfclipse.core.model.GeneratorModel;
import org.perfclipse.core.model.ModelMapper;
import org.perfclipse.core.model.RunModel;
import org.perfclipse.wizards.pages.GeneratorPage;

/**
 * @author Jakub Knetl
 *
 */
public class GeneratorEditWizard extends AbstractPerfCakeEditWizard {

	private GeneratorPage generatorPage;
	private GeneratorModel generator;

	public GeneratorEditWizard(GeneratorModel generator) {
		super("Edit Generator");
		ModelMapper mapper = generator.getMapper();
		this.generator = generator;
	}

	@Override
	public boolean performFinish() {

		Generator gen = generator.getGenerator();
		
		if (gen.getClazz() == null || 
				!(gen.getClazz().equals(generatorPage.getGeneratorName()))){
			getCommand().add(new EditGeneratorTypeCommand(generator, generatorPage.getGeneratorName()));
		}
		if (gen.getThreads() == null ||
				!(gen.getThreads().equals(generatorPage.getThreads()))){
			getCommand().add(new EditGeneratorThreadsCommand(generator, generatorPage.getThreads()));
		}
		
		return super.performFinish();
	}

	@Override
	public void addPages() {
		generatorPage = new GeneratorPage(generator);
		addPage(generatorPage);

		super.addPages();
	}

}
