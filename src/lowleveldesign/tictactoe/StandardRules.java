package lowleveldesign.tictactoe;

public class StandardRules implements IRuleStrategy{

	@Override
	public boolean checkWinner(Board board, Piece piece, int row, int col) {
		// check row
		boolean winner = true;
		for(int i=0; i< board.m; i++) {
			if(board.board[i][col].piece == null ||  !board.board[i][col].piece.equals(piece)) {
				winner = false;
				break;
			}
		}
		if(winner) return true;
		
		// check col
		winner = true;
		for(int i=0; i< board.n; i++) {
			if(board.board[row][i].piece == null ||  !board.board[row][i].piece.equals(piece)) {
				winner = false;
				break;
			}
		}		
		if(winner) return true;
		
		
		// check diagonal
		winner = true;
		if(row == col) {
			for(int i=0; i < board.m; i++)  {
				if(board.board[i][i].piece == null || !board.board[i][i].piece.equals(piece)) {
					winner = false;
					break;
				}
			}
		} else winner = false;
		if(winner) return true;
		
		
		// check anti-diagonal
		winner = true;
		for(int i= 0, j = board.n -1; i < board.m && j >= 0; i++, j--) {
			if(board.board[i][j].piece == null || !board.board[i][j].piece.equals(piece)) {
				winner = false;
			}
		}
		if(winner) return true;
		
		// no winner
		return false;
	}

}
