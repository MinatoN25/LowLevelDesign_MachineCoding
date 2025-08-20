package issueResolutionSystem.exceptions;

public class AgentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7216815306419292803L;

	public AgentNotFoundException(String id) {
		super("Agent not found: " + id);
	}
}