package org.perfclipse.ui.gef.policies.directedit;

import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.perfclipse.ui.gef.figures.ILabeledFigure;

public abstract class LabelDirectEditPolicy extends DirectEditPolicy {
	
	protected ILabeledFigure labeledFigure;

	public LabelDirectEditPolicy(ILabeledFigure labeledFigure) {
		if (labeledFigure == null)
			throw new IllegalArgumentException("figure must not be null");
		if (!(labeledFigure instanceof ILabeledFigure))
			throw new IllegalArgumentException("Figure has to be instance of ILabeledFigure");

		this.labeledFigure = labeledFigure;
	}

	@Override
	protected void showCurrentEditValue(DirectEditRequest request) {
		labeledFigure.getLabel().setText((String) request.getCellEditor().getValue());
	}

}
