package org.perfclipse.ui.gef.policies;

import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.perfcake.model.Scenario.Messages.Message;
import org.perfcake.model.Scenario.Messages.Message.ValidatorRef;
import org.perfclipse.model.MessagesModel;
import org.perfclipse.model.ModelMapper;

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

	/**
	 * Searches for instance of Message which has assigned same validatorRef instance
	 * @param validatorRef  validator ref whose parent message is searched for.
	 * @param mapper ModelMapper for given scenario
	 * @return PerfCake model of the message. or null if no message with given validator ref.
	 */
	protected Message findParentMessage(ValidatorRef validatorRef, ModelMapper mapper) {
		MessagesModel messages = mapper.getMessagesModel();
		
		for (Message m : messages.getMessages().getMessage()){
			for (ValidatorRef ref : m.getValidatorRef()){
				if (ref == validatorRef)
					return m;
			}
		}
		
		return null;
	}

}