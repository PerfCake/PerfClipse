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

import java.util.ArrayList;
import java.util.List;

import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfcake.model.Scenario.Validation.Validator;
import org.perfclipse.model.MessageModel;
import org.perfclipse.model.ModelMapper;
import org.perfclipse.model.ValidatorModel;
import org.perfclipse.model.ValidatorRefModel;
import org.perfclipse.ui.gef.commands.EditValidatorIdCommand;
import org.perfclipse.ui.gef.commands.EditValidatorRefCommand;
import org.perfclipse.ui.gef.commands.EditValidatorTypeCommand;
import org.perfclipse.ui.gef.commands.EditValidatorValueCommand;
import org.perfclipse.ui.wizards.pages.ValidatorPage;

/**
 * @author Jakub Knetl
 *
 */
public class ValidatorEditWizard extends AbstractPerfCakeEditWizard {

	private ValidatorModel validator;
	private ValidatorPage validatorPage;
	
	private List<MessageModel> messages;
	/**
	 * @param validator
	 */
	public ValidatorEditWizard(ValidatorModel validator, List<MessageModel> messages) {
		super("Edit validator");
		this.validator = validator;
		this.messages = messages;
	}
	@Override
	public boolean performFinish() {
		Validator v = validator.getValidator();
		if (v.getClazz() == null ||
				!v.getClazz().equals(validatorPage.getValidatorName()))
			getCommand().add(new EditValidatorTypeCommand(validator, validatorPage.getValidatorName()));
		if (v.getId() == null ||
				!v.getId().equals(validatorPage.getValidatorId())){
			getCommand().add(new EditValidatorIdCommand(validator, validatorPage.getValidatorId()));
			//check and change validatorRefs
			if (messages != null){
				for (MessageModel m : messages){
					for (ValidatorRef ref : m.getMessage().getValidatorRef()){
						ModelMapper mapper = validator.getMapper();
						getCommand().add(new EditValidatorRefCommand(
								(ValidatorRefModel) mapper.getModelContainer(ref),
								validatorPage.getValidatorId()));
					}
						
				}
			}
		}
		if (v.getValue() == null || 
				!v.getValue().equals(validatorPage.getValidatorValue()))
			getCommand().add(new EditValidatorValueCommand(validator, validatorPage.getValidatorValue()));
		return super.performFinish();
	}
	@Override
	public void addPages() {
		List<ValidatorModel> validators = new ArrayList<>();
		if (validator.getMapper().getValidation() != null && 
				validator.getMapper().getValidation().getValidation() != null){
			for (Validator v : validator.getMapper().getValidation().getValidation().getValidator()){
				if (v != validator.getValidator())
					validators.add((ValidatorModel) validator.getMapper().getModelContainer(v));
			}
		}
		//TODO: in new sceanrio wizard validators will be always empty!!!???
		validatorPage = new ValidatorPage(validator, validators);
		addPage(validatorPage);
		super.addPages();
	}
	
	
}
