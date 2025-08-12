package lowleveldesign.tictactoe;

import java.util.Objects;

public class Player {
	Piece piece;
	String name;

	public Player(Piece piece, String name) {
		super();
		this.piece = piece;
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, piece);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(name, other.name) && Objects.equals(piece, other.piece);
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
