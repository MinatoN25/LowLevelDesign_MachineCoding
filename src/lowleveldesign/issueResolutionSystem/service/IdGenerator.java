package issueResolutionSystem.service;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {
	private final AtomicInteger issueSeq = new AtomicInteger(0);
	private final AtomicInteger agentSeq = new AtomicInteger(0);

	public String nextIssueId() {
		return "Issue" + issueSeq.incrementAndGet();
	}

	public String nextAgentId() {
		return "Agent" + agentSeq.incrementAndGet();
	}
}