package issueResolutionSystem.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class Agent {
	private final String id;
	private final String email;
	private final String name;
	private final Set<IssueType> skills;

	private volatile String activeIssueId = null;

	private final Queue<String> waitingQueue = new ConcurrentLinkedQueue<>();

	private final List<String> workHistory = new ArrayList<>();
	private final ReentrantLock lock = new ReentrantLock(true);

	public Agent(String id, String email, String name, Set<IssueType> skills) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.skills = EnumSet.copyOf(skills);
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public Set<IssueType> getSkills() {
		return Collections.unmodifiableSet(skills);
	}

	public ReentrantLock getLock() {
		return lock;
	}

	public boolean isFree() {
		return activeIssueId == null;
	}

	public void startIssue(String issueId) {
		this.activeIssueId = issueId;
		this.workHistory.add(issueId);
	}

	public void finishActiveIssue() {
		this.activeIssueId = null;
	}

	public void enqueueIssue(String issueId) {
		waitingQueue.offer(issueId);
	}

	public String pollNextQueuedIssue() {
		return waitingQueue.poll();
	}

	public int queueSize() {
		return waitingQueue.size();
	}

	public List<String> getWorkHistory() {
		return Collections.unmodifiableList(workHistory);
	}

	@Override
	public String toString() {
		return "Agent[" + "id='" + id + '\'' + ", name='" + name + '\'' + ", skills=" + skills + ", active="
				+ activeIssueId + ", queued=" + waitingQueue.size() + ']';
	}
}
