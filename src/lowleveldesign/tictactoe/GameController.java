package lowleveldesign.tictactoe;

import java.util.List;

public class GameController {
	
	public static void main(String[] args) {
		
		Jump ladder1 = new Jump(14, 3);
		Jump ladder2 = new Jump(20, 15);
		Jump snake1 = new Jump(2, 22);
		Jump snake2 = new Jump(16, 19);
		
		Player player1 = new Player(1, "Manish");
		Player player2 = new Player(2, "Minato");
		Player player3 = new Player(2, "Itachi");
		
		Game game = new Game(List.of(ladder1, ladder2, snake1, snake2), 2, List.of(player1, player2, player3), 4, 6);
		
		game.play();
	}

}
