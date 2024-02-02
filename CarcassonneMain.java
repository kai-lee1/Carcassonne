package carcassonne;

import javax.swing.JFrame;

public class CarcassonneMain {
	// Frame initialization
	static JFrame f = new JFrame();

	public static void main(String[] args) {
		Board board = new Board(2);
		GUI.gui(board, f);
		f.setSize(500, 133);

		board.gameTime(board); // TODO i think theres a better way to do this

	}

	public static void drawGUI (Board board){
		f.getContentPane().removeAll(); //or remove(JComponent)
		f.repaint();
		GUI.gui(board, f);
	}
}


