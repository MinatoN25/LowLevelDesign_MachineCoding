package issueResolutionSystem.strategy;

import issueResolutionSystem.domain.Agent;

public final class AssignmentResult {
	public final Agent directAssignee;
	public final Agent queuedOn;

	public AssignmentResult(Agent directAssignee, Agent queuedOn) {
		this.directAssignee = directAssignee;
		this.queuedOn = queuedOn;
	}

	public static AssignmentResult assignTo(Agent a) {
		return new AssignmentResult(a, null);
	}

	public static AssignmentResult queueOn(Agent a) {
		return new AssignmentResult(null, a);
	}
}