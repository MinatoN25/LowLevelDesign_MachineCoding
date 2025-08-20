package issueResolutionSystem.repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import issueResolutionSystem.domain.Agent;
import issueResolutionSystem.exceptions.AgentNotFoundException;

public final class InMemoryAgentRepository implements AgentRepository {
	private final Map<String, Agent> store = new ConcurrentHashMap<>();

	@Override
	public Agent save(Agent agent) {
		store.put(agent.getId(), agent);
		return agent;
	}

	@Override
	public Agent findById(String id) {
		Agent a = store.get(id);
		if (a == null)
			throw new AgentNotFoundException(id);
		return a;
	}

	@Override
	public Collection<Agent> findAll() {
		return store.values();
	}
}