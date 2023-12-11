import java.util.Scanner;

public class CarcassonneMain {
	public static void main (String[] args) {
		Board board = new Board(2);
		/*
		for (int i = 0; i < 133; i++) {  //create tiles
			for (int j = 0; j < 133; j++) {
				for (int x : board.board[i][j].types)
				System.out.print(x);
			}
			System.out.println();
		}
		*/
		for (int i = 0; i < 65; i++) {
			for (int j = 0; j < 4; j++)
				System.out.print(board.tiles.get(i).types[j]);
			System.out.println();
		}

		board.gameTime(board); //TODO i think theres a better way to do this

	}
}
