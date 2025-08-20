package issueResolutionSystem.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import issueResolutionSystem.domain.Issue;
import issueResolutionSystem.exceptions.IssueNotFoundException;

public final class InMemoryIssueRepository implements IssueRepository {
	private final Map<String, Issue> store = new ConcurrentHashMap<>();

	@Override
	public Issue save(Issue issue) {
		store.put(issue.getId(), issue);
		return issue;
	}

	@Override
	public Issue findById(String id) {
		Issue i = store.get(id);
		if (i == null)
			throw new IssueNotFoundException(id);
		return i;
	}

	@Override
	public List<Issue> findByCustomerEmail(String email) {
		return store.values().stream().filter(i -> i.getCustomerEmail().equals(email))
				.sorted(Comparator.comparing(Issue::getId)).collect(Collectors.toList());
	}

	@Override
	public Collection<Issue> findAll() {
		return store.values();
	}
}