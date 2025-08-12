package lowleveldesign.tictactoe;

import java.util.Objects;

public class Piece {
	char symbol;

	public Piece(char symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		return symbol == other.symbol;
	}

	@Override
	public String toString() {
		return symbol + "";
	}
}
