package lowleveldesign.tictactoe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Game {
	Board board;
	LinkedList<Player> player = new LinkedList<>();
	int row;
	int col;
	IRuleStrategy rules;
	List<INotification> observers = new ArrayList<>();
	
	static Scanner sc = new Scanner(System.in);
	public Game(int row, int col, IRuleStrategy rules) {
		super();
		this.board = new Board(row, col);
		this.row = row;
		this.col = col;
		this.rules = rules;
	}
	
	public void addObserver(INotification notification) {
		this.observers.add(notification);
	}
	
	public void addPlayer(Player player) {
		this.player.add(player);
		
		for (INotification iNotification : observers) {
			iNotification.addPlayer(player);
		}
		
	}
	
	public void play() {
		while(player.size() > 1) {
			Player turn = player.peekFirst();
			System.out.println(turn.name + " turn");
			int x = sc.nextInt();
			int y = sc.nextInt();
			
			if(!board.checkValidInput(x, y) || !board.canPlace(x, y)) continue;
			board.playPiece(x, y, turn.piece);
			board.displayBoard();
			
			boolean winner = rules.checkWinner(board, turn.piece, x, y);
			
			if(winner) {
				for (INotification iNotification : observers) {
					iNotification.alert(turn);
				}
				player.remove(turn);
				continue;
			}
			
			player.addLast(player.pollFirst());
		}
	}
	
	
}
