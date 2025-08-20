package issueResolutionSystem.repository;

import java.util.Collection;

import issueResolutionSystem.domain.Agent;

public interface AgentRepository {
	Agent save(Agent agent);

	Agent findById(String id);

	Collection<Agent> findAll();
}