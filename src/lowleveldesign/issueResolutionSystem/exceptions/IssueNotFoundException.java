package issueResolutionSystem.exceptions;

public class IssueNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3105880010658097831L;

	public IssueNotFoundException(String id) {
		super("Issue not found: " + id);
	}
}