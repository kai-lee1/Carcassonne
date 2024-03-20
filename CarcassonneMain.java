package carcassonne;

import javax.swing.*;

public class CarcassonneMain {
	// Frame initialization
	static JFrame f = new JFrame();
	static Tile currentTile = new EmptyTile();
	static Input inp = new Input();
	static boolean ready = false;
	static long endWaitTime = System.currentTimeMillis() + 500*1000;
	static int cityCount = 0;
	static boolean end = false;
	static int playerCount = 2;

	public static void main(String[] args) {
		Board board = new Board(playerCount);
		f.setSize(500, 133);
		GUI.gui(board, f, 222*64, 222*64);
		drawGUI(board);

		board.gameTime(board); // TODO i think theres a better way to do this

	}

	public static void drawGUI (Board board){
		f.getContentPane().removeAll(); //or remove(JComponent)
		f.repaint();
		GUI.gui(board, f, GUI.sp.getHorizontalScrollBar().getValue(), GUI.sp.getVerticalScrollBar().getValue());
		inp.draw();
	}

	public static void waitButton() {
		while (System.currentTimeMillis() < endWaitTime && !ready) {
			// isConditionMet = condition();
			if (ready || end) {
				break;
			} else {
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {}
			}
		}
		ready = false;
	}
}