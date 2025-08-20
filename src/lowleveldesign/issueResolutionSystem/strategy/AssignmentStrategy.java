package issueResolutionSystem.strategy;

import java.util.Collection;

import issueResolutionSystem.domain.Agent;
import issueResolutionSystem.domain.Issue;

public interface AssignmentStrategy {
	AssignmentResult assign(Issue issue, Collection<Agent> agents);
}