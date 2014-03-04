package org.perfclipse.ui.editors.palettefactories;

import org.perfcake.model.ObjectFactory;
import org.perfcake.model.Scenario.Reporting.Reporter.Destination;

public class DestinationFactory extends ParametrizedSimpleFactory {

	public DestinationFactory(Class<?> aClass, String parameter) {
		super(aClass, parameter);
	}
	
	@Override
	public Object getNewObject() {
		ObjectFactory f = new ObjectFactory();
		Destination d = f.createScenarioReportingReporterDestination();
		d.setClazz(parameter);
		
		return d;
	}

}
