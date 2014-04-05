package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;

public abstract class ValidatorRefGraphicalNodeEditPolicy extends
		GraphicalNodeEditPolicy {

	public ValidatorRefGraphicalNodeEditPolicy() {
		super();
	}

	/**
	 * Check if validator does not contain same reference 
	 * @param message which has reference
	 * @param refID Reference id from message to be checked 
	 * @return True if there is no such reference exits. False if adding this
	 * reference will cause duplicity.
	 */
	protected boolean isReferenceUnique(Message message, String refID) {
		//check if connection is unique
		if (message.getValidatorRef() != null){
			for (ValidatorRef currentRef : message.getValidatorRef()){
				if (refID.equals(currentRef.getId()))
					return false;
			}
		}
		
		return true;
	}

}