package org.perfclipse.model;

import org.perfcake.model.Scenario;

public class ScenarioModel extends Scenario {
	
	public ScenarioModel() {
		super();
	}
	
	public ScenarioModel(Scenario model){
		super();
		// TODO rewrite initialization using reflection!!!
		this.generator = model.getGenerator();
		this.messages = model.getMessages();
		this.properties = model.getProperties();
		this.sender = model.getSender();
		this.reporting = model.getReporting();
		this.validation = model.getValidation();
	}

}
