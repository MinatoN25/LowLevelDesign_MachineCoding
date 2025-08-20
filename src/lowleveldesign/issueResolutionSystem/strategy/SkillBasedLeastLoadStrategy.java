package issueResolutionSystem.strategy;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import issueResolutionSystem.domain.Agent;
import issueResolutionSystem.domain.Issue;
import issueResolutionSystem.exceptions.AssignmentException;

public final class SkillBasedLeastLoadStrategy implements AssignmentStrategy {
	@Override
	public AssignmentResult assign(Issue issue, Collection<Agent> allAgents) {
		List<Agent> skilled = allAgents.stream().filter(a -> a.getSkills().contains(issue.getType()))
				.collect(Collectors.toList());
		if (skilled.isEmpty()) {
			throw new AssignmentException("No agents skilled for type " + issue.getType());
		}

		for (Agent a : skilled) {
			if (a.isFree())
				return AssignmentResult.assignTo(a);
		}

		// queue on the skilled agent with the smallest waiting queue
		Agent leastLoaded = skilled.stream().min(Comparator.comparingInt(Agent::queueSize)).orElseThrow(); 
		return AssignmentResult.queueOn(leastLoaded);
	}
}