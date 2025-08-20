package issueResolutionSystem.service;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import issueResolutionSystem.domain.Agent;
import issueResolutionSystem.domain.Issue;
import issueResolutionSystem.domain.IssueStatus;
import issueResolutionSystem.domain.IssueType;
import issueResolutionSystem.exceptions.AssignmentException;
import issueResolutionSystem.exceptions.ValidationException;
import issueResolutionSystem.repository.AgentRepository;
import issueResolutionSystem.repository.IssueRepository;
import issueResolutionSystem.strategy.AssignmentResult;
import issueResolutionSystem.strategy.AssignmentStrategy;

public final class IssueService {
	private final IssueRepository issues;
	private final AgentRepository agents;
	private final AssignmentStrategy assignmentStrategy;

	public IssueService(IssueRepository issues, AgentRepository agents, AssignmentStrategy strategy) {
		this.issues = issues;
		this.agents = agents;
		this.assignmentStrategy = strategy;
	}

	public Issue createIssue(String id, String transactionId, IssueType type, String subject, String description,
			String email) {
		Issue issue = new Issue(id, transactionId, type, subject, description, email);
		issues.save(issue);
		return issue;
	}

	public Agent addAgent(String id, String email, String name, List<IssueType> skills) {
		if (skills == null || skills.isEmpty())
			throw new ValidationException("Agent must have at least one skill.");
		Agent agent = new Agent(id, email, name, EnumSet.copyOf(skills));
		agents.save(agent);
		return agent;
	}

	public synchronized void assignIssue(String issueId) {
		Issue issue = issues.findById(issueId);
		if (issue.getStatus() != IssueStatus.OPEN) {
			throw new AssignmentException("Issue " + issueId + " already assigned or resolved.");
		}

		AssignmentResult result = assignmentStrategy.assign(issue, agents.findAll());
		if (result.directAssignee != null) {
			Agent a = result.directAssignee;
			lockAgent(a, () -> {
				if (!a.isFree()) { 
					a.enqueueIssue(issue.getId());
				} else {
					a.startIssue(issue.getId());
					issue.assignTo(a.getId());
				}
			});
		} else if (result.queuedOn != null) {
			Agent a = result.queuedOn;
			lockAgent(a, () -> a.enqueueIssue(issue.getId()));
		}
	}

	public void updateIssue(String issueId, IssueStatus status, String notes) {
		Issue issue = issues.findById(issueId);
		issue.updateStatus(status, notes);
	}

	public void resolveIssue(String issueId, String resolutionNotes) {
		Issue issue = issues.findById(issueId);
		String agentId = issue.getAssignedAgentId();
		if (agentId == null)
			throw new ValidationException("Issue not assigned to any agent.");
		Agent agent = agents.findById(agentId);

		// Resolving issue
		issue.resolve(resolutionNotes);

		// pull queued requests
		lockAgent(agent, () -> {
			agent.finishActiveIssue();
			String nextIssueId = agent.pollNextQueuedIssue();
			if (nextIssueId != null) {
				Issue next = issues.findById(nextIssueId);
				if (next.getStatus() == IssueStatus.OPEN) {
					agent.startIssue(next.getId());
					next.assignTo(agent.getId());
				}
			}
		});
	}

	public List<Issue> getIssuesByCustomerEmail(String email) {
		validateEmail(email);
		return issues.findByCustomerEmail(email);
	}

	public Map<String, List<String>> viewAgentWorkHistory() {
		return agents.findAll().stream()
				.collect(Collectors.toMap(Agent::getId, Agent::getWorkHistory, (a, b) -> a, LinkedHashMap::new));
	}

	private void validateEmail(String email) {
		if (email == null || !email.contains("@"))
			throw new ValidationException("Invalid email: " + email);
	}

	private void lockAgent(Agent agent, Runnable action) {
		ReentrantLock lock = agent.getLock();
		lock.lock();
		try {
			action.run();
		} finally {
			lock.unlock();
		}
	}
}