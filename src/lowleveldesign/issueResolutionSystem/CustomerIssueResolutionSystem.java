package issueResolutionSystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import issueResolutionSystem.api.CustomerIssueResolutionSystemAPI;
import issueResolutionSystem.exceptions.ValidationException;


//Driver class
public class CustomerIssueResolutionSystem {
	public static void main(String[] args) {
		CustomerIssueResolutionSystemAPI api = new CustomerIssueResolutionSystemAPI();

		String i1 = api.createIssue("T1", "PAYMENT_RELATED", "Payment Failed", "My payment failed but money is debited",
				"naruto@anime.com");
		String i2 = api.createIssue("T2", "MUTUAL_FUND_RELATED", "Purchase Failed", "Unable to purchase Mutual Fund",
				"minato@anime.com");
		String i3 = api.createIssue("T3", "PAYMENT_RELATED", "Payment Failed", "My payment failed but money is debited",
				"kushina@anime.com");
		String i4 = api.createIssue("T3", "INSURANCE_RELATED", "Insurance Query", "My Insurance premium is wrong",
				"hinata@anime.com");
		System.out.println();
		System.out.println();
		

		// Add agents with skills
		api.addAgent("agent1@test.com", "Agent 1", Arrays.asList("PAYMENT_RELATED", "GOLD_RELATED"));
		api.addAgent("agent2@test.com", "Agent 2", Arrays.asList("MUTUAL_FUND_RELATED"));
		System.out.println();
		System.out.println();
		
		// Assign issues (Agent1 takes Issue1, Agent2 takes Issue2, Issue3 gets queued on Agent1)
		api.assignIssue(i1);
		api.assignIssue(i2);
		api.assignIssue(i3);
		
		//No Agent skilled for Insurance
		api.assignIssue(i4);
		System.out.println();
		System.out.println();

		// Fetch issues by customer email
		Map<String, String> filter = new HashMap<>();
		filter.put("email", "minato@anime.com");
		api.getIssues(filter);
		System.out.println();
		System.out.println();
		
		// Update In Progress
		api.updateIssue(i1, "IN_PROGRESS", "waiting for payment confirmation");

		try {
			// Agent 1 can not take the issue, results in exception
			api.resolveIssue(i3, "Payment failed debited amount will get reversed");
		} catch (ValidationException ex) {
			System.out.println("The issue is not yet assigned, can not be resolved");
		}

		api.resolveIssue(i1, "Payment failed debited amount will get reversed");
		api.updateIssue(i3, "IN_PROGRESS", "waiting for payment confirmation");
		api.resolveIssue(i3, "Payment failed debited amount will get reversed");
		System.out.println();
		System.out.println();

		// View agent work history
		System.out.println("Agent work history ----- ");
		api.viewAgentsWorkHistory();
	}
}