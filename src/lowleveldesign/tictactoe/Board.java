package lowleveldesign.tictactoe;

public class Board {
	public Cell[][] board;
	public int m;
	public int n;

	public Board(int m, int n) {
		super();
		this.m = m;
		this.n = n;
		initializeBoard();
	}
	
	private void initializeBoard() {
		this.board = new Cell[m][n];
		for(int i=0; i< m; i++) {
			for(int j =0; j< n; j++) {
				board[i][j] = new Cell();
			}
		}
	}
	
	public boolean checkValidInput(int x, int y) {
		if(x < 0 || y < 0 || x >= m || y >= n) {
			System.out.println("Please enter valid input");
			return false;
		}
		return true;
	}
	
	public boolean canPlace(int x, int y) {
		if(board[x][y].piece == null) return true;
		return false;
	}
	
	public void playPiece(int x, int y, Piece piece) {
		board[x][y].piece = piece;
	}
	
	public void displayBoard() {
		System.out.println();
		System.out.println();
		System.out.println("--------------------");
		for(int i=0; i< m; i++) {
			for(int j=0; j< n; j++) {
				System.out.print(board[i][j].piece + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println("--------------------");
	}
}
