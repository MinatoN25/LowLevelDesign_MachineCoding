package issueResolutionSystem.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import issueResolutionSystem.domain.Issue;
import issueResolutionSystem.domain.IssueStatus;
import issueResolutionSystem.domain.IssueType;
import issueResolutionSystem.exceptions.AssignmentException;
import issueResolutionSystem.exceptions.ValidationException;
import issueResolutionSystem.repository.AgentRepository;
import issueResolutionSystem.repository.InMemoryAgentRepository;
import issueResolutionSystem.repository.InMemoryIssueRepository;
import issueResolutionSystem.repository.IssueRepository;
import issueResolutionSystem.service.IdGenerator;
import issueResolutionSystem.service.IssueService;
import issueResolutionSystem.strategy.AssignmentStrategy;
import issueResolutionSystem.strategy.SkillBasedLeastLoadStrategy;

public final class CustomerIssueResolutionSystemAPI {
	private final IdGenerator ids = new IdGenerator();
	private final IssueService service;

	public CustomerIssueResolutionSystemAPI( ) {
		IssueRepository issueRepo = new InMemoryIssueRepository();
		AgentRepository agentRepo = new InMemoryAgentRepository();
		AssignmentStrategy strategy = new SkillBasedLeastLoadStrategy();
		this.service = new IssueService(issueRepo, agentRepo, strategy);
	}

	public String createIssue(String txnId, String issueType, String subject, String description, String email) {
		IssueType type = parseType(issueType);
		String id = ids.nextIssueId();
		service.createIssue(id, txnId, type, subject, description, email);
		System.out.println("Issue " + id + " created against transaction \"" + txnId + "\"");
		return id;
	}

	public String addAgent(String email, String name, List<String> listIssueTypes) {
		String id = ids.nextAgentId();
		List<IssueType> types = listIssueTypes.stream().map(this::parseType).collect(Collectors.toList());
		service.addAgent(id, email, name, types);
		System.out.println("Agent " + id + " created");
		return id;
	}

	public void assignIssue(String issueId) {
		try {
			service.assignIssue(issueId);
			System.out.println("Issue " + issueId + " is accepted");
		} catch (AssignmentException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public List<Issue> getIssues(Map<String, String> filter) {
		if (filter.containsKey("email")) {
			String email = filter.get("email");
			List<Issue> out = service.getIssuesByCustomerEmail(email);
			prettyPrintIssues(out);
			return out;
		}
		throw new ValidationException("Unsupported filter. Supported: {\"email\": \"...\"}");
	}

	public void updateIssue(String issueId, String status, String notes) {
		IssueStatus st = parseStatus(status);
		service.updateIssue(issueId, st, notes);
		System.out.println(issueId + " status updated to " + st);
	}

	public void resolveIssue(String issueId, String resolution) {
		service.resolveIssue(issueId, resolution);
		System.out.println(issueId + " issue marked resolved");
	}

	public Map<String, List<String>> viewAgentsWorkHistory() {
		Map<String, List<String>> map = service.viewAgentWorkHistory();
		for (var e : map.entrySet()) {
			System.out.println(e.getKey() + " -> " + e.getValue());
		}
		return map;
	}

	private IssueType parseType(String s) {
		switch (s) {
		case "PAYMENT_RELATED":
		case "PAYMENT":
			return IssueType.PAYMENT_RELATED;
		case "MUTUAL_FUND_RELATED":
		case "MUTUAL_FUND":
			return IssueType.MUTUAL_FUND_RELATED;
		case "GOLD_RELATED":
		case "GOLD":
			return IssueType.GOLD_RELATED;
		case "INSURANCE_RELATED":
		case "INSURANCE":
			return IssueType.INSURANCE_RELATED;
		default:
			throw new ValidationException("Unknown issue type: " + s);
		}
	}

	private IssueStatus parseStatus(String s) {
		switch (s) {
		case "OPEN":
			return IssueStatus.OPEN;
		case "IN_PROGRESS":
			return IssueStatus.IN_PROGRESS;
		case "RESOLVED":
			return IssueStatus.RESOLVED;
		default:
			throw new ValidationException("Unknown status: " + s);
		}
	}

	private void prettyPrintIssues(List<Issue> issues) {
		System.out.println("getIssues(" + issues.size() + "):");
		for (Issue i : issues) {
			System.out.println("    - " + i);
		}
	}
}
