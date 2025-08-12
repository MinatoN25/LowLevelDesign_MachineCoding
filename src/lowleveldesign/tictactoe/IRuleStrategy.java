package lowleveldesign.tictactoe;

public interface IRuleStrategy {
	
	boolean checkWinner(Board board, Piece piece, int row, int col);
}
