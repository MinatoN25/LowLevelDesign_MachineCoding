package lowleveldesign.tictactoe;

public class GameInitializer {

	public static void main(String[] args) {
		Player player1 = new Player(new Piece('X'), "Manish");
		Player player2 = new Player(new Piece('O'), "Karthik");
		
		Game game = new Game(3, 3, new StandardRules());
		game.addObserver(new InGamePopUp());
		game.addPlayer(player1);
		game.addPlayer(player2);
		
		game.play();
	}
}
