package carcassonne;

import javax.swing.*;

public class CarcassonneMain {
	// Frame initialization
	static JFrame f = new JFrame();
	static Input inp = new Input();

	public static void main(String[] args) {
		Board board = new Board(2);
		f.setSize(500, 133);
		drawGUI(board);
		// inp.draw();

		board.gameTime(board); // TODO i think theres a better way to do this

	}

	public static void drawGUI (Board board){
		f.getContentPane().removeAll(); //or remove(JComponent)
		f.repaint();
		GUI.gui(board, f);
	}
}