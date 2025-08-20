package issueResolutionSystem.exceptions;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 3808086382882370923L;

	public ValidationException(String msg) {
		super(msg);
	}
}