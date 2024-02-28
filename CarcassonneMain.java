package carcassonne;

import javax.swing.*;

public class CarcassonneMain {
	// Frame initialization
	static JFrame f = new JFrame();
	static Input inp = new Input();
	static boolean ready = false;
	static long endWaitTime = System.currentTimeMillis() + 500*1000;

	public static void main(String[] args) {
		Board board = new Board(2);
		f.setSize(500, 133);
		GUI.gui(board, f, 0, 0);
		drawGUI(board);
		inp.draw();

		board.gameTime(board);

	}

	public static void drawGUI (Board board){
		f.getContentPane().removeAll(); //or remove(JComponent)
		f.repaint();
		GUI.gui(board, f, GUI.sp.getHorizontalScrollBar().getValue(), GUI.sp.getVerticalScrollBar().getValue());
	}

	public static void waitButton() {
		while (System.currentTimeMillis() < endWaitTime && !ready) {
			// isConditionMet = condition();
			if (ready) {
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