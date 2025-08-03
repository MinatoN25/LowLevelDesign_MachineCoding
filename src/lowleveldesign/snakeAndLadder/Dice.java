package lowleveldesign.snakeAndLadder;

public class Dice {
	private static final int MAX = 6;
	int diceCount = 1;

	public Dice(int diceCount) {
		super();
		this.diceCount = diceCount;
	}

	public int rollDice() {
		int rollSum =0;
		for (int i = 0; i < diceCount; i++) {
			rollSum += (int) (Math.random() * MAX) + 1;
		}
		return rollSum;
	}

}
