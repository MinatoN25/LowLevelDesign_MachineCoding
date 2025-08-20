package issueResolutionSystem.domain;

import java.time.Instant;

public final class Issue {
	private final String id;
	private final String transactionId;
	private final IssueType type;
	private final String subject;
	private final String description;
	private final String customerEmail;

	private volatile IssueStatus status = IssueStatus.OPEN;
	private volatile String resolutionNotes = "";
	private volatile String assignedAgentId = null;
	private final Instant createdAt = Instant.now();
	private volatile Instant updatedAt = createdAt;

	public Issue(String id, String transactionId, IssueType type, String subject, String description, String customerEmail) {
		this.id = id;
		this.transactionId = transactionId;
		this.type = type;
		this.subject = subject;
		this.description = description;
		this.customerEmail = customerEmail;
	}

	public String getId() {
		return id;
	}

	public IssueType getType() {
		return type;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public IssueStatus getStatus() {
		return status;
	}

	public String getAssignedAgentId() {
		return assignedAgentId;
	}

	public String getResolutionNotes() {
		return resolutionNotes;
	}

	public synchronized void assignTo(String agentId) {
		this.assignedAgentId = agentId;
		this.status = IssueStatus.IN_PROGRESS;
		this.updatedAt = Instant.now();
	}

	public synchronized void updateStatus(IssueStatus newStatus, String notes) {
		if (this.status == IssueStatus.RESOLVED) {
			throw new IllegalStateException("Issue is already resolved.");
		}
		
		if (newStatus == IssueStatus.RESOLVED && this.assignedAgentId == null) {
			throw new IllegalStateException("Cannot resolve an unassigned issue.");
		}
		this.status = newStatus;
		if (notes != null && !notes.isBlank()) {
			this.resolutionNotes = notes;
		}
		this.updatedAt = Instant.now();
	}

	public synchronized void resolve(String notes) {
		updateStatus(IssueStatus.RESOLVED, notes);
	}

	@Override
	public String toString() {
		return "Issue[" + "id='" + id + '\'' + ", txn='" + transactionId + '\'' + ", type=" + type + ", status="
				+ status + ", agent=" + assignedAgentId + ", updatedAt=" + updatedAt + ']';
	}
}