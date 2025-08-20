package issueResolutionSystem.repository;

import java.util.Collection;
import java.util.List;

import issueResolutionSystem.domain.Issue;

public interface IssueRepository {
	Issue save(Issue issue);

	Issue findById(String id);

	List<Issue> findByCustomerEmail(String email);

	Collection<Issue> findAll();
}