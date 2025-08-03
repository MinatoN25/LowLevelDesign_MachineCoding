package lowleveldesign.tictactoe;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {
	Board board;
	LinkedList<Player> players = new LinkedList<>();
	Dice dice;
	Map<Player, Integer> positions = new HashMap<>();
	int winnerTrack =1;

	Game(List<Jump> snakesAndLadders, int dices, List<Player> players, int m, int n) {
		this.board = new Board(m, n, snakesAndLadders);
		this.players.addAll(players);
		this.dice = new Dice(dices);

		for (Player player : players) {
			positions.put(player, 0);
		}
	}

	private int checkJump(Cell playerCell, Player player, int newPos) {
		if (playerCell.jump != null) {
			if (playerCell.jump.end > positions.get(player)) {
				System.out.println(player + " got the ladder and jumped to: " + playerCell.jump.end);
			} else {
				System.out.println(player + " got the snake and jumped to: " + playerCell.jump.end);
			}
			return playerCell.jump.end;
		}
		System.out.println(player + " jumped to: " + newPos);
		return newPos;
	}

	public void play() {
		while (players.size() > 1) {
			Player turn = players.peekFirst();
			int roll = this.dice.rollDice();

			System.out.println(turn.name + " rolled: " + roll);
			int newPos = positions.get(turn) + roll;
			
			if (newPos > board.row * board.col - 1) {
				System.out.println(turn + " came at position: " + winnerTrack++);
				players.pollFirst();
				continue;
			} else {
				players.pollFirst();
				players.offerLast(turn);
			}

			Cell playerCell = board.getCell(newPos);
			newPos = checkJump(playerCell, turn, newPos);
			positions.put(turn, newPos);
			
		}
	}
}
