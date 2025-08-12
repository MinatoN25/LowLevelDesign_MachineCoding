package lowleveldesign.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class InGamePopUp implements INotification {
	
	List<Player> players = new ArrayList<>();

	@Override
	public void alert(Player winner) {
		for (Player player : players) {
			if(player.equals(winner)) {
				System.out.println("You have won!!!");
			} else {
				System.out.println(winner + " has won!!!");
			}
		}
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}

}
