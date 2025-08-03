package lowleveldesign.snakeAndLadder;

import java.util.List;

public class Board {
	Cell[][] cell;
	int row;
	int col;
	List<Jump> jumps;
	public Board(int row, int col, List<Jump> jumps) {
		super();
		this.cell = new Cell[row][col];
		this.row = row;
		this.col = col;
		this.jumps = jumps;
		initializeBoard();
		addSnakeLadderActions();
	}
	
	public Cell getCell(int start) {
		int cellRow = start / col;
		int cellCol = start % col;
		return cell[cellRow][cellCol];
	}
	
	private void addSnakeLadderActions() {
		for(Jump j : jumps) {
			int start = j.start;
			Cell currCell = getCell(start);
			currCell.jump = j;
		}
	}
	
	private void initializeBoard() {
		for(int i=0; i< row; i++) {
			for(int j=0; j< col; j++) {
				cell[i][j] = new Cell();
			}
		}
	}
}
